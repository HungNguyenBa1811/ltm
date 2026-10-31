import java.io.*;
import java.net.*;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Bai25 {
    private static final String HOST = "36.50.135.242";
    private static final int PORT = 2210;
    private static final String CREDS = "B23DCAT120;ANnslMY6"; 

    public static void main(String[] args) {
        try (Socket client = new Socket(HOST, PORT)) {
            GZIPOutputStream out = new GZIPOutputStream(client.getOutputStream(), true);
            out.write((CREDS + "\n").getBytes());
            out.flush();
            DataInputStream in = new DataInputStream(new GZIPInputStream(client.getInputStream()));
            ByteArrayOutputStream lb = new ByteArrayOutputStream();
            int next;
            while ((next = in.readUnsignedByte()) != '\n') 
                lb.write(next);
            String s = lb.toString();
            String reversed = new StringBuilder(s).reverse().toString();
            String base64 = Base64.getEncoder().encodeToString(reversed.getBytes());
            out.write((reversed + "|" + base64 + "\n").getBytes());
            out.flush();
            out.finish();            
        } catch (Exception e) {
        }
    }
}
