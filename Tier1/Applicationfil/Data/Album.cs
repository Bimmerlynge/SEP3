using System;

namespace Client.Data
{
    [Serializable]
    public class Album
    {
        public int AlbumId { get; set; }
        public string AlbumTitle { get; set; }
        public int Duration { get; set; }
        

        
    }
}