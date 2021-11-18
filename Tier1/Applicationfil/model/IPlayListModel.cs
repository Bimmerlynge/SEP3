using System.Collections.Generic;
using System.Threading.Tasks;
using Client.Data;

namespace Client.model
{
    public interface IPlayListModel
    {
        Task<PlayList> CreatePlaylist(PlayList playList);
        Task<IList<PlayList>> GetAllPlaylist();
        Task UpdatePlaylist(PlayList playlist);
        Task DeletePlayList(int playListID);
        



    }
}		
;
