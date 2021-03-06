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
import java.io.InputStream;
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
  
  private static void setupSerial() throws IOException {
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
    if (serialPort.openPort()) {
      serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 0);
      InputStream in = serialPort.getInputStream();
      InputStreamReader isr = new InputStreamReader(in);
      BufferedReader reader = new BufferedReader(isr);
      String line;
      while (true) {
        log.info("reading from buffered reader");
        if (reader.ready() && (line = reader.readLine()) != null) {
          log.info(line);
        }
      }
      log.info("closing input stream");
      in.close();
      log.info("closing port");
      serialPort.closePort();
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