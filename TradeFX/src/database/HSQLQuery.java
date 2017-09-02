package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Symbol;

public class HSQLQuery {
	Connection con = null;
	ResultSetMetaData m;
	ArrayList al;
	int count;

	public HSQLQuery() {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
		} catch (ClassNotFoundException e) {
			System.err.println("Keine Treiber-Klasse!");
			return;
		}
	}

	public ArrayList query(String q) {
		al = new ArrayList();
		
		try {
			con = DriverManager.getConnection("jdbc:hsqldb:file:C:/Users/Stefan/Desktop/hsdb/new;shutdown=true", "sa",
					"");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(q);
			showMetatData(rs);
	//		System.out.print("getting Rowsets:");
			while (rs.next())
				al.add(getRowset(rs));
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
//		System.out.println();
		//System.out.println("Result has "+al.size()+" Entrys");
		return al;
	}

	public void showMetatData(ResultSet r) {

		try {
			m = r.getMetaData();
			count = m.getColumnCount();
//			System.out.println("Result " + m.getTableName(1) + " has " + count + " Columns");
//			System.out.print("Columnames: ");
			for (int i = 1; i <= count; i++) {
	//			System.out.print(m.getColumnLabel(i) + ";");
			}
//			System.out.print("; ColumnSignature: ");
			for (int i = 1; i <= count; i++) {
		//		System.out.print(m.getColumnTypeName(i) + ";");
			}
//			System.out.println();
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
					resultList.add(r.getDate(i));
					break;
				default:
					break;
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		}
	//	System.out.print(resultList);
		return resultList;
	}
}
