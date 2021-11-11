using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Text;
using AppServer.Networking;
using AppServer.Networking.ClientSide;
using NAudio.Wave;

namespace AppServer
{
    class Program
    {
        
         //static Dictionary<int, TcpClient> clients = new Dictionary<int, TcpClient>();

        static void Main(string[] args)
        {
            IServer server = new Server();
            server.startServer();
        }
    }
}