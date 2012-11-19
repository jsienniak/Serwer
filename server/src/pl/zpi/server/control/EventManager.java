package pl.zpi.server.control;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import pl.zpi.server.utils.Config;

public class EventManager {
	
	private static EventManager _instance;
	private static Map<String, Event> eventMapping;
	private EventManager(){
		eventMapping = new HashMap<String, Event>();
	}
	
	public static EventManager getInstance(){
		if(_instance == null){ 
			_instance = new EventManager();
		}
		return _instance;
	}
	public static void autoloadEvents(){
		System.out.println("Starting autoload events");
		File eventDir = new File(Config.getConf().getWorkingDirectory()+"/"+Config.getConf().getNotNull("EVENTS_PATH"));
		System.out.println("Event dir is: "+eventDir);
		if(eventDir.exists() && eventDir.isDirectory()){
			File files[] = eventDir.listFiles();
			for(File f:files){
				String fileName = f.getName();
				System.out.println("File name is: "+fileName);
				
				if(!".".equals(fileName) && !"..".equals(fileName)){
					String className = fileName.substring(0, fileName.indexOf("class") - 1);
					className = Config.getConf().getNotNull("EVENTS_PACKAGE") + className;
					System.out.println("Loading event class: "+className);
					try {
						Class c = Class.forName(className);
						Event e = (Event) c.newInstance();
						EventManager.getInstance().registerEvent(e);
					} catch (ClassNotFoundException e) {
						
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {

						e.printStackTrace();
					}
				}
			}
		}
	}
	public synchronized void registerEvent(Event e){
		if(!eventMapping.containsKey(e)){
			eventMapping.put(e.getName(), e);
		}
	}
	
	public synchronized void unregisterEvent(String eventName){		
		if(eventMapping.containsKey(eventName)){
			eventMapping.remove(eventName);	
		}
	}
	
	public synchronized void unregisterEvent(Event ev){
		unregisterEvent(ev.getName());
	}
	
	//TODO gdy null wyrzucic no such event Event
	public Event getByName(String eventName){
		Event result = eventMapping.get(eventName);
		return result;
	}
}
