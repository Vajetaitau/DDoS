package ufc.dto.ddos;

import java.sql.Timestamp;
import java.util.*;

public class PacketCountStorage {

    private Map<String, PacketCount> packetCountsGroupedByDestination;
    private List<PacketCount> currentTimePacketCounts;
    private List<List<PacketCount>> listOfListOfPacketCounts;
    private Long currentTimeInMilis;

    private List<Long> listOfFrom;
    private Long from;
    private int windowWidth;

    private List<EntropyInTimeInterval> listOfEntropies;
    private EntropyInTimeInterval minimumEntropy;
    private Double notNormalizedCurrentTimeEntropy;
    private Double normalizedCurrentTimeEntropy;
    private List<AverageInTimeInterval> listOfAverages;
    private List<DifferenceFromAverageInTimeInterval> listOfDifferences;

    public PacketCountStorage() {
        this.packetCountsGroupedByDestination = new HashMap<String, PacketCount>();
        this.currentTimePacketCounts = new ArrayList<PacketCount>();
        this.listOfListOfPacketCounts = new ArrayList<List<PacketCount>>();
        this.currentTimeInMilis = 0L;

        this.listOfFrom = new ArrayList<Long>();
        this.from = 0L;
        this.windowWidth = 50;

        this.listOfEntropies = new ArrayList<EntropyInTimeInterval>();

        this.minimumEntropy = new EntropyInTimeInterval();
        this.minimumEntropy.setEntropy(1D);
    }

    private PacketCountStorage decreasePacketCounts(List<PacketCount> packetCountsToDecrease) {
        for (PacketCount pc: packetCountsToDecrease) {
            String destination = pc.getDestination();

            PacketCount countByDestination = packetCountsGroupedByDestination.get(destination);
            if (countByDestination == null) {
                continue;
            }
            countByDestination.setCount(countByDestination.getCount() - pc.getCount());

            if (countByDestination.getCount() == 0) {
                packetCountsGroupedByDestination.remove(destination);
            }
        }
        return this;
    }

    private PacketCountStorage increasePacketCounts(List<PacketCount> packetCountsToIncrease) {
        for (PacketCount pc: packetCountsToIncrease) {
            String destination = pc.getDestination();

            PacketCount countByDestination = packetCountsGroupedByDestination.get(destination);
            if (countByDestination == null) {
                countByDestination = new PacketCount(null, destination, pc.getTime(), 0, 0);
                packetCountsGroupedByDestination.put(destination, countByDestination);
            }
            countByDestination.setCount(countByDestination.getCount() + pc.getCount());
        }
        return this;
    }

    public PacketCountStorage addCurrentIntervalToStorage() {
        if (currentTimePacketCounts.isEmpty()) {
            return this;
        }

        this.increasePacketCounts(currentTimePacketCounts);

        listOfListOfPacketCounts.add(currentTimePacketCounts);
        if (listOfListOfPacketCounts.size() > windowWidth) {
            this.decreasePacketCounts(listOfListOfPacketCounts.remove(0));
        }

        addNewFrom(currentTimePacketCounts.get(0).getFrom());
        countEntropy();
        addCurrentEntropyToEntropyList();

        return this;
    }

    private static int binlog( int bits ) // returns 0 for bits=0
    {
        int log = 0;
        if( ( bits & 0xffff0000 ) != 0 ) { bits >>>= 16; log = 16; }
        if( bits >= 256 ) { bits >>>= 8; log += 8; }
        if( bits >= 16  ) { bits >>>= 4; log += 4; }
        if( bits >= 4   ) { bits >>>= 2; log += 2; }
        return log + ( bits >>> 1 );
    }

    public PacketCountStorage cleanCurrentInterval() {
        this.currentTimePacketCounts = new ArrayList<PacketCount>();
        this.notNormalizedCurrentTimeEntropy = 0D;
        this.normalizedCurrentTimeEntropy = 0D;
        return this;
    }

    public boolean timeExceedsCurrentTime(Timestamp time) {
        return time.getTime() > this.currentTimeInMilis;
    }

