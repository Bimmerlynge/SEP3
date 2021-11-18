using System;

namespace Client.Data
{
    [Serializable]
    public class Album
    {
        public int Id { get; set; }
        public string Title { get; set; }
        public int Duration { get; set; }
       // public DateTime ReleaseDate { get; set; }

        
    }
}