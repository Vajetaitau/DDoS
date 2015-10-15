package ufc.core.service.secondLayer.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufc.core.exceptions.GeneralException;
import ufc.core.service.secondLayer.DDOSServiceL2;
import ufc.dto.ddos.GroupedIpDetails;
import ufc.dto.ddos.PacketCountInTimeInterval;
import ufc.dto.ddos.PacketInfo;
import ufc.persistence.entity.Packet;
import ufc.persistence.repository.PacketDao;
import ufc.rest.request.PacketCountInTimeIntervalsRequest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

/**
 * Created by K on 10/9/2015.
 */
@Service("ddosServiceL2")
public class DDOSServiceL2Impl implements DDOSServiceL2 {

    private static final String pathToFile = "C:\\Users\\K\\Desktop\\testinis";
    @Autowired private PacketDao packetDao;

    @Override
    public void uploadFileToDatabase() throws GeneralException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(pathToFile));
            String line;
            while ((line = br.readLine()) != null) {
                Packet packet = getPacket(line);
                packetDao.save(packet);
            }
        } catch (Exception e) {
            throw new GeneralException("Could not upload file to the database!", e);
        }
    }

    @Override
    public List<GroupedIpDetails> getGroupedSourceIpDetails(Integer threshold, Integer limit, String order) throws GeneralException {
        return packetDao.findGroupedSourceIps(threshold, limit, order);
    }

    @Override
    public List<GroupedIpDetails> getGroupedDestinationIpDetails(Integer threshold, Integer limit, String order) throws GeneralException {
        return packetDao.findGroupedDestinationIps(threshold, limit, order);
    }

    @Override
    public List<PacketCountInTimeInterval> getPacketCountInTimeIntervals(Long multiplier, Long dividor, String sourceIp, Integer firstResult, Integer maxResults) throws GeneralException {
        return packetDao.findPacketCountInTimeIntervals(multiplier, dividor, sourceIp, firstResult, maxResults);
    }

    @Override
    public List<PacketCountInTimeInterval> findPacketCounts(Integer start, Integer end, Integer increment, List<PacketInfo> packetInfoList) throws GeneralException {
        return packetDao.findPacketCounts(start, end, increment, packetInfoList);
    }

    private Packet getPacket(String line) {
        String[] cols = line.split(",");

        Packet packet = new Packet();
        packet.setNumber(Integer.valueOf(removeQuotes(cols[0])));
        packet.setTimestamp(Double.valueOf(removeQuotes(cols[1])));
        packet.setSource(removeQuotes(cols[2]));
        packet.setDestination(removeQuotes(cols[3]));
        packet.setProtocol(removeQuotes(cols[4]));
        packet.setLength(Integer.valueOf(removeQuotes(cols[5])));
        packet.setInfo(removeQuotes(cols[6]));

        return packet;
    }

    private String removeQuotes(String withQuotes) {
        return withQuotes.substring(1, withQuotes.length() - 1);
    }

}
