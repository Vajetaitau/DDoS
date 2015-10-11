package ufc.core.service.firstLayer.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufc.core.exceptions.GeneralException;
import ufc.core.service.firstLayer.DDOSService;
import ufc.core.service.secondLayer.DDOSServiceL2;
import ufc.dto.ddos.GroupedSourceIpsDetails;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.FileReader;
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
    public List<GroupedSourceIpsDetails> getGroupedSourceIps(Integer threshold, Integer limit, String order) throws GeneralException {
        return ddosServiceL2.getGroupedSourceIpDetails(threshold, limit, order);
    }

}
