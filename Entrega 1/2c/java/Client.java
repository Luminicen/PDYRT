/*
 * Client.java
 * Just sends stdin read data to and receives back some data from the server
 *
 * usage:
 * java Client serverhostname port
 */
import java.io.*;
import java.net.*;
import checker.MD5Checksum;
public class Client
{
  public static void main(String[] args) throws IOException
  {
    /* Check the number of command line parameters */
    if ((args.length != 2) || (Integer.valueOf(args[1]) <= 0) )
    {
      System.out.println("2 arguments needed: serverhostname port");
      System.exit(1);
    }

    /* The socket to connect to the echo server */
    Socket socketwithserver = null;

    try /* Connection with the server */
    { 
      socketwithserver = new Socket(args[0], Integer.valueOf(args[1]));
    }
    catch (Exception e)
    {
      System.out.println("ERROR connecting");
      System.exit(1);
    } 

    /* Streams from/to server */
    DataInputStream  fromserver;
    DataOutputStream toserver;
    long totalTime = 0;
    /* Streams for I/O through the connected socket */
    fromserver = new DataInputStream(socketwithserver.getInputStream());
    toserver   = new DataOutputStream(socketwithserver.getOutputStream());
    int bufferSize = 1000000;
    byte[] buffer = new byte[bufferSize];
    for (int j = 0; j < bufferSize; j++)
      buffer[j] = 'a';
    byte[] checksum = MD5Checksum.generate(buffer);
    int ok = 0;
    toserver.writeInt(bufferSize);
    ok=fromserver.readInt();
    toserver.write(checksum, 0, checksum.length);
    ok=fromserver.readInt();
    toserver.write(buffer, 0, bufferSize);
    ok=fromserver.readInt();
    System.out.println("Tiempo total: "+ totalTime);
    if (ok == 0){
      System.out.println("Se recibieron correctamente");
      
    }
    fromserver.close();
    toserver.close();
    socketwithserver.close();
  }
}
