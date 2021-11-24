using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text.Json;
using System.Threading.Tasks;
using Client.Data;
using Client.model;
using Client.Networking;
using NUnit.Framework;
using _Client = Client.Networking.Client;


namespace ClientTest.model
{
    public class SongSearchModelTest
    {
        private static IClient client = new _Client();
        private IAudioTestModel audioTestModel = new AudioTestModel(client);
        private ISongSearchModel songSearchModel = new SongSearchModel(client);


        [Test]
        public async Task TestIfCorrectSongIsFoundByTitle()
        {
            int songNumberTest = 0;

            IList<Song> listOfAllSongs = await audioTestModel.GetAllSongs();
            string songTitleTest = listOfAllSongs[songNumberTest].Title;

            IList<Song> songList = await songSearchModel.GetSongsByFilterAsync("Title", songTitleTest);
            
            Assert.That(songList.Any(song => song.Id == listOfAllSongs[songNumberTest].Id));
        }
        
        [Test]
        public async Task TestIfCorrectSongIsFoundByArtistName()
        {
            int songNumberTest = 0;

            IList<Song> listOfAllSongs = await audioTestModel.GetAllSongs();
            string artistName = listOfAllSongs[songNumberTest].Artists[0].ArtistName;

            IList<Song> songList = await songSearchModel.GetSongsByFilterAsync("Artist", artistName);
            
            Assert.That(songList.Any(song => song.Id == listOfAllSongs[songNumberTest].Id));
        }
        
        
        [Test]
        public async Task TestIfCorrectSongIsFoundByAlbum()
        {
            int songNumberTest = 0;

            IList<Song> listOfAllSongs = await audioTestModel.GetAllSongs();
            string albumTitle = listOfAllSongs[songNumberTest].Album.AlbumTitle;

            IList<Song> songList = await songSearchModel.GetSongsByFilterAsync("Album", albumTitle);
            
            Assert.That(songList.Any(song => song.Id == listOfAllSongs[songNumberTest].Id));
        }
        
        
        [Test]
        public async Task SearchBySomethingNotImplemented()
        {

            Assert.ThrowsAsync<Exception>(() => songSearchModel.GetSongsByFilterAsync("Handsize","HandsizeSearchFiels"));
        }
        
        [Test]
        public async Task SearchByNull()
        
        {
            int songNumberTest = 0;

            IList<Song> listOfAllSongs = await audioTestModel.GetAllSongs();
            string albumTitle = listOfAllSongs[songNumberTest].Album.AlbumTitle;
            
            Assert.ThrowsAsync<Exception>(() => songSearchModel.GetSongsByFilterAsync(null,albumTitle));

        }
        
        [Test]
        public async Task SearchByEmptyString()
        
        {
            int songNumberTest = 0;

            IList<Song> listOfAllSongs = await audioTestModel.GetAllSongs();
            string albumTitle = listOfAllSongs[songNumberTest].Album.AlbumTitle;
        
        
            Assert.ThrowsAsync<Exception>(() => songSearchModel.GetSongsByFilterAsync("",albumTitle));
        }
        
        [Test]
        public void SongTitleIsEmpty()
        {
         
            Assert.ThrowsAsync<Exception>(() => songSearchModel.GetSongsByFilterAsync("Title",""));
        }
        
        [Test]
        public void SongTitleIsNull()
        {
            Assert.ThrowsAsync<Exception>(() => songSearchModel.GetSongsByFilterAsync("Title",null));

        }
        
        [Test]
        public async Task SongTitleNotFound()
        {
          
        
            Assert.AreEqual(0, songSearchModel.GetSongsByFilterAsync("Title","NotAValidTitlejnjadnf").Result.Count);
        }
        
        [Test]
        public void ArtistNameIsEmpty()
        {
            Assert.ThrowsAsync<Exception>(() => songSearchModel.GetSongsByFilterAsync("Artist",""));

        }
        
        [Test]
        public void ArtistNameIsNull()
        {
            Assert.ThrowsAsync<Exception>(() => songSearchModel.GetSongsByFilterAsync("Artist",null));

        }
        
        [Test]
        public async Task ArtistNameNotFound()
        {

            Assert.AreEqual(0, songSearchModel.GetSongsByFilterAsync("Artist","NotaValidtArtistNamejasdf").Result.Count);
        }
        
        [Test]
        public void AlbumTitleIsEmpty()
        {
           
            Assert.ThrowsAsync<Exception>(() => songSearchModel.GetSongsByFilterAsync("Album",""));
        }
        
        [Test]
        public void AlbumTitleIsNull()
        {
            Assert.ThrowsAsync<Exception>(() => songSearchModel.GetSongsByFilterAsync("Album",null));
        }
        
        [Test]
        public async Task AlbumTitleNotFound()
        {

            Assert.AreEqual(0,songSearchModel.GetSongsByFilterAsync("Album","NotAValidAlbumTitlekjnjkasdf").Result.Count);
        }
    }
}