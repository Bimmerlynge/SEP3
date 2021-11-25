using System.Collections.Generic;
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

        public async Task<IList<Song>> GetAllSongs()
        {
            return await client.GetAllSongs();
        }
    }
}