package ufc.dto.ddos;

public class StorageByDestinationInTimeDomain extends StorageInTimeDomain {

    @Override
    String getIp(PacketCount pc) {
        return pc.getDestination();
    }
}
