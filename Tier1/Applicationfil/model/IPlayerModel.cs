using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Client.Data;

namespace Client.model
{
    public interface IPlayerModel
    {
        Task PlaySongAsync(Song song);
        Task PlayPauseToggleAsync();
        Task PlayFromAsync(int sec);
        Task SetVolumeAsync(int percentage);
        Task PlayPreviousSong();
        Task PlayNextSongAsync();

        bool IsPlaying { get; }
        Action UpdatePlayState { get; set; } 
        Action ProgressBarUpdate { get; set; }
        IList<Song> CurrentPlaylist { get; set; }
        string UpdateDisplay();
        void StopPlaying();
        Task<int> UpdateProgressBar();
    }
}