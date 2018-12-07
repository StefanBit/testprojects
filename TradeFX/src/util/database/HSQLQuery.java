package util.database;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controller.TradeFXApplicationController;
import controller.TradeFXBusinessController;
import model.Symbol;
import model.TradeFXModel;
import util.log.Log;

public class HSQLQuery {
	static Boolean DEBUG=false;
	Connection con = null;
	String Databasefile;
	ResultSetMetaData m;
	ArrayList al;
	int count;

	public HSQLQuery() {
//		Databasefile= Paths.get(".").toAbsolutePath().normalize().toString()+(String) TradeFXBusinessController.getInstance().myProperties.getProperty("dbfile").toString();
		Databasefile= Paths.get(".").toAbsolutePath().normalize().toString()+(String) TradeFXModel.getInstance().getMyProperies().getProperty("dbfile").toString();

		try {
			Class.forName("org.hsqldb.jdbcDriver");
			open();
		} catch (ClassNotFoundException e) {
			System.err.println("Keine Treiber-Klasse!");
			return;
		}
	}

	public void open() {
		try {
			con = DriverManager.getConnection("jdbc:hsqldb:file:"+Databasefile+";shutdown=true", "sa","");
			Log.config("Using DB File "+Databasefile);
		} catch (SQLException e) {
			System.out.println("Mein Fehler");
			e.printStackTrace();
		}
//		finally {
//			if (con != null)
//				try {
//					con.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//		}
	}
	
	public void close(){
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList query(String q) {
		al = new ArrayList();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(q);
			if (DEBUG) System.out.println("lölö"+q);
			
			if (DEBUG) System.out.println("Result: "+rs );
			
			showMetatData(rs);
	//		System.out.print("getting Rowsets:");
			while (rs.next())
				al.add(getRowset(rs));
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			System.out.println("Mein Fehler");
			e.printStackTrace();
		} 
//		finally {
//			if (con != null)
//				try {
//					con.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
	//	}
//		System.out.println();
		if (DEBUG) System.out.println("Result has "+al.size()+" Entrys");
		return al;
	}

	public void showMetatData(ResultSet r) {
		if (DEBUG) System.out.println("Show Metadata for ");
		try {
			m = r.getMetaData();
			count = m.getColumnCount();
			if (DEBUG) System.out.println("Result " + m.getTableName(1) + " has " + count + " Columns");
			if (DEBUG) System.out.print("Columnames: ");
			for (int i = 1; i <= count; i++) {
				if (DEBUG) System.out.print(m.getColumnLabel(i) + ";");
			}
			if (DEBUG) System.out.print("; ColumnSignature: ");
			for (int i = 1; i <= count; i++) {
				if (DEBUG) System.out.print(m.getColumnTypeName(i) + ";");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public ArrayList getRowset(ResultSet r) {
		Symbol rowset = null;
		ArrayList resultList;
		resultList = new ArrayList<>();
		for (int i = 1; i <= count; i++) {
			try {
				switch (m.getColumnTypeName(i)) {

				case "VARCHAR":
					resultList.add(r.getString(i));
					break;
				case "INTEGER":
					resultList.add(r.getInt(i));
					break;
				case "DOUBLE":
					resultList.add(r.getDouble(i));
					break;
				case "DATE":
					resultList.add((java.util.Date) r.getDate(i));
					break;
				default:
					break;
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		}
		if (DEBUG) System.out.print("ResultList:" +resultList);
		return resultList;
	}
}
