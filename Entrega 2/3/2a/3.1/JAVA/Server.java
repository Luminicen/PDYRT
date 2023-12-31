/*
 * EchoServer.java
 * Just receives some data and sends back a "message" to a client
 *
 * Usage:
 * java Server port
 */
import java.io.*;
import java.net.*;
import checker.MD5Checksum;;
public class Server
{
  public static void main(String[] args) throws IOException
  {
    /* Check the number of command line parameters */
    if ((args.length != 1) || (Integer.valueOf(args[0]) <= 0) )
    {
      System.out.println("1 arguments needed: port");
      System.exit(1);
    }
	
	//sleep antes de la comunicacion
  	try
    {
  		Thread.sleep(10*1000);
  	}
  	catch (Exception e)
    { 
      System.out.println(e);
    }
		
    /* The server socket */
    ServerSocket serverSocket = null;    
    try
    {
      serverSocket = new ServerSocket(Integer.valueOf(args[0]));
    } 
    catch (Exception e)
    {
      System.out.println("Error on server socket");
      System.exit(1);
    }

    /* The socket to be created on the connection with the client */
    Socket connected_socket = null;

    try /* To wait for a connection with a client */
    {
      connected_socket = serverSocket.accept();
    }
    catch (IOException e)
    {
      System.err.println("Error on Accept");
      System.exit(1);
    }

    /* Streams from/to client */
    DataInputStream fromclient;
    DataOutputStream toclient;

    /* Get the I/O streams from the connected socket */
    fromclient = new DataInputStream(connected_socket.getInputStream());
    toclient   = new DataOutputStream(connected_socket.getOutputStream());
    int bufferSize = fromclient.readInt(); 
    toclient.writeInt(0);
    byte[] checksum = new byte[16];
    fromclient.read(checksum);
    toclient.writeInt(0);
    byte[] buffer = new byte[bufferSize];
    int totalBytesRead = 0;
    while (totalBytesRead < bufferSize)
      {
        int bytesRead = fromclient.read(buffer,totalBytesRead,bufferSize - totalBytesRead);
        if ( bytesRead < 0 )
          {
            System.err.println("Error to read buffer");
            System.exit(1);
          }
        totalBytesRead += bytesRead;
      }
      if(MD5Checksum.isValid(checksum, buffer)){toclient.writeInt(0);} else {toclient.writeInt(1);}
      System.out.println("Validacion: " + MD5Checksum.isValid(checksum, buffer));
      System.out.println("Byetes que se leyeron: " + totalBytesRead);
        

    /* Close everything related to the client connection */
    fromclient.close();
    toclient.close();
    connected_socket.close();
    serverSocket.close();
  }
}
