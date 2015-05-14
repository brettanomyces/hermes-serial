package nz.co.brettyukich.hermes.serial.data;

/**
 * Created by cascade on 14/05/15.
 */
public class DaoException extends Exception {
  public DaoException (String messsage, Throwable cause){
    super(messsage, cause);
  }
}
