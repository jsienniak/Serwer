package pl.zpi.server.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
	
	private String filePath = "";
	private static Config _instance;
	private static Properties p;
	private  String workingDir;
	public  void setWorkingDir(String dir){
		workingDir = dir;
		filePath = dir +  "config/server.properties";
		System.out.println("Config: new config dir is: "+filePath);
		Config.getConf().readConfig();
	}
	public String getWorkingDirectory(){
		return workingDir;
	}
	private void readConfig() {
		
		p = new Properties();
		File f = new File(filePath);
		try {
			FileInputStream in = new FileInputStream(f);
			p.load(in);
			in.close();
		} catch (IOException e) {
			if (e instanceof FileNotFoundException) {
				System.out.println("Config: Nie znaleziono pliku");
			} else {
				System.out.println("IOException");
			}
		}
	}
	
	public void storeConfig() {

		File f = new File(filePath);
		try {
			FileOutputStream stream = new FileOutputStream(f);
			p.store(stream, "test prop");
			stream.close();

		} catch (IOException e) {
			if (e instanceof FileNotFoundException) {
				System.out.println("Nie znaleziono pliku");
			} else {
				System.out.println("IOException");
			}
		}

	}
	
	public String getNotNull(String name){
		String result = p.getProperty(name);
		return result == null ? "" : result;
	}
	
	public String get(String name){
		return p.getProperty(name);
	}
	
	public void set(String key, String value){
		p.setProperty(key, value);
		storeConfig();
	}
	private Config() {
		System.out.println("Loading config\nConfig dir is: "+filePath);
		readConfig();
	}

	public static Config getConf() {
		if (_instance == null) {
			_instance = new Config();
		}
		return _instance;
	}
}
