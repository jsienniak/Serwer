package pl.zpi.server.db;

import java.sql.Connection;
import java.util.Vector;

public interface DatabaseObj {
	/**
	 * Odczyt danych z bazy danych. Jeżeli obiektu nie ma w bazie danych to zwraca false
	 * @return <b>true</b> - odczyt udany.<br/><b>false</b> - gdy wystąpił błąd lub obiektu nie ma w bazie danych
	 */
	public abstract boolean read(); 
	
	/**
	 * Zapis do bazy danych. 
	 * @return <b>true</b> - zapis udany.<br/><b>false</b> - gdy wystąpił błąd zapisu
	 */
	public abstract boolean write();

	/**
	 * Usunięcie obiektu z bazy danych. Jeżeli obiektu nie ma w bazie danych to zwraca false
	 * @return <b>true</b> - usuwanie udane.<br/><b>false</b> - gdy wystąpił błąd lub obiektu nie ma w bazie danych
	 */
	public abstract boolean delete();

	public abstract Vector<DatabaseObj> executeQuery();
	
	public abstract Vector<DatabaseObj> executeQuery(String condition);

	/**
	 * Wzraca unikalny ID (klucz główny) obiektu
	 * @return unikalny ID (klucz główny) obiektu
	 */
	public abstract int getId();

	/**
	 * Manipulacja polami obiektu
	 * @param columnName nazwa kolumny
	 * @param value jej wartość
	 */
	public abstract void set(String columnName, String value);
	
	/**
	 * Odczyt wartości z kolumny
	 * @param columnName nazwa kolumny z której chcemy pobrać wartość
	 * @return
	 */
	public abstract String get(String columnName);

	/**
	 * Jeżeli pole jest puste zwraca 0
	 * @see get(...) 
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