package server.DAO;

import shared.Album;
import shared.Artist;
import shared.Song;

import java.sql.*;
import java.util.ArrayList;

public class SongDAO extends BaseDAO implements ISongDAO

{

  @Override public void postAllSongs(ArrayList<Song> songs)
  {

    for (Song song : songs)
    {
      try (Connection connection = getConnection())
      {
        PreparedStatement songStatement = connection.prepareStatement(
            "INSERT INTO Song"
                + "(songTitle, songDuration, songReleaseYear, MP3) VALUES (?, ?, ?, ?)",
            PreparedStatement.RETURN_GENERATED_KEYS);
        songStatement.setString(1, song.getTitle());
        songStatement.setInt(2, song.getDuration());
        songStatement.setInt(3, song.getReleaseYear());
        songStatement.setBytes(4, song.getMp3());
        songStatement.executeUpdate();
        ResultSet songSet = songStatement.getGeneratedKeys();
        if (songSet.next())
        {
          for (Artist artist : song.getArtists())
          {
            PreparedStatement artistStatement = connection
                .prepareStatement("INSERT INTO Artist (artistName) VALUES (?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            artistStatement.setString(1, artist.getName());
            artistStatement.executeUpdate();
            ResultSet artistSet = artistStatement.getGeneratedKeys();
            if (artistSet.next())
            {

              PreparedStatement artistSongStatement = connection
                  .prepareStatement(
                      "INSERT INTO ArtistSongRelation (artistId, songId) VALUES (?, ?)");
              artistSongStatement.setInt(1, artistSet.getInt("artistId"));
              artistSongStatement.setInt(2, songSet.getInt("songId"));
              artistSongStatement.executeUpdate();
            }
          }
          PreparedStatement albumStatement = connection
              .prepareStatement("INSERT INTO Album (albumTitle) VALUES (?)",
                  PreparedStatement.RETURN_GENERATED_KEYS);
          albumStatement.setString(1, song.getAlbumProperty().getTitle());
          albumStatement.executeUpdate();
          ResultSet albumSet = albumStatement.getGeneratedKeys();
          if (albumSet.next())
          {
            PreparedStatement albumSongStatement = connection.prepareStatement(
                "INSERT INTO AlbumSongRelation (albumId, songId)"
                    + "VALUES (?, ?)");
            albumSongStatement.setInt(1, albumSet.getInt("albumId"));
            albumSongStatement.setInt(2, songSet.getInt("songId"));
            albumSongStatement.executeUpdate();
          }
        }

      }
      catch (SQLException throwables)
      {
        throwables.printStackTrace();
      }

    }
    System.out.println("DONE INSERTING");
  }

  @Override public Song getSongWithMP3(int songId)
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement preparedStatement = connection
          .prepareStatement("SELECT * FROM AllSongs WHERE songId = ?");
      preparedStatement.setInt(1, songId);
      ResultSet resultSet = preparedStatement.executeQuery();
      Song song = null;
      ArrayList<Artist> listOfArtists = new ArrayList<>();
      while (resultSet.next())
      {
        song = new Song(resultSet.getInt("songid"),
            resultSet.getString("songtitle"), resultSet.getInt("songduration"),
            resultSet.getInt("songreleaseyear"),
            new Album(resultSet.getInt("albumId"),
                resultSet.getString("albumtitle"),
                resultSet.getInt("albumduration")));
        song.setMp3(resultSet.getBytes("mp3"));

        Artist artist = new Artist(resultSet.getInt("artistid"),
            resultSet.getString("artistname"));
        listOfArtists.add(artist);

      }
      return song;
    }
    catch (SQLException throwables)
    {
      throwables.printStackTrace();
      return null;
    }

  }

  @Override
  public void addNewSong(Song newSong) {
    try (Connection connection = getConnection())
    {
      addNewSongToDatabase(newSong, connection);

      newSongAlbum(newSong, connection);

      songArtistRelation(newSong, connection);

    }
    catch (SQLException throwables)
    {
      throwables.printStackTrace();
    }
  }

  private void addNewSongToDatabase(Song newSong, Connection connection) throws SQLException {
    PreparedStatement preparedStatement = connection
            .prepareStatement("INSERT INTO Song(songTitle, songDuration, songReleaseYear, mp3) VALUES" +
                    "    (?,?,?,?);", PreparedStatement.RETURN_GENERATED_KEYS);
    preparedStatement.setString(1, newSong.getTitle());
    preparedStatement.setInt(2, newSong.getDuration());
    preparedStatement.setInt(3, newSong.getReleaseYear());
    preparedStatement.setBytes(4, newSong.getMp3());
    preparedStatement.execute();
    ResultSet resultSetWithKeys = preparedStatement.getGeneratedKeys();
    if (resultSetWithKeys.next()){
      newSong.setId(resultSetWithKeys.getInt("songId"));
    }
  }

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

  private void newSongAlbum(Song newSong, Connection connection) throws SQLException {
    if (newSong.getAlbumProperty().getId() == 0) {
      PreparedStatement preparedStatementAlbum = connection.prepareStatement("INSERT INTO Album(albumTitle) VALUES " +
              "(?);", PreparedStatement.RETURN_GENERATED_KEYS);
      preparedStatementAlbum.setString(1, newSong.getAlbumProperty().getTitle());
      preparedStatementAlbum.execute();

      ResultSet resultSetWithKeys = preparedStatementAlbum.getGeneratedKeys();
      if (resultSetWithKeys.next()){
        newSong.getAlbumProperty().setId(resultSetWithKeys.getInt("albumId"));
      }
    }

    PreparedStatement preparedStatementAlbumSongConnection = connection.prepareStatement("INSERT INTO AlbumSongRelation(albumId, songId) VALUES " +
            "(?, ?);");
    preparedStatementAlbumSongConnection.setInt(1, newSong.getAlbumProperty().getId());
    preparedStatementAlbumSongConnection.setInt(2, newSong.getId());
    preparedStatementAlbumSongConnection.execute();
  }

  @Override public ArrayList<Song> getAllSongs()
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement preparedStatement = connection
          .prepareStatement("SELECT * FROM AllSongs");
      ResultSet resultSet = preparedStatement.executeQuery();

      ArrayList<Song> listOfSongs = new ArrayList<>();
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
          listOfSongs.add(song);
          songId = song.getId();
        }

        Artist artist = new Artist(resultSet.getInt("artistid"),
            resultSet.getString("artistname"));
        listOfSongs.get(listOfSongs.size() - 1).addArtist(artist);

        Album album = new Album(resultSet.getInt("albumId"),
            resultSet.getString("albumtitle"),
            resultSet.getInt("albumduration"));
        listOfSongs.get(listOfSongs.size() - 1).setAlbums(album);
      }
      return listOfSongs;

    }
    catch (SQLException throwables)
    {
      throwables.printStackTrace();
      return null;
    }

  }

}
