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
	public Scheduler() {
		if (ses == null) {
			ses2 = Executors.newScheduledThreadPool(250);
			ses = Executors.newSingleThreadScheduledExecutor();
			ses.scheduleAtFixedRate(new SchedulerThread(ses2), 0, 24, TimeUnit.HOURS);
			ses.submit(new SchedulerThread(ses2));
		//	readDB(); 
		}
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

	private synchronized void readDB() {
		logger.info("SchedulerThread:run()");
		Calendar c = Calendar.getInstance();

		// int currentHour = c.get(Calendar.);
		int currentDayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		String months[] = new String[] { "ND", "PN", "WT", "ŚR", "CZ", "PT", "SB", "ND" };
		System.out.println(currentDayOfWeek - 1);
		DBHarmonogramy h = new DBHarmonogramy();
		Vector v = h.executeQuery(" dni like '%" + String.valueOf(currentDayOfWeek - 1) + "%'");
		logger.info("Got " + v.size() + " schedules");
		DatabaseObjImpl o;
		for (Object dboi : v) {
			System.out.println("NEXT");
			o = (DatabaseObjImpl) dboi;

			int port = o.getInt(DBHarmonogramy.port);
			int val = o.getInt(DBHarmonogramy.w_start);
			int module = o.getInt(DBHarmonogramy.module);

			System.out.println("Params read");
			ses.schedule(new WorkerThread(module, port, val), 2, TimeUnit.SECONDS);
		}
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
	private synchronized void readDB() {
		logger.info("SchedulerThread:run()");
		Calendar c = Calendar.getInstance();

		// int currentHour = c.get(Calendar.);
		int currentDayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		String months[] = new String[] { "ND", "PN", "WT", "ŚR", "CZ", "PT", "SB", "ND" };
		System.out.println(currentDayOfWeek - 1);
		DBHarmonogramy h = new DBHarmonogramy();
		Vector v = h.executeQuery(" dni like '%" + String.valueOf(currentDayOfWeek - 1) + "%'");
		logger.info("Got " + v.size() + " schedules");
		DatabaseObjImpl o;
		for (Object dboi : v) {
			System.out.println("NEXT");
			o = (DatabaseObjImpl) dboi;

			int port = o.getInt(DBHarmonogramy.port);
			int val = o.getInt(DBHarmonogramy.w_start);
			int module = o.getInt(DBHarmonogramy.module);

			System.out.println("Params read");
			ses.schedule(new WorkerThread(module, port, val), 2, TimeUnit.SECONDS);
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
		logger.info(String.format("Schedule applied:  m = %d p = %d v = %d", module, port, value));
		// FIXME lipa
		List<Module> modules = ((ModuleGet) EventManager.getInstance().getByName("module.get")).getModules();
		// boolean result = modules.get(module).setValue(port,
		// String.valueOf(value));
		// logger.info("Schedule result: "+result);
	}
}
