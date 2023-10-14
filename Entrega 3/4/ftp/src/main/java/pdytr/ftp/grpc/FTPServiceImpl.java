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
      Byte[] buffer = new byte[request.getOffset];
      int readBytes;
      RandomAccessFile rfile = new File(request.getName(), "r"){
        rfile.seek(request.getPosition);

        //supestamente lee hasta completar el buff sino usar read(bytes[], off,len)
        readBytes = rfile.read(buffer);

        //leyo hasta el final del archivo
        if(readBytes == -1){
          readBytes = rfile.length() - request.getOffset;
        }

      };

      // You must use a builder to construct a new Protobuffer object
    FTPService.ReadResponse response = FTPService.ReadResponse.newBuilder()
      .setContent(buffer)
      .setRequestedReadBytes(request.getRequestedReadBytes)
      .setReadBytes(readBytes)
      .build();

    // Use responseObserver to send a single response back
    responseObserver.onNext(response);
    }
    catch(FileNotFoundException e){
      System.out.println("El archivo solicitado no existe");
      e.printStackTrace();
    }
    finally{
      if (file != null){
        file.close();
      }
    }

    

    // When you are done, you must call onCompleted.
    responseObserver.onCompleted();
  }

  public void write(FTPService.WriteRequest request,
        StreamObserver<FTPService.WriteResponse> responseObserver) {
    
    // HelloRequest has toString auto-generated.
    System.out.println(request);

    // You must use a builder to construct a new Protobuffer object
    FTPService.WriteResponse response = FTPService.WriteResponse.newBuilder()
      .setName(request.getName)
      .setOffset(request.getOffset)
      .setBuffer(request.getBuffer)
      .build();

    // Use responseObserver to send a single response back
    responseObserver.onNext(response);

    // When you are done, you must call onCompleted.
    responseObserver.onCompleted();
  }
}