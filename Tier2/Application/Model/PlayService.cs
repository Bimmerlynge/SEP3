using System;
using System.Collections.Generic;
using System.IO;
using System.Text.Json;
using System.Threading.Tasks;
using AppServer.Data;
using AppServer.Networking.DataSide;

namespace AppServer.Model
{
    public class PlayService : IPlayService
    {
        private IDataEndPoint dataEndPoint = new DataEndPoint();

        public async Task<IList<Song>> GetAllSongsAsync()
        {
            return await dataEndPoint.GetAllSongs();

          
        }

        public async Task<string> PlayAsync(Song song)
        {
            return await dataEndPoint.GetSongWithMP3(song);
        }
    }
}