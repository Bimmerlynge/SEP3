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
        private IList<Song> songsToShow;
        
        
        public AudioTestModel(IClient client)
        {
            this.client = client;
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
            
            return allSongs;
        }

        public async Task<IList<Song>> GetSongsByFilterAsync(string filterOption, string searchField)
        {
            string[] args = {filterOption, searchField};
            string arg = JsonSerializer.Serialize(args);
            TransferObj transferObj = new TransferObj() {Action = "GETSONGSBYFILTER", Arg = arg};
            string transString = JsonSerializer.Serialize(transferObj);

            string inFromServer = await client.GetSongsByFilter(transString);

            Console.WriteLine("I model" + inFromServer);
            
            TransferObj tObj = JsonSerializer.Deserialize<TransferObj>(inFromServer);
            IList<Song> songsToReturn = JsonSerializer.Deserialize<IList<Song>>(tObj.Arg, 
                new JsonSerializerOptions(){PropertyNameCaseInsensitive = true});
            return songsToReturn;
        }
    }
}