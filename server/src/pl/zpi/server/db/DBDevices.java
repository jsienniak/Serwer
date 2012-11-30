package pl.zpi.server.db;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class DBDevices extends DatabaseObjImpl{

	protected static String[] columnNames = null;

	private static final Logger logger = Logger.getLogger(DBDevices.class.getName());

	public DBDevices(int ID) {
		this();
		this.objectID = ID;
	}

	public DBDevices() {
		super();
		this.primaryKey = "id_device";
		this.tableName = "devices";
		data = new HashMap<String, String>();
	}

	public DBDevices(Map data) {
		this();
		this.data = data;
	}

	public DBDevices(String table, String primaryKey) {
		data = new HashMap<String, String>();
		this.primaryKey = primaryKey;
		this.tableName = table;
	}

}
