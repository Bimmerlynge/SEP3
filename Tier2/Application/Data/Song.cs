using System;
using System.Collections.Generic;

namespace AppServer.Data
{
    [Serializable]

    public class Song
    {
        public int Id { get; set; }
        public string Title { get; set; }
        
        public int Duration { get; set; }
        
        public Album Album { get; set; }

        public List<Artist> Artists { get; set; }
        //public List<Album> Albums { get; set; }
        //public DateTime ReleaseDate { get; set; }
        
        //public DateTime ReleaseDate { get; set; }
        
        public int ReleaseYear { get; set; }
        
        public byte[] Mp3 { get; set; }
        
    }
}