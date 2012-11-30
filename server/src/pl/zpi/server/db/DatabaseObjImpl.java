package pl.zpi.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Logger;

import java.sql.ResultSetMetaData;
/**
 * Klasa wspomagająca, ma zaimplementowane wszystkie metody potrzebne do jej obsługi
 * @author mm
 *
 */
public class  DatabaseObjImpl implements DatabaseObj {
	// TODO tego tu później nie będzie
	String url = "jdbc:mysql://localhost:3306/zpi";
	String user = "root";
	String password = "a";
	public static boolean debug = true;
	public Map<String, String> data;
	protected String columnNames[] = null;
	public String primaryKey;
	public String tableName;

	private String filter;
	protected int objectID;
	private static final Logger logger = Logger.getLogger(DatabaseObjImpl.class.getName());
	public DatabaseObjImpl(int ID) {
		this();
		this.objectID = ID;
		if (columnNames == null) {
		}
	}

	public DatabaseObjImpl() {
		config();
		if(columnNames == null){
			getColumnNames();
		}
		data = new HashMap<String, String>();
	}
	public void config(){
		throw new UnsupportedOperationException("DatabaseObjImpl nie powinna byc uzywana");
	}
	
	public DatabaseObjImpl(Map data) {
		this();
		this.data = data;
	}
	public DatabaseObjImpl(Map data, String tName, String pKey) {
		this.primaryKey = pKey;
		this.tableName = tName;
		this.data = data;
	}
	public DatabaseObjImpl(String table, String primaryKey) {
		data = new HashMap<String, String>();
		this.primaryKey = primaryKey;
		this.tableName = table;
	}

