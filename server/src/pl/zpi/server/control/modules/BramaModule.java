package pl.zpi.server.control.modules;

import pl.zpi.server.control.Module;
import pl.zpi.server.modbus.Comm;

/**
 * Created with IntelliJ IDEA.
 * User: Jacek
 * Date: 19.11.12
 * Time: 19:55
 * To change this template use File | Settings | File Templates.
 */
public class BramaModule extends Module<Boolean> {


    Boolean value = false;

    private static int BRAMA_STATUS = 4;
    private static int BRAMA_COMMAND = 4;


    @Override
    public Boolean getValue(int port) {
        if(!portInRange(port)){
            throw new IllegalArgumentException();
        }
        return Comm.getInstance().readIn(BRAMA_STATUS);  //To change body of implemented methods use File | Settings | File Templates.
    }



    @Override
    public boolean setValue(int port, String value) {
        if(!portInRange(port)){
            throw new IllegalArgumentException();
        }
        Comm.getInstance().writeANOut(BRAMA_COMMAND,Boolean.valueOf(value)?1:0);
        return true;
    }

    /*@Override
    public Boolean getValue(int port) {
        if(!portInRange(port)){
            throw new IllegalArgumentException();
        }
        return value;  //To change body of implemented methods use File | Settings | File Templates.
    }
    @Override
    public boolean setValue(int port, String value) {
        if(!portInRange(port)){
            throw new IllegalArgumentException();
        }
        this.value=Boolean.parseBoolean(value);
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }*/

    @Override
    public Boolean[] getValues() {
        return new Boolean[]{value};  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean portInRange(int port) {
        return port==0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getModuleName() {
        return "brama";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getModuleInfo() {
        return "moduł sterujący bramą";  //To change body of implemented methods use File | Settings | File Templates.
    }
}
