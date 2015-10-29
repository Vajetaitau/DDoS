package ufc.core.service.secondLayer.impl;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;
import ufc.core.exceptions.GeneralException;
import ufc.core.service.secondLayer.DDOSServiceL2;
import ufc.dto.ddos.GroupedIpDetails;
import ufc.dto.ddos.PacketCountInTimeInterval;
import ufc.dto.ddos.PacketInfo;
import ufc.persistence.entity.Packet;
import ufc.persistence.repository.PacketDao;
import ufc.rest.request.PacketCountInTimeIntervalsRequest;
import ufc.utils.FileUploader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by K on 10/9/2015.
 */
@Service("ddosServiceL2")
public class DDOSServiceL2Impl implements DDOSServiceL2 {

    private static final String pathToFile1 = "C:\\Users\\K\\Desktop\\TrainingData\\w2\\1d-inside.csv";
    private static final String pathToFile2 = "C:\\Users\\K\\Desktop\\TrainingData\\w2\\1d-outside.csv";

    @Autowired private PacketDao packetDao;
    @Autowired private FileUploader fileUploader;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Override
    public void uploadFileToDatabase() throws GeneralException {
        String[] files = {pathToFile1, pathToFile2};

        for (int i = 0; i < files.length; i++) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(files[i]));
                String line;
                while ((line = br.readLine()) != null) {
                    Packet packet = getPacket(line, i);
                    packetDao.save(packet);
                }
            } catch (Exception e) {
                throw new GeneralException("Could not upload file to the database!", e);
            }
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
    public List<PacketCountInTimeInterval> findPacketCounts(Timestamp start, Timestamp end, Integer increment, List<PacketInfo> packetInfoList) throws GeneralException {
        return packetDao.findPacketCounts(start, end, increment, packetInfoList);
    }

    private Packet getPacket(String line, int fileIndex) throws GeneralException {
        String[] cols = line.split("\\\",\\\"");
        cols[0] = cols[0].substring(1);
        cols[cols.length - 1] = cols[cols.length - 1].substring(0, cols[cols.length - 1].length() - 1);

        Packet packet = new Packet();
        packet.setNumber(Integer.valueOf(cols[0]));

        try {
            String time = cols[1];
            String[] parts = time.split("\\.");
            Date parsedDate = simpleDateFormat.parse(parts[0]);
            Timestamp timestamp = new Timestamp(parsedDate.getTime());
            timestamp.setNanos(Integer.parseInt(parts[1]) * 1000);
            packet.setTimestamp(timestamp);
        } catch (Exception e) {
            throw new GeneralException("Could", e);
        }


        packet.setSource(cols[2]);
        packet.setDestination(cols[3]);
        packet.setProtocol(cols[4]);
        packet.setLength(Integer.valueOf(cols[5]));
        packet.setInfo(cols[6]);
        packet.setFileName(String.valueOf(fileIndex));

        return packet;
    }

}
