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

        public async Task<string> GetSongWithMP3(Song song)
        {
            using HttpClient client = new HttpClient();
            Task<string> stringAsync = client.GetStringAsync(uri + $"songs/{song.Id}");
            Console.WriteLine(stringAsync.Result.Length);
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

        public async Task<IList<byte[]>> GetAllMP3()
        {
            using HttpClient client = new HttpClient();
            int count = 0;
            IList<byte[]> toReturn = new List<byte[]>();
            while (true)
            {
                try
                {
                    byte[] byteArrayAsync = await client.GetByteArrayAsync(uri + "mp3/" + count++);
                    Console.WriteLine(byteArrayAsync.Length);
                    toReturn.Add(byteArrayAsync);
                }
                catch (Exception e)
                {
                    break;
                }
            }

            return toReturn;
        }

        public async Task PostAllSongs(IList<Song> songList)
        {
            using HttpClient client = new HttpClient();
            string songListAsJson = JsonSerializer.Serialize(songList, new JsonSerializerOptions{PropertyNamingPolicy = JsonNamingPolicy.CamelCase});
        
            StringContent content = new StringContent(songListAsJson, Encoding.UTF8, "application/json");
            
            HttpResponseMessage response = await client.PostAsync(uri + "songss", content);
            if (!response.IsSuccessStatusCode)
            {
                throw new Exception($@"Error: {response.StatusCode}, {response.ReasonPhrase}");
            }
        }
    }
}