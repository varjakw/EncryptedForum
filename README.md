# EncryptedMessageBoard
Encrypted message board for Advanced Computer Networks Assignment 2

# TCP
This is connection-ortiented while UDP is connectionless. 

# Threading
Threading is required for multiple connections (multiple clients) to be made.

# Docker
Create a network and 4 containers in it, then connects the containers to the network.
```
docker network create -d bridge --subnet 172.20.0.0/16 messageboard

docker create --name user1 --cap-add=ALL -ti -v messageboard:/messageboard java /bin/bash


docker create --name user2 --cap-add=ALL -ti -v messageboard:/messageboard java /bin/bash


docker create --name user3 --cap-add=ALL -ti -v messageboard:/messageboard java /bin/bash

docker create --name server --cap-add=ALL -ti -v messageboard:/messageboard java /bin/bash

docker network connect messageboard user1
docker network connect messageboard user2
docker network connect messageboard user3
docker network connect messageboard server
```

## How to run it
Terminal 1
```
cd \\wsl$\docker-desktop-data\version-pack-data\community\docker\volumes\messageboard\_data\X11

ls

javac -cp . *.java
```
Terminal 2
```
docker start -i user1

cd /messageboard

java -cp . client
```
Terminal 3
```
docker start -i user2

cd /messageboard

java -cp . client
```
Terminal 4
```
docker start -i user3

cd /messageboard

java -cp . client
```
