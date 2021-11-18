using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.Json;
using System.Threading.Tasks;
using AppServer.Data;
using AppServer.Model;
using NUnit.Framework;
using JsonSerializer = System.Text.Json.JsonSerializer;

namespace AppServerTest.Model
{
    public class SongSearchServiceTest
    {
        private ISongSearchService songSearchService;
        private IPlayService playService;

        [SetUp]
        public void Setup()
        {
            songSearchService = new SongSearchService();
            playService = new PlayService();
        }

        [Test]
        public async Task TestIfCorrectSongIsFoundByTitle()
        {
            int songNumberTest = 0;

            var listOfAllSongs = await GetListOfAllSongs();

            string songTitleTest = listOfAllSongs[songNumberTest].Title;

            string[] args = {"Title", songTitleTest};

            var listOfDeserilizedSong = ListOfSongSearchFor(args);


            Assert.That(listOfDeserilizedSong.Any(song => song.Id == listOfAllSongs[songNumberTest].Id));
        }

        [Test]
        public async Task TestIfCorrectSongIsFoundByArtistName()
        {
            int songNumberTest = 0;

            var listOfAllSongs = await GetListOfAllSongs();

            string songArtistName = listOfAllSongs[songNumberTest].Artists[0].ArtistName;

            string[] args = {"Artist", songArtistName};

            var listOfDeserilizedSong = ListOfSongSearchFor(args);

            Assert.That(listOfDeserilizedSong.Any(song => song.Id == listOfAllSongs[songNumberTest].Id));
        }


        [Test]
        public async Task TestIfCorrectSongIsFoundByAlbum()
        {
            int songNumberTest = 0;
            var listOfAllSongs = await GetListOfAllSongs();

            string songAlbumTitle = listOfAllSongs[songNumberTest].AlbumProperty.AlbumTitle;

            string[] args = {"Album", songAlbumTitle};

            var listOfDeserilizedSong = ListOfSongSearchFor(args);

            Assert.That(listOfDeserilizedSong.Any(song => song.Id == listOfAllSongs[songNumberTest].Id));
        }


        [Test]
        public async Task SearchBySomethingNotImplemented()
        {
            string[] args = {"HandSize", "HandSizeObject"};

            string arg = JsonSerializer.Serialize(args);
            TransferObj transferObj = new TransferObj() {Action = "GETSONGSBYFILTER", Arg = arg};


            Assert.ThrowsAsync<Exception>(() => songSearchService.GetSongsByFilterJsonAsync(transferObj));
        }


        [Test]
        public async Task SearchByNull()

        {
            int songNumberTest = 0;
            var listOfAllSongs = await GetListOfAllSongs();

            string songAlbumTitle = listOfAllSongs[songNumberTest].AlbumProperty.AlbumTitle;
            string[] args = {null, songAlbumTitle};

            string arg = JsonSerializer.Serialize(args);
            TransferObj transferObj = new TransferObj() {Action = "GETSONGSBYFILTER", Arg = arg};


            Assert.ThrowsAsync<Exception>(() => songSearchService.GetSongsByFilterJsonAsync(transferObj));
        }


        [Test]
        public async Task SearchByEmptyString()

        {
            int songNumberTest = 0;
            var listOfAllSongs = await GetListOfAllSongs();

            string songAlbumTitle = listOfAllSongs[songNumberTest].AlbumProperty.AlbumTitle;
            string[] args = {"", songAlbumTitle};

            string arg = JsonSerializer.Serialize(args);
            TransferObj transferObj = new TransferObj() {Action = "GETSONGSBYFILTER", Arg = arg};


            Assert.ThrowsAsync<Exception>(() => songSearchService.GetSongsByFilterJsonAsync(transferObj));
        }

        [Test]
        public void SongTitleIsEmpty()
        {
            string[] args = {"Title", ""};

            string arg = JsonSerializer.Serialize(args);
            TransferObj transferObj = new TransferObj() {Action = "GETSONGSBYFILTER", Arg = arg};


            Assert.ThrowsAsync<Exception>(() => songSearchService.GetSongsByFilterJsonAsync(transferObj));
        }

