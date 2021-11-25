using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using AppServer.Data;
using Client.Util;

namespace AppServer.Networking.DataSide
{
    public class DataEndPoint : IDataEndPoint
    {
        private string uri = "http://localhost:8080/";


        public async Task<IList<Song>> GetAllSongs()
        {
            using HttpClient client = new HttpClient();
            string stringAsync = await client.GetStringAsync(uri + "songs");

            Console.WriteLine(stringAsync);
            return JsonSerializer.Deserialize<IList<Song>>(stringAsync,
                new JsonSerializerOptions {PropertyNameCaseInsensitive = true});
        }

        public async Task<Song> GetSongWithMP3(Song song)
        {
            using HttpClient client = new HttpClient();
            string stringAsync = await client.GetStringAsync(uri + $"songs/{song.Id}");
            Console.WriteLine("GetSongWithMp3.lenght::::: " + stringAsync.Length);
            Song songWithMP3 = JsonSerializer.Deserialize<Song>(stringAsync, new JsonSerializerOptions() { PropertyNameCaseInsensitive = true, Converters = { new ByteArrayConverter() }});
            Console.WriteLine("GetSongWithMp3.Song.Mp3.Lenth::::: " + songWithMP3.Title);
            return  songWithMP3;
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

        public async Task PostAllSongs(List<Song> songList)
        {
            using HttpClient client = new HttpClient();
            string songListAsJson = JsonSerializer.Serialize(songList,
                new JsonSerializerOptions {PropertyNamingPolicy = JsonNamingPolicy.CamelCase});
            StringContent content = new StringContent(songListAsJson, Encoding.UTF8, "application/json");

            Console.WriteLine("Yike");
            HttpResponseMessage response = await client.PostAsync(uri + "songss", content);
            if (!response.IsSuccessStatusCode)
            {
                throw new Exception($@"Error: {response.StatusCode}, {response.ReasonPhrase}");
            }

            Console.WriteLine("Done");
        }
    }
}