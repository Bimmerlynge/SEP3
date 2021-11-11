using System.Collections.Generic;
using Client.Data;
using Client.model;
using Microsoft.AspNetCore.Components;
using NUnit.Framework;


namespace ClientTest.model
{
    public class AudioTestModelTest
    {
        [Inject] public IAudioTestModel Model { get; set; }
        [Inject] public IPlayerModel PlayerModel { get; set; }

        private IList<Song> songs;


        [SetUp]
        public void SetUp()
        {
            songs = Model.GetAllSongs().Result;

        }


        [Test]
        public void SkipToNextSong()
        {
            
        }
        
        
        
    }
}