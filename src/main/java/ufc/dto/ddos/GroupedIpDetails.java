package ufc.dto.ddos;

public class GroupedIpDetails {
    private String ip;
    private long count;

    public GroupedIpDetails() {
    }

    public GroupedIpDetails(String ip, long count) {
        this.ip = ip;
        this.count = count;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
