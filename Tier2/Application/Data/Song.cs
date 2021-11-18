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
        
        public Album AlbumProperty { get; set; }

        public IList<Artist> Artists { get; set; }
        
        public int ReleaseYear { get; set; }
        
        public byte[] Mp3 { get; set; }
        
    }
}