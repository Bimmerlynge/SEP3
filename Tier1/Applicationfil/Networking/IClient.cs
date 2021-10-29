using System.Collections.Generic;
using System.Threading.Tasks;
using Client.Data;

namespace Client.Networking
{
    public interface IClient
    {
        Task<string> GetAllSongs(string transforObject);
        Task PlaySong(string tansfAsJson, string serverFile);
    }
}