Genereaza artefactele din Intellij din PRject sturcture mai intai, si apoi da-le build. Le gasesti in Artefacts la out

rmiregistry -J-Djava.class.path="/home/radu/Desktop/POS/Laboratorul2Calculator/src/commons.jar"
java -Djava.rmi.server.codebase=file:"/home/radu/Desktop/POS/Laboratorul2Calculator/src/server.jar" -Djava.security.policy="/home/radu/Desktop/POS/Laboratorul2Calculator/src/servant/rmi_server.policy" -jar server.jar
java -Djava.rmi.server.codebase=file:"/home/radu/Desktop/POS/Laboratorul2Calculator/src/client.jar" -Djava.security.policy="/home/radu/Desktop/POS/Laboratorul2Calculator/src/client/rmi_client.policy" -jar client.jar

https://docs.oracle.com/javase/tutorial/rmi/compiling.html

gosh dang it de artefacte stupide ca mi-a generat artefact de client la server...
si drc, ca nu pot sa-i pun spatii in path-uri...