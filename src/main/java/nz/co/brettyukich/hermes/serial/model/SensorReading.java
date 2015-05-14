package nz.co.brettyukich.hermes.serial.model;

import java.sql.Timestamp;

/**
 * Created by cascade on 1/05/15.
 */
public class SensorReading {

    private Timestamp timestamp;
    private float reading;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public float getReading() {
        return reading;
    }

    public void setReading(float reading) {
        this.reading = reading;
    }
}
