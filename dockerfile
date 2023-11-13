FROM openjdk:17 AS client-build
COPY .  /usr/src/server
WORKDIR /usr/src/server/main/java
run javac */*.java
CMD ["java", "client.ClientApp", "4080", "TCP"]

FROM openjdk:17 AS server-build
COPY . /usr/src/client
WORKDIR /usr/src/client/main/java
run javac */*.java
CMD ["java", "server.ServerApp", "4080", "4088"]
