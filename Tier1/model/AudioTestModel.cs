using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using Client.Data;
using Client.Networking;

namespace Client.model
{
    public class AudioTestModel : IAudioTestModel
    {
        private IClient client;
        public AudioTestModel(IClient client)
        {
            
            this.client = client;
        }
        
        

        private int counter = 0;
        public void playSong(Song song)
        {
            
            
            client.PlaySong(song);
            
        }

        public IList<Song> GetAllSongs()
        {
            
            return client.GetAllSongs();
        }
    }
}