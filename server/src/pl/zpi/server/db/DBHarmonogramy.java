package pl.zpi.server.db;

import java.util.logging.Logger;




public class DBHarmonogramy extends DatabaseObjImpl {
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

