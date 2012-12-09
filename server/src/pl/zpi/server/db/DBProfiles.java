package pl.zpi.server.db;

import java.util.logging.Logger;




public class DBProfiles extends DatabaseObjImpl {
	public static final String name = "name";
	public static final String value1 = "v1";
	public static final String value2 = "v2";
	public static final String value3 = "v3";
	public static final String userId = "user_id";

	//FIXME columnNames static -> init
	private static Logger logger = Logger.getLogger(DBProfiles.class.getName());
	public DBProfiles(){
		super(); 
		if(columnNames == null){
			getColumnNames();
		}
	}
	@Override
	public void config(){
		this.primaryKey = "id_profile";
		this.tableName = "profiles";
	}
    public DBProfiles(int i) {
		super(i);
	}

	@Override
	protected synchronized void getColumnNames(){
		super.getColumnNames();
	}
}

