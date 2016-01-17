package ufc.core.service.firstLayer.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufc.core.exceptions.GeneralException;
import ufc.core.service.firstLayer.DDOSService;
import ufc.core.service.secondLayer.DDOSServiceL2;
import ufc.dto.ddos.*;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by K on 10/8/2015.
 */
@Service("ddosService")
@Transactional
public class DDOSServiceImpl implements DDOSService {

    private static final String pathToFile = "C:\\Users\\K\\Desktop\\testinis";
    private final DDOSServiceL2 ddosServiceL2;

    @Autowired
    public DDOSServiceImpl(DDOSServiceL2 ddosServiceL2) {
        this.ddosServiceL2 = ddosServiceL2;
    }

    @Override
    public String getLine() throws GeneralException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(pathToFile));
            String line;
            while ((line = br.readLine()) != null) {
                return line;
            }
        } catch (Exception e) {

        }
        return "xx";
    }

    @Override
    public void uploadFileToDatabase() throws GeneralException {
        ddosServiceL2.uploadFileToDatabase();
    }

    @Override
    public List<GroupedIpDetails> getGroupedSourceIps(Integer threshold, Integer limit, String order) throws GeneralException {
        return ddosServiceL2.getGroupedSourceIpDetails(threshold, limit, order);
    }

    @Override
    public List<GroupedIpDetails> getGroupedDestinationIps(Integer threshold, Integer limit, String order) throws GeneralException {
        return ddosServiceL2.getGroupedDestinationIpDetails(threshold, limit, order);
    }

    @Override
    public List<PacketCountInTimeInterval> findPacketCounts(Timestamp start, Timestamp end, Integer increment, List<PacketInfo> packetInfoList) throws GeneralException {
        return ddosServiceL2.findPacketCounts(start, end, increment, packetInfoList);
    }

    @Override
    public void parseAttackFile() throws GeneralException {
        ddosServiceL2.parseAttackFile();
    }

    @Override
    public EntropyInformation getEntropy(Timestamp start, Timestamp end, Integer increment, Integer windowWidth) {
        return ddosServiceL2.getEntropy(start, end, increment, windowWidth);
    }

    @Override
    public void scanForDDoSAttacks() {
        ddosServiceL2.scanForDDoSAttacks();
    }
}
