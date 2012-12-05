package pl.zpi.server.control;

import java.util.Calendar;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import pl.zpi.server.control.events.ModuleGet;
import pl.zpi.server.db.DBHarmonogramy;
import pl.zpi.server.db.DatabaseObjImpl;

//TODO sprawdzić, czy ten obiekt nie natworzy więcej SchedulerExecutorService
public class Scheduler {
	Logger logger = Logger.getLogger(Scheduler.class.getName());
	/**
	 * Tutaj lepiej zastosować pule wątków, gdyż ten executor musi odpalać sam
	 * siebie
	 */
	private static ScheduledExecutorService ses;
	private static ScheduledExecutorService ses2;
	private SchedulerThread schedulerThread;
	private static Scheduler _instance;
	private Scheduler() {
		if (ses == null) {
			ses2 = Executors.newScheduledThreadPool(250);
			ses = Executors.newSingleThreadScheduledExecutor();
			// ses.scheduleAtFixedRate(new SchedulerThread(ses2), 0, 24,
			// TimeUnit.HOURS);
			schedulerThread = new SchedulerThread(ses2);
			ses.submit(schedulerThread);
			// readDB();
		}
	}
	public static Scheduler getInstance(){
		if(_instance == null){
			_instance = new Scheduler();
		}
		return _instance;
	}
	/**
	 * Funkcja sprawdza, czy dany obiekt harmonogramu jest do wykonania dzisiaj,
	 * jeżeli tak to umieszcza go w póli do wykonania
	 * @param sch
	 */
	public void checkSchedule(DBHarmonogramy sch){
		schedulerThread.checkSchedule(sch);
	}
	/**
	 * Dodanie harmonogramu do schedulera. Funkcja sprawdza czy dana akcja
	 * przewidziana jest na dzień dzisiejszy. Jeżeli nie jest to nie zostaje
	 * dodana do wykonania. (Zrobi to później SchedulerThread)
	 * 
	 * @param h
	 *            obiekt harmonogramu do dodania
	 */
	public void addNewSchedule(DBHarmonogramy h) {

	}

}

/**
 * Wątek, który uruchamia się raz dziennie. o godzinie 23:58. Zbiera on
 * wszystkie zaplanowane zadania na następny dzień i ustawia je w schedulerze
 * Później harmonogramem zajmuje się WorkerThread.
 * 
 * @author mm
 * 
 */
class SchedulerThread implements Runnable {
	Logger logger = Logger.getLogger(SchedulerThread.class.getName());
	private ScheduledExecutorService ses;
	
	public SchedulerThread(ScheduledExecutorService ses) {
		logger.info("SchedulerThread created");
		this.ses = ses;
		
	}

