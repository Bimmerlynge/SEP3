package shared;

public class Mp3
{
  private byte[] data;
  private String path;

  public Mp3(byte[] data, String path)
  {
    this.data = data;
    this.path = path;
  }

  public byte[] getData()
  {
    return data;
  }

  public void setData(byte[] data)
  {
    this.data = data;
  }

  public String getPath()
  {
    return path;
  }

  public void setPath(String path)
  {
    this.path = path;
  }
}
