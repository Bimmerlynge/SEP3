using System;
using System.Collections.Generic;

namespace Client.Data
{
    [Serializable]

    public class Song
    {
        public int Id { get; set; }
        public string Url { get; set; }
        public string Title { get; set; }
        
        public int Duration { get; set; }

        public IList<Artist> Artists { get; set; }
        //public DateTime ReleaseDate { get; set; }
        
        
    }
}