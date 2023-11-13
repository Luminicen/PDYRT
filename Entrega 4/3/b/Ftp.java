import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class Ftp {
    public static byte[] read(String name, int position, int amount) {

        RandomAccessFile rfile;
        long readBytes = 0;

        //crea un buffer del tamaño de datps cantidad a leer
        byte[] buffer;
        try{

            rfile = new RandomAccessFile(name, "r");
            buffer = (amount > 0) ? new byte[amount] : new byte[parserfile.length()];

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
            return buffer;
        }

    }

    public static void write(String name, byte[] content) {

        RandomAccessFile wfile;
        long size = 0;
        long amount = 0;
        try{
            wfile = new RandomAccessFile(name, "rw");

            size = wfile.length();

            wfile.seek(size);
            wfile.write(content);

            amount = wfile.length() - size;

            wfile.close();

        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            System.out.println("Cantidad de bytes escritos: " + amount);

        }
    }
}
