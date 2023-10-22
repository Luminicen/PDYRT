package pdytr.example.grpc;
import io.grpc.*;


public class Client
{
    public static void main( String[] args ) throws Exception
    {
      // Channel is the abstraction to connect to a service endpoint
      // Let's use plaintext communication because we don't have certs
      if(args.length < 1){
        System.out.println("Ingresar cantidad de buffer");
        System.exit(1);
      }
      int bufferSize = Integer.valueOf(args[0]);
      String buffer = "";
      for (int j = 0; j < bufferSize; j++)
          buffer = buffer + "a";
      final ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8080")
        .usePlaintext(true)
        .build();

      // It is up to the client to determine whether to block the call
      // Here we create a blocking stub, but an async stub,
      // or an async stub with Future are always possible.
      GreetingServiceGrpc.GreetingServiceBlockingStub stub = GreetingServiceGrpc.newBlockingStub(channel);
      long tiempoInicio = System.nanoTime();
      GreetingServiceOuterClass.HelloRequest request =
        GreetingServiceOuterClass.HelloRequest.newBuilder()
          .setName(buffer)
          .build();
      long resultado_a = System.nanoTime() - tiempoInicio;
      // Finally, make the call using the stub
      tiempoInicio = System.nanoTime();
      GreetingServiceOuterClass.HelloResponse response = 
        stub.greeting(request);
      long resultado_b = System.nanoTime() - tiempoInicio;
      System.out.println(response);
      double segundos = (double) ((resultado_a+resultado_b)/2) / 1_000_000_000.0;
      System.out.println("Tiempo transcurrido: "+ segundos);
      // A Channel should be shutdown before stopping the process.
      channel.shutdownNow();
    }
}