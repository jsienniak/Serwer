package pl.zpi.server.db;

public class Harmonogram{

    int start;
    int end;
    int days;
    int module;
    int port;
    String value_start;
    String value_end;

    public Harmonogram(int start, int end, int days, int module, int port, String value_start, String value_end) {
        this.start=start;
        this.end=end;
        this.days=days;
        this.module=module;
        this.port=port;
        this.value_start=value_start;
        this.value_end=value_end;
    }
}
