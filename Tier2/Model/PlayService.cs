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
        // flyttes ud senere
        private IList<Song> songs = new List<Song>();
        private IDataEndPoint dataEndPoint = new DataEndPoint();

        public async Task<string> GetAllSongsAsJsonAsync()
        {
            
            string allSongs = await dataEndPoint.GetAllSongs();
            Console.WriteLine("Play: alls onsg: " + allSongs);
            TransferObj sentObj = new TransferObj() {Arg = allSongs};
            string transAsJson = JsonSerializer.Serialize(sentObj);
            
            
            return transAsJson;
        }

        
        public async Task<byte[]> PlayAsync(string urlOfSong)
        {
            var filePath = urlOfSong;
            Console.WriteLine($"Does file exist on path {filePath}? " + File.Exists(filePath));
            return await File.ReadAllBytesAsync(filePath);
        }
        
    }
}