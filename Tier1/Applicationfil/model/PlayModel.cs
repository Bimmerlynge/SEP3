using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text.Json;
using System.Threading;
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
        private IList<Song> previouslySongs = new List<Song>();
        private Song currentSong;


        public bool IsPlaying
        {
            get { return waveOut.PlaybackState == PlaybackState.Playing; }
        }

        public Action UpdatePlayState { get; set; }
        public Action ProgressBarUpdate { get; set; }
        public IList<Song> CurrentPlaylist { get; set; }

        public PlayerModel(IClient client)
        {
            this.client = client;
            waveOut = new WaveOutEvent();
            waveOut.Volume = 0.1f;

        }

        public async Task PlaySongAsync(Song song)
        {
            if (fileReader != null)
            {
                fileReader.Dispose();
            }

            waveOut.Dispose();
            string serverFile = "wwwroot\\audio\\" + song.Title + song.Id + ".mp3";
            
            await FileExists(song, serverFile);
            fileReader = new Mp3FileReader(serverFile);
            
            waveOut.Init(fileReader);
            waveOut.Play();
            currentSong = song;
            UpdatePlayState.Invoke();
            Thread t1 = new Thread(() =>
            {
                while (true)
                {
                    ProgressBarUpdate.Invoke();
                    Thread.Sleep(500);
                }
            });
            t1.Start();

            if (previouslySongs.Count == 0 || previouslySongs[^1].Id != song.Id)
            {
                previouslySongs.Add(song);
            }
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
            {
                waveOut.Pause();
            }
            else
            {
                waveOut.Play();
            }

            UpdatePlayState.Invoke();
        }

        public async Task PlayFromAsync(float progress)
        {
            int sec = (int)(currentSong.Duration * (progress/100));
            waveOut.Stop();
            fileReader.CurrentTime = TimeSpan.FromSeconds(sec);
            waveOut.Play();
            UpdatePlayState.Invoke();
        }

        public async Task SetVolumeAsync(int percentage)
        {
            float toSet = (float) percentage / 100;
            waveOut.Volume = toSet;
            Console.WriteLine(waveOut.Volume);
        }

        public async Task PlayPreviousSong()
        {
            if (previouslySongs.Count >= 2)
            {
                previouslySongs.RemoveAt(previouslySongs.Count - 1);
                Console.WriteLine(previouslySongs.Count);
                await PlaySongAsync(previouslySongs[^1]);
            }
        }

        public async Task PlayNextSongAsync()
        {
            int index = CurrentPlaylist.IndexOf(currentSong);
            if (index == CurrentPlaylist.Count - 1)
            {
                index = 0;
            }
            else
            {
                index++;
            }

            Console.WriteLine(index);
            await PlaySongAsync(CurrentPlaylist[index]);
        }

        public string UpdateDisplay()
        {
            if (currentSong.Artists.Count < 2)
            {
                return currentSong.Title + " " + currentSong.Artists[0].ArtistName + currentSong.Duration;
            }
            else
            {
                return currentSong.Title + " Various Artists";
            }
            
        }

        public void StopPlaying()
        {
            if (waveOut != null && fileReader != null)
            {
                waveOut.Stop();
                waveOut.Dispose();
                fileReader.Dispose();
            }
        }

        public async Task<double> UpdateProgressBar()
        {
            return fileReader.CurrentTime.TotalSeconds;
        }

        public async Task<Song> GetCurrentSongAsync()
        {
             return currentSong;
        }
    }
}