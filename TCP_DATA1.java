import java.io.*;
import java.net.*;

public class TCP_DATA1 {
    private static final String HOST = "36.50.135.242";
    private static final int PORT = 2206; 
    private static final String CREDS = "B23DCAT120;rR4TMMmV";
    public static void main(String[] args) throws Exception {
        try (
            Socket client = new Socket(HOST, PORT);
        ) {
            DataInputStream in = new DataInputStream(client.getInputStream());
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            out.writeUTF(CREDS);
            out.flush();
            int a = in.readInt();
            int b = in.readInt();
            out.writeInt(a + b);
            out.writeInt(a * b);
            out.flush();
        } catch(Exception e) {
        }
    }
}
