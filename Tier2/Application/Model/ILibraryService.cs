using System.Collections.Generic;
using System.Threading.Tasks;
using AppServer.Data;

namespace AppServer.Model
{
    public interface ILibraryService
    {
        Task<IList<byte[]>> GetAllMP3Async();
        Task SendSongListToDBAsync();
    }
}