package ufc.dto.ddos;

import java.sql.Timestamp;

/**
 * Created by K on 11/28/2015.
 */
public class EntropyInTimeInterval {
    Timestamp time;
    long numberInPacketCountDomain;
    Double entropy;
    String destination;

    public long getNumberInPacketCountDomain() {
        return numberInPacketCountDomain;
    }

    public void setNumberInPacketCountDomain(long numberInPacketCountDomain) {
        this.numberInPacketCountDomain = numberInPacketCountDomain;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Double getEntropy() {
        return entropy;
    }

    public void setEntropy(Double entropy) {
        this.entropy = entropy;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
