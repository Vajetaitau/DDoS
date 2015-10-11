package ufc.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufc.persistence.entity.Packet;
import ufc.persistence.repository.impl.PacketDaoImpl;

/**
 * Created by K on 10/9/2015.
 */
public interface PacketDao extends JpaRepository<Packet, Integer>, PacketDaoCustom {

}
