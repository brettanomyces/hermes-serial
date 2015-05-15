package nz.co.brettyukich.hermes.serial.serial;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

public class SerialMessageHandler {
  
  private static final Logger log = LoggerFactory.getLogger(SerialMessageHandler.class);
  
  private Gson gson = new Gson();
  private DataSource dataSource;
  
  public SerialMessageHandler(DataSource dataSource){
    this.dataSource = dataSource;
  }

  // todo parse message into POJOs, then persist them
  public void handleMessage(String message){
    JsonElement element = new JsonParser().parse(message);
    if (element.isJsonObject()){
      JsonObject object = (JsonObject)element;
      object.get("sensors");
    } else {
      
    }
  }
}
