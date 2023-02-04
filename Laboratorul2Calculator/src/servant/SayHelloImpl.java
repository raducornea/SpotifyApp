package servant;

import commons.ISayHello;

import java.rmi.RemoteException;

public class SayHelloImpl implements ISayHello {
    public SayHelloImpl() {
        super();
    }

    @Override
    public String sayHello(String s) throws RemoteException {
        StringBuilder resultString = new StringBuilder();

        // should split String s: a|x|c, where separator is space, a and b are nums and x iss operation
        String[] arrOfStr = s.split(" ");

        Integer a = Integer.parseInt(arrOfStr[0]);
        Integer b = Integer.parseInt(arrOfStr[2]);
        String x = arrOfStr[1];
        switch (x){
            case "+": { resultString.append(a + b); break; }
            case "-": { resultString.append(a - b); break; }
            case "/": { resultString.append(a / b); break; }
            case "*": { resultString.append(a * b); break; }
            default: resultString.append("Hello " + s);
        }

        return resultString.toString();
    }
}
