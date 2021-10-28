using System.Collections.Generic;
using System.Threading.Tasks;
using AppServer.Data;

namespace AppServer.Model
{
    public interface IPlayService
    {
        public byte[] Play(string urlOfSong);

        IList<Song> GetAllSongs();
    }
}