        [Test]
        public void SongTitleIsNull()
        {
            string[] args = {"Title", null};

            string arg = JsonSerializer.Serialize(args);
            TransferObj transferObj = new TransferObj() {Action = "GETSONGSBYFILTER", Arg = arg};


            Assert.ThrowsAsync<Exception>(() => songSearchService.GetSongsByFilterJsonAsync(transferObj));
        }

        [Test]
        public async Task SongTitleNotFound()
        {
            int songNumberTest = 0;

            string[] args = {"Title", "asdNotTitleOfSongasdf"};

            var listOfDeserilizedSong = ListOfSongSearchFor(args);

            Assert.AreEqual(0, listOfDeserilizedSong.Count);
        }

        
        [Test]
        public void ArtistNameIsEmpty()
        {
            string[] args = {"Artist", ""};

            string arg = JsonSerializer.Serialize(args);
            TransferObj transferObj = new TransferObj() {Action = "GETSONGSBYFILTER", Arg = arg};
            Assert.ThrowsAsync<Exception>(() => songSearchService.GetSongsByFilterJsonAsync(transferObj));
        }

        [Test]
        public void ArtistNameIsNull()
        {
            string[] args = {"Artist", null};

            string arg = JsonSerializer.Serialize(args);
            TransferObj transferObj = new TransferObj() {Action = "GETSONGSBYFILTER", Arg = arg};


            Assert.ThrowsAsync<Exception>(() => songSearchService.GetSongsByFilterJsonAsync(transferObj));
        }

        [Test]
        public async Task ArtistNameNotFound()
        {
            string[] args = {"Artist", "asdNotAnArtistNamesdf"};

            var listOfDeserilizedSong = ListOfSongSearchFor(args);

            Assert.AreEqual(0, listOfDeserilizedSong.Count);
        }

        
        
        [Test]
        public void AlbumTitleIsEmpty()
        {
            string[] args = {"Album", ""};

            string arg = JsonSerializer.Serialize(args);
            TransferObj transferObj = new TransferObj() {Action = "GETSONGSBYFILTER", Arg = arg};
            Assert.ThrowsAsync<Exception>(() => songSearchService.GetSongsByFilterJsonAsync(transferObj));
        }

        
        [Test]
        public void AlbumTitleIsNull()
        {
            string[] args = {"Album", null};

            string arg = JsonSerializer.Serialize(args);
            TransferObj transferObj = new TransferObj() {Action = "GETSONGSBYFILTER", Arg = arg};


            Assert.ThrowsAsync<Exception>(() => songSearchService.GetSongsByFilterJsonAsync(transferObj));
        }

        [Test]
        public async Task AlbumTitleNotFound()
        {
            int songNumberTest = 0;

            string[] args = {"Album", "asdNotAnAlbumTitleasdf"};

            var listOfDeserilizedSong = ListOfSongSearchFor(args);

            Assert.AreEqual(0, listOfDeserilizedSong.Count);
        }

        

        //Help Method
        private List<Song> ListOfSongSearchFor(string[] args)
        {
            string arg = JsonSerializer.Serialize(args);
            TransferObj transferObj = new TransferObj() {Action = "GETSONGSBYFILTER", Arg = arg};

            string songWithTitle = songSearchService.GetSongsByFilterJsonAsync(transferObj).Result;

            List<Song> listOfDeserilizedSong = JsonSerializer.Deserialize<List<Song>>(songWithTitle,
                new JsonSerializerOptions() {PropertyNameCaseInsensitive = true});
            return listOfDeserilizedSong;
        }

            //Help Method
        private async Task<IList<Song>> GetListOfAllSongs()
        {
            string listWithEverySongInDatabase = await playService.GetAllSongsAsJsonAsync();

            TransferObj transferObj = JsonSerializer.Deserialize<TransferObj>(listWithEverySongInDatabase);

            IList<Song> listOfAllSongs = JsonSerializer.Deserialize<IList<Song>>(transferObj.Arg,
                new JsonSerializerOptions() {PropertyNameCaseInsensitive = true});
            return listOfAllSongs;
        }
    }
}