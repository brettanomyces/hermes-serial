package nz.co.brettyukich.hermes.serial;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
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


  public static void main(String[] args) throws IOException{
    log.info("starting hermes-serial");
    // TODO once I've got serial working reliably
    // setupDatasource();
    for (SerialPort port : SerialPort.getCommPorts()){
      log.info(port.getDescriptivePortName());
      log.info(port.getSystemPortName());
      log.info(String.valueOf(port.getBaudRate()));
    }
    setupSerial();
  }
  
  private static void setupSerial() {
    SerialPort serialPort = SerialPort.getCommPort(Properties.getValue("serial.name"));
    serialPort.setComPortParameters(
        Integer.parseInt(Properties.getValue("serial.baud")),
        Integer.parseInt(Properties.getValue("serial.databits")),
        Integer.parseInt(Properties.getValue("serial.stopbits")),
        Integer.parseInt(Properties.getValue("serial.parity"))
    );
//    serialPort.openPort();
//    serialPort.addDataListener(new SerialPortDataListener() {
//      @Override
//      public int getListeningEvents() {
//        return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
//      }
//
//      @Override
//      public void serialEvent(SerialPortEvent event) {
//        if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
//          return;
//        byte[] newData = new byte[serialPort.bytesAvailable()];
//        int numRead = serialPort.readBytes(newData, newData.length);
//        log.info("Read " + numRead + " bytes.");
//        log.info(new String(newData));
//      }
//    });
    if (serialPort.openPort()){
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
      String line;
      while (true) {
        try {
          if (serialPort.bytesAvailable() > 0) {
            while ((line = bufferedReader.readLine()) != null) {
              log.info("line: " + line);
            }
            log.info("eof / socket closed");
          }
        } catch (IOException e) {
          log.error("failed to read data from serial port");
        }
      }
    } else {
      log.error("Failed to open serial port " + Properties.getValue("serial.name"));
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
}