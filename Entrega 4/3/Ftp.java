public class Ftp {
    public void read(string name, int position, int amount) {

        RandomAccessFile rfile;
        long readBytes = 0;

        //crea un buffer del tamaño de datps cantidad a leer
        byte[] buffer = new byte[amount];
        try{

            rfile = new RandomAccessFile(name, "r");

            rfile.seek(position);
            readBytes = rfile.read(buffer);

            //si leyó hasta el final del archivo se setea en -1
            if(readBytes == -1){
                readBytes = rfile.length() - amount;
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
            System.out.println("Cantidad de bytes leidos: " + readBytes);
        }

    }

    public void write(string name) {

        RandomAccessFile wfile;
        long size = 0;
        long amount = 0;
        try{
            wfile = new RandomAccessFile(name, "rw");

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
            System.out.println("Cantidad de bytes escritos: " + amount);

        }

        // When you are done, you must call onCompleted.
        responseObserver.onCompleted();
    }
}
