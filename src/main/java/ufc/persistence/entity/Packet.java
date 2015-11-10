package ufc.persistence.entity;

import org.hibernate.annotations.*;
import ufc.constants.NamedQueryNames;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.sql.Timestamp;

@NamedQueries({
        @NamedQuery(name = NamedQueryNames.GET_GROUPED_SOURCE_IPS
                , query = "" +
                "select new ufc.dto.ddos.GroupedIpDetails(source, count(source)) " +
                "from Packet " +
                "group by source " +
                "having count(source) > :threshold " +
                "order by count(source) desc"),
        @NamedQuery(name = NamedQueryNames.GET_GROUPED_DESTINATION_IPS
                , query = "" +
                "select new ufc.dto.ddos.GroupedIpDetails(destination, count(destination)) " +
                "from Packet " +
                "group by destination " +
                "having count(destination) > :threshold " +
                "order by count(destination) desc")
})

@Entity
@Table(name = "packets")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Packet extends Item {

    @Column
    Integer number;

    @Column
    Timestamp timestamp;

    @Column
    String source;

    @Column
    String destination;

    @Column
    String protocol;

    @Column
    Integer length;

    @Column(length = 1024 * 16)
    String info;

    @Column
    String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
