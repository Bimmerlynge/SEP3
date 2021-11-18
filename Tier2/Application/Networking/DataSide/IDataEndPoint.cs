using System.Collections;
using System.Collections.Generic;
using System.Threading.Tasks;
using AppServer.Data;


namespace AppServer.Networking.DataSide
{
    public interface IDataEndPoint
    {
        Task<string> GetAllSongs();
        Task<string> GetSongsByFilter(TransferObj tObj);
        Task<IList<byte[]>> GetAllMP3();
        Task PostAllSongs(IList<Song> songList);
        Task<string> GetSongWithMP3(Song song);
    }
}