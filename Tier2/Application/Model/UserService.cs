using System.Collections.Generic;
using System.Threading.Tasks;
using AppServer.Data;

namespace AppServer.Model
{
    public class UserService : IUserService
    {
        
        public IList<User> Users { get; }

        public Task<IList<User>> GetUsers()
        {
            throw new System.NotImplementedException();
        }

        public Task<User> AddUser(User user)
        {
            throw new System.NotImplementedException();
        }

        public void RemoveUser(User user)
        {
            throw new System.NotImplementedException();
        }

        
        public User ValidateUser(string userName, string password)
        {
            throw new System.NotImplementedException();
        }
        
        
    }
}