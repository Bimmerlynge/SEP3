using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Client.Data;
using Client.model;
using Microsoft.AspNetCore.Components;


namespace Client.Pages
{
    public partial class AudioTestPage : ComponentBase
    {
        [Inject] public IAudioTestModel Model { get; set; }

        private string currentSong;
        private IList<Song> songs;

        


        protected override async Task OnInitializedAsync()
        {
            songs = await Model.GetAllSongs();
        }


        private async Task playSong(ChangeEventArgs e)
        {
            Song song = songs.First(t => t.Id == int.Parse((string)e.Value));
            await Model.playSong(song);
            currentSong = song.Title + song.Id + ".mp3";
        }



        
    }
    
}