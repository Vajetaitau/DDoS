package ufc.dto.ddos;

import java.sql.Timestamp;

public abstract class ValueInTimeInterval {
    private Timestamp time;
    private Double value;

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
