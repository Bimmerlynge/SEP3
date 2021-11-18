using System;
using System.Collections.Generic;
using System.IO;
using System.Text.Json;
using AppServer.Data;
using AppServer.Networking.DataSide;
using NUnit.Framework;
using JsonSerializer = System.Text.Json.JsonSerializer;

namespace AppServerTest.Model
{
    public class PlayServiceTest
    {
        private IDataEndPoint DataEndPoint;
        private long maxByteSize = 20000000; //Gets From Client "SongFromServer" method

        [SetUp]
        public void Setup()
        {
            DataEndPoint = new DataEndPoint();
        }
        
        
        /*
         * Skal laves om, lige nu kigger den på filer på Test siden, skal lige direct over på server først
         * 
         */
        // [Test]
        // public void CanFindCorrectFileBasedOnSongObject()
        // {
        //     var songs = GetSongsFromDatabase();
        //
        //     foreach (Song song in songs)
        //     {
        //         string songUrl = song.Url;
        //         
        //         if (!File.Exists(songUrl))
        //         {
        //             Console.Write(songUrl);
        //             Assert.Fail("File does not exist");
        //         }
        //         
        //         FileInfo file = new FileInfo(songUrl);
        //         if (!file.Extension.Equals(".mp3") || !file.Name.Contains(song.Title))
        //         {
        //             Assert.Fail();
        //         }
        //     }
        //     Assert.Pass();
        //     
        // }

  

        // [Test]
        // public void FileDoesNotExceedByteSize()
        // {
        //     var songs = GetSongsFromDatabase();
        //     foreach (Song song in songs)
        //     {
        //         FileInfo fileInfo = new FileInfo(song.Url);
        //         if (fileInfo.Length > maxByteSize)
        //         {
        //             Assert.Fail($"{song.Title}, Song to big");
        //         }
        //     }
        //     Assert.Pass();
        // }
        
        private IList<Song> GetSongsFromDatabase()
        {
            string listOfSongsAsJson = DataEndPoint.GetAllSongs().Result;
            IList<Song> songs = JsonSerializer.Deserialize<IList<Song>>(listOfSongsAsJson,
                new JsonSerializerOptions() {PropertyNameCaseInsensitive = true});
            return songs;
        }

    }
}