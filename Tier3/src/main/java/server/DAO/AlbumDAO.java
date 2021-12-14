package server.DAO;

import shared.Album;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Denne klasse står for interaktionen med databasen hvad angår albummer.
 * Der kan søges på albummer på deres title.
 * Der kan søges efter alle albummer
 */

public class AlbumDAO extends BaseDAO implements IAlbumDAO
{

  @Override
  public ArrayList<Album> searchForAlbums(String title) {

    try (Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement(
              "SELECT * FROM Album WHERE albumTitle ILIKE ?"
      );
      statement.setString(1, "%"+title+"%");

      return getAlbumsAndReturnFromResultSet(statement);
    }
    catch (SQLException throwables)
    {
      throwables.printStackTrace();
      throw new InternalError(throwables.getMessage());
    }
  }

  @Override
  public ArrayList<Album> getAllAlbums() {
    try (Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement(
              "SELECT * FROM Album"
      );

      return getAlbumsAndReturnFromResultSet(statement);

    }
    catch (SQLException throwables)
    {
      throwables.printStackTrace();
      throw new InternalError(throwables.getMessage());
    }
  }

  /**
   * @param statement SQL statement, hvor parameterne er sat
   * @return Liste af alle albummber, som opfylder det givne statement.
   * @throws SQLException
   */
  private ArrayList<Album> getAlbumsAndReturnFromResultSet(PreparedStatement statement) throws SQLException {
    ResultSet resultSet = statement.executeQuery();

    ArrayList<Album> albums = new ArrayList<>();
    while (resultSet.next()) {
      Album newAlbum = new Album(resultSet.getInt("albumId"), resultSet.getString("albumTitle"));
      albums.add(newAlbum);
    }
    return albums;
  }
}
