package pl.zpi.server.control.modules;

import gnu.io.CommPortIdentifier;
import net.wimpi.modbus.net.SerialConnection;
import net.wimpi.modbus.util.SerialParameters;
import pl.zpi.server.control.Module;
import pl.zpi.server.modbus.Comm;

import java.util.Enumeration;

/**
 * Created with IntelliJ IDEA.
 * User: Jacek
 * Date: 19.11.12
 * Time: 22:09
 * To change this template use File | Settings | File Templates.
 */
public class ModbusModule extends Module<String> {
    @Override
    public String getValue(int port) {
        Enumeration<CommPortIdentifier> ports = CommPortIdentifier.getPortIdentifiers();
        String wyn = null;
        while(ports.hasMoreElements()&&wyn==null){
            CommPortIdentifier portId = ports.nextElement();
            if(portId.getPortType() == CommPortIdentifier.PORT_SERIAL){
                wyn=portId.getName();
                wyn = temp().toString();
            }
        }
        return wyn;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private synchronized Boolean temp() {
        return true;//Comm.getInstance().readIn(0);
    }


    @Override
    public boolean setValue(int port, String value) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String[] getValues() {
        return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean portInRange(int port) {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getModuleName() {
        return "modbus.test";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getModuleInfo() {
        return "testowanie modbusa";  //To change body of implemented methods use File | Settings | File Templates.
    }
}
