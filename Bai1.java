import java.io.*;
import java.net.*;
import java.util.*;

public class Bai1 {
    private static final String HOST = "36.50.135.242";
    private static final int PORT = 2208; 
    private static final String CREDS = "B23DCAT120;29mOyvIB";
    public static String solve(String s) {
        List<String> res = new ArrayList<>();
        String w[] = s.split(",");
        for (int i = 0; i < w.length; i++) {
            if (w[i].endsWith(".edu")) {
                res.add(w[i]);
            }
        }
        return String.join(",", res);
    }
    public static void main(String[] args) throws Exception {
        try (
            Socket client = new Socket(HOST, PORT);
        ) {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            out.write(CREDS);
            out.newLine();
            out.flush();
            String a = in.readLine();
            out.write(solve(a));
            out.newLine();
            out.flush();
        } catch (Exception e) {
        }
    }
}
