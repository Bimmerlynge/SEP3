using System.Threading.Tasks;
using Client.Data;

namespace Client.model
{
    public interface IUserService
    {
        
        Task<User> ValidateUser(User user);

        Task RegisterUser(User user);

    }
}