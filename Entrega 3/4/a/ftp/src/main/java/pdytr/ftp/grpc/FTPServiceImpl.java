      package pdytr.ftp.grpc;

      import java.io.RandomAccessFile;
      import java.io.FileNotFoundException;
      import io.grpc.stub.StreamObserver;
      import com.google.protobuf.ByteString;

      public class FTPServiceImpl extends FtpServiceGrpc.FtpServiceImplBase {
        @Override
        public void read(FTPService.ReadRequest request,
              StreamObserver<FTPService.ReadResponse> responseObserver) {

          RandomAccessFile rfile;
          long readBytes = 0;

          //crea un buffer del tamaño de datps cantidad a leer
          byte[] buffer = new byte[request.getAmount()];
          try{
            
            rfile = new RandomAccessFile(request.getName(), "r");
            
            rfile.seek(request.getPosition());
            readBytes = rfile.read(buffer);

            //si leyó hasta el final del archivo se setea en -1
            if(readBytes == -1){
              readBytes = rfile.length() - request.getAmount();
            }

            rfile.close();
            
          }
          catch(FileNotFoundException e){
            System.out.println("El archivo solicitado no existe");
            e.printStackTrace();
          }
          catch(Exception e){
            e.printStackTrace();
          }
          finally{
            // You must use a builder to construct a new Protobuffer object
            FTPService.ReadResponse response = FTPService.ReadResponse.newBuilder()
              .setContent(ByteString.copyFrom(buffer))
              .setRequestedReadBytes(request.getAmount())
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
          
            RandomAccessFile wfile;
            long size = 0;
            long amount = 0;
            try{
              wfile = new RandomAccessFile(request.getName(), "rw");

              size = wfile.length();
    	
    	        wfile.seek(size);
              wfile.write(request.getBuffer().toByteArray());

              amount = wfile.length() - size;

              wfile.close();

            }
            catch(Exception e){
              e.printStackTrace();
            }
            finally{
              // You must use a builder to construct a new Protobuffer object
              FTPService.WriteResponse response = FTPService.WriteResponse.newBuilder()
                .setWrittenBytes(amount)
                .build();

              // Use responseObserver to send a single response back
              responseObserver.onNext(response);
            
            }

            // When you are done, you must call onCompleted.
            responseObserver.onCompleted();
        }
      }
