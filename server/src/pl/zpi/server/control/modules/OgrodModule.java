package pl.zpi.server.control.modules;

import pl.zpi.server.control.Module;

/**
 * Created with IntelliJ IDEA.
 * User: Jacek
 * Date: 19.11.12
 * Time: 22:59
 * To change this template use File | Settings | File Templates.
 */
public class OgrodModule extends Module<Boolean> {

    Boolean values[] = new Boolean[]{false,false,false};

    private static int OGROD_COMMAND =4;
    private static int OGROD_STATUS =3;
    private static int TRYB_CZUJKI =0;

    /* zapis port0 command port1 tryb czujki
    *odczyt port0 status port1 tryb czujki
     */

    //**@Override
    @Override
	public Boolean getValue(int port) {
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
        values[port]=Boolean.parseBoolean(value);
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /*@Override
    public Boolean getValue(int port) {
        if(!portInRange(port)){
            throw new IllegalArgumentException();
        }
        return (port==0?Comm.getInstance().readAnalogOut(OGROD_STATUS):Comm.getInstance().readAnalogOut(TRYB_CZUJKI))>0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean setValue(int port, String value) {
        if(!portInRange(port)){
            throw new IllegalArgumentException();
        }
        Comm.getInstance().writeOut(OGROD_COMMAND,Boolean.valueOf(value));
        return true;
    }*/

    @Override
    public Boolean[] getValues() {
        return values;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean portInRange(int port) {
        return port==0||port==1;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getModuleName() {
        return "ogrod";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getModuleInfo() {
        return "moduł sterujący ogrodem";  //To change body of implemented methods use File | Settings | File Templates.
    }
}

