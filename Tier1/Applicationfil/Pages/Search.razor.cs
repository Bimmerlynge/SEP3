
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Client.Data;
using Client.model;
using Microsoft.AspNetCore.Components;

namespace Client.Pages
{
    public partial class Search : ComponentBase
    {
        [Inject] private IAudioTestModel Model { get; set; }
        
        private IList<Song> songsToShow;
        private string filterOption = "Title";
        private string searchField = "";
        
        protected override async Task OnInitializedAsync()
        {
            songsToShow = await Model.GetAllSongs();
        }

        private async void Filter()
        {
            songsToShow = null;
            if (!string.IsNullOrEmpty(searchField))
            {
                songsToShow = await Model.GetSongsByFilterAsync(filterOption, searchField);
            }
            else
            {
                songsToShow = await Model.GetAllSongs();
            }

            searchField = "";
            StateHasChanged();
        }
    }
}