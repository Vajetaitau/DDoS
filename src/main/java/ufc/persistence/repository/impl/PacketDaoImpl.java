package ufc.persistence.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ufc.constants.NamedQueryNames;
import ufc.dto.ddos.GroupedIpDetails;
import ufc.dto.ddos.PacketCountInTimeInterval;
import ufc.dto.ddos.PacketInfo;
import ufc.persistence.repository.PacketDaoCustom;
import ufc.rest.request.PacketCountInTimeIntervalsRequest;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class PacketDaoImpl implements PacketDaoCustom {

    @Autowired
    EntityManager entityManager;

    @Override
    public List<GroupedIpDetails> findGroupedSourceIps(Integer threshold, Integer limit, String order) {
        Query query = entityManager.createNamedQuery(NamedQueryNames.GET_GROUPED_SOURCE_IPS);
        query.setParameter("threshold", Long.valueOf(threshold));
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public List<GroupedIpDetails> findGroupedDestinationIps(Integer threshold, Integer limit, String order) {
        Query query = entityManager.createNamedQuery(NamedQueryNames.GET_GROUPED_DESTINATION_IPS);
        query.setParameter("threshold", Long.valueOf(threshold));
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public List<PacketCountInTimeInterval> findPacketCountInTimeIntervals(Long multiplier, Long dividor, String sourceIp, Integer firstResult, Integer maxResults) {
        Query query = entityManager.createNativeQuery(
                "select cast((p.timestamp * cast(:multiplier as bigint)) as bigint) / :dividor as time, p.source, count(p.source) " +
                        "from packets as p " +
                        "where p.source like :source " +
                        "group by time, p.source " +
                        "order by time"
        );
        query.setParameter("multiplier", multiplier);
        query.setParameter("dividor", dividor);
        query.setParameter("source", sourceIp);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }

    @Override
    public List<PacketCountInTimeInterval> findPacketCounts(Integer start, Integer end, Integer increment,
                                                            List<PacketInfo> packetInfoList) {

        String[] seriesLetters = {"a", "b", "c", "d", "e", "f", "g", "h", "i"};

        StringBuilder query = new StringBuilder();
        query.append("with intervals as ( ");
        query.append("select s as start, s + ").append(increment).append(" as end ");
        query.append("from generate_series(").append(start).append(", ").append(end).append(", ").append(increment).append(") as s ");
        query.append(") ");

        query.append("select i.start, pp.ip, pp.series, count(pp.ip) ");
        query.append("from (");
        for (int i = 0; i < packetInfoList.size(); i++) {
            String column = packetInfoList.get(i).getIsSource() ? "source" : "destination";

            query.append("select p.timestamp as timestamp, p.").append(column).append(" as ip, cast('").append(seriesLetters[i]).append("' as text) as series ");
            query.append("from packets as p ");
            query.append("where p.").append(column).append(" like :ip").append(i).append(" ");
            if (i != packetInfoList.size() - 1) {
                query.append("union all ");
            }
        }
        query.append(") as pp ");
        query.append("right join intervals as i on pp.timestamp >= i.start and pp.timestamp < i.end ");
        query.append("group by i.start, pp.ip, pp.series ");
        query.append("order by i.start asc");

        Query nativeQuery = entityManager.createNativeQuery(query.toString());
        for (int i = 0; i < packetInfoList.size(); i++) {
            nativeQuery.setParameter("ip" + i, packetInfoList.get(i).getIp());
        }

        List<PacketCountInTimeInterval> list = new ArrayList<PacketCountInTimeInterval>();
        for (Object r : nativeQuery.getResultList()) {
            Object[] resultRow = (Object[]) r;
            PacketCountInTimeInterval p = new PacketCountInTimeInterval((Integer) resultRow[0], (String) resultRow[1], ((BigInteger) resultRow[3]).longValue(), (String) resultRow[2]);
            list.add(p);
        }
        return list;
    }

}
