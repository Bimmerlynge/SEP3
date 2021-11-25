using System.Collections.Generic;
using System.Threading.Tasks;
using AppServer.Data;

namespace AppServer.Model
{
    public interface ISongSearchService
    {
        
        Task<IList<Song>> GetSongsByFilterJsonAsync(string[] args);

        
    }
}