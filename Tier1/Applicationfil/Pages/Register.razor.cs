using System;
using System.Threading.Tasks;
using Blazored.Modal;
using Blazored.Modal.Services;
using Client.Data;
using Client.model;
using Microsoft.AspNetCore.Components;

namespace Client.Pages
{
    public partial class Register : ComponentBase

    {
        [Inject] private NavigationManager NavigationManager { get; set; }
        [Inject] private IUserService UserService { get; set; }

        private string username;
        private string password;
        private string errorMessage;

        [CascadingParameter] public BlazoredModalInstance BlazoredModal { get; set; }
        private User User = new User(){Role = "StandardUser"};

        public async Task PerformRegister()
        {
            await BlazoredModal.CloseAsync(ModalResult.Ok(User));
        }
        
        private async Task Cancel()
        {
            await BlazoredModal.CancelAsync();
        }
    }
}