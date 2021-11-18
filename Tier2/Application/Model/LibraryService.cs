using System;
using System.Collections;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Threading.Tasks;
using AppServer.Data;
using AppServer.Networking.DataSide;

namespace AppServer.Model
{
    public class LibraryService : ILibraryService
    {
        private IDataEndPoint dataEndPoint = new DataEndPoint();
        private IList<byte[]> songsByte = new List<byte[]>();
        private IList<Song> songList = new List<Song>();
        public async Task<IList<byte[]>> GetAllMP3Async()
        {
            return await dataEndPoint.GetAllMP3();
        }

        public async Task SendSongListToDBAsync()
        {
            string path = @"C:\Users\basti\RiderProjects\SEP3\Tier2\Application\Util\Audio\tempFile.mp3";
            songsByte = await GetAllMP3Async();
            
            //Builder Pattern!!!! GO go implement
            foreach (byte[] MP3Byte in songsByte)
            {
                using (FileStream byteToMp3 = File.Create(path))
                {
                    await byteToMp3.WriteAsync(MP3Byte, 0, MP3Byte.Length);
                }
                
                TagLib.File file = TagLib.File.Create(path);
                string title = file.Tag.Title;
                string albumName = file.Tag.Album;
                string[] artistName = file.Tag.Performers;
                uint year = file.Tag.Year;
                int duration = (int)file.Properties.Duration.TotalSeconds;
                
                Artist artist = new Artist();
                artist.ArtistName = artistName[0]; //Lav det her om, lige nu får den artistName som string
                
                Song song = new Song()
                {
                    Title = title,
                    AlbumProperty = new Album() {AlbumTitle = albumName},
                    Artists = new List<Artist>() {artist},
                    Duration = duration,
                    ReleaseYear = (int)year,
                    Mp3 = MP3Byte
                };
                
                songList.Add(song);
                
            }
            
            await dataEndPoint.PostAllSongs(songList);
            Console.WriteLine("Post Done");

        }
    }
}