	@Override
	public boolean read() {
		Connection con = null;
		PreparedStatement statement = null;
		try {
			con = DriverManager.getConnection(url, user, password);
			ResultSet rs;
			statement = con.prepareStatement("SELECT * FROM " + tableName + " WHERE " + primaryKey + " = " + getId());
			rs = statement.executeQuery();
			// nazwy kolumn
			if (columnNames == null) {
				getColumnNames(statement);
			}
			if(data == null){
				data = new HashMap<String, String>();
			}
			if (rs.next()) {
				for (int i = 0; i < columnNames.length; i++) {
					data.put(columnNames[i], rs.getString(i + 1));
				}
				objectID = Integer.valueOf(data.get(primaryKey));
			}
			statement.close();
			return true;
		} catch (SQLException e) {
			logger.severe("#BLAD: Nie mozna odczytac obiektu");
			logger.severe(statement.toString());
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				logger.severe("#BLAD: Nie mozna zamknac polaczenia");
				e.printStackTrace();
			}
		}
	}
	protected synchronized void getColumnNames(){
		Connection con = null;
		PreparedStatement statement = null;
		try {
			con = DriverManager.getConnection(url, user, password);
			ResultSet rs;
			statement = con.prepareStatement("SELECT * FROM " + tableName +" LIMIT 1");
			rs = statement.executeQuery();
			getColumnNames(statement);
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/*protected synchronized void getColumnNames(PreparedStatement statement) throws SQLException {
		getColumnNames(statement, DatabaseObjImpl.columnNames);
	}*/
	protected synchronized void getColumnNames(PreparedStatement statement) throws SQLException {
		ResultSetMetaData rsmd = statement.getMetaData();
		columnNames = new String[rsmd.getColumnCount()];
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			columnNames[i - 1] = rsmd.getColumnName(i).toLowerCase();
		}
		if(debug){
			
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < columnNames.length; i++){
				sb.append(" "+columnNames[i]);
			}
			logger.info("Columns listed:"+sb.toString());
		}
	}

	@Override
	public boolean write() {
		Connection con = null;
		Statement st = null;
		PreparedStatement pstm = null;
		try {
			con = DriverManager.getConnection(url, user, password);
			ResultSet rs;

			if (data != null) {
				if(columnNames == null){
					PreparedStatement statement = con.prepareStatement("SELECT * FROM "+tableName + " LIMIT 1");
					getColumnNames(statement);					
				}
				StringBuilder sb = new StringBuilder();
				if (objectID > 0) {
					
					for (int i = 0; i < columnNames.length; i++) {
						String currentCol = columnNames[i];
						// pomijamy niezmienione
						if (!primaryKey.equals(currentCol) && data.containsKey(currentCol)) {
							sb.append(currentCol).append(" = ").append(singleQuote(data.get(currentCol))).append(", ");
						}
					}
					pstm = con.prepareStatement("UPDATE " + tableName + " SET " + sb.substring(0, sb.length() - 2)+" WHERE "+primaryKey+" = "+getId());
					
					pstm.executeUpdate();
					// FIXME
					con.close();
					return true;
				} else {
					// insert
					sb.append("(");
					StringBuilder columns = new StringBuilder();
					columns.append("(");
					for (int i = 0; i < columnNames.length; i++) {
						String currentCol = columnNames[i];
						// pomijamy klucz główny
						if (!primaryKey.equals(currentCol) && data.containsKey(currentCol)) {
							sb.append("'").append(data.get(currentCol)).append("'").append(",");
							columns.append(currentCol).append(",");
						}
					}

					String values = sb.substring(0, sb.length() - 1) + ")";
					String cols = columns.substring(0, columns.length() - 1) + ")";

					st = con.createStatement();
					if(debug){
						logger.info("INSERT INTO " + tableName + " " + cols + " VALUES " + values);
					}
					st.executeUpdate("INSERT INTO " + tableName + " " + cols + " VALUES " + values, Statement.RETURN_GENERATED_KEYS);

					//wyciagamy przydzielone ID
					rs = st.getGeneratedKeys();
	
					if(rs.next()){
						objectID = rs.getInt(1);
					}
					con.close();
					return true;
				}
			} else {
				return false;
			}
		} catch (SQLException e) {
			logger.severe("Blad INSERT/UPDATE");
			if(pstm != null){
				logger.severe(pstm.toString());
			}
			e.printStackTrace();
			return false;
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					logger.severe("#BLAD: Nie mozna zamknac polaczenia");
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public boolean delete() {
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DriverManager.getConnection(url, user, password);
			if (beforeDelete(con)) {
				st = con.prepareStatement("DELETE FROM " + tableName + " WHERE " + primaryKey + " = " + getId());
				int r = st.executeUpdate();

				afterDelete(con);

				con.close();
				return r > 0;

			}
		} catch (SQLException e) {
			logger.severe("BLAD: Nie mozna usunac elementu");
			logger.severe(st.toString());
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				logger.severe("BLAD: Nie mozna zamknac polaczenia");
				e.printStackTrace();
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Vector<DatabaseObj> executeQuery() {
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DriverManager.getConnection(url, user, password);

			java.sql.ResultSet rs;
			if (filter != null) {
				st = con.prepareStatement("SELECT * FROM " + tableName + " WHERE " + filter);
				rs = st.executeQuery();
			} else {
				st = con.prepareStatement("SELECT * FROM " + tableName);
				rs = st.executeQuery();
			}
			// nazwy kolumn
			if (columnNames == null) {
				getColumnNames(st);
			}

			con.close();

			Vector<DatabaseObj> result = new Vector<DatabaseObj>();
			while (rs.next()) {
				DatabaseObjImpl dbo = new DatabaseObjImpl(tableName, primaryKey);
				for (int i = 0; i < columnNames.length; i++) {
					dbo.set(columnNames[i], rs.getString(i + 1));
				}
				result.add(dbo);
				dbo = null;
			}
			return result;
		} catch (SQLException e) {
			logger.severe("BLAD. Niepoprawna skladnia");

			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				logger.severe("Nie mozna zamknac polaczenia");
				e.printStackTrace();
			}

		}
		return null;
	}

	@Override
	public int getId() {
		return objectID;
	}

	@Override
	public void set(String columnName, String value) {
		data.put(columnName.toLowerCase(), value);
	}
	public static String singleQuote(String s){
		if (!s.startsWith("'")){
			return String.format("'%s'", s);
		}
		return s;
	}
	@Override
	public String get(String columnName) {
		return data.get(columnName);
	}

	@Override
	public int getInt(String columnName) {
		int result;
		try {
			result = Integer.valueOf(data.get(columnName));
		} catch (NumberFormatException e) {
			return 0;
		}
		return result;
	}

	@Override
	public String getStringNotNull(String columnName) {
		String res = data.get(columnName);
		return res == null ? "" : res;
	}

	@Override
	public boolean beforeWrite(Connection c) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean beforeDelete(Connection c) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean afterWrite(Connection c) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean afterDelete(Connection c) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void appendCondition(String cond) {
		if (filter == null) {
			filter = cond;
		} else {
			filter = filter + " " + cond;
		}
	}

	@Override
	public void clearFilter() {
		filter = null;
	}

	@Override
	public Vector<DatabaseObj> executeQuery(String condition) {
		filter = condition;
		return executeQuery();
	}

	@Override
	public String toString() {
		if (data != null) {
			return data.toString();
		}
		return "null";
	}

}
