using System.Collections.Generic;
using System.Threading.Tasks;
using AppServer.Data;

namespace AppServer.Model
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
