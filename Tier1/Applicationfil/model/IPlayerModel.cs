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
    }
}