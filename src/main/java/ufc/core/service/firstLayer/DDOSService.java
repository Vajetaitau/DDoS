package ufc.core.service.firstLayer;

import ufc.core.exceptions.GeneralException;
import ufc.dto.ddos.GroupedSourceIpsDetails;

import java.util.List;

public interface DDOSService {

    public String getLine() throws GeneralException;
    public void uploadFileToDatabase() throws GeneralException;
    public List<GroupedSourceIpsDetails> getGroupedSourceIps(Integer threshold, Integer limit, String order) throws GeneralException;

}


