package pl.zpi.server.control.modules;

import pl.zpi.server.control.Module;

public class DummyModule extends Module<Integer> {
	Simulation s[];
	public DummyModule() {
		s = new Simulation[4];
		for(int i = 0; i < s.length; i++){
			s[i] = new Simulation();
			s[i].start();
		}
	}

	@Override
	public Integer getValue(int i) {
		return s[i].internalState;
	}

	@Override
	public boolean setValue(int port, String i) {
		if(port < 0 || port >= s.length){
			return false;
		}
		s[port].targetState = Integer.parseInt(i);
		return Integer.parseInt(i) < 100;
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
	public Integer[] getValues() {
		Integer result[] = new Integer[s.length];
		for(int i = 0; i < result.length; i++ ){
			result[i] = s[i].internalState;
		}
		return result;
	}

    @Override
    public boolean portInRange(int port) {
        return port >= 0 || port < s.length;  //To change body of implemented methods use File | Settings | File Templates.
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

	@Override
	@Deprecated
	public void destroy() {
		running = false;
		super.destroy();
	}

}
