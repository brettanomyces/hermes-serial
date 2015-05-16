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

  public void insertBaffelReading(String baffel, BaffelReading reading) throws DataAccessException {
    String sql = "" +
        "insert into some_table (bfr_bfl_id, bfr_timestamp, bfr_reading) " +
        "select bfl_id, ?, ?" +
        "from bfl_baffels " +
        "where bfl_name = ?" +
        ";";

    try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql)){
      stmt.setTimestamp(0, reading.getTimestamp());
      stmt.setString(1, reading.getBaffelState().name());
      stmt.setString(2, baffel);
      stmt.execute();

    } catch (SQLException e) {
      log.error("failed to insert baffel reading", e);
      // swallow
    }
  }
}
