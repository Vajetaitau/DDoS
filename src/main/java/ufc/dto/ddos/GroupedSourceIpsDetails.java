package ufc.dto.ddos;

public class GroupedSourceIpsDetails {
    private String ip;
    private Integer count;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
