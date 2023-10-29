  package pdytr.ftp.grpc;

  import io.grpc.*;
  import com.google.protobuf.ByteString;
  import java.io.File;
  import java.io.FileInputStream;
  import java.io.IOException;


  public class Client
  {
      public static void main( String[] args ) throws Exception
      {

        if(args.length < 4){
          System.out.println("Ingresar 1ro el modo: r (read) o w (write)");
          System.out.println("Para modo r ingresar 2: nombre, 3: posicion y 4: cantidad de bytes a leer");
          System.out.println("Para modo w ingresar 2: nombre, 3: cantidad de bytes a escribir y 4: contenido");
          System.out.println("Para modo f ingresar 2: nombre, 3: cantidad de bytes a escribir y 4: path del archivo");
          System.exit(1);
        }

        // Channel is the abstraction to connect to a service endpoint
        // Let's use plaintext communication because we don't have certs
        final ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8080")
          .usePlaintext(true)
          .build();

        FtpServiceGrpc.FtpServiceBlockingStub stub = FtpServiceGrpc.newBlockingStub(channel);

        // It is up to the client to determine whether to block the call
        // Here we create a blocking stub, but an async stub,
        // or an async stub with Future are always possible.
        
        switch(args[0]){
        case "r":
          read(stub, args[1], Long.parseLong(args[2]), Integer.parseInt(args[3]));
          break;
        case "w":
          write(stub, args[1], Integer.parseInt(args[2]), args[3].getBytes());
          break;
        case "f":
          byte[] fileDataToWrite = readFile(args[1]);
          writeFile(stub, args[1], fileDataToWrite.length, fileDataToWrite);
   
        default:
          System.out.println("modo ingresado invalido. Ingrese r para modo read o w para modo write");
        }
        
        // A Channel should be shutdown before stopping the process.
        channel.shutdownNow();
      }

      public static void read(FtpServiceGrpc.FtpServiceBlockingStub stub, String name, long position, int amount){
        FTPService.ReadRequest request =
          FTPService.ReadRequest.newBuilder()
            .setName(name)
            .setPosition(position)
            .setAmount(amount)
            .build();

        // Finally, make the call using the stub
        FTPService.ReadResponse response = 
          stub.read(request);

        System.out.println(response);
      }

      public static void write(FtpServiceGrpc.FtpServiceBlockingStub stub, String name, int amount, byte[] buffer){
        FTPService.WriteRequest request =
          FTPService.WriteRequest.newBuilder()
            .setName(name)
            .setAmount(amount)
            .setBuffer(ByteString.copyFrom(buffer))
            .build();

        // Finally, make the call using the stub
        FTPService.WriteResponse response = 
          stub.write(request);

        System.out.println(response);

      }
       public static void writeFile(FtpServiceGrpc.FtpServiceBlockingStub stub, String name, int amount, byte[] buffer){
        FTPService.WriteFileRequest request =
          FTPService.WriteFileRequest.newBuilder()
            .setName(name)
            .setAmount(amount)
            .setBuffer(ByteString.copyFrom(buffer))
            .build();

        // Finally, make the call using the stub
        FTPService.WriteFileResponse response = stub.writeFile(request);

        System.out.println(response);

      }
      public static byte[] readFile(String filePath) {
        byte[] fileData = null;
        try {
            File file = new File(filePath);
            fileData = new byte[(int) file.length()];

            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                fileInputStream.read(fileData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileData;
    }


  }
