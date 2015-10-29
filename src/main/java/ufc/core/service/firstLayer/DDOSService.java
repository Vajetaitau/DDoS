package ufc.core.service.firstLayer;

import ufc.core.exceptions.GeneralException;
import ufc.dto.ddos.GroupedIpDetails;
import ufc.dto.ddos.PacketCountInTimeInterval;
import ufc.dto.ddos.PacketInfo;
import ufc.rest.request.PacketCountInTimeIntervalsRequest;

import java.sql.Timestamp;
import java.util.List;

public interface DDOSService {

    public String getLine() throws GeneralException;
    public void uploadFileToDatabase() throws GeneralException;
    public List<GroupedIpDetails> getGroupedSourceIps(Integer threshold, Integer limit, String order) throws GeneralException;
    public List<GroupedIpDetails> getGroupedDestinationIps(Integer threshold, Integer limit, String order) throws GeneralException;
    public List<PacketCountInTimeInterval> getPacketCountInTimeIntervals(Long multiplier, Long dividor, String sourceIp, Integer firstResult, Integer maxResults) throws GeneralException;
    public List<PacketCountInTimeInterval> findPacketCounts(Timestamp start, Timestamp end, Integer increment, List<PacketInfo> packetInfoList) throws GeneralException;

}


