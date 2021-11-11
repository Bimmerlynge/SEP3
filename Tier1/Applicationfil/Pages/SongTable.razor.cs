using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Client.Data;
using Client.model;
using Microsoft.AspNetCore.Components;

namespace Client.Pages
{
    public partial class SongTable : ComponentBase
    {
        [Inject] private IAudioTestModel Model { get; set; }
        [Parameter]
        public IList<Song> SongList { get; set; }
        
        private string generateArtists(Song song)
        {
            IList<Artist> artists = song.Artists;
            string toReturn = artists[0].ArtistName;
            int count = artists.Count;
            switch (count)
            {
                case 1:
                    break;
                case 2:
                    toReturn += ", " + artists[1].ArtistName;
                    break;
                case 3:
                    toReturn += ", " + artists[1].ArtistName + ", " + artists[2].ArtistName;
                    break;
                default:
                    toReturn += ", " + artists[1].ArtistName + " and various more"; 
                    break;
            }
            return toReturn;
           
            }
        
        private string generateAlbums(Song song)
        {
            IList<Album> albums = song.Albums;
            string toReturn = albums[0].Title;
            int count = albums.Count;
            switch (count)
            {
                case 1:
                    break;
                case 2:
                    toReturn += ", " + albums[1].Title;
                    break;
                case 3:
                    toReturn += ", " + albums[1].Title + ", " + albums[2].Title;
                    break;
                default:
                    toReturn += ", " + albums[1].Title + " and various more"; 
                    break;
            }
            return toReturn;
            
        }
    }
}