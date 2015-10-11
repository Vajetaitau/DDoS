package ufc.rest.response.ddos;

import java.util.ArrayList;
import java.util.List;

public class DDOSGetGroupedSourceIpsResponse {

    private List<GroupedSorceIps> list = new ArrayList<GroupedSorceIps>();

    public List<GroupedSorceIps> getList() {
        return list;
    }

    public void setList(List<GroupedSorceIps> list) {
        this.list = list;
    }

    public class GroupedSorceIps {
        private String ip;
        private Integer count;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }

}
