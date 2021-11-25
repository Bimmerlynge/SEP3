using System.Collections.Generic;
using System.Threading.Tasks;
using AppServer.Data;

namespace AppServer.Model
{
    public interface IPlayService
    {
        Task<string> PlayAsync(Song song);

        Task<IList<Song>> GetAllSongsAsync();
        
    }
}