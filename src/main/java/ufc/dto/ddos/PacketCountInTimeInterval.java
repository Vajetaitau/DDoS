package ufc.dto.ddos;

import java.sql.Timestamp;

/**
 * Created by K on 10/11/2015.
 */
public class PacketCountInTimeInterval {

    private Timestamp time;
    private String ipAddress;
    private long count;
    private String series;
    private String file;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public PacketCountInTimeInterval() {
    }

    public PacketCountInTimeInterval(Timestamp time, String ipAddress, long count, String series) {
        this.time = time;
        this.ipAddress = ipAddress;
        this.count = count;
        this.series = series;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
