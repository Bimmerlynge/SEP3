package server.DAO;

import shared.Album;
import shared.Artist;
import shared.Song;
import java.sql.*;
import java.util.ArrayList;

/**
 * Interaktion med databasen hvad angår informantion omkring en sang, her er lydfil til sang ikke inkluderet.
 *
 * Der kan tilføjes og fjernes sange
 *
 * Der kan søges på en bestemt sang på dens Id
 * Der kan søges på at få alle sange i databasen
 */
public class SongDAO extends BaseDAO implements ISongDAO
{

  @Override
  public Song getSongById(int id){
    Song song = null;
    try(Connection connection = getConnection())
    {
      PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM AllSongs WHERE songId = ?");
      preparedStatement.setInt(1,id);
      ResultSet resultSet = preparedStatement.executeQuery();
      int songId = 0;
      Song songToReturn = null;
      while (resultSet.next())
      {
        if (songId != resultSet.getInt("songid"))
        {
          songToReturn = new Song(resultSet.getInt("songid"),
              resultSet.getString("songtitle"),
              resultSet.getInt("songduration"), resultSet.getInt("songreleaseyear"), null,
              resultSet.getString("songPath"));
          songId = songToReturn.getId();

          Album album = new Album(resultSet.getInt("albumId"), resultSet.getString("albumtitle"));
          songToReturn.setAlbum(album);
        }

        Artist artist = new Artist(resultSet.getInt("artistid"),
            resultSet.getString("artistname"));
        songToReturn.addArtist(artist);
      }
      return songToReturn;
    }
    catch (SQLException throwables)
    {
      throwables.printStackTrace();
      throw new InternalError(throwables.getMessage());
    }
  }

  @Override
  public int addNewSong(Song newSong) {

    try (Connection connection = getConnection())
    {
      int newSongId = addNewSongToDatabase(newSong, connection);

      newSongAlbum(newSong, connection);

      songArtistRelation(newSong, connection);

      return newSongId;
    }
    catch (SQLException throwables)
    {
      throwables.printStackTrace();
      throw new InternalError(throwables.getMessage());
    }
  }

  @Override
  public void removeSongFromId(int songId) throws NoSuchFieldException {
    try(Connection connection = getConnection()) {
      PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Song WHERE songId = ?;");
      preparedStatement.setInt(1, songId);
      int rowsEffected = preparedStatement.executeUpdate();
      if (rowsEffected == 0){
        throw new NoSuchFieldException();
      }
    }  catch (SQLException throwables)
    {
      throwables.printStackTrace();
      throw new InternalError(throwables.getMessage());
    }
  }

  private int addNewSongToDatabase(Song newSong, Connection connection) throws SQLException {
    PreparedStatement preparedStatement = connection
            .prepareStatement("INSERT INTO Song(songTitle, songDuration, songReleaseYear, songPath) VALUES" +
                    "    (?,?,?,?);", PreparedStatement.RETURN_GENERATED_KEYS);
    preparedStatement.setString(1, newSong.getTitle());
    preparedStatement.setInt(2, newSong.getDuration());
    preparedStatement.setInt(3, newSong.getReleaseYear());
    preparedStatement.setString(4, newSong.getSongPath());
    preparedStatement.execute();
    ResultSet resultSetWithKeys = preparedStatement.getGeneratedKeys();
    if (resultSetWithKeys.next()){
      newSong.setId(resultSetWithKeys.getInt("songId"));
    }
    return newSong.getId();
  }

  /**
   * Tilføjer relation mellem sange og deres artister
   * @param newSong Den sang relationerne skal laves på.
   * @param connection Forbindelse til databasen
   * @throws SQLException
   */
  private void songArtistRelation(Song newSong, Connection connection) throws SQLException {
    for (Artist artist: newSong.getArtists()) {

      putNewArtistInDatabase(connection, artist);

      PreparedStatement preparedStatementSongArtistRelation = connection.prepareStatement("INSERT INTO ArtistSongRelation(artistId, songId) VALUES " +
              "(?, ?);");
      preparedStatementSongArtistRelation.setInt(1, artist.getId());
      preparedStatementSongArtistRelation.setInt(2, newSong.getId());
      preparedStatementSongArtistRelation.execute();
    }
  }

  /**
   * Hvis en artist ikke er i databasen skal de oprettes i systemet.
   * Dette sker under oprettelse af sangen
   * @param connection forbindelse til databasen
   * @param artist Den artist som skal oprettes i databasen
   * @throws SQLException
   */
  private void putNewArtistInDatabase(Connection connection, Artist artist) throws SQLException {
    if (artist.getId() == 0){
      PreparedStatement preparedStatementArtist =  connection.prepareStatement("INSERT INTO Artist(artistName) VALUES (?);", PreparedStatement.RETURN_GENERATED_KEYS);
      preparedStatementArtist.setString(1, artist.getName());
      preparedStatementArtist.execute();

      ResultSet resultSetWithKeys = preparedStatementArtist.getGeneratedKeys();
      if (resultSetWithKeys.next()){
        artist.setId(resultSetWithKeys.getInt("artistId"));
      }
    }
  }

  /**
   * Hvis et album ikke er i databasne skal det oprettes i systemet.
   * Dette sker under oprettelse af sangen.
   * @param newSong Sangen albummet er en del af
   * @param connection forbindelsen til databsen
   * @throws SQLException
   */

  private void newSongAlbum(Song newSong, Connection connection) throws SQLException {
    if (newSong.getAlbum().getId() == 0) {
      PreparedStatement preparedStatementAlbum = connection.prepareStatement("INSERT INTO Album(albumTitle) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS);
      preparedStatementAlbum.setString(1, newSong.getAlbum().getTitle());
      preparedStatementAlbum.execute();

      ResultSet resultSetWithKeys = preparedStatementAlbum.getGeneratedKeys();
      if (resultSetWithKeys.next()){
        newSong.getAlbum().setId(resultSetWithKeys.getInt("albumId"));
      }
    }

    PreparedStatement preparedStatementAlbumSongConnection = connection.prepareStatement("UPDATE Song SET albumId = (?) WHERE songId = (?);");
    preparedStatementAlbumSongConnection.setInt(1, newSong.getAlbum().getId());
    preparedStatementAlbumSongConnection.setInt(2, newSong.getId());
    preparedStatementAlbumSongConnection.execute();
  }

  @Override
  public ArrayList<Song> getAllSongs()
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement preparedStatement = connection
          .prepareStatement("SELECT * FROM AllSongs");
      ResultSet resultSet = preparedStatement.executeQuery();
      return getSongsFromResultSet(resultSet);

    }
    catch (SQLException throwables)
    {
      throwables.printStackTrace();
      throw new InternalError();
    }

  }

  private ArrayList<Song> getSongsFromResultSet(ResultSet resultSet) throws SQLException {
    ArrayList<Song> listOfSongs = new ArrayList<>();
    int songId = 0;

    while (resultSet.next()) {
      if (songId != resultSet.getInt("songid")) {
        Song song = new Song(resultSet.getInt("songid"),
            resultSet.getString("songtitle"),
            resultSet.getInt("songduration"), resultSet.getInt("songreleaseyear"), null,resultSet.getString("songPath"));
        listOfSongs.add(song);
        songId = song.getId();


        Album album = new Album(resultSet.getInt("albumId"), resultSet.getString("albumtitle"));
        song.setAlbum(album);
      }

      Artist artist = new Artist(resultSet.getInt("artistid"),
          resultSet.getString("artistname"));
      listOfSongs.get(listOfSongs.size() - 1).addArtist(artist);

    }
    return listOfSongs;
  }


}
