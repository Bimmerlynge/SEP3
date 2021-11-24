using System;
using System.IO;
using System.Net.Sockets;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using Client.Data;
using Client.Util;


namespace Client.Networking
{
    public class Client : IClient
    {
        public async Task<string> GetAllSongs(string transforObjekt)
        {
            using TcpClient client = GetTcpClient();
            NetworkStream stream = client.GetStream();
            byte[] bytes = Encoding.ASCII.GetBytes(transforObjekt);
            await stream.WriteAsync(bytes, 0, bytes.Length);


            //TODO Hvorfor læser vi 2 gange, kan de gøres bedere? 
            byte[] buffer = new byte [50000];
            int bytesRead = await stream.ReadAsync(buffer, 0, buffer.Length);
            string inFromServer = Encoding.ASCII.GetString(buffer, 0, bytesRead);

            return inFromServer;
        }

        public async Task<Song> PlaySong(string transfAsJson)
        {
            TcpClient client = GetTcpClient();
            NetworkStream stream = client.GetStream();

            byte[] toServer = Encoding.ASCII.GetBytes(transfAsJson);
            await stream.WriteAsync(toServer);

            return await SongFromServer(client);
            
        }

        public async Task<string> GetSongsByFilter(string transString)
        {
            using TcpClient client = GetTcpClient();

            NetworkStream stream = client.GetStream();
            byte[] toServer = Encoding.ASCII.GetBytes(transString);
            await stream.WriteAsync(toServer);

            byte[] buffer = new byte[50000];
            int bytesRead = await stream.ReadAsync(buffer, 0, buffer.Length);
            string inFromServer = Encoding.ASCII.GetString(buffer, 0, bytesRead);


            Console.WriteLine("I client: " + inFromServer);
            return inFromServer;
        }

        private async Task<Song> SongFromServer(TcpClient client)
        {
            NetworkStream stream = client.GetStream();
            
            byte[] dataFromServer = new byte[30000000];
            int bytesRead = await stream.ReadAsync(dataFromServer, 0, dataFromServer.Length);
            
            string inFromServer = Encoding.ASCII.GetString(dataFromServer, 0, bytesRead);
            Song song = JsonSerializer.Deserialize<Song>(inFromServer, new JsonSerializerOptions
            {
                PropertyNameCaseInsensitive = true,
                Converters = { new ByteArrayConverter() }
            });
            return song;
        }

        private TcpClient GetTcpClient()
        {
            return new TcpClient("localhost", 1098);
        }
        
    }
}