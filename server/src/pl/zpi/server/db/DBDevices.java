package pl.zpi.server.db;


public class DBDevices extends DatabaseObjImpl{

	public DBDevices(){
		super(); 
		if(columnNames == null){
			getColumnNames();
		}
	}
	@Override
	public void config(){
		this.primaryKey = "id_device";
		this.tableName = "devices";
	}
    public DBDevices(int i) {
		super(i);
	}

	@Override
	protected synchronized void getColumnNames(){
		super.getColumnNames();
	}

}
