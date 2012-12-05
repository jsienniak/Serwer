package pl.zpi.server.db;

import java.util.logging.Logger;




public class DBHarmonogramy extends DatabaseObjImpl {
	public static final String g_start = "g_start";
	public static final String g_stop = "g_stop";
	public static final String w_start = "w_start";
	public static final String w_end = "w_end";
	public static final String active = "active";
	public static final String port = "p";
	public static final String module = "m_id";
	//FIXME columnNames static -> init
	private static Logger logger = Logger.getLogger(DBHarmonogramy.class.getName());
	public DBHarmonogramy(){
		super(); 
		if(columnNames == null){
			getColumnNames();
		}
	}
	@Override
	public void config(){
		this.primaryKey = "id_harmonogramy";
		this.tableName = "harmonogramy";
	}
    public DBHarmonogramy(int i) {
		super(i);
	}

	@Override
	protected synchronized void getColumnNames(){
		super.getColumnNames();
	}
}

