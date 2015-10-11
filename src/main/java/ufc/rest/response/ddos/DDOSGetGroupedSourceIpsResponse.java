package ufc.rest.response.ddos;

import ufc.dto.ddos.GroupedSourceIpsDetails;

import java.util.ArrayList;
import java.util.List;

public class DDOSGetGroupedSourceIpsResponse {

    private List<GroupedSourceIpsDetails> list = new ArrayList<GroupedSourceIpsDetails>();

    public List<GroupedSourceIpsDetails> getList() {
        return list;
    }

    public void setList(List<GroupedSourceIpsDetails> list) {
        this.list = list;
    }

}
