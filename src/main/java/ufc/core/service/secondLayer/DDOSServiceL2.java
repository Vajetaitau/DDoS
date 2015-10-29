package ufc.core.service.secondLayer;

import ufc.core.exceptions.GeneralException;
import ufc.dto.ddos.GroupedIpDetails;
import ufc.dto.ddos.PacketCountInTimeInterval;
import ufc.dto.ddos.PacketInfo;
import ufc.rest.request.PacketCountInTimeIntervalsRequest;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by K on 10/9/2015.
 */
public interface DDOSServiceL2 {

    public void uploadFileToDatabase() throws GeneralException;
    public List<GroupedIpDetails> getGroupedSourceIpDetails(Integer threshold, Integer limit, String order) throws GeneralException;
    public List<GroupedIpDetails> getGroupedDestinationIpDetails(Integer threshold, Integer limit, String order) throws GeneralException;
    public List<PacketCountInTimeInterval> getPacketCountInTimeIntervals(Long multiplier, Long dividor, String sourceIp, Integer firstResult, Integer maxResults) throws GeneralException;
    public List<PacketCountInTimeInterval> findPacketCounts(Timestamp start, Timestamp end, Integer increment, List<PacketInfo> packetInfoList) throws GeneralException;

}
