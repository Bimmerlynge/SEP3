# SEP3 Java

Dataserver connected to postgres database, found under Tier 3, database.

AppServer and Client on: https://github.com/Nissen99/SEP3Csharp


To run, create an Enum called "MINKODE", in the Util package in the server package, with your postgres password


```
package server.util;

public enum MINKODE {
    PASSWORD("PostgresPasswordHere");

    public final String password;
    MINKODE(String password)
    {
        this.password = password;
    }
    }
```
And adujst the connection information in "BaseDAO"

An empty folder called "audio" must be created under Tier 3 
