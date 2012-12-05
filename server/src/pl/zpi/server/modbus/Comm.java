package pl.zpi.server.modbus;

import net.wimpi.modbus.ModbusCoupler;
import net.wimpi.modbus.io.ModbusSerialTransaction;
import net.wimpi.modbus.msg.ModbusRequest;
import net.wimpi.modbus.msg.ReadCoilsRequest;
import net.wimpi.modbus.msg.ReadCoilsResponse;
import net.wimpi.modbus.msg.ReadInputDiscretesRequest;
import net.wimpi.modbus.msg.ReadInputDiscretesResponse;
import net.wimpi.modbus.msg.ReadInputRegistersRequest;
import net.wimpi.modbus.msg.ReadInputRegistersResponse;
import net.wimpi.modbus.msg.ReadMultipleRegistersRequest;
import net.wimpi.modbus.msg.ReadMultipleRegistersResponse;
import net.wimpi.modbus.msg.WriteCoilRequest;
import net.wimpi.modbus.msg.WriteSingleRegisterRequest;
import net.wimpi.modbus.net.SerialConnection;
import net.wimpi.modbus.procimg.InputRegister;
import net.wimpi.modbus.procimg.Register;
import net.wimpi.modbus.procimg.SimpleRegister;
import net.wimpi.modbus.util.BitVector;
import net.wimpi.modbus.util.SerialParameters;

/**
 * Created with IBlueJ
 * User: Jacek
 * Date: 16.11.12
 * Time: 19:16
 * To change this template use File | Settings | File Templates.
 */
public class Comm {

    private static int OUTCOUNT = 16;
    private static int INCOUNT = 16;
    private static int ANOUTCOUNT = 16;
    private static int ANINCOUNT = 8;

    private static String PORTNAME = "COM1";
    private static int UNITID = 2;
    private static long timespan = 1000;
    private static long ANtimespan = 3000;
    private static boolean valid = false;
    private static boolean ANvalid  = false;
    private static boolean outvalid = false;
    private static boolean ANoutvalid  = false;

    private SerialParameters params;
    private long readOutTimestamp = 0;
    private BitVector readOutBitVector;
    private long readInTimestamp = 0;
    private BitVector readInBitVector;
    private InputRegister[] readANINRegisters;
    private long readANINTimestamp = 0;
    private Register[] readANOUTRegisters;
    private long readANOUTTimestamp = 0;


    private static Comm INSTANCE =null;

    public synchronized static Comm getInstance(){
        if(INSTANCE==null) {
            INSTANCE = new Comm();
        }
        return INSTANCE;
    }

    private Comm() {
        params = new SerialParameters();
        params.setPortName(PORTNAME);
        params.setBaudRate(9600);
        params.setDatabits(8);
        params.setParity(0);
        params.setStopbits(1);
        params.setEncoding("RTU");
        params.setReceiveTimeout(150);
        params.setEcho(false);
        ModbusCoupler.getReference().setUnitID(0);
    }

    public synchronized boolean readIn(int index){
        SerialConnection con = null;
        if (System.currentTimeMillis() - readInTimestamp > timespan || !valid) {
            try {
                con = new SerialConnection(params);
                con.open();
                ModbusRequest req = new ReadInputDiscretesRequest(0, INCOUNT);
                req.setUnitID(UNITID);
                req.setHeadless();
                ModbusSerialTransaction trans = new ModbusSerialTransaction(con);
                trans.setRequest(req);
                readInTimestamp = System.currentTimeMillis();
                trans.execute();
                ReadInputDiscretesResponse res = (ReadInputDiscretesResponse) trans.getResponse();
                readInBitVector = res.getDiscretes();
                valid = true;
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } finally {
                con.close();
            }
        }
        return readInBitVector.getBit(index);
    }


    public synchronized boolean readOut(int index) {
        SerialConnection con = null;
        if (System.currentTimeMillis() - readOutTimestamp > timespan ||!outvalid) {
            try {
                con = new SerialConnection(params);
                con.open();
                ModbusRequest req = new ReadCoilsRequest(0, OUTCOUNT);
                req.setUnitID(UNITID);
                req.setHeadless();
                ModbusSerialTransaction trans = new ModbusSerialTransaction(con);
                trans.setRequest(req);
                readOutTimestamp = System.currentTimeMillis();
                trans.execute();
                ReadCoilsResponse res = (ReadCoilsResponse) trans.getResponse();
                readOutBitVector = res.getCoils();
                outvalid = true;
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } finally {
                con.close();
            }
        }
        return readOutBitVector.getBit(index);
    }

    public synchronized int readAnalogIn(int index){
        SerialConnection con = null;
        if (System.currentTimeMillis() - readANINTimestamp > ANtimespan || !ANvalid) {
            try {
                con = new SerialConnection(params);
                con.open();
                ModbusRequest req = new ReadInputRegistersRequest(0,ANINCOUNT);
                req.setUnitID(UNITID);
                req.setHeadless();
                ModbusSerialTransaction trans = new ModbusSerialTransaction(con);
                trans.setRequest(req);
                readANINTimestamp = System.currentTimeMillis();
                trans.execute();
                ReadInputRegistersResponse res = (ReadInputRegistersResponse) trans.getResponse();
                readANINRegisters = res.getRegisters();
                ANvalid = true;
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } finally {
                con.close();
            }
        }
        return readANINRegisters[index].getValue();
    }


    public synchronized int readAnalogOut(int index) {
        SerialConnection con = null;
        if (System.currentTimeMillis() - readANOUTTimestamp > ANtimespan || !ANoutvalid) {
            try {
                con = new SerialConnection(params);
                con.open();
                ModbusRequest req = new ReadMultipleRegistersRequest(0,ANOUTCOUNT);
                req.setUnitID(UNITID);
                req.setHeadless();
                ModbusSerialTransaction trans = new ModbusSerialTransaction(con);
                trans.setRequest(req);
                readANOUTTimestamp = System.currentTimeMillis();
                trans.execute();
                ReadMultipleRegistersResponse res = (ReadMultipleRegistersResponse) trans.getResponse();
                readANOUTRegisters = res.getRegisters();
                ANoutvalid = true;
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } finally {
                con.close();
            }
        }
        return readANOUTRegisters[index].getValue();
    }

    public synchronized void writeOut(int index, boolean value) {
        SerialConnection con = null;
        try {
            con = new SerialConnection(params);
            con.open();
            ModbusRequest req = new WriteCoilRequest(index,value);
            req.setUnitID(UNITID);
            req.setHeadless();
            ModbusSerialTransaction trans = new ModbusSerialTransaction(con);
            trans.setRequest(req);
            trans.execute();
            outvalid = false;
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            con.close();
        }
    }

    public synchronized void writeANOut(int index, int value){
        SerialConnection con = null;
        try {
            con = new SerialConnection(params);
            con.open();
            ModbusRequest req = new WriteSingleRegisterRequest(index,new SimpleRegister(value));
            req.setUnitID(UNITID);
            req.setHeadless();
            ModbusSerialTransaction trans = new ModbusSerialTransaction(con);
            trans.setRequest(req);
            trans.execute();
            ANoutvalid = false;
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            con.close();
        }
    }


}
