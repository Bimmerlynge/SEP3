using System;
using System.Collections.Generic;
using System.Text.Json;
using System.Threading.Tasks;
using Client.Data;
using Client.Networking;

namespace Client.model
{
    public class AudioTestModel : IAudioTestModel
    {
        private IClient client;
        public AudioTestModel(IClient client)
        {
            this.client = client;
        }
        
        public async Task playSong(Song song)
        {
            string songAsJson = JsonSerializer.Serialize(song);
            TransferObj transferObj = new TransferObj() {Action = "PLAYSONG", Arg = songAsJson};
            string transf = JsonSerializer.Serialize(transferObj);
            
            string serverFile = "wwwroot\\audio\\" + song.Title + song.Id +".mp3";

            await client.PlaySong(transf, serverFile);
            
        }

        public async Task<IList<Song>> GetAllSongs()
        {
            TransferObj transferObj = new TransferObj() {Action = "GETSONGS"};
            string transString = JsonSerializer.Serialize(transferObj);
            
            string inFromServer = await client.GetAllSongs(transString);
            // Console.WriteLine("infroms sarea: " + inFromServer);
            
            TransferObj tObj = JsonSerializer.Deserialize<TransferObj>(inFromServer,new JsonSerializerOptions() {PropertyNameCaseInsensitive = true});
            // Console.WriteLine("Trans from server: "+ tObj.Action + "   " + tObj.Arg);
            IList<Song> allSongs = JsonSerializer.Deserialize<IList<Song>>(tObj.Arg, new JsonSerializerOptions() {PropertyNameCaseInsensitive = true});

            // int i = 0;
            // foreach (Song allSong in allSongs)
            // {
            //     Console.WriteLine(i++);
            //     Console.WriteLine(allSong.Title);
            // }
            return allSongs;
        }
    }

  
}