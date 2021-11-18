using System;
using System.Net.Sockets;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using AppServer.Data;
using AppServer.Model;

namespace AppServer.Networking.ClientSide
{
    public class ClientHandler : IClientHandler
    {
        private TcpClient client;
        private IPlayService model = new PlayService();
        

        public ClientHandler(TcpClient client)
        {
            this.client = client;
        }


        public async void ListenToClientAsync()
        {
            Console.WriteLine("LISTEN");
            TransferObj fromClient = readFromClientAsync(client.GetStream()).Result;

            switch (fromClient.Action)
            {
                case "GETSONGS":
                    await GetAllSongsAsync();
                    Console.WriteLine("SEND SONGS");
                    break;
                case "PLAYSONG":
                    Song song = JsonSerializer.Deserialize<Song>(fromClient.Arg);
                    await HandlePlaySongAsync(song, client.GetStream());
                    break;
                case "GETSONGSBYFILTER":
                    await GetSongsByFilterAsync(fromClient);
                    Console.WriteLine("SENDING FILTERED SONGS");
                    break;
            }

            client.Dispose();
        }

        private async Task GetSongsByFilterAsync(TransferObj tObj)
        {
            Console.WriteLine(tObj.Arg);
            string transAsJson = await model.GetSongsByFilterJsonAsync(tObj);
            Console.WriteLine("clienthandler; " + transAsJson);
            
            byte[] bytes = Encoding.ASCII.GetBytes(transAsJson);

            NetworkStream stream = client.GetStream();
            await stream.WriteAsync(bytes, 0, bytes.Length);
            
        }

        private async Task<TransferObj> readFromClientAsync(NetworkStream stream)
        {
            byte[] dataFromServer = new byte[5000];
            int bytesRead = await stream.ReadAsync(dataFromServer, 0, dataFromServer.Length);
            string readFromClient = Encoding.ASCII.GetString(dataFromServer, 0, bytesRead);
            Console.WriteLine(readFromClient);
            TransferObj transferObj = JsonSerializer.Deserialize<TransferObj>(readFromClient);

            return transferObj;
        }


        public async Task GetAllSongsAsync()
        {
            string transAsJson = await model.GetAllSongsAsJsonAsync();
            byte[] bytes = Encoding.ASCII.GetBytes(transAsJson);

            Console.WriteLine("GetallSongs sending: {0} bytes", bytes.Length);

            NetworkStream stream = client.GetStream();
            await stream.WriteAsync(bytes, 0, bytes.Length);
        }

        private async Task HandlePlaySongAsync(Song song, NetworkStream stream)
        {
            
            string jsonSong = await model.PlayAsync(song);
            byte[] bytes = Encoding.ASCII.GetBytes(jsonSong);
            await stream.WriteAsync(bytes, 0, bytes.Length);
        }
    }
}