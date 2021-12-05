package server.DAO;

import shared.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaylistDAO extends BaseDAO implements IPlaylistDAO {


    @Override
    public ArrayList<Playlist> getAllPlaylistForUser(User user) {
        try (Connection connection = getConnection()) {

            PreparedStatement infoStatement = connection.prepareStatement("SELECT * FROM Playlist WHERE username = ?");
            infoStatement.setString(1, user.getUsername());
            ResultSet playlistResultSet = infoStatement.executeQuery();

            ArrayList<Playlist> allUsersPlaylists = new ArrayList<>();

            addingPlaylistToAllPlaylist(user, connection, playlistResultSet, allUsersPlaylists);

            return allUsersPlaylists;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

    }


    @Override
    public int createNewPlaylist(Playlist playlist) throws Exception {
        try (Connection connection = getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO playlist(playlistTitle, username) VALUES  (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, playlist.getTitle());
            preparedStatement.setString(2, playlist.getUser().getUsername());
            preparedStatement.execute();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                return generatedKeys.getInt("playlistId");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new Exception("Exeption in INSER TO PLAYLIST");
        }
        throw new Exception("No keys generated from playlist");

    }

    @Override
    public void removePlaylistFromId(int playlistId) throws NoSuchFieldException {

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM playlist where playlistId = ?;");
            preparedStatement.setInt(1, playlistId);
            int rowsEffected = preparedStatement.executeUpdate();

            if (rowsEffected == 0){
                throw new NoSuchFieldException("No such playlist found");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

        @Override

    public Playlist getPlaylistFromId(int playlistId) {

            try (Connection connection = getConnection()) {

                PreparedStatement infoStatement = connection.prepareStatement("SELECT * FROM PlaylistWithSongsAndUser WHERE playListId = ?;");
                infoStatement.setInt(1, playlistId);
                ResultSet playlistResultSet = infoStatement.executeQuery();
                Playlist newPlaylist = null;

                if (playlistResultSet.next()) {

                    newPlaylist  = new Playlist(
                            playlistResultSet.getInt("playlistId"),
                            playlistResultSet.getString("playlistTitle"),
                            new User(playlistResultSet.getString("username"), null, null));

                    ResultSet songListResultSet = getResultSetWithAllSongsFromPlaylist(connection, newPlaylist);

                    addingSongsToPlaylist(connection, newPlaylist, songListResultSet);
                }

                return newPlaylist;

            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return null;
            }
    }



    private void addingPlaylistToAllPlaylist(User user, Connection connection, ResultSet playlistResultSet, ArrayList<Playlist> allUsersPlaylists) throws SQLException {
        while (playlistResultSet.next()) {
            Playlist newPlaylist = new Playlist(
                    playlistResultSet.getInt("playlistId"),
                    playlistResultSet.getString("playlistTitle"),
                    user);

            ResultSet songListResultSet = getResultSetWithAllSongsFromPlaylist(connection, newPlaylist);

            addingSongsToPlaylist(connection, newPlaylist, songListResultSet);
            allUsersPlaylists.add(newPlaylist);
        }
    }

    private void addingSongsToPlaylist(Connection connection, Playlist newPlaylist, ResultSet songListResultSet) throws SQLException {
        while (songListResultSet.next()) {

            Song newSong = getNewSong(songListResultSet);

            ResultSet artistResultSet = getArtistResultSet(connection, newSong);

            addingArtistsToSong(newSong, artistResultSet);

            newPlaylist.addSong(newSong);
        }
    }

    private void addingArtistsToSong(Song newSong, ResultSet artistResultSet) throws SQLException {
        while (artistResultSet.next()) {

            Artist newArtist = new Artist(artistResultSet.getInt("artistId"),
                    artistResultSet.getString("artistName"));
            newSong.addArtist(newArtist);
        }
    }

    private ResultSet getArtistResultSet(Connection connection, Song newSong) throws SQLException {
        PreparedStatement artistsStatement = connection.prepareStatement("SELECT * FROM Artist JOIN " +
                "ArtistSongRelation ASR ON Artist.artistId = ASR.artistId WHERE songId = ?");
        artistsStatement.setInt(1, newSong.getId());
        return artistsStatement.executeQuery();
    }

    private Song getNewSong(ResultSet songListResultSet) throws SQLException {
        return new Song(songListResultSet.getInt("songId"),
                                songListResultSet.getString("songTitle"),
                                songListResultSet.getInt("songDuration"),
                                songListResultSet.getInt("songReleaseYear"),
                                new Album(songListResultSet.getInt("albumId"),
                                        songListResultSet.getString("albumTitle"),
                                        songListResultSet.getInt("albumDuration")
                                ),
                                songListResultSet.getString("mp3"));
    }

    private ResultSet getResultSetWithAllSongsFromPlaylist(Connection connection, Playlist newPlaylist) throws SQLException {
        PreparedStatement songStatement = connection.prepareStatement("SELECT S.songId, songTitle, songDuration, songReleaseYear, mp3, ASR.albumId, albumTitle, albumDuration\n" +
                "FROM PlaylistSongRelation\n" +
                "         JOIN Song S ON S.songId = PlaylistSongRelation.songId\n" +
                "         JOIN AlbumSongRelation ASR ON S.songId = ASR.songId\n" +
                "         JOIN Album A ON A.albumId = ASR.albumId\n" +
                "WHERE playlistId = ?");
        songStatement.setInt(1, newPlaylist.getId());
        return songStatement.executeQuery();
    }



}



