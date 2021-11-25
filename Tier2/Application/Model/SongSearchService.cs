using System;
using System.Collections;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Reflection;
using System.Text.Json;
using System.Threading.Tasks;
using AppServer.Data;
using AppServer.Networking.DataSide;

namespace AppServer.Model
{
    public class SongSearchService : ISongSearchService
    {
        private readonly IDataEndPointSearchSong endPointSearchSong = new DataEndPointSearchSong();
        
        public async Task<IList<Song>> GetSongsByFilterJsonAsync(string[] args)
        {
            switch (args[0])
            {
                case "Title":
                    return await getSongFromTitle(args[1]);

                case "Artist":
                    return await getSongFromArtist(args[1]);

                case "Album":
                    return await getSongFromAlbum(args[1]);
                
                default:
                    throw new Exception("You have tried to search " + args[0] + " which is not valid");
            }
            
        }

        private async Task<IList<Song>> getSongFromArtist(string artistName)
        {
            return await  endPointSearchSong.GetSongsByArtistNameAsync(artistName);
        }

        private async Task<IList<Song>>  getSongFromAlbum(string albumTitle)
        {
            return await endPointSearchSong.GetSongsByAlbumTitleAsync(albumTitle);
            
        }

        private async Task<IList<Song>> getSongFromTitle(string songTitle)
        {
            return await endPointSearchSong.GetSongsByTitleAsync(songTitle);
            
        }
        
    }
}