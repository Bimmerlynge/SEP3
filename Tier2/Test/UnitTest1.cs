using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text.Json;
using AppServer.Data;
using AppServer.Networking.DataSide;
using Newtonsoft.Json;
using NUnit.Framework;
using JsonSerializer = System.Text.Json.JsonSerializer;

using AppServer.Model;

namespace AppServerTest
{
    public class Tests
    {
        private IPlayService _playService = new PlayService();
        [SetUp]
        public void Setup()
        {
        }

        [Test]
        public void Test1()
        {
            Assert.Pass();
        }

       
        
        
    }
}