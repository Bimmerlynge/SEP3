package server.DAO;

import shared.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interaktion med databasen hvad angår playlister.
 * Når en playliste skal ud af databasen skal den være inklusiv sangene den består af.
 */

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

        } catch (SQLException throwables)
        {
            throwables.printStackTrace();
            throw new InternalError(throwables.getMessage());
        }

    }


    @Override
    public int createNewPlaylist(Playlist playlist) throws Exception {
        if (isValidPlaylist(playlist)) throw new IllegalArgumentException();

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
        }  catch (SQLException throwables)
        {
            throwables.printStackTrace();
            throw new InternalError(throwables.getMessage());
        }
        throw new Exception("No keys generated from playlist");

    }

    private boolean isValidPlaylist(Playlist playlist) {
        return playlist == null || playlist.getTitle() == null || playlist.getTitle().equals("") || playlist.getUser() == null;
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

        }  catch (SQLException throwables)
        {
            throwables.printStackTrace();
            throw new InternalError(throwables.getMessage());
        }
    }

        @Override
    public Playlist getPlaylistFromId(int playlistId) throws NoSuchFieldException {

            try (Connection connection = getConnection()) {

                PreparedStatement infoStatement = connection.prepareStatement("SELECT * FROM playlist WHERE playListId = ?;");
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
                } else {
                    throw new NoSuchFieldException();
                }

                return newPlaylist;

            }  catch (SQLException throwables)
            {
                throwables.printStackTrace();
                throw new InternalError(throwables.getMessage());
            }
    }


    /**
     * Opretter playlite og ligger den i allUsersPlaylists listen
     *
     * @param user user som playlisten er koblet til.
     * @param connection forbindelsen til databasen
     * @param playlistResultSet resultSet givet efter SQL query angående playlister.
     * @param allUsersPlaylists Liste hvor alle playliste for en given user skal ingå.
     * @throws SQLException
     */
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

    /**
     * Laver sange for en given playliste og tilføjer dem til den givne playlist.
     *
     * @param connection forbindelse til databasen
     * @param newPlaylist den playlist de nye sange skal være en del af
     * @param songListResultSet ResultSet med sange som skal tilføjes playlisten
     * @throws SQLException
     */
    private void addingSongsToPlaylist(Connection connection, Playlist newPlaylist, ResultSet songListResultSet) throws SQLException {
        while (songListResultSet.next()) {

            Song newSong = getNewSong(songListResultSet);

            ResultSet artistResultSet = getArtistResultSet(connection, newSong);

            addingArtistsToSong(newSong, artistResultSet);

            newPlaylist.addSong(newSong);
        }
    }

    /**
     * tager en sang og tilføjer de nødvendige artister til sangen
     * @param newSong Den sang artisterne skal tilføjes til
     * @param artistResultSet ResultSet som indeholder alle artister, der skal tilføjes til sangen
     * @throws SQLException
     */
    private void addingArtistsToSong(Song newSong, ResultSet artistResultSet) throws SQLException {
        while (artistResultSet.next()) {

            Artist newArtist = new Artist(artistResultSet.getInt("artistId"),
                    artistResultSet.getString("artistName"));
            newSong.addArtist(newArtist);
        }
    }

    /**
     * Generere resultset som indeholder alle artister, der har en relation til den givne sang
     *
     * @param connection forbindelse til databasen
     * @param newSong sangen artisterne skal have en relation til
     * @return ResultSet som indeholder alle artister der har en relation til den givne sang
     * @throws SQLException
     */
    private ResultSet getArtistResultSet(Connection connection, Song newSong) throws SQLException {
        PreparedStatement artistsStatement = connection.prepareStatement("SELECT * FROM Artist JOIN " +
                "ArtistSongRelation ASR ON Artist.artistId = ASR.artistId WHERE songId = ?");
        artistsStatement.setInt(1, newSong.getId());
        return artistsStatement.executeQuery();
    }

    /**
     * Tager et resultset og laver en ny sang udfra det.
     *
     * Result set skal være på rigtig plads, denne metode sørger ikke for der er noget i result settet.
     * @param songListResultSet Resultset som indeholder sang
     * @return Song object udpakker fra resultSet
     * @throws SQLException
     */
    private Song getNewSong(ResultSet songListResultSet) throws SQLException {
        return new Song(songListResultSet.getInt("songId"),
                                songListResultSet.getString("songTitle"),
                                songListResultSet.getInt("songDuration"),
                                songListResultSet.getInt("songReleaseYear"),
                                new Album(songListResultSet.getInt("albumId"),
                                        songListResultSet.getString("albumTitle")
                                ),
                                songListResultSet.getString("songPath"));
    }

    /**
     * Generrer et resultset, som indeholder alle sange for en bestemt playliste
     *
     * @param connection forbindelse til databasem
     * @param newPlaylist Den playliste man vil finde sange for
     * @return Resultset som indeholder alle sang for den bestemte playliste
     * @throws SQLException
     */
    private ResultSet getResultSetWithAllSongsFromPlaylist(Connection connection, Playlist newPlaylist) throws SQLException {
        PreparedStatement songStatement = connection.prepareStatement("SELECT S.songId, songTitle, songDuration, songReleaseYear, songPath, S.albumId , albumTitle\n" +
                "                FROM PlaylistSongRelation\n" +
                "                         JOIN Song S ON S.songId = PlaylistSongRelation.songId\n" +
                "                         JOIN Album A ON A.albumId = S.albumId\n" +
                "                WHERE playlistId = ?;");
        songStatement.setInt(1, newPlaylist.getId());
        return songStatement.executeQuery();
    }



}