	@Override
	public void run() {
		readDB();
	}
	public synchronized void checkSchedule(DBHarmonogramy har){
		logger.info("Checking new schedule");
		Calendar c = Calendar.getInstance();
		int currentDayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		String days = har.get("dni");
		
		//jest git, dzień pasuje
		if(days.indexOf(String.valueOf(currentDayOfWeek - 1)) >= 0){
			logger.info("New Schedule is for today");
			String s_time = har.getStringNotNull(DBHarmonogramy.g_start);
			int hour = Integer.parseInt(s_time.substring(0, 2));
			int hourDiff = hour - c.get(Calendar.HOUR_OF_DAY);
			if (hourDiff >= 0) {
				int minute = Integer.parseInt(s_time.substring(3, 5));
				int minuteDiff = minute - c.get(Calendar.MINUTE);

				int delayInSec = hourDiff * 3600 + minuteDiff * 60;

				int port = har.getInt(DBHarmonogramy.port);
				int val = har.getInt(DBHarmonogramy.w_start);
				int module = har.getInt(DBHarmonogramy.module);
				
				if(delayInSec > 0){
				logger.info("Scheduled IN in "+delayInSec+" sec");
				
					ses.schedule(new WorkerThread(module, port, val), delayInSec, TimeUnit.SECONDS);
	
					String e_time = har.getStringNotNull(DBHarmonogramy.g_stop);
					hour = Integer.parseInt(e_time.substring(0, 2));
					hourDiff = hour - c.get(Calendar.HOUR_OF_DAY);
	
					minute = Integer.parseInt(e_time.substring(3, 5));
					minuteDiff = minute - c.get(Calendar.MINUTE);
	
					delayInSec = hourDiff * 3600 + minuteDiff * 60;
	
					port = har.getInt(DBHarmonogramy.port);
					val = har.getInt(DBHarmonogramy.w_end);
					module = har.getInt(DBHarmonogramy.module);
	
					logger.info("Scheduled END in "+delayInSec+" sec");
					ses.schedule(new WorkerThread(module, port, val), delayInSec, TimeUnit.SECONDS);
				}
			}
		}
	}
	/**
	 * Metoda wyciąga z bazy danych wszystkie harmonogramy na aktualny dzien
	 * tygodnia i umieszcza je w kolejne do wykonania.
	 */
	private synchronized void readDB() {
		logger.info("SchedulerThread:run()");
		Calendar c = Calendar.getInstance();

		// int currentHour = c.get(Calendar.);
		int currentDayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		DBHarmonogramy h = new DBHarmonogramy();
		Vector v = h.executeQuery(" dni like '%" + String.valueOf(currentDayOfWeek - 1) + "%'");
		logger.info("Got " + v.size() + " schedules");
		DatabaseObjImpl o;
		for (Object dboi : v) {
			logger.info("Scheduling event");
			o = (DatabaseObjImpl) dboi;
			String s_time = o.getStringNotNull(DBHarmonogramy.g_start);
			int hour = Integer.parseInt(s_time.substring(0, 2));
			int hourDiff = hour - c.get(Calendar.HOUR_OF_DAY);
			if (hourDiff >= 0) {
				int minute = Integer.parseInt(s_time.substring(3, 5));
				int minuteDiff = minute - c.get(Calendar.MINUTE);

				int delayInSec = hourDiff * 3600 + minuteDiff * 60;

				int port = o.getInt(DBHarmonogramy.port);
				int val = o.getInt(DBHarmonogramy.w_start);
				int module = o.getInt(DBHarmonogramy.module);
				
				if(delayInSec > 0){
				logger.info("Scheduled IN in "+delayInSec+" sec");
				
					ses.schedule(new WorkerThread(module, port, val), delayInSec, TimeUnit.SECONDS);
	
					String e_time = o.getStringNotNull(DBHarmonogramy.g_stop);
					hour = Integer.parseInt(e_time.substring(0, 2));
					hourDiff = hour - c.get(Calendar.HOUR_OF_DAY);
	
					minute = Integer.parseInt(e_time.substring(3, 5));
					minuteDiff = minute - c.get(Calendar.MINUTE);
	
					delayInSec = hourDiff * 3600 + minuteDiff * 60;
	
					port = o.getInt(DBHarmonogramy.port);
					val = o.getInt(DBHarmonogramy.w_end);
					module = o.getInt(DBHarmonogramy.module);
	
					logger.info("Scheduled END in "+delayInSec+" sec");
					ses.schedule(new WorkerThread(module, port, val), delayInSec, TimeUnit.SECONDS);
				}
			}
		}
	}
}

/**
 * Wątek uruchamiany przy wyzwoleniu zaplanowanego zadania wykonuje żądane
 * zadanie.
 * 
 * @author mm
 * 
 */
class WorkerThread implements Runnable {
	Logger logger = Logger.getLogger(WorkerThread.class.getName());
	int module;
	int port;
	int value;

	/**
	 * Każdy wątek musi mieć dowiązany obiekt harmonogramu blokujemy więc
	 * tworzenie 'pustego' obiektu
	 */
	private WorkerThread() {

	}

	public WorkerThread(int module, int port, int value) {
		logger.info(String.format("WorkerThread added: m = %d, p = &d, v = %d", module, port, value));
		this.port = port;
		this.module = module;
		this.value = value;
	}

	@Override
	public void run() {
		logger.info(String.format("Schedule runnnn:  m = %d p = %d v = %d", module, port, value));
		// FIXME lipa
		List<Module> modules = ((ModuleGet) EventManager.getInstance().getByName("module.get")).getModules();
		boolean result = modules.get(module).setValue(port, String.valueOf(value));
		logger.info("Schedule result: " + result);
	}
}
