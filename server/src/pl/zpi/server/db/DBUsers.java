package pl.zpi.server.db;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class DBUsers extends DatabaseObjImpl {

	public static boolean debug = true;
	protected static String[] columnNames = null;
	private static final Logger logger = Logger.getLogger(DBUsers.class.getName());
	
	public DBUsers(int ID) {
		this();
		this.objectID = ID;
		if (columnNames == null) {
		}
	}

	public void config(){
		this.primaryKey = "id_users";
		this.tableName = "users";
	}
	public DBUsers() {
		super();
		this.primaryKey = "id_users";
		this.tableName = "users";
		data = new HashMap<String, String>();
	}

	public DBUsers(Map data) {
		this();
		this.data = data;
	}
	
	@Override
	protected synchronized void getColumnNames(){
		super.getColumnNames();
	}

}
