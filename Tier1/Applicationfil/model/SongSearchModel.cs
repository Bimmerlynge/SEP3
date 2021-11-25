using System;
using System.Collections.Generic;
using System.Text.Json;
using System.Threading.Tasks;
using Client.Data;
using Client.Networking;

namespace Client.model
{
    public class SongSearchModel : ISongSearchModel
    {
        private IClient client;

        public SongSearchModel(IClient client)
        {
            this.client = client;
        }

        public async Task<IList<Song>> GetSongsByFilterAsync(string filterOption, string searchField)
        {
            string[] args = {filterOption, searchField};

            return await client.GetSongsByFilterAsync(args);
            
        }
    }
}