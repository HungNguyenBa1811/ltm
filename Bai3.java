import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Bai3 {
    private static final String HOST = "36.50.135.242";
    private static final int PORT = 2206; 
    private static final String CREDS = "B23DCAT120;YTnAsPFS";
    public static String solve(String s) {
        String w[] = s.split(",");
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        for (int i = 0; i < w.length; i++) {
            a.add(Integer.valueOf(w[i]));
            b.add(Integer.valueOf(w[i]));
        }
        int max1 = -1;
        int max2 = -1;
        int idx2 = -1;
        int idx1 = -1;

        for (int i = 0; i < a.size(); i++) {
            int x = a.get(i);
            if (x > max1) {
                max2 = max1;
                max1 = x;
                idx2 = idx1;
                idx1 = i;
            } else if (x > max2 && x < max1) {
                max2 = x;
                idx2 = i;

            }
        }
        return max2 + "," + idx2;
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
