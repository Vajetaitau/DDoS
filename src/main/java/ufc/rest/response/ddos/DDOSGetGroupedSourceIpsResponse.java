package ufc.rest.response.ddos;

import ufc.dto.ddos.GroupedIpDetails;

import java.util.ArrayList;
import java.util.List;

public class DDOSGetGroupedSourceIpsResponse {

    private List<GroupedIpDetails> list = new ArrayList<GroupedIpDetails>();

    public List<GroupedIpDetails> getList() {
        return list;
    }

    public void setList(List<GroupedIpDetails> list) {
        this.list = list;
    }

}
