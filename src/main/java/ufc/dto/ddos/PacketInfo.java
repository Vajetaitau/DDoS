package ufc.dto.ddos;

/**
 * Created by K on 10/13/2015.
 */
public class PacketInfo {

    private String source;
    private String destination;
    private boolean returnSource;

    public boolean isReturnSource() {
        return returnSource;
    }

    public void setReturnSource(boolean returnSource) {
        this.returnSource = returnSource;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
