using System;
using System.Threading.Tasks;
using Blazored.Modal.Services;
using Client.Authentication;
using Client.Data;
using Client.model;
using Microsoft.AspNetCore.Components;
using Microsoft.AspNetCore.Components.Authorization;


namespace Client.Pages
{
    public partial class Index : ComponentBase
    {
        [Inject] public IModalService ModalService { get; set; }
        [Inject] private AuthenticationStateProvider AuthenticationStateProvider { get; set; }
        [Inject] private NavigationManager NavigationManager { get; set; }
        
        [Inject] private IUserService UserService { get; set; }


        private string username;
        private string password;
        private string errorMessage;

        public async Task PerformLogin()
        {
            errorMessage = "";
            try
            {
                User user = new User() {UserName = username, Password = password, Role = "StandardUser"};
                ((CustomAuthenticationStateProvider) AuthenticationStateProvider).ValidateLogin(user);
                username = "";
                password = "";
            }
            catch (Exception e)
            {
                errorMessage = e.Message;
            }
        }

        public async Task PerformLogout()
        {
            errorMessage = "";
            username = "";
            password = "";
            try
            {
                ((CustomAuthenticationStateProvider) AuthenticationStateProvider).Logout();
                NavigationManager.NavigateTo("/");
            }
            catch (Exception e)
            {
            }
        }
        
        private async void registerPopup()
        {
            var form = ModalService.Show<Register>();
            var result = await form.Result;
            if (!result.Cancelled)
            {
                User justCreated = (User) result.Data;
                await UserService.RegisterUser(justCreated);
                
                StateHasChanged(); 
            }

        }
    }
}