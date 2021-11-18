using System.Collections.Generic;
using System.Threading.Tasks;
using Client.Data;

namespace Client.model
{
    public interface ISongSearchModel
    {
        Task<IList<Song>> GetSongsByFilterAsync(string filterOption, string searchField);

    }
}