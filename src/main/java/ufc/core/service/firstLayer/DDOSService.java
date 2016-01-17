package ufc.core.service.firstLayer;

import ufc.core.exceptions.GeneralException;
import ufc.dto.ddos.*;
import ufc.rest.request.PacketCountInTimeIntervalsRequest;

import java.sql.Timestamp;
import java.util.List;

public interface DDOSService {

    String getLine() throws GeneralException;
    void uploadFileToDatabase() throws GeneralException;
    List<GroupedIpDetails> getGroupedSourceIps(Integer threshold, Integer limit, String order) throws GeneralException;
    List<GroupedIpDetails> getGroupedDestinationIps(Integer threshold, Integer limit, String order) throws GeneralException;
    List<PacketCountInTimeInterval> findPacketCounts(Timestamp start, Timestamp end, Integer increment, List<PacketInfo> packetInfoList) throws GeneralException;
    void parseAttackFile() throws GeneralException;
    EntropyInformation getEntropy(Timestamp start, Timestamp end, Integer increment, Integer widowWidth);
    void scanForDDoSAttacks();
}


