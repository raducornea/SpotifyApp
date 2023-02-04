Genereaza artefactele din Intellij din PRject sturcture mai intai, si apoi da-le buil. Le gasesti in Artefacts la out

rmiregistry -J-Djava.class.path="/home/radu/Desktop/POS/Laboratorul 2/src/commons.jar"
java -Djava.rmi.server.codebase=file:"/home/radu/Desktop/POS/Laboratorul 2/src/server.jar" -Djava.security.policy="/home/radu/Desktop/POS/Laboratorul 2/src/servant/rmi_server.policy" -jar server.jar
java -Djava.rmi.server.codebase=file:"/home/radu/Desktop/POS/Laboratorul 2/src/client.jar" -Djava.security.policy="/home/radu/Desktop/POS/Laboratorul 2/src/client/rmi_client.policy" -jar client.jar


https://docs.oracle.com/javase/tutorial/rmi/compiling.html

