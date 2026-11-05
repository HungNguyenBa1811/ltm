import java.io.*;
import java.net.*;

public class TCP_DATA2 {
    private static final String HOST = "36.50.135.242";
    private static final int PORT = 2207;
    private static final String CREDS = "B23DCAT120;AGHMGYCZ"; 
    public static String solve(String s, int b) throws Exception {
        String dict = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String res = "";
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) 
                res += dict.charAt((((((int) (s.charAt(i)) - 'A') - b) % 26) + 26) % 26);
            else 
                res += s.charAt(i);
        }
        return res;
    }
    public static void main(String[] args) throws Exception {
        try (Socket client = new Socket(HOST, PORT)) {
            DataInputStream in = new DataInputStream(client.getInputStream());
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            out.writeUTF(CREDS);
            out.flush();
            String a = in.readUTF();
            int b = in.readInt();
            out.writeUTF(solve(a, b));
            out.flush();
        } catch (Exception e) {
        }
    }
}
