using System.Collections;
using System.Collections.Generic;
using System.Threading.Tasks;
using AppServer.Data;


namespace AppServer.Networking.DataSide
{
    public interface IDataEndPoint
    {
        Task<string> GetAllSongs();
        Task<string> GetMessage();
        Task<Song> GetSong();
        Task<string> GetSongsByFilter(TransferObj tObj);
    }
}