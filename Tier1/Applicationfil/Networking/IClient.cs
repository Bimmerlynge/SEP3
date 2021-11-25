using System;
using System.Collections;
using System.Collections.Generic;
using System.Threading.Tasks;
using Client.Data;

namespace Client.Networking
{
    public interface IClient
    {
        Task<IList<Song>> GetAllSongs();
        Task<Song> PlaySong(Song song);

        
        Task<IList<Song>> GetSongsByFilterAsync(string[] filterOptions);
        
        Task RegisterUser(User user);
        Task<User> validateUser(User user);
    }
}