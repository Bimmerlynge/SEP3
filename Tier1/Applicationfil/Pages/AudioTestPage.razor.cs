using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Blazored.Modal;
using Blazored.Modal.Services;
using Client.Data;
using Client.model;
using Microsoft.AspNetCore.Components;


namespace Client.Pages
{
    public partial class AudioTestPage : ComponentBase
    {
        [Inject] public IAudioTestModel Model { get; set; }
        [Inject] public IPlayerModel Player { get; set; }
        [Inject] public IModalService ModalService { get; set; }

        private string currentSong;
        private IList<Song> songs;
        
        protected override async Task OnInitializedAsync()
        {
            songs = await Model.GetAllSongs();
        }


        private async Task playSong(ChangeEventArgs e)
        {
            try
            {           
                //Quick fix, skal aligevel udskiftes, gør intet når man vælger "Select Below"
                if ("Select Below".Equals((string) e.Value))
                {
                    return;
                }                
                Song song = songs.First(t => t.Id == int.Parse((string)e.Value));
                await Player.PlaySongAsync(song);
                currentSong = song.Title + song.Id + ".mp3";
            }
            catch (Exception exception)
            {
                Console.WriteLine(exception.Message);
                ModalService.Show<Popup>("Error");
            }
         
        }

        public async Task TogglePlay()
        {
            Player.SetVolumeAsync(0);
            Player.PlayPauseToggleAsync();
        }
    }
    }