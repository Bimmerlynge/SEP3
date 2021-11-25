using System;

namespace AppServer.Data
{
    [Serializable]
    public class TransferObj<T>
    {
        public string Action { get; set; }
        public T Arg { get; set; }

    
    }
}