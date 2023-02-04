package servant;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import commons.ISayHello;

public class ServantRunner {
    private static final String rmiServantName = "RMIHello World";

    public static void main(String[] args) {
        if (null == System.getSecurityManager()) {
            System.setSecurityManager(new SecurityManager());
        }

        try {
            ISayHello remoteWorker = new SayHelloImpl();
            // las OS-ul sa aleaga portul pentru mine
            // not always a good choice >:P
            ISayHello servantStub = (ISayHello) UnicastRemoteObject.exportObject(remoteWorker, 0);
            Registry targetRegistry = LocateRegistry.getRegistry();
            targetRegistry.rebind(ServantRunner.rmiServantName, servantStub);

            System.out.println("Greeter awaiting messages >:)");
        } catch (Exception ex) {
            System.err.println("Should not be generic exception unless last resort");
            ex.printStackTrace();
        }
    }
}
