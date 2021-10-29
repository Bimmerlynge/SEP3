using System;

namespace Client.Data
{
    [Serializable]
    public class TransferObj
    {
        public string Action { get; set; }
        public string Arg { get; set; }
    }
}