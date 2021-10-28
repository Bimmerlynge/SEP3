using System;
using System.Collections.Generic;
using System.IO;
using System.Net.Sockets;
using System.Text;
using System.Text.Json;
using System.Threading;
using Client.Data;
using NAudio.Wave;

namespace Client.Networking
{
    public class Client : IClient
    {
        
        
        public IList<Song> GetAllSongs()
        {
            using TcpClient client = GetTcpClient();
            TransferObj transferObj = new TransferObj() {Action = "GETSONGS"};
            string transString = JsonSerializer.Serialize(transferObj);
            byte[] bytes = Encoding.ASCII.GetBytes(transString);
            NetworkStream stream = client.GetStream();
            stream.Write(bytes, 0, bytes.Length);

                

            byte[] buffer = new byte [5000];
            int bytesRead = stream.Read(buffer, 0, buffer.Length);
            Console.WriteLine("GetAllSongs read: {0} bytes", bytesRead);
            string inFromServer = Encoding.ASCII.GetString(buffer, 0, bytesRead);
            TransferObj tObj = JsonSerializer.Deserialize<TransferObj>(inFromServer);
            
            IList<Song> allSongs = JsonSerializer.Deserialize<IList<Song>>(tObj.Arg);
            


            return allSongs;
        }

        public void PlaySong(Song song)
        {
            TcpClient client = GetTcpClient();

            NetworkStream stream = client.GetStream();
            string s = JsonSerializer.Serialize(song);
            TransferObj transferObj = new TransferObj() {Action = "PLAYSONG", Arg = s};
            string transf = JsonSerializer.Serialize(transferObj);
            byte[] toServer = Encoding.ASCII.GetBytes(transf);
            stream.Write(toServer);
            
            SongFromServer(client, song);

        }
        
        private void SongFromServer(TcpClient client, Song song)
        {
            NetworkStream stream = client.GetStream();
            string serverFile = "wwwroot\\audio\\" + song.Title + song.Id +".mp3";
            Console.WriteLine(File.Exists(serverFile));

            if (!File.Exists(serverFile))
            {
                Console.WriteLine("Efter GetStream()");
                byte[] dataFromServer = new byte[8000000];
                int bytesRead = 0;
            
                bytesRead = stream.Read(dataFromServer, 0, dataFromServer.Length);
            
            
                Console.WriteLine("Bytes read: " + bytesRead);
                
            
                int counter = 0;
                while (true)
                {
                    try
                    {
                        using (FileStream byteToMp3 = File.Create(serverFile))
                        {
                            byteToMp3.Write(dataFromServer, 0, bytesRead);
                        }
                
                        break;
                    }
                    catch (IOException e)
                    {
                        //Console.WriteLine(e);
                        Console.WriteLine(counter++);
                    }
                }
            
            }

            client.Dispose();
        }

        protected TcpClient GetTcpClient()
        {
            return new TcpClient("localhost", 1098);
        }

        private void Play(string serverFile)
        {
            var mp3Reader = new Mp3FileReader(serverFile);
            var waveOut = new WaveOutEvent();
            waveOut.Init(mp3Reader);
            waveOut.Play();
            Thread.Sleep(10000);
        }

    }
}