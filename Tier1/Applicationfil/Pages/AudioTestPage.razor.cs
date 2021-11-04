using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
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
        private IList<Song> songs;
        private Song currentSong;
        
        protected override async Task OnInitializedAsync()
        {
            songs = await Model.GetAllSongs();
            Player.CurrentPlaylist = songs;

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
                currentSong = songs.First(t => t.Id == int.Parse((string)e.Value));
                await Player.PlaySongAsync(currentSong);
            }
            catch (Exception exception)
            {
                Console.WriteLine(exception.Message);
                ModalService.Show<Popup>("Error");
            }
        }
        
    }
    }