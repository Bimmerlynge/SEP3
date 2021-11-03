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
            HttpClient client = new HttpClient();
            Task<string> stringAsync = client.GetStringAsync(uri + "song");
            
            return await stringAsync;
        }   

        public async Task<string> GetMessage()
        {
            HttpClient client = new HttpClient();
            Task<string> stringAsync = client.GetStringAsync(uri + "message");
            string json = await stringAsync;
            string message = JsonSerializer.Deserialize<string>(json);
            return message;
        }

        public async Task<Song> GetSong()
        {
            HttpClient client = new HttpClient();
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