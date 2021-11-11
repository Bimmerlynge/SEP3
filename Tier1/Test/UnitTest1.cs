using System.Collections.Generic;
using Client.Data;
using Client.model;
using Client.Networking;
using NUnit.Framework;


using _Client = Client.Networking.Client;


namespace ClientTest
{
    public class Tests
    {

        private static IClient client = new _Client();
        //private static global::Client.Networking.Client _client = new();
        private IAudioTestModel Model = new AudioTestModel(client);

        private string currentSong;
        private IList<Song> songs;
        
        [SetUp]
        public void Setup()
        {
            songs = Model.GetAllSongs().Result;
        }

        [Test]
        public void TestGetSongList()
        {
            Assert.AreEqual(3, songs.Count);
            Assert.AreEqual("Ring_Of_Fire", songs[0].Title);
            Assert.AreEqual("Champion", songs[1].Title);
            Assert.AreEqual("Under_The_Bridge", songs[2].Title);
        }
        
        [Test]
        public void TestIfSelectedSongIsCorrect()
        {
            //Kan nok ikke lade sig gøre?
        }
    }
}