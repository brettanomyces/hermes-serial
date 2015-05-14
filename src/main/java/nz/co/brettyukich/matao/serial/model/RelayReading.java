package nz.co.brettyukich.matao.serial.model;


import java.sql.Timestamp;

/**
 * Created by cascade on 1/05/15.
 */
public class RelayReading {

    private Timestamp timestamp;
    private RelayState state;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public RelayState getState() {
        return state;
    }

    public void setState(RelayState state) {
        this.state = state;
    }

}
