package ufc.dto.ddos;

/**
 * Created by K on 10/11/2015.
 */
public class PacketCountInTimeInterval {

    private long time;
    private String ipAddress;
    private long count;
    private String series;

    public PacketCountInTimeInterval() {
    }

    public PacketCountInTimeInterval(long time, String ipAddress, long count, String series) {
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
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
