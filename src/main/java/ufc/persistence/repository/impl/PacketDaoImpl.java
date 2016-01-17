package ufc.persistence.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ufc.constants.NamedQueryNames;
import ufc.dto.ddos.GroupedIpDetails;
import ufc.dto.ddos.PacketCount;
import ufc.dto.ddos.PacketCountInTimeInterval;
import ufc.dto.ddos.PacketInfo;
import ufc.persistence.repository.PacketDaoCustom;
import ufc.rest.request.PacketCountInTimeIntervalsRequest;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
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

    @Override
    public List<PacketCount> findPacketCounts(Timestamp start, Timestamp end, Integer increment) {
        StringBuilder query = new StringBuilder();

        query.append("select tt.interval_start as t, destination as d, count(*) as c, sum(count(*)) over (partition by tt.interval_start order by tt.interval_start asc) ");
        query.append("from packets ");
        query.append("right join ( ");
            query.append("select generate_series as interval_start, generate_series + '1 second' as interval_end ");
            query.append("from generate_series(cast(:start as timestamp), :end, cast(concat('1', 'second') as interval))");
        query.append(") as tt on packets.timestamp >= tt.interval_start and packets.timestamp <= tt.interval_end ");
        query.append("where filename = '0' ");
        query.append("group by tt.interval_start, destination ");
        query.append("order by tt.interval_start asc");

        Query nativeQuery = entityManager.createNativeQuery(query.toString());
        nativeQuery.setParameter("start", start);
        nativeQuery.setParameter("end", end);

        List<PacketCount> list  = new ArrayList<PacketCount>();
        for (Object r: nativeQuery.getResultList()) {
            Object[] resultRow = (Object[]) r;
            Timestamp intervalStart = (Timestamp) resultRow[0];
            String destination = (String) resultRow[1];
            long count = ((BigInteger) resultRow[2]).longValue();
            long sum = ((BigDecimal) resultRow[3]).longValue();
            PacketCount packetCount = new PacketCount(null, destination, intervalStart, count, sum);
            list.add(packetCount);
        }
        return list;
    }

    @Override
    public List<PacketCount> findPacketCountsBySourceInTimeDomain(Timestamp start, Timestamp end, Integer increment) {
        StringBuilder query = new StringBuilder();

        query.append("select tt.interval_start as t, source as s, count(*) as c, sum(count(*)) over (partition by tt.interval_start order by tt.interval_start asc) ");
        query.append("from packets ");
        query.append("right join ( ");
        query.append("select generate_series as interval_start, generate_series + '1 second' as interval_end ");
        query.append("from generate_series(cast(:start as timestamp), :end, cast(concat('1', 'second') as interval))");
        query.append(") as tt on packets.timestamp >= tt.interval_start and packets.timestamp <= tt.interval_end ");
        query.append("where filename = '0' ");
        query.append("group by tt.interval_start, source ");
        query.append("order by tt.interval_start asc");

        Query nativeQuery = entityManager.createNativeQuery(query.toString());
        nativeQuery.setParameter("start", start);
        nativeQuery.setParameter("end", end);

        List<PacketCount> list  = new ArrayList<PacketCount>();
        for (Object r: nativeQuery.getResultList()) {
            Object[] resultRow = (Object[]) r;
            Timestamp intervalStart = (Timestamp) resultRow[0];
            String source = (String) resultRow[1];
            long count = ((BigInteger) resultRow[2]).longValue();
            long sum = ((BigDecimal) resultRow[3]).longValue();
            PacketCount packetCount = new PacketCount(source, null, intervalStart, count, sum);
            list.add(packetCount);
        }
        return list;
    }

    @Override
    public List<PacketCount> findPacketCountsByDestinationInCountDomain(Timestamp timeStart, Timestamp timeEnd, long start, long end, Integer increment) {
        StringBuilder query = new StringBuilder();

        query.append("select num, d, count(*), sum(count(*)) over (partition by num order by num asc) ");
        query.append("from ( ");
        query.append("select p.number / :increment as num, p.destination as d ");
        query.append("from packets as p ");
        query.append("where p.number >= :start ");
        query.append("and p.number < :end ");
        query.append("and filename = '0' ");
        query.append("and p.timestamp >= :timeStart ");
        query.append("and p.timestamp <= :timeEnd ");
        query.append(") as x ");
        query.append("group by num, d ");
        query.append("order by num asc");

        Query nativeQuery = entityManager.createNativeQuery(query.toString());
        nativeQuery.setParameter("start", start);
        nativeQuery.setParameter("end", end);
        nativeQuery.setParameter("increment", increment);
        nativeQuery.setParameter("timeStart", timeStart);
        nativeQuery.setParameter("timeEnd", timeEnd);

        List<PacketCount> list  = new ArrayList<PacketCount>();
        for (Object r: nativeQuery.getResultList()) {
            Object[] resultRow = (Object[]) r;
            long numberInCountDomain = ((Integer) resultRow[0]).longValue();
            String destination = (String) resultRow[1];
            long count = ((BigInteger) resultRow[2]).longValue();
            long sum = ((BigDecimal) resultRow[3]).longValue();
            PacketCount packetCount = new PacketCount(null, destination, numberInCountDomain, count, sum);
            list.add(packetCount);
        }
        return list;
    }

    @Override
    public List<PacketCount> findPacketCountsBySourceInCountDomain(Timestamp timeStart, Timestamp timeEnd, long start, long end, Integer increment) {
        StringBuilder query = new StringBuilder();

        query.append("select num, d, count(*), sum(count(*)) over (partition by num order by num asc) ");
        query.append("from ( ");
        query.append("select p.number / :increment as num, p.source as d ");
        query.append("from packets as p ");
        query.append("where p.number >= :start ");
        query.append("and p.number < :end ");
        query.append("and filename = '0' ");
        query.append("and p.timestamp >= :timeStart ");
        query.append("and p.timestamp <= :timeEnd ");
        query.append(") as x ");
        query.append("group by num, d ");
        query.append("order by num asc");

        Query nativeQuery = entityManager.createNativeQuery(query.toString());
        nativeQuery.setParameter("start", start);
        nativeQuery.setParameter("end", end);
        nativeQuery.setParameter("increment", increment);
        nativeQuery.setParameter("timeStart", timeStart);
        nativeQuery.setParameter("timeEnd", timeEnd);

        List<PacketCount> list  = new ArrayList<PacketCount>();
        for (Object r: nativeQuery.getResultList()) {
            Object[] resultRow = (Object[]) r;
            long numberInCountDomain = ((Integer) resultRow[0]).longValue();
            String source = (String) resultRow[1];
            long count = ((BigInteger) resultRow[2]).longValue();
            long sum = ((BigDecimal) resultRow[3]).longValue();
            PacketCount packetCount = new PacketCount(source, null, numberInCountDomain, count, sum);
            list.add(packetCount);
        }
        return list;
    }
}
