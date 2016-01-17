package ufc.dto.ddos;

public class StorageBySourceInTimeDomain extends StorageInTimeDomain {

    @Override
    String getIp(PacketCount pc) {
        return pc.getSource();
    }
}
