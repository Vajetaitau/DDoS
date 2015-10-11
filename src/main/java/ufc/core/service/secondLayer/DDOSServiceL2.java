package ufc.core.service.secondLayer;

import ufc.core.exceptions.GeneralException;
import ufc.dto.ddos.GroupedSourceIpsDetails;

import java.util.List;

/**
 * Created by K on 10/9/2015.
 */
public interface DDOSServiceL2 {

    public void uploadFileToDatabase() throws GeneralException;
    public List<GroupedSourceIpsDetails> getGroupedSourceIpDetails(Integer threshold, Integer limit, String order) throws GeneralException;

}
