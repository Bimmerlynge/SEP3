using System;
using System.Collections.Generic;
using System.IO;
using System.Runtime.CompilerServices;
using System.Text.Json;
using System.Threading.Tasks;
using AppServer.Data;
using AppServer.Networking.DataSide;
using NAudio.Wave;

namespace AppServer.Model
{
    public class PlayService : IPlayService
    {
        // flyttes ud senere
        private IList<Song> songs = new List<Song>();
        private IDataEndPoint dataEndPoint = new DataEndPoint();

        public IList<Song> GetAllSongs()
        {
            string json = dataEndPoint.GetAllSongs().Result;
            songs = JsonSerializer.Deserialize<List<Song>>(json, new JsonSerializerOptions
             {
                 PropertyNameCaseInsensitive = true
             });
            return songs;
        }
        
        public byte[] Play(string urlOfSong)
        {
            var filePath = urlOfSong;
            Console.WriteLine($"Does file exist on path {filePath}? " + File.Exists(filePath));
            return File.ReadAllBytes(filePath);
        }
    }
}