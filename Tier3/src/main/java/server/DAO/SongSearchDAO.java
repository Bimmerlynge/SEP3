package server.DAO;

import shared.Album;
import shared.Artist;
import shared.Song;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SongSearchDAO extends BaseDAO implements ISongSearchDAO {
    @Override
    public ArrayList<Song> getSongsByTitle(String songTitle) {

        if (songTitle == null || songTitle.isEmpty())
        {
            throw new IllegalArgumentException("song title is null or empty");

        }
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM AllSongs WHERE songTitle ILIKE ?");

            preparedStatement.setString(1, "%" + songTitle + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            return getSongsFromResultSet(resultSet);

        }  catch (SQLException throwables)
        {
            throwables.printStackTrace();
            throw new InternalError(throwables.getMessage());
        }
    }


    @Override
    public ArrayList<Song> getSongsByArtist(String artistName) {

        if (artistName == null || artistName.isEmpty())
        {
            throw new IllegalArgumentException("artist name is null or empty");

        }
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM AllSongs WHERE artistName ILIKE ?");

            preparedStatement.setString(1, "%" + artistName + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            return getSongsFromResultSet(resultSet);

        }  catch (SQLException throwables)
        {
            throwables.printStackTrace();
            throw new InternalError(throwables.getMessage());
        }
    }

    @Override
    public ArrayList<Song> getSongsByAlbum(String albumTitle) {

        if(albumTitle == null || albumTitle.isEmpty())
        {
            throw new IllegalArgumentException("album title is null or empty");
        }
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM AllSongs WHERE albumTitle ILIKE ?");

            preparedStatement.setString(1, "%" + albumTitle + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            return getSongsFromResultSet(resultSet);

        } catch (SQLException throwables)
        {
            throwables.printStackTrace();
            throw new InternalError(throwables.getMessage());
        }
    }


    private ArrayList<Song> getSongsFromResultSet(ResultSet resultSet) throws SQLException {
        ArrayList<Song> listOfSongs = new ArrayList<>();
        int songId = 0;

        while (resultSet.next()) {
            if (songId != resultSet.getInt("songid")) {
                Song song = new Song(resultSet.getInt("songid"),
                        resultSet.getString("songtitle"),
                        resultSet.getInt("songduration"), resultSet.getInt("songreleaseyear"), resultSet.getString("mp3"));
                listOfSongs.add(song);
                songId = song.getId();


              Album album = new Album(resultSet.getInt("albumId"), resultSet.getString("albumtitle"),
                  resultSet.getInt("albumduration"));
              song.setAlbums(album);
            }

          Artist artist = new Artist(resultSet.getInt("artistid"),
              resultSet.getString("artistname"));
          listOfSongs.get(listOfSongs.size() - 1).addArtist(artist);


        }
        return listOfSongs;
    }
}
