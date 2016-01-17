package ufc.core.service.secondLayer;

import ufc.core.exceptions.GeneralException;
import ufc.dto.ddos.*;
import ufc.rest.request.PacketCountInTimeIntervalsRequest;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by K on 10/9/2015.
 */
public interface DDOSServiceL2 {

    void uploadFileToDatabase() throws GeneralException;

    List<GroupedIpDetails> getGroupedSourceIpDetails(Integer threshold, Integer limit, String order) throws GeneralException;

    List<GroupedIpDetails> getGroupedDestinationIpDetails(Integer threshold, Integer limit, String order) throws GeneralException;

    List<PacketCountInTimeInterval> findPacketCounts(Timestamp start, Timestamp end, Integer increment, List<PacketInfo> packetInfoList) throws GeneralException;

    void parseAttackFile() throws GeneralException;

    EntropyInformation getEntropy(Timestamp start, Timestamp end, Integer increment, Integer widowWidth);

    void scanForDDoSAttacks();
}
