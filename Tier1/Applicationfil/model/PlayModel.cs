using System;
using System.IO;
using System.Text.Json;
using System.Threading.Tasks;
using Client.Data;
using Client.Networking;
using NAudio.Wave;

namespace Client.model
{
    public class PlayerModel : IPlayerModel
    {
        private IClient client;
        private Mp3FileReader fileReader;
        private IWavePlayer waveOut;

        public PlayerModel(IClient client)
        {
            this.client = client;
            waveOut = new WaveOutEvent();
            //waveOut.Volume = 0.5f;

        }

        public async Task PlaySongAsync(Song song)
        {
            if (fileReader != null)
                fileReader.Dispose();
            waveOut.Dispose();
            string serverFile = "wwwroot\\audio\\" + song.Title + song.Id + ".mp3";
            
            await FileExists(song, serverFile);
            fileReader = new Mp3FileReader(serverFile);
            
            waveOut.Init(fileReader);
            waveOut.Play();
        }

        private async Task FileExists(Song song, string serverFile)
        {
            if (!File.Exists(serverFile))
            {
                string songAsJson = JsonSerializer.Serialize(song);
                TransferObj transferObj = new TransferObj() {Action = "PLAYSONG", Arg = songAsJson};
                string transf = JsonSerializer.Serialize(transferObj);
                await client.PlaySong(transf, serverFile);
            }
        }

        public async Task PlayPauseToggleAsync()
        {
            Console.WriteLine("Current playstate: " + waveOut.PlaybackState);
            if (waveOut.PlaybackState == PlaybackState.Playing)
                waveOut.Pause();
            else
                waveOut.Play();
        }

        public async Task PlayFromAsync(int sec)
        {
            Console.WriteLine(fileReader.CurrentTime);
            
            waveOut.Stop();
            fileReader.CurrentTime = TimeSpan.FromSeconds(sec);
            waveOut.Play();
        }

        public async Task SetVolumeAsync(int percentage)
        {
            float toSet = (float)percentage / 100;
            waveOut.Volume = toSet;
            Console.WriteLine(waveOut.Volume);
            
        }
    }
}