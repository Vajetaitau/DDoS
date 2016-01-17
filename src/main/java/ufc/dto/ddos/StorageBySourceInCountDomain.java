package ufc.dto.ddos;

/**
 * Created by K on 1/16/2016.
 */
public class StorageBySourceInCountDomain extends StorageInCountDomain {
    @Override
    String getIp(PacketCount pc) {
        return pc.getSource();
    }
}
