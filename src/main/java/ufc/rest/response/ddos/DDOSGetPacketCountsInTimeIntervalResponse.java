package ufc.rest.response.ddos;

import ufc.dto.ddos.PacketCountInTimeInterval;

import java.util.List;

/**
 * Created by K on 10/11/2015.
 */
public class DDOSGetPacketCountsInTimeIntervalResponse {

    List<PacketCountInTimeInterval> list;

    public List<PacketCountInTimeInterval> getList() {
        return list;
    }

    public void setList(List<PacketCountInTimeInterval> list) {
        this.list = list;
    }
}
