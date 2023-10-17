package pdytr.ftp.grpc;
import io.grpc.*;

public class App{
	public static void main(String[] args)throws Exception
	{
		Server server = ServerBuilder.forPort(8080)
        .addService(new FTPServiceImpl())
        .build();
      	// Start the server
      	server.start();
      	// Server threads are running in the background.
      	System.out.println("Server started");
      	// Don't exit the main thread. Wait until server is terminated.
      	server.awaitTermination();
	}
}