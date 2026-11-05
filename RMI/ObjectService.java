package RMI;

import java.rmi.*;

public interface ObjectService extends Remote {
    java.io.Serializable requestObject(String studentCode, String qCode) throws RemoteException;
    void submitObject(String studentCode, String qCode, java.io.Serializable object) throws RemoteException;
}