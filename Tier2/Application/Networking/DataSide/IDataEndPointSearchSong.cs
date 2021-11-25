using System.Collections;
using System.Collections.Generic;
using System.Threading.Tasks;
using AppServer.Data;

namespace AppServer.Networking.DataSide
{
    public interface IDataEndPointSearchSong
    {
        Task<IList<Song>> GetSongsByTitleAsync(string songTitle);
        Task<IList<Song>> GetSongsByArtistNameAsync(string artistName);

        Task<IList<Song>> GetSongsByAlbumTitleAsync(string albumTitle);
    }
}