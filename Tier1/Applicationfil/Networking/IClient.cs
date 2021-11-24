using System.Collections.Generic;
using System.Threading.Tasks;
using Client.Data;

namespace Client.Networking
{
    public interface IClient
    {
        Task<string> GetAllSongs(string transforObject);
        Task<Song> PlaySong(string tansfAsJson);

        
        Task<string> GetSongsByFilter(string transString);
        
    }
}