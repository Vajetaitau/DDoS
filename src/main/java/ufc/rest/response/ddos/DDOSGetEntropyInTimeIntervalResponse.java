package ufc.rest.response.ddos;

import ufc.dto.ddos.EntropyInTimeInterval;
import ufc.dto.ddos.EntropyInformation;

import java.util.List;

/**
 * Created by K on 11/28/2015.
 */
public class DDOSGetEntropyInTimeIntervalResponse {

    EntropyInformation entropyInformation;

    public DDOSGetEntropyInTimeIntervalResponse() {
    }

    public DDOSGetEntropyInTimeIntervalResponse(EntropyInformation entropyInformation) {
        this.entropyInformation = entropyInformation;
    }

    public EntropyInformation getEntropyInformation() {
        return entropyInformation;
    }

    public void setEntropyInformation(EntropyInformation entropyInformation) {
        this.entropyInformation = entropyInformation;
    }
}
