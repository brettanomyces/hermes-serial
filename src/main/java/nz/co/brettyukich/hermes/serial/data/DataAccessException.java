package nz.co.brettyukich.hermes.serial.data;

/**
 * Created by cascade on 14/05/15.
 */
public class DataAccessException extends Exception {
  public DataAccessException(String messsage, Throwable cause){
    super(messsage, cause);
  }
}
