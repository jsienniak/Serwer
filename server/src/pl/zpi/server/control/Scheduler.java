package pl.zpi.server.control;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import pl.zpi.server.db.DBHarmonogramy;
//TODO sprawdzić, czy ten obiekt nie natworzy więcej SchedulerExecutorService
public class Scheduler {
	/**
	 * Tutaj lepiej zastosować pule wątków, gdyż ten executor 
	 * musi odpalać sam siebie
	 */
	private static ScheduledExecutorService ses;
	public Scheduler(){
		if(ses == null){
			ses = Executors.newSingleThreadScheduledExecutor();
		}
	}
	/**
	 * Dodanie harmonogramu do schedulera. Funkcja sprawdza czy dana
	 * akcja przewidziana jest na dzień dzisiejszy. Jeżeli nie jest
	 * to nie zostaje dodana do wykonania. (Zrobi to później SchedulerThread)
	 * @param h obiekt harmonogramu do dodania
	 */
	public void addNewSchedule(DBHarmonogramy h){
		
	}
	public static void main(String args[]){
		
		//		"SELECT * FROM harmonogramy WHERE days LIKE %currentDayOfWeek% "
	}
	
}
/**
 * Wątek, który uruchamia się raz dziennie. o godzinie 23:58. Zbiera on
 * wszystkie zaplanowane zadania na następny dzień i ustawia je w schedulerze
 * Później harmonogramem zajmuje się WorkerThread.
 * @author mm
 *
 */
class SchedulerThread implements Runnable{

	@Override
	public void run() {
		Calendar c = Calendar.getInstance();
		Date d = new Date();
		int currentHour = 0;
		//int currentHour = c.get(Calendar.);
		int currentDayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		String months[] = new String[]{"ND", "PN","WT","ŚR", "CZ", "PT", "SB", "ND"};
		System.out.println(currentDayOfWeek - 1);
		DBHarmonogramy h = new DBHarmonogramy();
		Vector startVector = h.executeQuery(" WHERE id_harmonogramy like %"+ String.valueOf(currentDayOfWeek - 1) +"% AND g_start = "+currentHour+":00");
		Vector endVector = h.executeQuery(" WHERE id_harmonogramy like %"+ String.valueOf(currentDayOfWeek - 1) +"% AND g_stop = "+currentHour+":00");

		
	}
}
/**
 * Wątek uruchamiany przy wyzwoleniu zaplanowanego zadania
 * wykonuje żądane zadanie.
 * @author mm
 *
 */
class WorkerThread implements Runnable{
	DBHarmonogramy task = null;
	
	/**
	 * Każdy wątek musi mieć dowiązany obiekt harmonogramu
	 * blokujemy więc tworzenie 'pustego' obiektu
	 */
	private WorkerThread(){
		
	}
	
	public WorkerThread(DBHarmonogramy task){
		this.task = task;
	}
	@Override
	public void run(){
		
	}
}
