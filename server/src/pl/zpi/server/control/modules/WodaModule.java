package pl.zpi.server.control.modules;

import pl.zpi.server.control.Module;
import pl.zpi.server.modbus.Comm;

/**
 * Created with IntelliJ IDEA.
 * User: Jacek
 * Date: 17.11.12
 * Time: 15:33
 * To change this template use File | Settings | File Templates.
 */
public class WodaModule extends Module<Integer> {

    /* modul pozwalajacy ustawic zadana temperature wody port 0
    * odczytac zadana temperature port 0
    * i odczytac aktualna temperature port 1
     */
    private static int WODA_ZADANA =9;
    private static int WODA_AKTUALNA =4;

    Integer[] values = new Integer[]{25,25};


    /*@Override
    public Integer getValue(int port) {
        if(!portInRange(port)){
            throw new IllegalArgumentException();
        }
        return values[port];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean setValue(int port, String value) {
        if(!portInRange(port)){
            throw new IllegalArgumentException();
        }
        values[port] = Integer.parseInt(value);
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }*/

    @Override
    public Integer getValue(int port){
        if(!portInRange(port)){
            throw new IllegalArgumentException();
        }
        return port==0?Comm.getInstance().readAnalogOut(WODA_ZADANA):Comm.getInstance().readAnalogIn(WODA_AKTUALNA);
    }

    public boolean setValue(int port, String value){
        if(!portInRange(port)){
            throw new IllegalArgumentException();
        }
        Comm.getInstance().writeANOut(WODA_ZADANA,Integer.parseInt(value));
        return true;
    }

    @Override
    public Integer[] getValues() {
        return values ;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean portInRange(int port) {
        return port>=0&&port<2;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getModuleName() {
        return "woda";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getModuleInfo() {
        return "Moduł sterujący temperaturą wody.";  //To change body of implemented methods use File | Settings | File Templates.
    }
}
