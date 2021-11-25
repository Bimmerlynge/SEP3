using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using AppServer.Data;
using AppServer.Model;
using Client.Util;

namespace AppServer.Networking.ClientSide
{
    public class ClientHandler : IClientHandler
    {
        private TcpClient client;
        private IPlayService playSongService = new PlayService();
        private IUserService userService = new UserService();
        private ISongSearchService songSearchService = new SongSearchService();


        public ClientHandler(TcpClient client)
        {
            this.client = client;
        }


        public async void ListenToClientAsync()
        {
            Console.WriteLine("LISTEN");
            TransferObj<Object> result = await readFromClientAsync(client.GetStream());

            switch (result.Action)
            {
                case "GETSONGS":
                    await GetAllSongsAsync();
                    Console.WriteLine("SEND SONGS");
                    break;
                case "PLAYSONG":
                    Console.WriteLine("arguemnt of song " + result.Arg);
                    Song song = ElementToObject<Song>((JsonElement) result.Arg);
                    await HandlePlaySongAsync(song, client.GetStream());
                    break;
                case "GETSONGSBYFILTER":
                    await GetSongsByFilterAsync((string[]) result.Arg);
                    Console.WriteLine("SENDING FILTERED SONGS");
                    break;
                case "REGISTERUSER":
                    User user = (User) result.Arg;
                    Console.WriteLine("REGISTERING NEW USER");
                    await RegisterUser(user);
                    break;
            }

            client.Dispose();
        }





        private T ElementToObject<T>(JsonElement element)
        {
            string stringElement = element.GetRawText();
            return JsonSerializer.Deserialize<T>(stringElement,new JsonSerializerOptions {PropertyNameCaseInsensitive = true});

        }

        private async Task GetSongsByFilterAsync(string[] args)
        {
            string transAsJson;
            try
            {
                IList<Song> songs = await songSearchService.GetSongsByFilterJsonAsync(args);
                TransferObj<IList<Song>> transferObj = new TransferObj<IList<Song>>() {Action = "Ok 200", Arg = songs};
                transAsJson = JsonSerializer.Serialize(transferObj);
            }
            catch (Exception e)
            {
                TransferObj<string> transferObj = new TransferObj<string>()
                    {Action = "Bad Request 400", Arg = e.Message};
                transAsJson = JsonSerializer.Serialize(transferObj);
            }

            byte[] bytes = Encoding.UTF8.GetBytes(transAsJson);

            NetworkStream stream = client.GetStream();
            await stream.WriteAsync(bytes, 0, bytes.Length);
        }

        private async Task<TransferObj<Object>> readFromClientAsync(NetworkStream stream)
        {
            byte[] dataFromServer = new byte[5000];
            int bytesRead = await stream.ReadAsync(dataFromServer, 0, dataFromServer.Length);
            string readFromClient = Encoding.UTF8.GetString(dataFromServer, 0, bytesRead);
            Console.WriteLine(readFromClient);
            TransferObj<Object> transferObj = JsonSerializer.Deserialize<TransferObj<Object>>(readFromClient,
                new JsonSerializerOptions
                {
                    PropertyNameCaseInsensitive = true,Converters = { new ByteArrayConverter() }
                });

            return transferObj;
        }


        public async Task GetAllSongsAsync()
        {
            IList<Song> songs = await playSongService.GetAllSongsAsync();

            await SendToClient("RESPONSE FROM SERVER", songs);
        }

        private async Task RegisterUser(User user)
        {
            await userService.RegisterUser(user);
        }


        private async Task SendToClient<T>(string action, T TObject)
        {
            NetworkStream stream = client.GetStream();
            TransferObj<T> transferObj = new TransferObj<T>
            {
                Action = action, Arg = TObject
            };
            string transferAsJson = JsonSerializer.Serialize(transferObj);

            Console.WriteLine("transfer as JSON " + transferAsJson);
            byte[] toServer = Encoding.UTF8.GetBytes(transferAsJson);
            await stream.WriteAsync(toServer);
        }


        private async Task HandlePlaySongAsync(Song song, NetworkStream stream)
        {
            string jsonSong = await playSongService.PlayAsync(song);
            byte[] bytes = Encoding.UTF8.GetBytes(jsonSong);
            await stream.WriteAsync(bytes, 0, bytes.Length);
        }
    }
}