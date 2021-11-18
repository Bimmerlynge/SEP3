using System.Collections.Generic;

namespace Client.Data
{
    public class PlayList
    {
        public int PlaylistID { get; set; }
        public string Title { get; set; }
        public List<Song> Songs { get; set; }
        public IList<int> SongIDs { get; set; }
    }
}