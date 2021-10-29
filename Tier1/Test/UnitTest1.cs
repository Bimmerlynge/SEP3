using System;
using System.Collections.Generic;
using System.Runtime.CompilerServices;
using System.Threading.Tasks;
using Client.Data;
using Client.model;
using Client.Networking;
using Microsoft.AspNetCore.Mvc.ModelBinding;
using NUnit.Framework;
using Client = Client.Networking.Client;

namespace ClientTest
{
    public class Tests
    {
        private static global::Client.Networking.Client _client = new();
        private IAudioTestModel Model = new AudioTestModel(_client);

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
            Assert.Pass();
        }
    }
}