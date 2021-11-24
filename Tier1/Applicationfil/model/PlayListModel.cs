using System.Collections.Generic;
using System.Threading.Tasks;
using Client.Data;
using Client.Networking;

namespace Client.model
{
    public class PlayListModel:IPlayListModel
    {
        private IClient Client;


        public PlayListModel(IClient client)
        {
            Client = client;
        }


        public Task<PlayList> CreatePlaylist(PlayList playList, User user)
        {
            throw new System.NotImplementedException();
        }

        public Task<IList<PlayList>> GetAllPlayForUser(User user)
        {
            throw new System.NotImplementedException();
        }

        public Task RemoveSongFromPlaylist(PlayList playList, Song song)
        {
            throw new System.NotImplementedException();
        }

        public Task AddSongToPlaylist(PlayList playList, Song song)
        {
            throw new System.NotImplementedException();
        }

        public Task DeletePlayList(PlayList playList)
        {
            throw new System.NotImplementedException();
        }
    }
}
