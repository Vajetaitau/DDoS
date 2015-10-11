package ufc.persistence.repository;

import ufc.dto.ddos.GroupedSourceIpsDetails;

import java.util.List;

public interface PacketDaoCustom {

    public List<GroupedSourceIpsDetails> findGroupedSourceIps(Integer threshold, Integer limit, String order);

}
