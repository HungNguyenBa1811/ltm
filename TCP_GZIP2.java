import java.io.*;
import java.net.*;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class TCP_GZIP2 {
    static final String HOST = "36.50.135.242";
    static final int PORT = 2210;
    static final String CREDS = "B23DCAT120;VAHA0iix";

    public static void main(String[] args) {
        try (Socket client = new Socket(HOST, PORT)) {
            GZIPOutputStream out = new GZIPOutputStream(client.getOutputStream(), true);
            out.write((CREDS + "\n").getBytes());
            out.flush();

            DataInputStream in = new DataInputStream(new GZIPInputStream(client.getInputStream()));
            ByteArrayOutputStream lb = new ByteArrayOutputStream();
            int nb;
            while ((nb = in.readUnsignedByte()) != '\n') {
                lb.write(nb);
            }
            String s = lb.toString();
            char[] ss = s.toCharArray();
            Arrays.sort(ss);
            String res = new String(ss);

            out.write((res + "\n").getBytes());
            out.flush();
            out.finish();
        } catch (Exception e) {
        }
    }
}
