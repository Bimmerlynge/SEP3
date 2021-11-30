package server.DAO;

import shared.Album;
import shared.Artist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ArtistDAO extends BaseDAO implements IArtistDAO {

    @Override
    public ArrayList<Artist> searchForArtists(String name) {

        try (Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM Artist WHERE artistName ILIKE ?"
            );
            statement.setString(1,"%"+name+"%");
            return getArtistAndReturnFromResultSet(statement);

        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<Artist> getAllArtist() {
        try (Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM Artist"
            );
            return getArtistAndReturnFromResultSet(statement);
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
            return null;
        }
    }

    private ArrayList<Artist> getArtistAndReturnFromResultSet(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();

        ArrayList<Artist> foundArtists = new ArrayList<>();
        while(resultSet.next()){
            Artist newArtist = new Artist(resultSet.getInt("artistId"), resultSet.getString("artistName"));
            foundArtists.add(newArtist);
        }

        return foundArtists;
    }


}
