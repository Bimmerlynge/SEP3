using System;
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
        private IDataEndPointSearchSong endPointSearchSong = new DataEndPointSearchSong();
        
        public async Task<string> GetSongsByFilterJsonAsync(TransferObj tObj)
        {
            string[] stringArray = JsonSerializer.Deserialize<string[]>(tObj.Arg);
            switch (stringArray[0])
            {
                case "Title":
                    return await getSongFromTitle(stringArray[1]);

                case "Artist":
                    return await getSongFromArtist(stringArray[1]);

                case "Album":
                    return await getSongFromAlbum(stringArray[1]);
                
                default:
                    throw new Exception("You have tried to search " + stringArray[0] + " which is not valid");
            }
            
        }

        private async Task<string> getSongFromArtist(string artistName)
        {
            return await endPointSearchSong.GetSongsByArtistNameAsync(artistName);
        }

        private async Task<string>  getSongFromAlbum(string albumTitle)
        {
            return await endPointSearchSong.GetSongsByAlbumTitleAsync(albumTitle);
            
        }

        private async Task<string> getSongFromTitle(string songTitle)
        {
            return await endPointSearchSong.GetSongsByTitleAsync(songTitle);
            
        }
    }
}