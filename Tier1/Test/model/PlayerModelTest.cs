using System.Collections.Generic;
using System.Threading.Tasks;
using Client.Data;
using Client.model;
using Client.Networking;
using Microsoft.AspNetCore.Components;
using NUnit.Framework;
using static Client.Startup;
using _Client = Client.Networking.Client;



namespace ClientTest.model
{
    public class PlayerModelTest
    {
 
        private static IClient client = new _Client();
        //private static global::Client.Networking.Client _client = new();
        private IAudioTestModel Model = new AudioTestModel(client);
        private IPlayerModel PlayerModel = new PlayerModel(client);


        private string currentSong;
        private IList<Song> songs;
        
        [SetUp]
        public void Setup()
        {
            songs = Model.GetAllSongs().Result;
        }



        [Test]
        public async Task SkipToNextSong()
        {
            await PlayerModel.PlaySongAsync(songs[0]);
            //IList<Song> currentPlaylist = PlayerModel.CurrentPlaylist;

            Song currentSong = await PlayerModel.GetCurrentSongAsync();

            await PlayerModel.PlayNextSongAsync();

            Song newCurrentSong = await PlayerModel.GetCurrentSongAsync();
            
            Assert.AreNotEqual(currentSong.Id, newCurrentSong.Id);

        }
        
    }
}