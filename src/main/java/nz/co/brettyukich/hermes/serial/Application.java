package nz.co.brettyukich.hermes.serial;

import jssc.SerialPort;
import jssc.SerialPortException;
import nz.co.brettyukich.hermes.serial.serial.SerialListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

  private static final Logger log = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args){
    log.info("starting hermes-serial");

    SerialPort serialPort = new SerialPort(Properties.getValue("serial.name"));
    try {
      serialPort.openPort();
      serialPort.setParams(
          Integer.valueOf(Properties.getValue("serial.baud")),
          Integer.valueOf(Properties.getValue("serial.databits")),
          Integer.valueOf(Properties.getValue("serial.stopbits")),
          Integer.valueOf(Properties.getValue("serial.parity"))
      );
      int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;
      serialPort.setEventsMask(mask);
      serialPort.addEventListener(new SerialListener(serialPort));
    } catch (SerialPortException e) {
      log.error("failed to setup serial connection", e);
    }
  }
}