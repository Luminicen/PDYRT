package pdytr.ftp.grpc;

import io.grpc.*;

public class Client
{
    public static void main( String[] args ) throws Exception
    {
      // Channel is the abstraction to connect to a service endpoint
      // Let's use plaintext communication because we don't have certs
      final ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8080")
        .usePlaintext(true)
        .build();

      // It is up to the client to determine whether to block the call
      // Here we create a blocking stub, but an async stub,
      // or an async stub with Future are always possible.
      
      if(args.length < 3){
        System.out.println("Ingresar nombre del archivo, posicion y cantidad de bytes a leer");
        System.exit(1);
      }

      String name = args[0];
      int position = Integer.parseInt(args[1]);
      int offset = Integer.parseInt(args[2]);

      FtpServiceGrpc.FtpServiceBlockingStub stub = FtpServiceGrpc.newBlockingStub(channel);
      FTPService.ReadRequest request =
        FTPService.ReadRequest.newBuilder()
          .setName(name)
          .setPosition(position)
          .setAmount(offset)
          .build();

      // Finally, make the call using the stub
      FTPService.ReadResponse response = 
        stub.read(request);

      System.out.println(response);

      // A Channel should be shutdown before stopping the process.
      channel.shutdownNow();
    }
}