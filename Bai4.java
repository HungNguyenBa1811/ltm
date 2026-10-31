import java.io.*;
import java.net.*;
import java.util.*;

public class Bai4 {
    private static final String HOST = "36.50.135.242";
    private static final int PORT = 2208; 
    private static final String CREDS = "B23DCAT120;1gai7JS2";
    public static String solve(String s) {
        Map<String, Integer> mp = new HashMap<>();
        String res = "";
        for (int i = 0; i < s.length(); i++) {
            if (!" ".equals(s.charAt(i) + ""))
                mp.put(s.charAt(i) + "", mp.getOrDefault(s.charAt(i) + "", 0) + 1);
        }
        for (int i = 0; i < s.length(); i++) {
            if (!" ".equals(s.charAt(i) + "")) {
                if (mp.get(s.charAt(i) + "") > 1) {
                    res += s.charAt(i) + ":" + mp.get(s.charAt(i) + "") + ",";
                    mp.put(s.charAt(i) + "", 0);
                }
            }
        }
        return res;
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
