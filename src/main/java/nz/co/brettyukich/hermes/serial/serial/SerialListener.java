package nz.co.brettyukich.hermes.serial.serial;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by cascade on 15/05/15.
 */
public class SerialListener implements SerialPortEventListener{

  private static final Logger log = LoggerFactory.getLogger(SerialListener.class);
  private StringBuilder sb = new StringBuilder();
  private SerialPort serialPort;

  public SerialListener(SerialPort serialPort){
    this.serialPort = serialPort;
  }

  @Override
  public void serialEvent(SerialPortEvent event) {
    if(event.isRXCHAR() && event.getEventValue() > 0){
      log.debug("data is available");
      try {
        byte buffer[] = serialPort.readBytes();
        for (byte b : buffer){
          if(b == '\r'){
            log.debug("found newline character");
            // newline represents end of stream
            String message = sb.toString();
            // todo process message
            log.info("received message: " + message);
          } else {
            sb.append((char)b);
          }
        }
      } catch (SerialPortException e) {
        // todo handle exception
        log.error("an error occured while reading data from serial port", e);
      }
    }
  }
}
