package ufc.persistence.repository;

import ufc.dto.ddos.GroupedIpDetails;
import ufc.dto.ddos.PacketCountInTimeInterval;
import ufc.dto.ddos.PacketInfo;
import ufc.rest.request.PacketCountInTimeIntervalsRequest;

import java.util.List;

public interface PacketDaoCustom {

    public List<GroupedIpDetails> findGroupedSourceIps(Integer threshold, Integer limit, String order);
    public List<GroupedIpDetails> findGroupedDestinationIps(Integer threshold, Integer limit, String order);
    public List<PacketCountInTimeInterval> findPacketCountInTimeIntervals(Long multiplier, Long dividor, String sourceIp, Integer firstResult, Integer maxResults);
    public List<PacketCountInTimeInterval> findPacketCounts(Integer start, Integer end, Integer increment, List<PacketInfo> packetInfoList);

}
