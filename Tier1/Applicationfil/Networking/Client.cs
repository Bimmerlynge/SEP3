using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using System.Net.Sockets;
using System.Text;
using System.Text.Json;
using System.Text.Json.Serialization;
using System.Threading.Tasks;
using Client.Data;
using Client.Pages;
using Client.Util;


namespace Client.Networking
{
    public class Client : IClient
    {
        public async Task<IList<Song>> GetAllSongs()
        {
            using TcpClient client = GetTcpClient();
            await SendServerRequest("GETSONGS", "", client);
            return await serverResponse<IList<Song>>(client, 100000);
        }


        public async Task<Song> PlaySong(Song song)
        {
            TcpClient client = new TcpClient("localhost", 1098);
            await SendServerRequest<Song>("PLAYSONG", song, client);
            return await serverResponse<Song>(client, 80000000);
        }

        public async Task<IList<Song>> GetSongsByFilterAsync(string[] filterOptions)
        {
            using TcpClient client = GetTcpClient();
             await SendServerRequest("GETSONGSBYFILTER", filterOptions,client);
            return await serverResponse<IList<Song>>(client, 500000);
        }

        public async Task RegisterUser(User user)
        {
            using TcpClient client = GetTcpClient();
            await SendServerRequest("REGISTERUSER", user,client);
        }

        public async Task<User> validateUser(User user)
        {
            using TcpClient client = GetTcpClient();
           await SendServerRequest("VALIDATEUSER", user,client);
            return await serverResponse<User>(client, 5000);
        }

        
        private async Task SendServerRequest<T>(string action, T TObject, TcpClient client)
        {
            NetworkStream stream = client.GetStream();
            TransferObj<T> transferObj = new TransferObj<T>
            {
                Action = action, Arg = TObject
            };
            string transferAsJson = JsonSerializer.Serialize(transferObj);
            byte[] toServer = Encoding.UTF8.GetBytes(transferAsJson);
            await stream.WriteAsync(toServer);
           
        }
        

        private async Task<T> serverResponse<T>(TcpClient client, int bufferSize)
        {
            NetworkStream stream = client.GetStream();

            byte[] dataFromServer = new byte[bufferSize];
            int bytesRead = await stream.ReadAsync(dataFromServer, 0, dataFromServer.Length);

            string inFromServer = Encoding.UTF8.GetString(dataFromServer, 0, bytesRead);
            TransferObj<T> objectFromServer = JsonSerializer.Deserialize<TransferObj<T>>(inFromServer, new JsonSerializerOptions
            {
                PropertyNameCaseInsensitive = true

            });
            
            return objectFromServer.Arg;
        }

        private TcpClient GetTcpClient()
        {
            return new TcpClient("localhost", 1098);
        }
    }
}