using System.Collections.Generic;
using System.Threading.Tasks;
using AppServer.Data;
using AppServer.Networking.DataSide;

namespace AppServer.Model
{
    public class UserService : IUserService
    {
        private IDataEndPointUser dataEndPoint = new DataEndPointUser();

        public async Task RegisterUser(User user)
        {
            await dataEndPoint.RegisterUser(user);
        }

        public async Task<User> ValidateUser(User user)
        {
            User userToReturn = await dataEndPoint.ValidateUser(user);
            return userToReturn;
        }
    }
}