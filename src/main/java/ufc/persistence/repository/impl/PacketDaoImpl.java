package ufc.persistence.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ufc.constants.NamedQueryNames;
import ufc.dto.ddos.GroupedSourceIpsDetails;
import ufc.persistence.repository.PacketDao;
import ufc.persistence.repository.PacketDaoCustom;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class PacketDaoImpl implements PacketDaoCustom {

    @Autowired
    EntityManager entityManager;

    @Override
    public List<GroupedSourceIpsDetails> findGroupedSourceIps(Integer threshold, Integer limit, String order) {
        Query query = entityManager.createNamedQuery(NamedQueryNames.GET_GROUPED_SOURCE_IPS);
        query.setParameter("threshold", Long.valueOf(threshold));
        query.setMaxResults(limit);
        return query.getResultList();
    }
}
