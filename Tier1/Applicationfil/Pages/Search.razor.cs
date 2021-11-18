
using System.Collections.Generic;
using System.Threading.Tasks;
using Client.Data;
using Client.model;
using Microsoft.AspNetCore.Components;

namespace Client.Pages
{
    public partial class Search : ComponentBase
    {
        [Inject] private IAudioTestModel AudioTestModel { get; set; }
        [Inject] private ISongSearchModel SongSearchModel { get; set; }

        
        private IList<Song> songsToShow;
        private string filterOption = "Title";
        private string searchField = "";
        
        protected override async Task OnInitializedAsync()
        {
            songsToShow = await AudioTestModel.GetAllSongs();
        }

        private async void Filter()
        {
            songsToShow = null;
            if (!string.IsNullOrEmpty(searchField))
            {
                songsToShow = await SongSearchModel.GetSongsByFilterAsync(filterOption, searchField);
            }
            else
            {
                songsToShow = await AudioTestModel.GetAllSongs();
            }
            
            StateHasChanged();
        }
    }
}