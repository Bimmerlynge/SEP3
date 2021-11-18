using System.Threading.Tasks;
using AppServer.Data;

namespace AppServer.Networking.DataSide
{
    public interface IDataEndPointSearchSong
    {
        Task<string> GetSongsByTitleAsync(string songTitle);
        Task<string> GetSongsByArtistNameAsync(string artistName);

        Task<string> GetSongsByAlbumTitleAsync(string albumTitle);
    }
}