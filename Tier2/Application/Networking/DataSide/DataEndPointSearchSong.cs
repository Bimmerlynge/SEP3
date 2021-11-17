using System;
using System.Net.Http;
using System.Threading.Tasks;

namespace AppServer.Networking.DataSide
{
    public class DataEndPointSearchSong:IDataEndPointSearchSong
    {
        private string uri = "http://localhost:8080/";

        
        public async Task<string> GetSongsByTitleAsync(string songTitle)
        {
            using HttpClient httpClient = new HttpClient();
            HttpResponseMessage responseMessage = await httpClient.GetAsync(uri + $"songSearch/songTitle={songTitle}");

            return await ResponseFromServer(responseMessage);

        }

        public async Task<string> GetSongsByArtistNameAsync(string artistName)
        {
            using HttpClient httpClient = new HttpClient();
            HttpResponseMessage responseMessage = await httpClient.GetAsync(uri + $"songSearch/artistName={artistName}");

            return await ResponseFromServer(responseMessage);

        }

        public async Task<string> GetSongsByAlbumTitleAsync(string albumTitle)
        {
            using HttpClient httpClient = new HttpClient();
            HttpResponseMessage responseMessage = await httpClient.GetAsync(uri + $"songSearch/albumTitle={albumTitle}");

            return await ResponseFromServer(responseMessage);
        }

        private async Task<string> ResponseFromServer(HttpResponseMessage responseMessage)
        {
            RequestCodeCheck(responseMessage);

            string responseFromServer = await responseMessage.Content.ReadAsStringAsync();

            return responseFromServer;
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