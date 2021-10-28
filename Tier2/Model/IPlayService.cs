using System.Collections.Generic;
using System.Threading.Tasks;
using AppServer.Data;

namespace AppServer.Model
{
    public interface IPlayService
    {
        public Task<byte[]> PlayAsync(string urlOfSong);

        Task<string> GetAllSongsAsJsonAsync();
    }
}