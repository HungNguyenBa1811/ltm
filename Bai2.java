import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Bai2 {
    private static final String HOST = "36.50.135.242";
    private static final int PORT = 2206; 
    private static final String CREDS = "B23DCAT120;mv2jDcO3";
    public static String solve(String s) {
        String w[] = s.split(",");
        List<Integer> a = new ArrayList<>();
        for (int i = 0; i < w.length; i++) {
            a.add(Integer.valueOf(w[i]));
        }
        
        int jmin = Integer.MAX_VALUE;
        int fi = -1, se = -1;
        for (int i = 0; i < a.size(); i++) {
            for (int j = i + 1; j < a.size(); j++) {
                if (jmin > Math.abs(a.get(i) - a.get(j))) {
                    jmin = Math.abs(a.get(i) - a.get(j));
                    fi = Math.min(a.get(i), a.get(j));
                    se = Math.max(a.get(i), a.get(j));
                }
            }
        }
        return jmin + "," + fi + "," + se;
    }
    public static void main(String[] args) throws Exception {
        try (
            Socket client = new Socket(HOST, PORT);
        ) {
            InputStream in = client.getInputStream();
            OutputStream out = client.getOutputStream();
            out.write(CREDS.getBytes(StandardCharsets.UTF_8));
            out.flush();
            byte[] buf = new byte[100001];
            int len = in.read(buf);
            String a = new String(buf, 0, len).trim();
            out.write(solve(a).getBytes(StandardCharsets.UTF_8));
            out.flush();
        } catch(Exception e) {
        }
    }
}
