using System;

namespace Client.Data
{
    [Serializable]
    public class Song
    {
        public int Id { get; set; }
        public string Title { get; set; }
        public string Url { get; set; }
    }
}