package server.DAO;

import shared.Song;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Interaktion med databasen hvad angår sanges relation til playlister.
 */
public class PlaylistManageDAO extends BaseDAO implements IPlaylistManageDAO
{
  @Override public void addSongToPlayList(int playlistId, Song song)
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement preparedStatement = connection.prepareStatement(
          "INSERT INTO PlaylistSongRelation (playlistId, songId) VALUES (?, ?)");
      preparedStatement.setInt(1, playlistId);
      preparedStatement.setInt(2, song.getId());
      preparedStatement.executeUpdate();

    }
    catch (SQLException throwables)
    {
      throwables.printStackTrace();
      throw new InternalError(throwables.getMessage());
    }
  }

  @Override public void removeSongFromPlaylist(int playlistId, int songId)
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement preparedStatement = connection.prepareStatement(
          "DELETE FROM PlaylistSongRelation WHERE playlistId = (?) AND songId = (?)");
      preparedStatement.setInt(1, playlistId);
      preparedStatement.setInt(2, songId);
      preparedStatement.executeUpdate();

    }
    catch (SQLException throwables)
    {
      throwables.printStackTrace();
      throw new InternalError(throwables.getMessage());
    }
  }
}
