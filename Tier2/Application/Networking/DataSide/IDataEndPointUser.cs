using System.Collections.Generic;
using System.Threading.Tasks;
using AppServer.Data;

namespace AppServer.Networking.DataSide
{
    public interface IDataEndPointUser
    {
        public Task RegisterUser(User user);
        public Task<User> ValidateUser(User user);
    }
}