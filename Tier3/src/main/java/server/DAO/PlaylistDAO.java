package server.DAO;

import shared.*;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaylistDAO extends BaseDAO implements IPlaylistDAO
{
  @Override public ArrayList<Playlist> getAllPlaylistForUser(User user)
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection
          .prepareStatement("SELECT * FROM Playlist WHERE username = ?");
      statement.setString(1, user.getUserName());
      ResultSet resultSet = statement.executeQuery();
      ArrayList<Playlist> playlists = new ArrayList<>();
      while (resultSet.next())
      {
        Playlist playlist = new Playlist(resultSet.getInt("playlistId"),
            resultSet.getString("playlistTitle"), user);
        playlists.add(playlist);
      }
      return playlists;
    }
    catch (SQLException throwables)
    {
      throwables.printStackTrace();
      return null;
    }
  }

  @Override public ArrayList<Song> getSongsInPlaylist(int playlistId)
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "SELECT *\n" + "FROM playlistwithsongs\n"
              + "JOIN AllSongs \"AS\" ON PlaylistWithSongs.songId = \"AS\".songId\n"
              + "WHERE playlistId = ?");
      statement.setInt(1, playlistId);
      ResultSet resultSet = statement.executeQuery();
      ArrayList<Song> songs = new ArrayList<>();
      int songId = 0;
      while (resultSet.next())
      {
        if (songId != resultSet.getInt("songid"))
        {
          Song song = new Song(resultSet.getInt("songid"),
              resultSet.getString("songtitle"),
              resultSet.getInt("songduration"),
              resultSet.getInt("songreleaseyear"),
              new Album(resultSet.getInt("albumId"),
                  resultSet.getString("albumtitle"),
                  resultSet.getInt("albumduration")));
          songs.add(song);
          songId = song.getId();
        }

        Artist artist = new Artist(resultSet.getInt("artistid"),
            resultSet.getString("artistname"));
        songs.get(songs.size() - 1).addArtist(artist);

        Album album = new Album(resultSet.getInt("albumId"),
            resultSet.getString("albumtitle"),
            resultSet.getInt("albumduration"));
        songs.get(songs.size() - 1).setAlbums(album);
      }
      return songs;
    }

    catch (SQLException throwables)
    {
      throwables.printStackTrace();
      return null;
    }
  }
}
