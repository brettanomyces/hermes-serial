package nz.co.brettyukich.hermes.serial.data;

import nz.co.brettyukich.hermes.serial.model.BaffelReading;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by cascade on 14/05/15.
 */
public class SerialLogDao {

  private static final Logger log = LoggerFactory.getLogger(SerialLogDao.class);

  private DataSource dataSource;

  public SerialLogDao(DataSource dataSource){
    this.dataSource = dataSource;
  }

  public void insertBaffelReading(String baffel, BaffelReading reading) throws DaoException {
    String sql = "insert into some_table (c1, c2) " +
        "select bfl_id, ?" +
        ";";

    try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql)){

    } catch (SQLException e) {
      throw new DaoException("failed to insert data", e);
    }
  }



}
