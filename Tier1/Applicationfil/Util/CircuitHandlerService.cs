using System.Threading;
using System.Threading.Tasks;
using Client.model;
using Microsoft.AspNetCore.Components.Server.Circuits;

namespace Client.Util
{
    public class CircuitHandlerService : CircuitHandler
    {
        private IPlayerModel player;
        
        public CircuitHandlerService(IPlayerModel player)
        {
            this.player = player;
        }
        
        public override async Task OnCircuitClosedAsync(Circuit circuit, CancellationToken cancellationToken)
        {
            player.StopPlaying();
            
        }
        
    }
}