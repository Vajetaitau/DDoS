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
import java.sql.Timestamp;
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
    public List<PacketCountInTimeInterval> findPacketCounts(Timestamp start, Timestamp end, Integer increment,
                                                            List<PacketInfo> packetInfoList) {

        String[] seriesLetters = {"a", "b", "c", "d", "e", "f", "g", "h", "i"};

        StringBuilder query = new StringBuilder();
        int minutesMultiplier = 1;
        int multiplier = increment;
        int dividor = 60 / increment;

        query.append("select tt.t as t, pp.series, count(pp.ip), pp.fileName ");
        query.append("from (");
        for (int i = 0; i < packetInfoList.size(); i++) {
            String column = packetInfoList.get(i).isReturnSource() ? "source" : "destination";

            query.append("select date_trunc('hour', p.timestamp) AS hour_timestamp");
            query.append(", cast((extract(minute FROM p.timestamp)) as int) / ").append(minutesMultiplier).append(" AS min_timestamp");
            query.append(", cast((extract(second FROM p.timestamp)) as int) / :increment AS second_timestamp");
            query.append(", p.fileName as fileName, p.").append(column).append(" as ip, cast('").append(seriesLetters[i]).append("' as text) as series ");
            query.append("from packets as p ");
            query.append("where p.source like :source").append(i).append(" ");
            query.append("and p.destination like :destination").append(i).append(" ");
            query.append("and p.timestamp >= :start ");
            query.append("and p.timestamp <= :end ");
            if (i != packetInfoList.size() - 1) {
                query.append("union all ");
            }
        }
        query.append(") as pp ");
        query.append("right join ( ");
        query.append("select generate_series as t ");
        query.append("from generate_series(");
        query.append("date_trunc('hour', cast(:start as timestamp))");
        query.append(", date_trunc('hour', cast(:end as timestamp)) + '1 hour'");
        query.append(", cast(concat(:increment, 'second') as interval))");
        query.append(") as tt on pp.hour_timestamp + cast(concat(pp.min_timestamp * 1, 'minutes') as interval) + cast(concat(pp.second_timestamp * :increment, 'seconds') as interval) = tt.t ");
        query.append("where tt.t >= :start and tt.t <= :end ");
        query.append("group by tt.t, pp.series, pp.fileName ");
        query.append("order by tt.t asc");

        Query nativeQuery = entityManager.createNativeQuery(query.toString());
        for (int i = 0; i < packetInfoList.size(); i++) {
            nativeQuery.setParameter("source" + i, packetInfoList.get(i).getSource().replace("*", "%"));
            nativeQuery.setParameter("destination" + i, packetInfoList.get(i).getDestination().replace("*", "%"));
        }
        nativeQuery.setParameter("start", start);
        nativeQuery.setParameter("end", end);
        nativeQuery.setParameter("increment", increment);

        List<PacketCountInTimeInterval> list = new ArrayList<PacketCountInTimeInterval>();
        for (Object r : nativeQuery.getResultList()) {
            Object[] resultRow = (Object[]) r;
            Timestamp timestamp = (Timestamp) resultRow[0];
            PacketCountInTimeInterval p = new PacketCountInTimeInterval(timestamp, "", ((BigInteger) resultRow[2]).longValue(), (String) resultRow[1]);
            p.setFile((String) resultRow[3]);
            list.add(p);
        }
        return list;
    }

}
