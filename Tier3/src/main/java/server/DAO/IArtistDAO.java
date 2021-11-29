package server.DAO;

import shared.Artist;

import java.util.ArrayList;

public interface IArtistDAO {

    ArrayList<Artist> searchForArtists(String name);
}
