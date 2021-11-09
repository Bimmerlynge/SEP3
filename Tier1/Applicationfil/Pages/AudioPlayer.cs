using System;
using System.Net.Security;
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

        private string display = "";
        private bool isPlaying;
        private ElementReference progress;
        public int progressValue { get; set; } 

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

        private void updatePlayState()
        {
            
            isPlaying = Player.IsPlaying;
            display = Player.UpdateDisplay();
            StateHasChanged();
        }
        private async Task updateProgressBar()
        {
            progressValue = await Player.UpdateProgressBar();
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