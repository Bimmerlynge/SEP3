package server.DAO;

import shared.Album;
import shared.Artist;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AlbumDAO extends BaseDAO implements IAlbumDAO
{

  @Override public ArrayList<Album> getAllAlbumsWithArtist()
  {
    try (Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement(
          "SELECT * FROM AlbumsWithArtist"
      );
      ResultSet resultSet = statement.executeQuery();

      ArrayList<Album> albums = new ArrayList<>();
      int albumId = 0;
      while(resultSet.next()){
        if (albumId != resultSet.getInt("albumId"))
        {
          Album album = new Album(resultSet.getInt("albumId"),
              resultSet.getString("title"), resultSet.getInt("duration"), resultSet.getDate("releaseDate"));
          albums.add(album);
          albumId = album.getAlbumId();
        }

        Artist artist = new Artist(resultSet.getInt("artistid"),
            resultSet.getString("artistname"));
        albums.get(albums.size() - 1).addArtist(artist);
      }

      return albums;

    }
    catch (SQLException throwables)
    {
      throwables.printStackTrace();
      return null;
    }


  }
}
