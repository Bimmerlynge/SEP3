package server.DAO;

import shared.ISong;
import shared.Song;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SongDAO extends BaseDAO implements ISongDAO {
    @Override
    public ArrayList<ISong> getAllSongs() {
        try (Connection connection = getConnection() ) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Song");
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<ISong> listOfSongs = new ArrayList<>();
            while (resultSet.next()){
                ISong song = new Song(resultSet.getInt("songId"),
                        resultSet.getString("url"),
                        resultSet.getString("title"),
                        resultSet.getInt("duration"),
                        resultSet.getDate("releaseDate"));
                listOfSongs.add(song);
            }
            return listOfSongs;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

    }
}
