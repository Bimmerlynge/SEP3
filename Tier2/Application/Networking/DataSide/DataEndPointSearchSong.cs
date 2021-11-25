using System;
using System.Collections;
using System.Collections.Generic;
using System.Net.Http;
using System.Text.Json;
using System.Threading.Tasks;
using AppServer.Data;

namespace AppServer.Networking.DataSide
{
    public class DataEndPointSearchSong : IDataEndPointSearchSong
    {
        private string uri = "http://localhost:8080/";


        public async Task<IList<Song>> GetSongsByTitleAsync(string songTitle)
        {
            using HttpClient httpClient = new HttpClient();
            HttpResponseMessage responseMessage = await httpClient.GetAsync(uri + $"songSearch/songTitle={songTitle}");

            return await ResponseFromServer(responseMessage);
        }

        public async Task<IList<Song>> GetSongsByArtistNameAsync(string artistName)
        {
            using HttpClient httpClient = new HttpClient();
            HttpResponseMessage responseMessage =
                await httpClient.GetAsync(uri + $"songSearch/artistName={artistName}");

            return await ResponseFromServer(responseMessage);
        }

        public async Task<IList<Song>> GetSongsByAlbumTitleAsync(string albumTitle)
        {
            using HttpClient httpClient = new HttpClient();
            HttpResponseMessage responseMessage =
                await httpClient.GetAsync(uri + $"songSearch/albumTitle={albumTitle}");

            return await ResponseFromServer(responseMessage);
        }

        private async Task<IList<Song>> ResponseFromServer(HttpResponseMessage responseMessage)
        {
            RequestCodeCheck(responseMessage);

            string responseFromServer = await responseMessage.Content.ReadAsStringAsync();

            return JsonSerializer.Deserialize<IList<Song>>(responseFromServer,
                new JsonSerializerOptions {PropertyNameCaseInsensitive = true});
        }


        private void RequestCodeCheck(HttpResponseMessage responseMessage)
        {
            Console.WriteLine("Checking request");
            if (!responseMessage.IsSuccessStatusCode)
            {
                Console.WriteLine("Not good");
                throw new Exception($"Error: {responseMessage.StatusCode}, {responseMessage.ReasonPhrase}");
            }
        }
    }
}