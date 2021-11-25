using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Client.Data;
using Client.Networking;

namespace Client.model
{
    public class UserService : IUserService
    {
        private IClient client;

        public UserService(IClient client)
        {
            this.client = client;
        }


        public async Task<User> ValidateUser(User user)
        {
            return await client.validateUser(user);
           
        }

        public async Task RegisterUser(User user)
        {
            await client.RegisterUser(user);
        }
    }
}