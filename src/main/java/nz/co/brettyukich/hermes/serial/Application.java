package nz.co.brettyukich.hermes.serial;

import jssc.SerialPortList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

  private static final Logger log = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args){
    log.info("starting hermes-serial");
    log.info("looking for ports");
    String[] portNames = SerialPortList.getPortNames();
    for (String name : portNames){
      log.info("found port: " + name);
    }
  }
}