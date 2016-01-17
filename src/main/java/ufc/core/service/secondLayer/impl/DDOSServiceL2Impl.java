package ufc.core.service.secondLayer.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufc.core.exceptions.GeneralException;
import ufc.core.service.secondLayer.DDOSServiceL2;
import ufc.core.service.secondLayer.modifiers.AttackLine;
import ufc.dto.ddos.*;
import ufc.persistence.entity.Packet;
import ufc.persistence.repository.PacketDao;
import ufc.utils.FileUploader;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by K on 10/9/2015.
 */
@Service("ddosServiceL2")
public class DDOSServiceL2Impl implements DDOSServiceL2 {

    private static final String pathToFile1 = "C:\\Users\\K\\Desktop\\TrainingData\\w1\\1d-inside.csv";
    private static final String pathToFile2 = "C:\\Users\\K\\Desktop\\TrainingData\\w1\\1d-outside.csv";

    private static final String pathToMinimumEntropyFile = "C:\\Users\\K\\Desktop\\TrainingData\\minimumEntropy-%s-%d.csv";

    @Autowired
    private PacketDao packetDao;
    @Autowired
    private FileUploader fileUploader;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void uploadFileToDatabase() throws GeneralException {
        String[] files = {pathToFile1, pathToFile2};

        for (int i = 0; i < files.length; i++) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(files[i]));
                String line;
                while ((line = br.readLine()) != null) {
                    Packet packet = getPacket(line, i);
                    packetDao.save(packet);
                }
            } catch (Exception e) {
                throw new GeneralException("Could not upload file to the database!", e);
            }
        }
    }

    @Override
    public List<GroupedIpDetails> getGroupedSourceIpDetails(Integer threshold, Integer limit, String order) throws GeneralException {
        return packetDao.findGroupedSourceIps(threshold, limit, order);
    }

    @Override
    public List<GroupedIpDetails> getGroupedDestinationIpDetails(Integer threshold, Integer limit, String order) throws GeneralException {
        return packetDao.findGroupedDestinationIps(threshold, limit, order);
    }

    @Override
    public List<PacketCountInTimeInterval> findPacketCounts(Timestamp start, Timestamp end, Integer increment, List<PacketInfo> packetInfoList) throws GeneralException {
        return packetDao.findPacketCounts(start, end, increment, packetInfoList);
    }

    private Packet getPacket(String line, int fileIndex) throws GeneralException {
        String[] cols = line.split("\\\",\\\"");
        cols[0] = cols[0].substring(1);
        cols[cols.length - 1] = cols[cols.length - 1].substring(0, cols[cols.length - 1].length() - 1);

        Packet packet = new Packet();
        packet.setNumber(Integer.valueOf(cols[0]));

        try {
            String time = cols[1];
            String[] parts = time.split("\\.");
            Date parsedDate = simpleDateFormat.parse(parts[0]);
            Timestamp timestamp = new Timestamp(parsedDate.getTime());
            timestamp.setNanos(Integer.parseInt(parts[1]) * 1000);
            packet.setTimestamp(timestamp);
        } catch (Exception e) {
            throw new GeneralException("Could", e);
        }


        packet.setSource(cols[2]);
        packet.setDestination(cols[3]);
        packet.setProtocol(cols[4]);
        packet.setLength(Integer.valueOf(cols[5]));
        packet.setInfo(cols[6]);
        packet.setFileName(String.valueOf(fileIndex));

        return packet;
    }

    @Override
    public void parseAttackFile() throws GeneralException {
        final String attackFilePath = "C:\\Users\\K\\Desktop\\TrainingData\\w5-attack-truth.txt";
        final String parsedOutput = "C:\\Users\\K\\Desktop\\TrainingData\\w5-attack-truth-parsed.txt";
        try {
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            BufferedReader br = new BufferedReader(new FileReader(attackFilePath));
            String line;
            while ((line = br.readLine()) != null) {
                String id = null;
                if (isSummarizedAttackLine(line)) {
                    id = getIdFromSummarizedAttackLine(line);
                    String columnNames = br.readLine();
                    String attackLine;
                    while (isAttackLine(attackLine = br.readLine(), id)) {
                        AttackLine al = new AttackLine(attackLine, id);

                        JsonObject obj = Json.createObjectBuilder()
                                .add("name", al.getName())
                                .add("source", al.getSourceIp())
                                .add("destination", al.getDestinationIp())
                                .add("increment", al.getIncrement())
                                .add("timeFrom", al.getTimeFrom())
                                .add("timeTo", al.getTimeTo())
                                .build();
                        arrayBuilder.add(obj);
                    }
                }
            }
            String parsedJson = arrayBuilder.build().toString();
            PrintWriter out = new PrintWriter(parsedOutput);
            out.write(parsedJson);
            out.flush();
            //writeToFile
        } catch (Exception e) {
            throw new GeneralException("Could not parse attack file!", e);
        }
    }

    private boolean isSummarizedAttackLine(String line) {
        return line.startsWith("Summarized attack");
    }

    private String getIdFromSummarizedAttackLine(String summarizedAttackLine) {
        return summarizedAttackLine.substring("Summarized attack: ".length());
    }

    private boolean isAttackLine(String line, String id) {
        if (line != null) {
            return line.trim().startsWith(id);
        } else {
            return false;
        }
    }

    @Override
    public EntropyInformation getEntropy(Timestamp start, Timestamp end, Integer increment, Integer windowWidth) {
        List<EntropyInTimeInterval> list = new ArrayList<EntropyInTimeInterval>();

        List<PacketCount> packetCounts = packetDao.findPacketCounts(start, end, increment);

        StorageByDestinationInTimeDomain packetCountStorage = new StorageByDestinationInTimeDomain();
        if (packetCounts != null && packetCounts.size() > 0) {
            packetCountStorage.setWindowWidth(windowWidth);

            for (int i = 0; i < packetCounts.size(); i++) {
                PacketCount pc = packetCounts.get(i);

                if (packetCountStorage.timeExceedsCurrentTime(pc.getTime())) {
                    packetCountStorage
                            .addCurrentIntervalToStorage()
                            .cleanCurrentInterval();
                }

                packetCountStorage.addNewPacketCount(pc);
            }
        }

        EntropyInformation entropyInformation = new EntropyInformation();
        entropyInformation.setListOfEntropies(packetCountStorage.getListOfEntropies());
//        entropyInformation.setListOfAverages(packetCountStorage.countAvarage().getListOfAverages());
//        entropyInformation.setListOfDifferences(packetCountStorage.countDifferenceFromAverage().getListOfDifferences());

        return entropyInformation;
    }

    @Override
    public void scanForDDoSAttacks() {


//        printMinimumEntropyInTimeDomain(10);
//        printMinimumEntropyInTimeDomain(15);
//        printMinimumEntropyInTimeDomain(20);
//        printMinimumEntropyInTimeDomain(25);
//        printMinimumEntropyInTimeDomain(35);
//        printMinimumEntropyInTimeDomain(40);
//        printMinimumEntropyInTimeDomain(50);
//        printMinimumEntropyInTimeDomain(60);
//        printMinimumEntropyInTimeDomain(70);
//        printMinimumEntropyInTimeDomain(80);
//        printMinimumEntropyInTimeDomain(90);

//        printMinimumEntropyInCountDomain(10);
//        printMinimumEntropyInCountDomain(15);
//        printMinimumEntropyInCountDomain(20);
//        printMinimumEntropyInCountDomain(25);
//        printMinimumEntropyInCountDomain(35);
//        printMinimumEntropyInCountDomain(45);
//        printMinimumEntropyInCountDomain(30);
//        printMinimumEntropyInCountDomain(40);
//        printMinimumEntropyInCountDomain(50);
//        printMinimumEntropyInCountDomain(60);
//        printMinimumEntropyInCountDomain(70);
//        printMinimumEntropyInCountDomain(80);
//        printMinimumEntropyInCountDomain(90);

        //findWhereEntropyIsLowerThan(40, 0.1813D, 0.17776D);

        findWhereEntropyIsLowerThanInCountDomain(45, 0.18156D, 0.17805D);
    }

    private void printMinimumEntropyInTimeDomain(int windowWidth) {
        System.out.println("Scanning started!");

        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        final String startDateString = "1999-03-01 08:00:00";
        final String endDateString = "1999-03-02 08:00:00";

        final long intervalPadding = 2 * 60 * 1000;
        final Integer amountOfSecondsInInterval = 20;
        final long oneSecond = 1 * 1000;


        try {
            PrintWriter out = new PrintWriter(String.format(pathToMinimumEntropyFile, "timeDomain", windowWidth));

            Date startDate = dateFormat.parse(startDateString);
            Date endDate = dateFormat.parse(endDateString);

            Date currentDate = startDate;

            StorageByDestinationInTimeDomain storageByDestinationInTimeDomain = new StorageByDestinationInTimeDomain();
            storageByDestinationInTimeDomain.setWindowWidth(windowWidth);

            StorageBySourceInTimeDomain storageBySourceInTimeDomain = new StorageBySourceInTimeDomain();
            storageBySourceInTimeDomain.setWindowWidth(windowWidth);

            while (currentDate.getTime() < endDate.getTime()) {
                Date startIntervalDate = new Date(currentDate.getTime() + oneSecond);
                Date endIntervalDate = new Date(currentDate.getTime() + intervalPadding);

                List<PacketCount> packetCountsByDestinationInTimeDomain = packetDao.findPacketCounts(
                        new Timestamp(startIntervalDate.getTime()), new Timestamp(endIntervalDate.getTime()), amountOfSecondsInInterval
                );
                List<PacketCount> packetCountsBySourceInTimeDomain = packetDao.findPacketCountsBySourceInTimeDomain(
                        new Timestamp(startIntervalDate.getTime()), new Timestamp(endIntervalDate.getTime()), amountOfSecondsInInterval
                );

                if (packetCountsByDestinationInTimeDomain != null && packetCountsByDestinationInTimeDomain.size() > 0) {
                    for (int i = 0; i < packetCountsByDestinationInTimeDomain.size(); i++) {
                        PacketCount pc = packetCountsByDestinationInTimeDomain.get(i);

                        if (storageByDestinationInTimeDomain.timeExceedsCurrentTime(pc.getTime())) {
                            storageByDestinationInTimeDomain
                                    .addCurrentIntervalToStorage()
                                    .cleanCurrentInterval();
                        }

                        storageByDestinationInTimeDomain.addNewPacketCount(pc);
                    }
                }

                if (packetCountsBySourceInTimeDomain != null && packetCountsBySourceInTimeDomain.size() > 0) {
                    for (int i = 0; i < packetCountsBySourceInTimeDomain.size(); i++) {
                        PacketCount pc = packetCountsBySourceInTimeDomain.get(i);

                        if (storageBySourceInTimeDomain.timeExceedsCurrentTime(pc.getTime())) {
                            storageBySourceInTimeDomain
                                    .addCurrentIntervalToStorage()
                                    .cleanCurrentInterval();
                        }

                        storageBySourceInTimeDomain.addNewPacketCount(pc);
                    }
                }


                EntropyInTimeInterval minimumEntropyByDestination = storageByDestinationInTimeDomain.getMinimumEntropy();
                EntropyInTimeInterval minimumEntropyBySource = storageBySourceInTimeDomain.getMinimumEntropy();

                out.write(
                        dateFormat.format(minimumEntropyByDestination.getTime()) + "," + minimumEntropyByDestination.getEntropy() + ","
                                + dateFormat.format(minimumEntropyBySource.getTime()) + "," + minimumEntropyBySource.getEntropy() + "\r\n"
                );

                storageByDestinationInTimeDomain.getMinimumEntropy().setEntropy(1D);
                storageBySourceInTimeDomain.getMinimumEntropy().setEntropy(1D);

                currentDate = endIntervalDate;
                System.out.println(dateFormat.format(currentDate));
            }

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printMinimumEntropyInCountDomain(int windowWidth) {
        System.out.println("Scanning started!");

        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        final String startDateString = "1999-03-01 08:00:00";
        final String endDateString = "1999-03-02 08:00:00";



        final Integer packetCountInOneWindow = 500;
        final long intervalPadding = 5 * packetCountInOneWindow;


        try {
            PrintWriter out = new PrintWriter(String.format(pathToMinimumEntropyFile, "countDomain", windowWidth));

            Timestamp startTime = new Timestamp(dateFormat.parse(startDateString).getTime());
            Timestamp endTime = new Timestamp(dateFormat.parse(endDateString).getTime());

            long startNumber = 0;
            long endNumber = 1500000;

            long currentNumber = startNumber;

            StorageByDestinationInCountDomain storageByDestinationInCountDomain = new StorageByDestinationInCountDomain();
            storageByDestinationInCountDomain.setWindowWidth(windowWidth);

            StorageBySourceInCountDomain storageBySourceInCountDomain = new StorageBySourceInCountDomain();
            storageBySourceInCountDomain.setWindowWidth(windowWidth);

            while (currentNumber < endNumber) {
                long startIntervalNumber = currentNumber;
                long endIntervalNumber = currentNumber + intervalPadding;

                List<PacketCount> packetCountsByDestinationInCountDomain = packetDao.findPacketCountsByDestinationInCountDomain(
                        startTime, endTime, startIntervalNumber, endIntervalNumber, packetCountInOneWindow
                );
                List<PacketCount> packetCountsBySourceInCountDomain = packetDao.findPacketCountsBySourceInCountDomain(
                        startTime, endTime, startIntervalNumber, endIntervalNumber, packetCountInOneWindow
                );

                if (packetCountsByDestinationInCountDomain != null && packetCountsByDestinationInCountDomain.size() > 0) {
                    for (int i = 0; i < packetCountsByDestinationInCountDomain.size(); i++) {
                        PacketCount pc = packetCountsByDestinationInCountDomain.get(i);

                        if (storageByDestinationInCountDomain.numberExceedsCurrentNumber(pc.getNumberInCountDomain())) {
                            storageByDestinationInCountDomain
                                    .addCurrentIntervalToStorage()
                                    .cleanCurrentInterval();
                        }

                        storageByDestinationInCountDomain.addNewPacketCount(pc);
                    }
                }

                if (packetCountsBySourceInCountDomain != null && packetCountsBySourceInCountDomain.size() > 0) {
                    for (int i = 0; i < packetCountsBySourceInCountDomain.size(); i++) {
                        PacketCount pc = packetCountsBySourceInCountDomain.get(i);

                        if (storageBySourceInCountDomain.numberExceedsCurrentNumber(pc.getNumberInCountDomain())) {
                            storageBySourceInCountDomain
                                    .addCurrentIntervalToStorage()
                                    .cleanCurrentInterval();
                        }

                        storageBySourceInCountDomain.addNewPacketCount(pc);
                    }
                }


                EntropyInTimeInterval minimumEntropyByDestination = storageByDestinationInCountDomain.getMinimumEntropy();
                EntropyInTimeInterval minimumEntropyBySource = storageBySourceInCountDomain.getMinimumEntropy();

                out.write(
                        minimumEntropyByDestination.getNumberInPacketCountDomain() + "," + minimumEntropyByDestination.getEntropy() + ","
                                + minimumEntropyBySource.getNumberInPacketCountDomain() + "," + minimumEntropyBySource.getEntropy() + "\r\n"
                );

                storageByDestinationInCountDomain.getMinimumEntropy().setEntropy(1D);
                storageBySourceInCountDomain.getMinimumEntropy().setEntropy(1D);

                currentNumber = endIntervalNumber;
                System.out.println(currentNumber);
            }

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void findWhereEntropyIsLowerThan(int windowWidth, Double lowerThanByDest, Double lowerThanBySource) {
        System.out.println("Scanning started!");

        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        final String startDateString = "1999-04-05 08:00:00";
        final String endDateString = "1999-04-06 06:00:00";

        final long intervalPadding = 2 * 60 * 1000;
        final Integer amountOfSecondsInInterval = 20;
        final long oneSecond = 1 * 1000;


        try {
            final String pathToAtacks = "C:\\Users\\K\\Desktop\\TrainingData\\attacks-%s-%d-%s.csv";

            PrintWriter out1 = new PrintWriter(String.format(pathToAtacks, "timeDomain", windowWidth, "destination"));
            PrintWriter out2 = new PrintWriter(String.format(pathToAtacks, "timeDomain", windowWidth, "source"));

            Date startDate = dateFormat.parse(startDateString);
            Date endDate = dateFormat.parse(endDateString);

            Date currentDate = startDate;

            StorageByDestinationInTimeDomain storageByDestinationInTimeDomain = new StorageByDestinationInTimeDomain();
            storageByDestinationInTimeDomain.setWindowWidth(windowWidth);
            storageByDestinationInTimeDomain.setAddToAttackListIfLowerThan(lowerThanByDest);

            StorageBySourceInTimeDomain storageBySourceInTimeDomain = new StorageBySourceInTimeDomain();
            storageBySourceInTimeDomain.setWindowWidth(windowWidth);
            storageBySourceInTimeDomain.setAddToAttackListIfLowerThan(lowerThanBySource);

            while (currentDate.getTime() < endDate.getTime()) {
                Date startIntervalDate = new Date(currentDate.getTime() + oneSecond);
                Date endIntervalDate = new Date(currentDate.getTime() + intervalPadding);

                List<PacketCount> packetCountsByDestinationInTimeDomain = packetDao.findPacketCounts(
                        new Timestamp(startIntervalDate.getTime()), new Timestamp(endIntervalDate.getTime()), amountOfSecondsInInterval
                );
                List<PacketCount> packetCountsBySourceInTimeDomain = packetDao.findPacketCountsBySourceInTimeDomain(
                        new Timestamp(startIntervalDate.getTime()), new Timestamp(endIntervalDate.getTime()), amountOfSecondsInInterval
                );

                if (packetCountsByDestinationInTimeDomain != null && packetCountsByDestinationInTimeDomain.size() > 0) {
                    for (int i = 0; i < packetCountsByDestinationInTimeDomain.size(); i++) {
                        PacketCount pc = packetCountsByDestinationInTimeDomain.get(i);

                        if (storageByDestinationInTimeDomain.timeExceedsCurrentTime(pc.getTime())) {
                            storageByDestinationInTimeDomain
                                    .addCurrentIntervalToStorage()
                                    .cleanCurrentInterval();
                        }

                        storageByDestinationInTimeDomain.addNewPacketCount(pc);
                    }
                }

                if (packetCountsBySourceInTimeDomain != null && packetCountsBySourceInTimeDomain.size() > 0) {
                    for (int i = 0; i < packetCountsBySourceInTimeDomain.size(); i++) {
                        PacketCount pc = packetCountsBySourceInTimeDomain.get(i);

                        if (storageBySourceInTimeDomain.timeExceedsCurrentTime(pc.getTime())) {
                            storageBySourceInTimeDomain
                                    .addCurrentIntervalToStorage()
                                    .cleanCurrentInterval();
                        }

                        storageBySourceInTimeDomain.addNewPacketCount(pc);
                    }
                }

                currentDate = endIntervalDate;
                System.out.println(dateFormat.format(currentDate));
            }

            for (EntropyInTimeInterval e: storageByDestinationInTimeDomain.getAttackList()) {
                out1.write(
                        dateFormat.format(e.getTime()) + "," + e.getEntropy() + "\r\n"
                );
            }

            for (EntropyInTimeInterval e: storageBySourceInTimeDomain.getAttackList()) {
                out2.write(
                        dateFormat.format(e.getTime()) + "," + e.getEntropy() + "\r\n"
                );
            }

            out1.flush();
            out1.close();

            out2.flush();
            out2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void findWhereEntropyIsLowerThanInCountDomain(int windowWidth, Double lowerThanByDest, Double lowerThanBySource) {
        System.out.println("Scanning started!");

        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        final String startDateString = "1999-04-05 08:00:00";
        final String endDateString = "1999-04-06 06:00:00";

        final Integer packetCountInOneWindow = 500;
        final long intervalPadding = 5 * packetCountInOneWindow;


        try {
            final String pathToAtacks = "C:\\Users\\K\\Desktop\\TrainingData\\attacks-%s-%d-%s.csv";

            PrintWriter out1 = new PrintWriter(String.format(pathToAtacks, "countDomain", windowWidth, "destination"));
            PrintWriter out2 = new PrintWriter(String.format(pathToAtacks, "countDomain", windowWidth, "source"));

            Timestamp startTime = new Timestamp(dateFormat.parse(startDateString).getTime());
            Timestamp endTime = new Timestamp(dateFormat.parse(endDateString).getTime());

            long startNumber = 0;
            long endNumber = 1500000;

            long currentNumber = startNumber;

            StorageByDestinationInCountDomain storageByDestinationInCountDomain = new StorageByDestinationInCountDomain();
            storageByDestinationInCountDomain.setWindowWidth(windowWidth);
            storageByDestinationInCountDomain.setAddToAttackListIfLowerThan(lowerThanByDest);

            StorageBySourceInCountDomain storageBySourceInCountDomain = new StorageBySourceInCountDomain();
            storageBySourceInCountDomain.setWindowWidth(windowWidth);
            storageBySourceInCountDomain.setAddToAttackListIfLowerThan(lowerThanBySource);

            while (currentNumber < endNumber) {
                long startIntervalNumber = currentNumber;
                long endIntervalNumber = currentNumber + intervalPadding;

                List<PacketCount> packetCountsByDestinationInCountDomain = packetDao.findPacketCountsByDestinationInCountDomain(
                        startTime, endTime, startIntervalNumber, endIntervalNumber, packetCountInOneWindow
                );
                List<PacketCount> packetCountsBySourceInCountDomain = packetDao.findPacketCountsBySourceInCountDomain(
                        startTime, endTime, startIntervalNumber, endIntervalNumber, packetCountInOneWindow
                );

                if (packetCountsByDestinationInCountDomain != null && packetCountsByDestinationInCountDomain.size() > 0) {
                    for (int i = 0; i < packetCountsByDestinationInCountDomain.size(); i++) {
                        PacketCount pc = packetCountsByDestinationInCountDomain.get(i);

                        if (storageByDestinationInCountDomain.numberExceedsCurrentNumber(pc.getNumberInCountDomain())) {
                            storageByDestinationInCountDomain
                                    .addCurrentIntervalToStorage()
                                    .cleanCurrentInterval();
                        }

                        storageByDestinationInCountDomain.addNewPacketCount(pc);
                    }
                }

                if (packetCountsBySourceInCountDomain != null && packetCountsBySourceInCountDomain.size() > 0) {
                    for (int i = 0; i < packetCountsBySourceInCountDomain.size(); i++) {
                        PacketCount pc = packetCountsBySourceInCountDomain.get(i);

                        if (storageBySourceInCountDomain.numberExceedsCurrentNumber(pc.getNumberInCountDomain())) {
                            storageBySourceInCountDomain
                                    .addCurrentIntervalToStorage()
                                    .cleanCurrentInterval();
                        }

                        storageBySourceInCountDomain.addNewPacketCount(pc);
                    }
                }

                currentNumber = endIntervalNumber;
                System.out.println(currentNumber);
            }

            for (EntropyInTimeInterval e : storageByDestinationInCountDomain.getAttackList()) {
                Timestamp time = packetDao.findMatchingTime(startTime, endTime, "0", e.getNumberInPacketCountDomain());
                out1.write(
                        dateFormat.format(time) + "," + e.getEntropy() + "\r\n"
                );
            }

            for (EntropyInTimeInterval e : storageBySourceInCountDomain.getAttackList()) {
                Timestamp time = packetDao.findMatchingTime(startTime, endTime, "0", e.getNumberInPacketCountDomain());
                out2.write(
                        dateFormat.format(time) + "," + e.getEntropy() + "\r\n"
                );
            }

            out1.flush();
            out1.close();

            out2.flush();
            out2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
