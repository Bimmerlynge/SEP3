using System.Collections.Generic;
using Client.Data;

namespace Client.model
{
    public interface IAudioTestModel
    {
        void playSong(Song song);
        IList<Song> GetAllSongs();
    }
}