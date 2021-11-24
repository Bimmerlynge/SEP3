using System.Collections.Generic;
using System.Threading.Tasks;
using Client.Data;

namespace Client.model
{
    public interface IPlayListModel
    {
        Task<PlayList> CreatePlaylist(PlayList playList, User user);
        Task<IList<PlayList>> GetAllPlayForUser(User user);
        Task RemoveSongFromPlaylist(PlayList playList, Song song);
        Task AddSongToPlaylist(PlayList playList, Song song);
        Task DeletePlayList(PlayList playList);
        



    }
}		
;
