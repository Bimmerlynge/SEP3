using System.Collections.Generic;

namespace AppServer.Data
{
    public class PlayList
    {
        public int PlaylistID { get; set; }
        public string Title { get; set; }
        public IList<int> SongIDs { get; set; }
    }
}