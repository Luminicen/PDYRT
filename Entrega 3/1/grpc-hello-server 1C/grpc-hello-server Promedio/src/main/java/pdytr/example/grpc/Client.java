package pdytr.example.grpc;
import java.util.concurrent.TimeUnit;

import io.grpc.*;
import io.grpc.Deadline;

public class Client {
    public static void main(String[] args) throws Exception {
        // Channel is the abstraction to connect to a service endpoint
        // Let's use plaintext communication because we don't have certs
        final ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8080")
            .usePlaintext(true)
            .build();

        // It is up to the client to determine whether to block the call
        // Here we create a blocking stub, but an async stub,
        // or an async stub with Future are always possible.

        // Create a deadline of 5 seconds
        long startTime = System.currentTimeMillis() ;
        //Deadline deadline = Deadline.after(1, TimeUnit.MILLISECONDS);

        GreetingServiceGrpc.GreetingServiceBlockingStub stub = GreetingServiceGrpc.newBlockingStub(channel);
            //.withDeadline(deadline); // Set the deadline directly on the stub

        GreetingServiceOuterClass.HelloRequest request = GreetingServiceOuterClass.HelloRequest.newBuilder()
            .setName("Freddie Mercury")
            .build();

        // Finally, make the call using the stub
        GreetingServiceOuterClass.HelloResponse response = stub.greeting(request);

        //System.out.println(response);

        // A Channel should be shutdown before stopping the process.
        channel.shutdownNow();
        long tiempo = System.currentTimeMillis()  - startTime;
        System.out.println(tiempo);
    }
}
