using System;
using System.Net.Sockets;
using System.Threading.Tasks;
using AppServer.Data;

namespace AppServer.Networking.ClientSide
{
    public interface IClientHandler
    {
        void ListenToClientAsync();
        Task GetAllSongsAsync();




    }
}