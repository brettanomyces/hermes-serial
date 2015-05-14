package nz.co.brettyukich.matao.serial.model;


import java.sql.Timestamp;

/**
 * Created by cascade on 1/05/15.
 */
public class BaffelReading {

    private Timestamp timestamp;
    private BaffelState baffelState;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public BaffelState getBaffelState() {
        return baffelState;
    }

    public void setBaffelState(BaffelState baffelState) {
        this.baffelState = baffelState;
    }

}
