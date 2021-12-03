package server.DAO;

import shared.*;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class PlaylistDAO extends BaseDAO implements IPlaylistDAO {


    @Override
    public ArrayList<Song> getSongsInPlaylist(int playlistId) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT *\n" + "FROM playlistwithsongs\n"
                            + "JOIN AllSongs \"AS\" ON PlaylistWithSongs.songId = \"AS\".songId\n"
                            + "WHERE playlistId = ?");
            statement.setInt(1, playlistId);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Song> songs = new ArrayList<>();
            int songId = 0;
            while (resultSet.next()) {
                if (songId != resultSet.getInt("songid")) {
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<Playlist> getAllPlaylistForUser(User user) {
        try (Connection connection = getConnection()) {

            PreparedStatement statement = connection
                    .prepareStatement("SELECT * FROM PlaylistWithSongsAndUser  WHERE username = ?");
            statement.setString(1, user.getUsername());
            ResultSet resultSet = statement.executeQuery();

            HashMap<Integer, Album> albumHashMap = new HashMap<>();
            HashMap<Integer, Playlist> playlistHashMap = new HashMap<>();

            while (resultSet.next()) {

                int playlistId = resultSet.getInt("playlistId");

                if (!playlistHashMap.containsKey(playlistId)) {

                    Playlist playlist = new Playlist(resultSet.getInt("playlistId"),
                            resultSet.getString("playlistTitle"), user);
                    playlistHashMap.put(playlistId, playlist);
                }

                playlistHashMap.get(playlistId).addSong(checkIfAlbumExistsAndReturnNewSong(resultSet, albumHashMap));


            }
            return new ArrayList<>(playlistHashMap.values());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public Playlist getPlaylistFromId(int playlistId) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM PlaylistWithSongsAndUser WHERE playListId = ?;");
            statement.setInt(1, playlistId);
            ResultSet resultSet = statement.executeQuery();

            ArrayList<Song> songs = new ArrayList<>();
            HashMap<Integer, Album> albumHashMap = new HashMap<>();
            Playlist playlist = null;
            boolean isFirst = true;
            while (resultSet.next()) {
                if (isFirst) {
                    playlist = new Playlist(resultSet.getInt("playlistId"), resultSet.getString("playlistTitle"),
                            new User(resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("role"))
                    );
                    isFirst = false;
                }



                songs.add(checkIfAlbumExistsAndReturnNewSong(resultSet, albumHashMap));



            }

            return playlist;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    private Song checkIfAlbumExistsAndReturnNewSong(ResultSet resultSet, HashMap<Integer, Album> albumHashMap) throws SQLException {
        int newAlbumId = resultSet.getInt("AlbumId");
        if (!albumHashMap.containsKey(newAlbumId)) {
            Album newAlbum = new Album(newAlbumId, resultSet.getString("albumTitle"), resultSet.getInt("albumDuration"));
            albumHashMap.put(newAlbumId, newAlbum);
        }

        return new Song(
                resultSet.getInt("songId"), resultSet.getString("songTitle"), resultSet.getInt("songDuration"),
                resultSet.getInt("songReleaseYear"), albumHashMap.get(newAlbumId));
    }


    @Override
    public void createNewPlaylist(Playlist playlist) {
        try (Connection connection = getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO playlist(playlistTitle, username) VALUES  (?, ?)");
            preparedStatement.setString(1, playlist.getTitle());
            preparedStatement.setString(2, playlist.getUser().getUsername());
            preparedStatement.execute();



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void removePlaylistFromId(int playlistId) {

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM playlist where playlistId = ?;");
            preparedStatement.setInt(1, playlistId);
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}

