﻿using System.Collections.Generic;
using System.Threading.Tasks;
using Client.Data;

namespace Client.model
{
    public interface IAudioTestModel
    {
       
        Task<IList<Song>> GetAllSongs();
    }
}