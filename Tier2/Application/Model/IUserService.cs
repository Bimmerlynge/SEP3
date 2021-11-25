using System.Collections.Generic;
using System.Threading.Tasks;
using AppServer.Data;

namespace AppServer.Model
{
    public interface IUserService
    {
        public Task RegisterUser(User user);
        
        public Task<User> ValidateUser(User user);
    }
}