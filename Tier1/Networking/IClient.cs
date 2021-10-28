using System.Collections.Generic;
using Client.Data;

namespace Client.Networking
{
    public interface IClient
    {
        IList<Song> GetAllSongs();
        void PlaySong(Song song);
    }
}