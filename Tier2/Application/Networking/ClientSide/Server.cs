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
            ILibraryService service = new LibraryService();
            service.SendSongListToDBAsync();
            while (true)
            {
                TcpClient client = listener.AcceptTcpClient();
                IClientHandler clientHandler = new ClientHandler(client);
                new Thread(clientHandler.ListenToClientAsync).Start();
            }
            
        }

    }

    
}