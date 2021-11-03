using System.IO;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;


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

        public async Task PlaySong(string transfAsJson, string serverFile)
        {
            TcpClient client = GetTcpClient();
            NetworkStream stream = client.GetStream();

            byte[] toServer = Encoding.ASCII.GetBytes(transfAsJson);
            await stream.WriteAsync(toServer);

            await SongFromServer(client, serverFile);
        }

        private async Task SongFromServer(TcpClient client, string serverFile)
        {
            NetworkStream stream = client.GetStream();
            
            byte[] dataFromServer = new byte[20000000];
            int bytesRead = await stream.ReadAsync(dataFromServer, 0, dataFromServer.Length);

            using (FileStream byteToMp3 = File.Create(serverFile))
            {
                await byteToMp3.WriteAsync(dataFromServer, 0, bytesRead);
            }

            client.Dispose();
        }

        protected TcpClient GetTcpClient()
        {
            return new TcpClient("localhost", 1098);
        }


        /*
         * Ikke brugt, skal nok heller ikke bruges
         
        private void Play(string serverFile)
        {
            var mp3Reader = new Mp3FileReader(serverFile);
            var waveOut = new WaveOutEvent();
            waveOut.Init(mp3Reader);
            waveOut.Play();
            Thread.Sleep(10000);
        }
        */
    }
}