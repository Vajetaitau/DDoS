package ufc.persistence.repository;

import ufc.dto.ddos.GroupedIpDetails;
import ufc.dto.ddos.PacketCount;
import ufc.dto.ddos.PacketCountInTimeInterval;
import ufc.dto.ddos.PacketInfo;
import ufc.rest.request.PacketCountInTimeIntervalsRequest;

import java.sql.Timestamp;
import java.util.List;

public interface PacketDaoCustom {

    List<GroupedIpDetails> findGroupedSourceIps(Integer threshold, Integer limit, String order);

    List<GroupedIpDetails> findGroupedDestinationIps(Integer threshold, Integer limit, String order);

    List<PacketCountInTimeInterval> findPacketCounts(Timestamp start, Timestamp end, Integer increment, List<PacketInfo> packetInfoList);

    List<PacketCount> findPacketCounts(Timestamp start, Timestamp end, Integer increment);

    List<PacketCount> findPacketCountsBySourceInTimeDomain(Timestamp start, Timestamp end, Integer increment);

    List<PacketCount> findPacketCountsByDestinationInCountDomain(Timestamp timeStart, Timestamp timeEnd, long start, long end, Integer increment);

    List<PacketCount> findPacketCountsBySourceInCountDomain(Timestamp timeStart, Timestamp timeEnd, long start, long end, Integer increment);

    Timestamp findMatchingTime(Timestamp timeStart, Timestamp timeEnd, String filename, long numberToMatch);
}