    private PacketCountStorage updateCurrentTime(Timestamp time) {
        this.currentTimeInMilis = time.getTime();
        return this;
    }

    private void addNewFrom(Long from) {
        this.from += from;

        this.listOfFrom.add(from);
        if (this.listOfFrom.size() > this.windowWidth) {
            this.from -= this.listOfFrom.remove(0);
        }
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public void addNewPacketCount(PacketCount packetCount) {
        currentTimePacketCounts.add(packetCount);
        updateCurrentTime(packetCount.getTime());
    }

    public List<EntropyInTimeInterval> getListOfEntropies() {
        return listOfEntropies;
    }

    private void countEntropy() {
        for (String destination: packetCountsGroupedByDestination.keySet()) {
            Long count = packetCountsGroupedByDestination.get(destination).getCount();

            Double probability = ((double) count) / from;
            notNormalizedCurrentTimeEntropy -= probability * (Math.log(probability) / Math.log(2));
        }
        Double maximumEntropy = -Math.log(1D / from) / Math.log(2);
        normalizedCurrentTimeEntropy = notNormalizedCurrentTimeEntropy / maximumEntropy;
    }

    private void addCurrentEntropyToEntropyList() {
        EntropyInTimeInterval e = new EntropyInTimeInterval();
        e.setTime(new Timestamp(currentTimeInMilis));
        e.setEntropy(normalizedCurrentTimeEntropy);

        listOfEntropies.add(e);

        if (e.getEntropy() < minimumEntropy.getEntropy()) {
            minimumEntropy = e;
        }
    }

    public PacketCountStorage countAvarage() {
        listOfAverages = new ArrayList<AverageInTimeInterval>();

        Double entropySum = 0D;
        for (int i = 0; i < listOfEntropies.size(); i++) {
            EntropyInTimeInterval entropyInTimeInterval = listOfEntropies.get(i);
            Double entropy = entropyInTimeInterval.getEntropy();
            Timestamp time = entropyInTimeInterval.getTime();

            entropySum += entropy;
            if (i >= windowWidth) {
                int oldIndex = i - windowWidth;
                Double oldEntropy = listOfEntropies.get(oldIndex).getEntropy();
                entropySum -= oldEntropy;
            }

            Double average = entropySum / windowWidth;

            AverageInTimeInterval averageInTimeInterval = new AverageInTimeInterval();
            averageInTimeInterval.setTime(time);
            averageInTimeInterval.setValue(average);

            listOfAverages.add(averageInTimeInterval);
        }

        return this;
    }

    public PacketCountStorage countDifferenceFromAverage() {
        listOfDifferences = new ArrayList<DifferenceFromAverageInTimeInterval>();

        for (int i = 0; i < listOfEntropies.size(); i++) {
            EntropyInTimeInterval entropyInTimeInterval = listOfEntropies.get(i);
            Double entropy = entropyInTimeInterval.getEntropy();
            Timestamp time = entropyInTimeInterval.getTime();

            AverageInTimeInterval averageInTimeInterval = listOfAverages.get(i);
            Double entropyAverage = averageInTimeInterval.getValue();

            Double difference = entropy - entropyAverage;

            DifferenceFromAverageInTimeInterval differenceFromAverageInTimeInterval = new DifferenceFromAverageInTimeInterval();
            differenceFromAverageInTimeInterval.setTime(time);
            differenceFromAverageInTimeInterval.setValue(difference);

            listOfDifferences.add(differenceFromAverageInTimeInterval);
        }

        return this;
    }

    public List<AverageInTimeInterval> getListOfAverages() {
        return listOfAverages;
    }

    public List<DifferenceFromAverageInTimeInterval> getListOfDifferences() {
        return listOfDifferences;
    }

    public EntropyInTimeInterval getMinimumEntropy() {
        return minimumEntropy;
    }

    public void setMinimumEntropy(EntropyInTimeInterval minimumEntropy) {
        this.minimumEntropy = minimumEntropy;
    }
}
