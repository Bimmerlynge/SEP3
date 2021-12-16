package server.DAO;

import shared.Artist;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Denne klasse står for interaktionen med databasen hvad angår Artister.
 * Der kan søges på artist på navn.
 * Der kan søges efter alle artister
 */
public class ArtistDAO extends BaseDAO implements IArtistDAO {


    @Override
    public ArrayList<Artist> searchForArtists(String name) {

        if (name == null || name.length() == 0) throw new IllegalArgumentException();


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
            throw new InternalError(throwables.getMessage());
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
            throw new InternalError(throwables.getMessage());
        }
    }

    /**
     * @param statement SQL statement, hvor parameterne er sat
     * @return Liste af alle artister, som opfylder det givne statement.
     * @throws SQLException
     */
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
