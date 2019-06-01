Projects Description

* The server was created by the professor and currently inactive.


- EX0: Tutorials for learning basic githubs

- EX1: Connecting to the server and send "Hi" to the server(To locally test it for correct result, I have EchoServer.java as well, meaning we execute EchoServer.java first, and then execute EchoClient.java).

- EX2: As I commented in the java file, the project..
	#0. Connect to the server.
 	#1. Receive (hex)Bytes.
 	#2. hex-to-decimal calculation; and store it to byte array, then print(in Hex)
 	#3. Get CRC32-value of the byte-array.
 	#4. Write to the Server.
 	#5. Listen to the server response.  

- EX3: Use Checksum for encryption/decrpytion.

- EX5: Report Project- ipconfig, ping, pcap-library, sniffer, TCP packets, 

- P1: ChatClient
	Similar as EX0 but connecting with actual server; create the socket, setup the socket for connecting, attempt to connect, and use thread(runner) to be keep connected with the server.

- P2: PhysLayerClient
	decode the byte that the user get from the server and send it back to server, to see if the decrption process was correct.

- P3: Ipv4Client
	Simulating Ipv4.
	#1. Send IPv4 Packet to Server. It will check, 
  	#2. Checksum value, and Size of Data. 
    #3. If good, the server will respond, "Good".

- P4: Ipv6Client
	Simulating Ipv6; similar as P3 but with more complicated encryption/decrption process.

- P5: udpClient
	Simulates Udp; similar as previous.

- P6: FileTransfer
	 send the integer of the packet size first and then the message type; verify the type of message the user gets; this also includes AES&RSA encrption/decrption process. 

	 For client side,
	#1. Generate AES_Session_Key
	#2. Encrypt the session key using the server's public key(Cipher.WRAP MODE)
	#3. Prompt the user to enter the path for a file to transfer.
 	#4. If the path is valid, ask the user to enter the desired chunk size in
 		bytes (default of 1024 bytes).
 	#5. Send the server a StartMessage that contains the file name, length of the
		file in bytes, chunk size, and encrypted session key. Server should response
		with AckMessage with seq_number:0, otherwise seq_num:-1
	#6_1. send each chunk of the file in order. After each chunk, wait for the
		server to respond with AckMessage. The sequence number in the ACK should be
		the number for the next expected chunk.
	#6_2. the client must first read the data from the file and store in an
		  array based on the chunk size. It should then calculate the CRC32 value for
		  the chunk. Finally, encrypt the chunk data using the session key. Note that
		  the CRC32 value is for the plaintext of the chunk, not the ciphertext
	#7. After receiving the final ACK, client can either begin a new file