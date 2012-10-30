package pl.zpi.server.control;

public abstract class Module {

	public abstract int getValue(int port);
	public abstract boolean setValue(int port, int value);
	public abstract int[] getValues();
	
	public abstract String getModuleName();
	public abstract String getModuleInfo();
	
}
