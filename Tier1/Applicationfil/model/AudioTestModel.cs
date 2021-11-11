using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text.Json;
using System.Threading.Tasks;
using Client.Data;
using Client.Networking;
using Microsoft.AspNetCore.Mvc.ModelBinding;

namespace Client.model
{
    public class AudioTestModel : IAudioTestModel
    {
        private IClient client;
        private IList<Song> previouslySongs = new List<Song>();

        


        public AudioTestModel(IClient client)
        {
            this.client = client;
        }

        public async Task playSong(Song song)
        {
            string serverFile = "wwwroot\\audio\\" + song.Title + song.Id + ".mp3";
            if (!File.Exists(serverFile))
            {
                string songAsJson = JsonSerializer.Serialize(song);
                TransferObj transferObj = new TransferObj() {Action = "PLAYSONG", Arg = songAsJson};
                string transf = JsonSerializer.Serialize(transferObj);


                await client.PlaySong(transf, serverFile);
            }

            if (previouslySongs.Count == 0 || previouslySongs[^1].Id != song.Id)
            {
                previouslySongs.Add(song);
            }
            
        }

        public async Task<IList<Song>> GetAllSongs()
        {
            TransferObj transferObj = new TransferObj() {Action = "GETSONGS"};
            string transString = JsonSerializer.Serialize(transferObj);

            string inFromServer = await client.GetAllSongs(transString);
            // Console.WriteLine("infroms sarea: " + inFromServer);

            TransferObj tObj = JsonSerializer.Deserialize<TransferObj>(inFromServer,
                new JsonSerializerOptions() {PropertyNameCaseInsensitive = true});
            // Console.WriteLine("Trans from server: "+ tObj.Action + "   " + tObj.Arg);
            IList<Song> allSongs = JsonSerializer.Deserialize<IList<Song>>(tObj.Arg,
                new JsonSerializerOptions() {PropertyNameCaseInsensitive = true});

            // int i = 0;
            // foreach (Song allSong in allSongs)
            // {
            //     Console.WriteLine(i++);
            //     Console.WriteLine(allSong.Title);
            // }
            return allSongs;
        }

        public async Task PauseSongAsync()
        {
            throw new NotImplementedException();
        }

        public async Task PlayFromAsync(int sec)
        {
            throw new NotImplementedException();
        }

        public async Task SetVolumeAsync(int percentage)
        {
            throw new NotImplementedException();
        }

        public async Task PlayPreviousSong()
        {
            if (previouslySongs.Any())
            {
                Console.WriteLine(previouslySongs.Count);
                await playSong(previouslySongs[^1]);
                previouslySongs.RemoveAt(previouslySongs.Count - 1);
            }
            
            
        }
    }
}