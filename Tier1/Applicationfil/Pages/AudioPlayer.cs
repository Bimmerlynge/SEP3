using System;
using System.Threading.Tasks;
using Blazored.Modal.Services;
using Client.Data;
using Client.model;
using Microsoft.AspNetCore.Components;

namespace Client.Pages
{
    public partial class AudioPlayer : ComponentBase
    {
        [Inject] public IPlayerModel Player { get; set; }
        [Inject] public IModalService ModalService { get; set; }

        private string display = "";
        private bool isPlaying;

        protected override async Task OnInitializedAsync()
        {
            Player.UpdatePlayState = () => updatePlayState();
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
    }
}