package pl.zpi.server.control.modules;

import pl.zpi.server.control.Module;

/**
 * Created with IntelliJ IDEA.
 * User: Jacek
 * Date: 19.11.12
 * Time: 19:43
 * To change this template use File | Settings | File Templates.
 */
public class RoletaModule extends Module<Integer> {

    Integer value=0;

    private static int ROLETA=0;

    @Override
    public Integer getValue(int port) {
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
        this.value=Integer.parseInt(value);
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /*@Override
    public Integer getValue(int port){
        if(!portInRange(port)){
            throw new IllegalArgumentException();
        }
        return Comm.getInstance().readAnalogOut(ROLETA);
    }

    @Override
    public boolean setValue(int port, String value) {
        if(!portInRange(port)){
            throw new IllegalArgumentException();
        }
        Comm.getInstance().writeANOut(ROLETA,Integer.parseInt(value));
        return true;
    }*/

    @Override
    public Integer[] getValues() {
        return new Integer[]{value};  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean portInRange(int port) {
        return port==0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getModuleName() {
        return "roleta";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getModuleInfo() {
        return "moduł sterujący roletą";  //To change body of implemented methods use File | Settings | File Templates.
    }
}
