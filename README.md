# EncryptedMessageBoard
Encrypted message board for Advanced Computer Networks Assignment 2

# TCP
This is connection-oriented while UDP is connectionless. 

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
Powershell Terminal
```
cd C:\Users\Public\Programs\EncryptedForum\X11

ls

javac -cp . *.java
```
MobaXterm Terminal 0 
```
 xhost + 1 2 7 . 0 . 0 . 1
 ```
MobaXterm Terminal 1
```
docker start -i server

cd C:\Users\Public\Programs\EncryptedForum\X11

java -cp . server
```

MobaXterm Terminal 2
```
docker start -i user1

cd C:\Users\Public\Programs\EncryptedForum\X11

java -cp . client
```
MobaXterm Terminal 3
```
docker start -i user2

cd C:\Users\Public\Programs\EncryptedForum\X11

java -cp . client
```
MobaXterm Terminal 4
```
docker start -i user3

cd C:\Users\Public\Programs\EncryptedForum\X11

java -cp . client
```
