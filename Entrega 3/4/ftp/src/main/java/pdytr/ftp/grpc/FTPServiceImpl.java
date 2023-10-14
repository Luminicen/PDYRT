  package pdytr.ftp.grpc;

  import java.io.RandomAccessFile;
  import java.io.FileNotFoundException;
  import io.grpc.stub.StreamObserver;

  public class FTPServiceImpl extends FtpServiceGrpc.FtpServiceImplBase {
    @Override
    public void read(FTPService.ReadRequest request,
          StreamObserver<FTPService.ReadResponse> responseObserver) {

      try{
        //crea un buffer del tamaño de datps cantidad a leer
        byte[] buffer = new byte[(Integer)request.getOffset()];
        int readBytes = 0;
        RandomAccessFile rfile = new RandomAccessFile(request.getName(), "r");
        
        //rfile.seek(request.getPosition);
        readBytes = rfile.read(buffer,(Integer)request.getPosition(),(Integer)request.getOffset());

        //si leyó hasta el final del archivo se setea en -1
        if(readBytes == -1){
          readBytes = rfile.length() - request.getOffset;
        }
        
      }
      catch(FileNotFoundException e){
        System.out.println("El archivo solicitado no existe");
        e.printStackTrace();
      }
      finally{
        if (rfile != null){
          rfile.close();
        }
        // You must use a builder to construct a new Protobuffer object
        FTPService.ReadResponse response = FTPService.ReadResponse.newBuilder()
          .setContent(buffer)
          .setRequestedReadBytes(request.getRequestedReadBytes)
          .setReadBytes(readBytes)
          .build();

        // Use responseObserver to send a single response back
        responseObserver.onNext(response);
      }

      // When you are done, you must call onCompleted.
      responseObserver.onCompleted();
    }

    public void write(FTPService.WriteRequest request,
          StreamObserver<FTPService.WriteResponse> responseObserver) {
      
        try{
          RandomAccessFile wfile = new RandomAccessFile(request.getName(), "rw");

          int size = wfile.length();

          wfile.write(request.getBuffer(), size, request.getOffset);

        }
        catch(Exception e){
          e.printStackTrace();
        }
        finally{
          int amount = 0
          if (wfile != null){
            wfile.close();
            amount = wfile.length() - size;
          }
          // You must use a builder to construct a new Protobuffer object
          FTPService.WriteResponse response = FTPService.WriteResponse.newBuilder()
            .setwrittenBytes(amount)
            .build();

          // Use responseObserver to send a single response back
          responseObserver.onNext(response);
        
        }

        // When you are done, you must call onCompleted.
        responseObserver.onCompleted();
    }
  }