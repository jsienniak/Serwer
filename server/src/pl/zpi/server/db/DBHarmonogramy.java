package pl.zpi.server.db;

import sun.security.jca.GetInstance;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Jacek
 * Date: 22.11.12
 * Time: 00:21
 * To change this template use File | Settings | File Templates.
 */
public class DBHarmonogramy extends HashMap<Integer, Harmonogram> {

    private int KEY = 0;

    private static DBHarmonogramy INSTANCE;

    public static DBHarmonogramy getInstance(){
        if(INSTANCE==null){
            INSTANCE = new DBHarmonogramy();
        }
        return INSTANCE;
    }

    private DBHarmonogramy(){
        super();
    }

    public int add(Harmonogram h){
        super.put(KEY++,h);
        return KEY;
    }

    public Harmonogram get(int k){
        return super.get(k);
    }

    public void remove(int k){
        super.remove(k);
    }

}

