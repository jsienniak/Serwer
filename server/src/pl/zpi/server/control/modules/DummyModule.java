package pl.zpi.server.control.modules;

import pl.zpi.server.control.Module;

public class DummyModule extends Module {
	Simulation s[];
	public DummyModule() {
		s = new Simulation[4];
		for(int i = 0; i < s.length; i++){
			s[i] = new Simulation();
			s[i].start();
		}
	}

	@Override
	public int getValue(int i) {
		return s[i].internalState;
	}

	@Override
	public boolean setValue(int port, int i) {
		if(port < 0 || port >= s.length){
			return false;
		}
		s[port].targetState = i;
		return i < 100;
	}

	@Override
	public String getModuleName() {
		return "Dummy changing module";
	}

	@Override
	public String getModuleInfo() {
		return "Modul uzywajacy swojego watku do symulowania sterownika";
	}

	@Override
	public int[] getValues() {
		int result[] = new int[s.length];
		for(int i = 0; i < result.length; i++ ){
			result[i] = s[i].internalState;
		}
		return result;
	}

}

class Simulation extends Thread {
	int internalState;
	int targetState = 3;
	boolean running = false;

	@Override
	public void run() {
		running = true;
		internalState = 0;
		while (running) {
			try {
				if (targetState > internalState) {
					internalState += 3;
				} else {
					internalState -= 2;
				}

				sleep(1300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
