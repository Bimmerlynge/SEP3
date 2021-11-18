using System;
using System.Collections.Generic;
using System.Text.Json;
using System.Threading.Tasks;
using Client.Data;
using Client.Networking;

namespace Client.model
{
    public class SongSearchModel :ISongSearchModel
    {
        private IClient client;

        public SongSearchModel(IClient client)
        {
            this.client = client;
        }
        
        public async Task<IList<Song>> GetSongsByFilterAsync(string filterOption, string searchField)
        {
            string[] args = {filterOption, searchField};
            string arg = JsonSerializer.Serialize(args);
            TransferObj transferObj = new TransferObj() {Action = "GETSONGSBYFILTER", Arg = arg};
            string transString = JsonSerializer.Serialize(transferObj);

            string inFromServer = await client.GetSongsByFilter(transString);
            Console.WriteLine("I model" + inFromServer);
            
            TransferObj transferObjFromServer =  JsonSerializer.Deserialize<TransferObj>(inFromServer, new JsonSerializerOptions(){PropertyNameCaseInsensitive = true});
            if (transferObjFromServer.Action.Equals("Bad Request 400"))
            {
                Console.WriteLine("Kommer vi ind i her");
                throw new Exception("Bad Request, try again with another request");
            }
            IList<Song> songsToReturn = JsonSerializer.Deserialize<IList<Song>>(transferObjFromServer.Arg, 
                new JsonSerializerOptions(){PropertyNameCaseInsensitive = true});
            
            return songsToReturn;
        }
    }
}
