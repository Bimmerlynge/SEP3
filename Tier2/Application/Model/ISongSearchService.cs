using System.Threading.Tasks;
using AppServer.Data;

namespace AppServer.Model
{
    public interface ISongSearchService
    {
        
        Task<string> GetSongsByFilterJsonAsync(TransferObj tObj);

        
    }
}