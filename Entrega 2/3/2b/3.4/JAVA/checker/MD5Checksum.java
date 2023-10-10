package checker;
import java.security.*;
public class MD5Checksum
{
    public static byte[] generate(byte[] message)
    {
        MessageDigest messageDigest = null;
        try
        {
            messageDigest = MessageDigest.getInstance("MD5");
            return messageDigest.digest(message);
        }
        catch (NoSuchAlgorithmException exception)
        {
            exception.printStackTrace();
            return null;
        }
    }
    private static String checksumToString(byte[] checksum)
    {
        StringBuffer stringBuffer = new StringBuffer();

        for (byte bytes : checksum)
            stringBuffer.append(String.format("%02x", bytes & 0xff));

        return new String(stringBuffer);
    }

    public static boolean isValid(byte[] checksum, byte[] message)
    {
        String checksumString = checksumToString(checksum);
        byte[] checksumMessageInBytes = MD5Checksum.generate(message);
        String checksumMessage = checksumToString(checksumMessageInBytes);

        return checksumString.equals(checksumMessage);
    }

}