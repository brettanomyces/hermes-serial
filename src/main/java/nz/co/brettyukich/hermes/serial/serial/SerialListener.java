package nz.co.brettyukich.hermes.serial.serial;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by cascade on 15/05/15.
 */
public class SerialListener implements SerialPortEventListener{

  private static final Logger log = LoggerFactory.getLogger(SerialListener.class);
  private SerialPort serialPort;

  public SerialListener(SerialPort serialPort){
    this.serialPort = serialPort;
  }

  @Override
  public void serialEvent(SerialPortEvent event) {
    byte[] buffer = new byte[1000];
    InputStream is = new ByteArrayInputStream(buffer);
    InputStreamReader isr = new InputStreamReader(is);
    BufferedReader reader = new BufferedReader(isr);

    if(event.isRXCHAR() && event.getEventValue() > 0){
      log.debug("data is available");
      try {
        is.read(serialPort.readBytes());
        String line = reader.readLine();
        if(line != null){
          log.info("recieved: " + line);
        }
      } catch (IOException e) {
        e.printStackTrace();
      } catch (SerialPortException e) {
        e.printStackTrace();
      }
    }
  }
}
