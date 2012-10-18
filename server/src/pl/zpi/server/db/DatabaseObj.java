package pl.zpi.server.db;

import java.sql.Connection;
import java.util.Vector;

public interface DatabaseObj {
	
	public abstract boolean read();

	public abstract boolean write();

	public abstract boolean delete();

	public abstract Vector<DatabaseObj> executeQuery();
	
	public abstract Vector<DatabaseObj> executeQuery(String condition);

	public abstract int getId();

	public abstract void set(String columnName, String value);

	public abstract String get(String columnName);

	/**
	 * Wyciąga int. Jeżeli pole jest puste zwraca 0
	 * @param columnName
	 * @return
	 */
	public abstract int getInt(String columnName);
	/**
	 * Wyciąga string, jeżeli pole jest puste to nie zwraca null
	 * @param columnName
	 * @return
	 */
	public abstract String getStringNotNull(String columnName);
	
	public abstract boolean beforeWrite(Connection c);
	
	public abstract boolean beforeDelete(Connection c);
	
	public abstract boolean afterWrite(Connection c);
	
	public abstract boolean afterDelete(Connection c);
	
	public abstract void appendCondition(String cond);
	
	public abstract void clearFilter();
	

}