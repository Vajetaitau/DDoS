package ufc.core.service.secondLayer.modifiers;

import ufc.core.exceptions.GeneralException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AttackLine {

    private static final String fakeIp = "000.000.000.000";
    private static final long intervalPadding = 60 * 1000;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
    private static final SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static final SimpleDateFormat durationDateFormat = new SimpleDateFormat("hh:mm:ss");
    private final String attackLine;
    private final String attackId;
    private String[] columns;
    private String date;
    private String time;
    private String duration;
    private String name;


    private String destinationIp;

    public AttackLine(String line, String attackId) throws GeneralException {
        this.attackLine = line;
        this.attackId = attackId;
        setColumns();
        setDate();
        setTime();
        setDuration();
        setDestinationIp();
        setName();
    }

    public String getIncrement() {
        return "1";
    }

    public String getSourceIp() {
        return "*";
    }

    public String getDestinationIp() {
        return this.destinationIp;
    }

    public String getTimeFrom() throws GeneralException {
        try {
            String timeFrom = this.date + " " + this.time;
            Date parsedTimeFrom = dateFormat.parse(timeFrom);
            Date timeFromWithPadding = new Date(parsedTimeFrom.getTime() - intervalPadding);
            return outputDateFormat.format(timeFromWithPadding);
        } catch (Exception e) {
            throw new GeneralException("Something wrong with date parsing!", e);
        }
    }

    public String getTimeTo() throws GeneralException {
        try {
            String timeFrom = this.date + " " + this.time;
            Date parsedTimeFrom = dateFormat.parse(timeFrom);
            String[] durationParts = this.duration.split(":");
            long durationSeconds = Long.parseLong(durationParts[2]);
            long durationMinutes = Long.parseLong(durationParts[1]);

            Date timeToWithPadding = new Date(parsedTimeFrom.getTime()
                    + durationSeconds * 1000
                    + durationMinutes * 1000 * 60
                    + intervalPadding);
            return outputDateFormat.format(timeToWithPadding);
        } catch (Exception e) {
            throw new GeneralException("Something wrong with date parsing!", e);
        }
    }

    private void setColumns() throws GeneralException {
        String trimmedLine = this.attackLine.trim();
        if (trimmedLine.startsWith(this.attackId)) {
            String lineWithoutId = trimmedLine.substring(this.attackId.length());
            this.columns = lineWithoutId.split(" ");
        } else {
            throw new GeneralException("Line does not match the pattern!");
        }
    }

    private void setDate() {
        this.date = this.columns[0];
    }

    private void setTime() {
        this.time = this.columns[1];
    }

    private void setDuration() {
        this.duration = this.columns[3];
    }

    private void setDestinationIp() {
        String destinationIpWithZeroes = this.columns[4].substring(0, fakeIp.length());
        String[] parts = destinationIpWithZeroes.split("\\.");

        StringBuilder destinationIpSb = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            while (part.startsWith("0")) {
                part = part.substring(1);
            }
            destinationIpSb.append(part);
            if (i != parts.length - 1) {
                destinationIpSb.append(".");
            }
        }
        this.destinationIp = destinationIpSb.toString();
    }

    private void setName() {
        this.name = this.columns[4].substring(fakeIp.length());
    }

    public String getName() {
        return name;
    }
}
