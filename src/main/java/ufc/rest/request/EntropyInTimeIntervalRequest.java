package ufc.rest.request;

import java.sql.Timestamp;

/**
 * Created by K on 11/28/2015.
 */
public class EntropyInTimeIntervalRequest {
    private Timestamp start;
    private Timestamp end;
    private Integer increment;
    private Integer windowWidth;


    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public Integer getIncrement() {
        return increment;
    }

    public void setIncrement(Integer increment) {
        this.increment = increment;
    }

    public Integer getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(Integer windowWidth) {
        this.windowWidth = windowWidth;
    }
}
