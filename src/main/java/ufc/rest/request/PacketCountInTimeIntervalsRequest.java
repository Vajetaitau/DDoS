package ufc.rest.request;

import ufc.dto.ddos.PacketInfo;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by K on 10/13/2015.
 */
public class PacketCountInTimeIntervalsRequest {

    private List<PacketInfo> list;
    private Timestamp start;
    private Timestamp end;
    private Integer increment;


    public Integer getIncrement() {
        return increment;
    }

    public void setIncrement(Integer increment) {
        this.increment = increment;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public List<PacketInfo> getList() {
        return list;
    }

    public void setList(List<PacketInfo> list) {
        this.list = list;
    }
}
