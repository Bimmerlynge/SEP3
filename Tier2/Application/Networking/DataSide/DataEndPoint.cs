using System;
using System.Collections;
using System.Collections.Generic;
using System.Net.Http;
using System.Text.Json;
using System.Threading.Tasks;
using AppServer.Data;

namespace AppServer.Networking.DataSide
{
    public class DataEndPoint : IDataEndPoint
    {
        private string uri = "http://localhost:8080/";

        
        public async Task<string> GetAllSongs()
        {
            using HttpClient client = new HttpClient();
            Task<string> stringAsync = client.GetStringAsync(uri + "songs");
            
            return await stringAsync;
        }

        public async Task<string> GetSongsByFilter(TransferObj tObj)
        {
            using HttpClient client = new HttpClient();
            string[] argsAsJson = JsonSerializer.Deserialize<string[]>(tObj.Arg);

            Console.WriteLine("Type: "+ argsAsJson[0] + ", paramter: " + argsAsJson[1]);
            
            Task<string> stringAsync = client.GetStringAsync(uri + $"songs/{argsAsJson[0]}={argsAsJson[1]}");
            return await stringAsync;
        }

        public async Task<PlayList> GetPlayList()
        {

            using HttpClient client = new HttpClient();
            Task<string> stringAsync = client.GetStringAsync(uri + "playList/1");
            string json = await stringAsync;
            PlayList playList = JsonSerializer.Deserialize<PlayList>(json, new JsonSerializerOptions
            {
                PropertyNameCaseInsensitive = true
            });
            return playList;
        }

        public async Task<string> GetMessage()
        {
            using HttpClient client = new HttpClient();
            Task<string> stringAsync = client.GetStringAsync(uri + "message");
            string json = await stringAsync;
            string message = JsonSerializer.Deserialize<string>(json);
            return message;
        }

        public async Task<Song> GetSong()
        {
            using HttpClient client = new HttpClient();
            Task<string> stringAsync = client.GetStringAsync(uri + "song/1");
            string json = await stringAsync;
            Song song = JsonSerializer.Deserialize<Song>(json, new JsonSerializerOptions
            {
                PropertyNameCaseInsensitive = true
            });
            return song;
        }
    }
}