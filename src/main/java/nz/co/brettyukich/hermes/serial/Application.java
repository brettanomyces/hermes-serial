package nz.co.brettyukich.hermes.serial;

import jssc.SerialPort;
import jssc.SerialPortException;
import nz.co.brettyukich.hermes.serial.serial.SerialInputStream;
import nz.co.brettyukich.hermes.serial.serial.SerialListener;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Application {

  private static final Logger log = LoggerFactory.getLogger(Application.class);
  private static DataSource dataSource;
  private static SerialPort serialPort;


  public static void main(String[] args) throws IOException {
    log.info("starting hermes-serial");
    setupDatasource();
    setupSerialPort();
    BufferedReader reader = new BufferedReader(new InputStreamReader(new SerialInputStream(serialPort)));
    while (true){
      log.info(reader.readLine());
    }

  }
  
  private static void setupDatasource(){
    // TODO allow for other types of datasources
    log.info("connecting to database");
    try {
      Class.forName(Properties.getValue("database.driver"));
    } catch (ClassNotFoundException cnfe) {
      throw new RuntimeException("could not find database driver: " + Properties.getValue("database.driver"), cnfe);
    }
    PGSimpleDataSource ds = new PGSimpleDataSource();
    ds.setDatabaseName(Properties.getValue("database.name"));
    ds.setServerName(Properties.getValue("database.host"));
    ds.setUser(Properties.getValue("database.user"));
    ds.setPassword(Properties.getValue("database.password"));
    dataSource = ds;
  }
  
  private static void setupSerialPort(){
    log.info("connecting to serial port");
    long wait = Long.valueOf(Properties.getValue("serial.wait.millis"));
    boolean connected = false;
    while (!connected){
      try {
        SerialPort sp = new SerialPort(Properties.getValue("serial.name"));
        sp.openPort();
        sp.setParams(
            Integer.valueOf(Properties.getValue("serial.baud")),
            Integer.valueOf(Properties.getValue("serial.databits")),
            Integer.valueOf(Properties.getValue("serial.stopbits")),
            Integer.valueOf(Properties.getValue("serial.parity"))
        );
        int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;
        sp.setEventsMask(mask);
        connected = true;
      } catch (SerialPortException spe) {
        log.error("failed to open serial port", spe);
        try {
          log.info("waiting " + wait + " seconds");
          Thread.sleep(wait);
        } catch (InterruptedException ie) {
          log.error("an exception occurred while waiting to connect to serial port", ie);
        }
      }
    }
  }
  
  private static void setupSerialEventListeners(SerialPort serialPort){
    
  }
  
}