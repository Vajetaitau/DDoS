package ufc.persistence.entity;

import ufc.constants.NamedQueryNames;

import javax.persistence.*;
import java.sql.Timestamp;

@NamedQueries({
        @NamedQuery(name = NamedQueryNames.GET_GROUPED_SOURCE_IPS
                , query = "" +
                "select new ufc.dto.ddos.GroupedSourceIpsDetails(source, count(source)) " +
                "from Packet " +
                "group by source " +
                "having count(source) > :threshold " +
                "order by count(source) desc")
})

@Entity
@Table(name = "packets")
public class Packet extends Item {

    @Column
    Integer number;

    @Column
    double timestamp;

    @Column
    String source;

    @Column
    String destination;

    @Column
    String protocol;

    @Column
    Integer length;

    @Column(length = 2048)
    String info;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(double timestamp) {
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
