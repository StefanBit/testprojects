package database;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javafx.concurrent.Task;
import model.HistData;
import model.Symbol;

public class DAOHsqlImpl<T> {

	String tablename;
	Class<T> c;
	Map<String, Method> mapSetter = new HashMap<String, Method>();
	T t = null;
	BeanInfo info;
	HSQLQuery q;
	
	public DAOHsqlImpl(Class<T> c) {
		this.c = c;
		this.tablename = c.getSimpleName();
		buildGetterSetterMap();
		System.out.println("Building new SQL Adapter for Class " + c.getSimpleName());
		q = new HSQLQuery();
		if (!exists()) {
			create();
		}
	}

	private Boolean exists() {
		Boolean exists;
		ArrayList al;
		al = q.query(("SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = '" + tablename.toUpperCase() + "'"));
		exists = ((al.size() == 0) ? false : true);
		System.out.println("SQL Table for "+tablename + "? " + exists);
		return exists;
	}
	
	private void create() {
		System.out.println("Creating table " + tablename);
		q.query("CREATE TABLE " + tablename + "(" + getSignature() + ")");
	}

	/**
	 * 
	 * @return String
	 */
	private String getSignature() {
		String s = "";
		for (Field o : c.getDeclaredFields()) {
			System.out.println(o.getGenericType().toString());
			s += o.getName() + " ";
			switch (o.getGenericType().toString()) {
			case "class java.lang.Integer":
				s += "INTEGER, ";
				break;
			case "class java.lang.String":
				s += "VARCHAR(100), ";
				break;
			case "class java.util.Date":
				s += "DATE, ";
				break;
			case "class java.lang.Double":
				s += "DOUBLE, ";
				break;
			default:
				s += "VARCHAR(10), ";
				break;
			}
		}
		s = s.substring(0, s.length() - 2);
	    System.out.println("Class " + c.getSimpleName() + " has Signature " + s);
		return s;
	}


	/**
	 * Builds String for SQL and insert a row
	 * 
	 * @param T
	 */
	public void insert(T t) {
		BeanInfo info;
		String valueString = "";
		String columnString = "";
	//	System.out.println("Inserting..");
		SimpleDateFormat ft = new SimpleDateFormat("YYYY-MM-dd");
		try {
			info = Introspector.getBeanInfo(t.getClass());
			PropertyDescriptor[] props = info.getPropertyDescriptors();
			//System.out.println("ü"+props.length);
			for (PropertyDescriptor pd : props) {
				String name = pd.getName();
				Method getter = pd.getReadMethod();
				Class<?> type = pd.getPropertyType();

				try {
					Object value = getter.invoke(t);
					switch (value.getClass().toString()) {
					case "class java.lang.String":
						columnString += name + ", ";
						valueString += "'" + value + "', ";
						break;
					case "class java.util.Date":
						columnString += name + ", ";
						valueString += "'" + ft.format(value) + "', ";
						break;
					case "class java.lang.Integer":
						columnString += name + ", ";
						valueString += value + ", ";
						break;
					case "class java.lang.Double":
						columnString += name + ", ";
						valueString += value + ", ";
						break;
					default:
					//	System.out.println(pd.getName()+","+pd.getPropertyType());
						break;
					}
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			columnString = columnString.substring(0, columnString.length() - 2);
			valueString = valueString.substring(0, valueString.length() - 2);
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		//System.out.println(columnString+"+"+valueString);
		q.query("INSERT INTO " + tablename + "(" + columnString + ")" + " VALUES (" + valueString + ");");
	}
	
	public void insertAll(ArrayList <T> al){
		for (T t : al) {
			insert(t);
		}
	}

	/**
	 * @return ArrayList<T>
	 */
	public ArrayList<T> getAll() {
		System.out.println("getAll");
		
		T t = null;
		ArrayList al, alTable;
		al = new ArrayList<>();
		
		alTable = q.query("SELECT * FROM " + tablename);
		for (int i = 0; i < alTable.size(); i++) {
			try {
				t= c.newInstance();
			} catch (InstantiationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// Call t SetterMethods for each Column in Current row.
			ArrayList row = (ArrayList) alTable.get(i);
			for (int j = 1; j <= row.size(); j++) {
				try {
					mapSetter.get(q.m.getColumnName(j)).invoke(t, row.get(j - 1));
				} catch (SQLException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			al.add(t);
		}
		return al;
	}
	/**
	 * @return ArrayList<T>
	 */
	public ArrayList<T> getAllWhere(String where) {
		System.out.println("getAll");
		where="pk="+where;
		T t = null;
		ArrayList al, alTable;
		al = new ArrayList<>();
		String query="SELECT * FROM " + tablename+" WHERE "+where +" ORDER BY DATE ASC";
		alTable = q.query(query);
		System.out.println(query);
		
		for (int i = 0; i < alTable.size(); i++) {
			try {
				t= c.newInstance();
			} catch (InstantiationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// Call t SetterMethods for each Column in Current row.
			ArrayList row = (ArrayList) alTable.get(i);
			for (int j = 1; j <= row.size(); j++) {
				try {
					mapSetter.get(q.m.getColumnName(j)).invoke(t, row.get(j - 1));
				} catch (SQLException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			al.add(t);
		}
		System.out.println("alTable has "+alTable.size()+" Entrys");
		System.out.println("Result has "+al.size()+" Entrys");
		return al;
	}
	
	
	/**
	 * Delete all
	 */
	public void deleteAll() {
		ArrayList al;
		al = q.query("DELETE FROM " + tablename);
		for (Object object : al) {
			System.out.println(object);
		}
	}
	public void dropTable() {
		ArrayList al;
		al = q.query("DROP TABLE " + tablename);
		for (Object object : al) {
			System.out.println(object);
		}
	}
	
	
	private void buildGetterSetterMap(){
		// Build Setters Map
		try {
			t = c.newInstance();
			info = Introspector.getBeanInfo(t.getClass());
			PropertyDescriptor[] props = info.getPropertyDescriptors();
			for (PropertyDescriptor pd : props) {
				mapSetter.put(pd.getName().toUpperCase(), pd.getWriteMethod());
			}
		} catch (InstantiationException | IllegalAccessException | IntrospectionException e1) {
			e1.printStackTrace();
		}

	}

	public static void main(String[] args) {
		DAOHsqlImpl s;
		s = new DAOHsqlImpl<HistData>(HistData.class);
		//s.insert(new Symbol(0, "MSFT"));
		//s.insert(new Symbol(1, "INTL"));
		//s.dropTable();
		System.out.println(s.getAll().get(1).toString());
//		 s.deleteAll();
	}

}
