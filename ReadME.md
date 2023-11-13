# Key-Value service
A simple key value store service. The service will only receive string key and string value without spaces.

## Operation format
```
# Sample key-value store operation
# PUT <key1> <value1>
# GET <key1>
# DELETE <key1>
```

## Run the server
```
cd CS6650HW1/src
./deploy.sh
```

## Run the TCP Client
```
cd CS6650HW1/src
./run_client.sh target 4080 TCP

# Then you could write key value store command in the terminal 

```


## Run the UDP Client
TCP Client and UDP client could be run at the same time. 
As the server is hosting both TCPServer and UDPServer at the same time.
```
cd CS6650HW1/src
./run_client.sh target 4088 UDP

# Then you could write key value store command in the terminal 
```