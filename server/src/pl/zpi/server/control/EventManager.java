package pl.zpi.server.control;

import java.util.HashMap;
import java.util.Map;

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
