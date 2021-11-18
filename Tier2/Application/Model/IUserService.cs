using System.Collections.Generic;
using System.Threading.Tasks;
using AppServer.Data;

namespace AppServer.Model
{
    public interface IUserService
    {
        public Task<IList<User>> GetUsers();
        public Task<User> AddUser(User user);
        public  void RemoveUser(User user);
        
        public User ValidateUser(string userName, string password);
    }
}