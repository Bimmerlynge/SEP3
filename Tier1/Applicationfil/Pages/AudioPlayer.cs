using System;
using System.Net.Security;
using System.Threading;
using System.Threading.Tasks;
using Blazored.Modal.Services;
using Client.Data;
using Client.model;
using Microsoft.AspNetCore.Components;
using Microsoft.AspNetCore.Components.Web;
using Microsoft.JSInterop;

namespace Client.Pages
{
    public partial class AudioPlayer : ComponentBase
    {
        [Inject] public IPlayerModel Player { get; set; }
        [Inject] public IModalService ModalService { get; set; }

        public double progressValuePercentage { get; set; }
        public int pVP { get; set; }
        private string songTitle = "";
        private string artistTitle = "";
        private bool isPlaying =false;
        private ElementReference progress;
        
        private string currentDuration = "";
        private double progressValue;
        private string totalDuration = "";
        private Song currentSong;

        protected override async Task OnInitializedAsync()
        {
            Player.UpdatePlayState = () => updatePlayState();
            Player.ProgressBarUpdate = () => updateProgressBar();
        }
        private async Task TogglePlay()
        {
            await Player.PlayPauseToggleAsync();
        }
        private async Task previousSong()
        {
            await Player.PlayPreviousSong();
        }

        private async Task nextSong()
        {
            await Player.PlayNextSongAsync();
        }

        private async Task controlVolume()
        {
            
            
        }

        private async Task updatePlayState()
        { 
            isPlaying = Player.IsPlaying;
            currentSong = await Player.GetCurrentSongAsync();
            songTitle = currentSong.Title;
            artistTitle = currentSong.Artists[0].ArtistName; //Giver kun første artist på listen - skal flyttes ud i modellen.
            TimeSpan totalDurationSpan = new TimeSpan(0, currentSong.Duration / 60, currentSong.Duration % 60);
            totalDuration = totalDurationSpan.ToString();
            StateHasChanged();
            
        }
        private async Task updateProgressBar()
        {
            // progressValue = await Player.UpdateProgressBar();
            // Console.WriteLine(progressValue);
            // TimeSpan currentDurationSpan = new TimeSpan(0, (int)(currentSong.Duration * progressValue / 100 / 60), (int)(currentSong.Duration * progressValue / 100 % 60));
            // currentDuration = currentDurationSpan.ToString();
            // Console.WriteLine(currentDuration);
            // await InvokeAsync(() => StateHasChanged());
            
            progressValue = await Player.UpdateProgressBar();
            progressValuePercentage = progressValue / currentSong.Duration * 100;
            pVP = (int) progressValuePercentage;
            Console.WriteLine(progressValuePercentage);
            TimeSpan currentDurationSpan = new TimeSpan(0, (int)(currentSong.Duration * progressValuePercentage / 100 / 60), (int)(currentSong.Duration * progressValuePercentage / 100 % 60));
            currentDuration = currentDurationSpan.ToString();
            await InvokeAsync(() => StateHasChanged());


        }
        
        private async Task progressBarClicked(MouseEventArgs mouseEventArgs)
        {
            float returnHack = 1;
            returnHack = await JS.InvokeAsync<float>("GetProgress", returnHack);
            Console.WriteLine(returnHack + " returHack efter barclick");
            await Player.PlayFromAsync(returnHack);
        }


    }
}