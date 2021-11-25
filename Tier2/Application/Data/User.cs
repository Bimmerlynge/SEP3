using System;
using System.ComponentModel.DataAnnotations;

namespace AppServer.Data
{
    [Serializable]
    public class User
    {
        public string UserName { get; set; }
        public string Role { get; set; }
        public string Password { get; set; }
    }
}