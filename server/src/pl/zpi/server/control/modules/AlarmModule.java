package pl.zpi.server.control.modules;

import pl.zpi.server.control.Module;
import pl.zpi.server.db.DBUsers;
import pl.zpi.server.db.DatabaseObjImpl;
import pl.zpi.server.modbus.Comm;

/**
 * Created with IntelliJ IDEA.
 * User: Jacek
 * Date: 19.11.12
 * Time: 22:58
 * To change this template use File | Settings | File Templates.
 */
public class AlarmModule extends Module<Integer> {

    private static int PIN = 1234;
    Integer[] values = new Integer[]{0,0};
    private static int ALARM_UZBROJENIE_CZYT = 6;
    private static int ALARM_IN = 1;
    private static int ALARM_UZBROJ =1;


    @Override
    public Integer getValue(int port) {
        if(!portInRange(port)){
            throw new IllegalArgumentException();
        }
        Integer wyn = null;
        switch(port){
            case 0:
                wyn = Comm.getInstance().readOut(ALARM_UZBROJENIE_CZYT)?1:0;
                break;
            case 1:
                wyn = PIN;
                break;
            case 2:
                wyn = Comm.getInstance().readIn(ALARM_IN)?1:0;
                break;
        }
        return wyn;  //To change body of implemented methods use File | Settings | File Templates.
    }



    @Override
    public boolean setValue(int port, String value) {
        if(!portInRange(port)){
            throw new IllegalArgumentException();
        }
        switch(port){
            case 0:
                Comm.getInstance().writeANOut(ALARM_UZBROJ,Integer.parseInt(value));
                return true;
            case 1:
                PIN= Integer.parseInt(value);
                return true;
        }
        return false;  
    }

    /*@Override
    public Integer getValue(int port) {
        if(!portInRange(port)){
            throw new IllegalArgumentException();
        }
        return values[port];
    }

    @Override
    public boolean setValue(int port, String value) {
        if(!portInRange(port)){
            throw new IllegalArgumentException();
        }
        values[port]=Integer.parseInt(value);
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }*/

    @Override
    public Integer[] getValues() {
        return values;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean portInRange(int port) {
        return port==0||port==1||port==2;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getModuleName() {
        return "alarm";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getModuleInfo() {
        return "moduł sterujący alarmem";  //To change body of implemented methods use File | Settings | File Templates.
    }
}
