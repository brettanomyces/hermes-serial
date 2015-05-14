package nz.co.brettyukich.matao.serial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;

public class Properties {

  private static Logger log = LoggerFactory.getLogger(Properties.class);
  private static final java.util.Properties properties;
  // static initializer
  static {
    properties = new java.util.Properties();
    try {
      properties.load(new FileInputStream("application.properties"));
    } catch (IOException e) {
      String msg = "failed to load properties";
      log.error(msg, e);
    }
  }

  public static String getValue(String key){
    String value = properties.getProperty(key);
    if (value == null){
      // kill application if we are trying to load a property that does not exist
      throw new RuntimeException("failed to load property: " + key);
    }
    return properties.getProperty(key);
  }
}
