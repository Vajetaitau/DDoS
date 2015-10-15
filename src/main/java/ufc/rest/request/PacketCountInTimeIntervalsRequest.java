package ufc.rest.request;

import ufc.dto.ddos.PacketInfo;

import java.util.List;

/**
 * Created by K on 10/13/2015.
 */
public class PacketCountInTimeIntervalsRequest {

    private List<PacketInfo> list;
    private Integer start;
    private Integer end;
    private Integer increment;

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public Integer getIncrement() {
        return increment;
    }

    public void setIncrement(Integer increment) {
        this.increment = increment;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public List<PacketInfo> getList() {
        return list;
    }

    public void setList(List<PacketInfo> list) {
        this.list = list;
    }
}
