using System.Collections.Generic;
using System.Threading.Tasks;
using AppServer.Data;
using AppServer.Model;
using Client.Data;

namespace Client.model
{
    public class PlayListModel:IPlayListModel
    {
        private static List<PlayList> allPlaylists;

        
        public async Task<PlayList> CreatePlaylist(PlayList playList)
        {
            allPlaylists = null;
            return null;
            //TODO

        }

        public Task<IList<PlayList>> GetAllPlaylist()
        {
            throw new System.NotImplementedException();
        }

        public Task UpdatePlaylist(PlayList playlist)
        {
            throw new System.NotImplementedException();
        }

        public Task DeletePlayList(int playListID)
        {
            throw new System.NotImplementedException();
        }
    }
}