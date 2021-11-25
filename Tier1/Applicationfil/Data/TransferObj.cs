using System;

namespace Client.Data
{
    [Serializable]
    public class TransferObj<T>
    {
        public string Action { get; set; }
        public T Arg { get; set; }
    }
}