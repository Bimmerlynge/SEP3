using System;
using System.ComponentModel.DataAnnotations;

namespace Client.Data
{
    [Serializable]
    public class User
    {
        public string UserName { get; set; }
        public string Password { get; set; }
        public string Role { get; set; }
        
        
        
        
        
    }
}