using System;
using System.Collections;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using AppServer.Data;
using AppServer.Networking.DataSide;

namespace AppServer.Model
{
    public class LibraryService : ILibraryService
    {
        private IDataEndPoint dataEndPoint = new DataEndPoint();
        private IList<byte[]> songsByte = new List<byte[]>();
        private List<Song> songList = new ();
        public async Task<IList<byte[]>> GetAllMP3Async()
        {
            return await dataEndPoint.GetAllMP3();
        }

        public async Task SendSongListToDBAsync()
        {
            string fileStreamPath = @"C:\Users\Mikkel\RiderProjects\SEP3FinalV2\Tier2\Application\Util\Audio\tempFile.mp3";
            songsByte = await GetAllMP3Async();
            
            //Builder Pattern!!!! GO go implement
            
            foreach (byte[] mp3Bytes in songsByte)
            {
                using (FileStream byteToMp3 = File.Create(fileStreamPath))
                {
                    await byteToMp3.WriteAsync(mp3Bytes, 0, mp3Bytes.Length);
                }
                TagLib.File file = TagLib.File.Create(fileStreamPath);
                Song song = CreateSongObject(file, mp3Bytes);
                songList.Add(song);
            }
            await dataEndPoint.PostAllSongs(songList);
            Console.WriteLine("Post Done");

        }

        private Song CreateSongObject(TagLib.File file, byte[] mp3)
        {
            string title = file.Tag.Title;
            string albumName = file.Tag.Album;
            string[] artists = TagSplitter(file.Tag.Performers[0]);
            uint year = file.Tag.Year;
            int duration = (int)file.Properties.Duration.TotalSeconds;

            Song song = new Song()
            {
                Title = title,
                Album = new Album() {AlbumTitle = albumName},
                Artists = Enumerable.Range(0,artists.Length).Select(i => new Artist{ArtistName = artists[i]}).ToList(),
                Duration = duration,
                ReleaseYear = (int)year,
                Mp3 = mp3
            };
            Console.WriteLine(song.Artists.Count);
            
            
            return song;
        }

        
        private string[] TagSplitter(string toSplit)
        {
            return toSplit.Split(",");
        }

    }
}