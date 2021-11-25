using System.Collections;
using System.Collections.Generic;
using System.Threading.Tasks;
using AppServer.Data;


namespace AppServer.Networking.DataSide
{
    public interface IDataEndPoint
    {
        Task<IList<Song>> GetAllSongs();
        Task<IList<byte[]>> GetAllMP3();
        Task PostAllSongs(List<Song> songList);
        Task<Song> GetSongWithMP3(Song song);
    }
}