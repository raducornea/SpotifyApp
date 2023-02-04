package commons;

import java.rmi.Remote;
import java.rmi.RemoteException;

// cum sa generezi un jar din interfata...
// https://docs.oracle.com/javase/tutorial/rmi/compiling.html
public interface ISayHello extends Remote {
    String sayHello(String name) throws RemoteException;
}