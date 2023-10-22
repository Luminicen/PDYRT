/*
 * Client.java
 * Just sends stdin read data to and receives back some data from the server
 *
 * usage:
 * java Client serverhostname port
 */

import java.io.*;
import java.net.*;

public class Client
{
  public static void main(String[] args) throws IOException
  {
    /* Check the number of command line parameters */
    if ((args.length != 3) || (Integer.valueOf(args[1]) <= 0) || (Integer.valueOf(args[2]) <0 ))
    {
      System.out.println("3 arguments needed: serverhostname port buffer");
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

    /* Streams for I/O through the connected socket */
    fromserver = new DataInputStream(socketwithserver.getInputStream());
    toserver   = new DataOutputStream(socketwithserver.getOutputStream());

    /* Buffer to use with communications (and its length) */
    
    /* Get some input from user */
    int bufferSize = Integer.valueOf(args[2]);
    byte[] buffer = new byte[bufferSize];
    for (int j = 0; j < bufferSize; j++)
      buffer[j] = 'a';
    /* Send read data to server */
    long tiempoInicio = System.nanoTime();
    long resultado_a;
     long resultado_b;
    toserver.write(buffer, 0, buffer.length);
    resultado_a = System.nanoTime() - tiempoInicio;

   
    /* Recv data back from server (get space) */
    buffer = new byte[10000000];
    tiempoInicio = System.nanoTime();
    fromserver.read(buffer);
    resultado_b = System.nanoTime() - tiempoInicio;

    /* Show data received from server */
    String resp = new String(buffer);
    System.out.println(resp);
    double segundos = (double) ((resultado_a+resultado_b)/2) / 1_000_000_000.0;
    System.out.println("Tiempo transcurrido: "+ segundos);
    fromserver.close();
    toserver.close();
    socketwithserver.close();
  }
}
