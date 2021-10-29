using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Sockets;
using System.Threading;
using AppServer.Data;
using AppServer.Model;
using AppServer.Networking.DataSide;

namespace AppServer.Networking.ClientSide
{
    public class Server : IServer
    {
       private IPlayService playService = new PlayService();

        public void startServer()
        {
            IPAddress ip = IPAddress.Any;
            TcpListener listener = new TcpListener(ip, 1098);
            listener.Start();
            Console.WriteLine("SERVER STARED");
            
            IDataEndPoint endPoint = new DataEndPoint();
            TestMessage(endPoint);
            TestSong(endPoint);
            //TestSongList(endPoint);



            while (true)
            {
                TcpClient client = listener.AcceptTcpClient();
                IClientHandler clientHandler = new ClientHandler(client);
                new Thread(clientHandler.ListenToClientAsync).Start();
                
            }
            
        }
        private void TestMessage(IDataEndPoint dataEndPoint)
        {
            var message = dataEndPoint.GetMessage().Result;
            Console.WriteLine(message);
        }
        
        private void TestSong(IDataEndPoint endPoint)
        {
            var song = endPoint.GetSong().Result;
            Console.WriteLine(song.Title);
        }
        
        /*
        private void TestSongList(IDataEndPoint dataEndPoint)
        {
            
            IList<Song> songs = playService.GetAllSongs();

            foreach (Song song in songs)
            {
                Console.WriteLine(song.Title);
            }
        }
        */
        
    }

    
}