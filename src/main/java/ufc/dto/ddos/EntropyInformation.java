package ufc.dto.ddos;


import java.util.List;

public class EntropyInformation {

    private List<EntropyInTimeInterval> listOfEntropies;
    private List<AverageInTimeInterval> listOfAverages;
    private List<DifferenceFromAverageInTimeInterval> listOfDifferences;
    private List<EntropyInTimeInterval> listOfEntropiesBySourceInTime;
    private List<EntropyInTimeInterval> listOfEntropiesByDestinationInTime;
    private List<EntropyInTimeInterval> listOfEntropiesBySourceInPacketCount;
    private List<EntropyInTimeInterval> listOfEntropiesByDestinationInPacketCount;

    public EntropyInformation() {
    }

    public EntropyInformation(List<EntropyInTimeInterval> listOfEntropies, List<AverageInTimeInterval> listOfAverages, List<DifferenceFromAverageInTimeInterval> listOfDifferences) {
        this.listOfEntropies = listOfEntropies;
        this.listOfAverages = listOfAverages;
        this.listOfDifferences = listOfDifferences;
    }

    public EntropyInformation(List<EntropyInTimeInterval> listOfEntropiesBySourceInTime, List<EntropyInTimeInterval> listOfEntropiesByDestinationInTime, List<EntropyInTimeInterval> listOfEntropiesBySourceInPacketCount, List<EntropyInTimeInterval> listOfEntropiesByDestinationInPacketCount) {
        this.listOfEntropiesBySourceInTime = listOfEntropiesBySourceInTime;
        this.listOfEntropiesByDestinationInTime = listOfEntropiesByDestinationInTime;
        this.listOfEntropiesBySourceInPacketCount = listOfEntropiesBySourceInPacketCount;
        this.listOfEntropiesByDestinationInPacketCount = listOfEntropiesByDestinationInPacketCount;
    }

    public List<EntropyInTimeInterval> getListOfEntropies() {
        return listOfEntropies;
    }

    public void setListOfEntropies(List<EntropyInTimeInterval> listOfEntropies) {
        this.listOfEntropies = listOfEntropies;
    }

    public List<AverageInTimeInterval> getListOfAverages() {
        return listOfAverages;
    }

    public void setListOfAverages(List<AverageInTimeInterval> listOfAverages) {
        this.listOfAverages = listOfAverages;
    }

    public List<DifferenceFromAverageInTimeInterval> getListOfDifferences() {
        return listOfDifferences;
    }

    public void setListOfDifferences(List<DifferenceFromAverageInTimeInterval> listOfDifferences) {
        this.listOfDifferences = listOfDifferences;
    }

    public List<EntropyInTimeInterval> getListOfEntropiesBySourceInTime() {
        return listOfEntropiesBySourceInTime;
    }

    public void setListOfEntropiesBySourceInTime(List<EntropyInTimeInterval> listOfEntropiesBySourceInTime) {
        this.listOfEntropiesBySourceInTime = listOfEntropiesBySourceInTime;
    }

    public List<EntropyInTimeInterval> getListOfEntropiesByDestinationInTime() {
        return listOfEntropiesByDestinationInTime;
    }

    public void setListOfEntropiesByDestinationInTime(List<EntropyInTimeInterval> listOfEntropiesByDestinationInTime) {
        this.listOfEntropiesByDestinationInTime = listOfEntropiesByDestinationInTime;
    }

    public List<EntropyInTimeInterval> getListOfEntropiesBySourceInPacketCount() {
        return listOfEntropiesBySourceInPacketCount;
    }

    public void setListOfEntropiesBySourceInPacketCount(List<EntropyInTimeInterval> listOfEntropiesBySourceInPacketCount) {
        this.listOfEntropiesBySourceInPacketCount = listOfEntropiesBySourceInPacketCount;
    }

    public List<EntropyInTimeInterval> getListOfEntropiesByDestinationInPacketCount() {
        return listOfEntropiesByDestinationInPacketCount;
    }

    public void setListOfEntropiesByDestinationInPacketCount(List<EntropyInTimeInterval> listOfEntropiesByDestinationInPacketCount) {
        this.listOfEntropiesByDestinationInPacketCount = listOfEntropiesByDestinationInPacketCount;
    }
}
