package ufc.dto.ddos;

/**
 * Created by K on 10/13/2015.
 */
public class PacketInfo {

    private String ip;
    private Boolean isSource;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Boolean getIsSource() {
        return isSource;
    }

    public void setIsSource(Boolean isSource) {
        this.isSource = isSource;
    }
}
