using System;
using System.Collections.Generic;
using System.Net.Sockets;
using System.Text;
using System.Text.Json;
using System.Threading;
using AppServer.Data;
using AppServer.Model;

namespace AppServer.Networking
{
    public class ClientHandler : IClientHandler
    {
        private TcpClient client;
        private IPlayService model = new PlayService();

        public ClientHandler(TcpClient client)
        {
            this.client = client;
        }
        

        public  void ListenToClient()
        {
           
            
                TransferObj fromClient = readFromClient(client.GetStream());
            

                switch (fromClient.Action)
                {
                    
                    case "GETSONGS":
                        
                        getAllSongs();
                        break;
                    case "PLAYSONG":
                        Song song = JsonSerializer.Deserialize<Song>(fromClient.Arg);
                        HandlePlaySong(song, client.GetStream());
                        break;
                    
                    
                }

                client.Dispose();

        }
        
        private TransferObj readFromClient(NetworkStream stream)
        {
            TransferObj transferObj;
            byte[] dataFromServer = new byte[5000];
            int bytesRead = stream.Read(dataFromServer, 0, dataFromServer.Length);
            string s = Encoding.ASCII.GetString(dataFromServer, 0, bytesRead);
            Console.WriteLine(s);
            transferObj = JsonSerializer.Deserialize<TransferObj>(s);
            

            return transferObj;
        }


  
        public void getAllSongs()
        {
            IList<Song> allSongs = model.GetAllSongs();
            string songListAsString = JsonSerializer.Serialize(allSongs);

            TransferObj sentObj = new TransferObj() {Arg = songListAsString};
            string transObj = JsonSerializer.Serialize(sentObj);
            byte[] bytes = Encoding.ASCII.GetBytes(transObj);
            Console.WriteLine("GetallSongs sending: {0} bytes", bytes.Length);
            NetworkStream stream = client.GetStream();
            stream.Write(bytes,0,bytes.Length);
            
        }
        
        
        private void HandlePlaySong(Song song, NetworkStream stream)
        { 
            byte[] songBytes = model.Play(song.Url);
            Console.WriteLine("Length of {0}: {1}",song.Title, songBytes.Length);
            stream.Write(songBytes, 0, songBytes.Length);
        }

    }
}