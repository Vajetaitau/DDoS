package ufc.dto.ddos;

import java.sql.Timestamp;

public class PacketCount {
    private String source;
    private String destination;

    //Priklauso, nuo to, kokiame intervale skaičiuojamos entropijos (ar laiko, ar pektų skaičiaus)
    private Timestamp time;
    private long numberInCountDomain;

    private long count;
    private long from;

    public PacketCount(String source, String destination, Timestamp time, long count, long from) {
        this.source = source;
        this.destination = destination;
        this.time = time;
        this.count = count;
        this.from = from;
    }

    public PacketCount(String source, String destination, long numberInCountDomain, long count, long from) {
        this.source = source;
        this.destination = destination;
        this.numberInCountDomain = numberInCountDomain;
        this.count = count;
        this.from = from;
    }

    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
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

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getNumberInCountDomain() {
        return numberInCountDomain;
    }

    public void setNumberInCountDomain(long numberInCountDomain) {
        this.numberInCountDomain = numberInCountDomain;
    }
}
