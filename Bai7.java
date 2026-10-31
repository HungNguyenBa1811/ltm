import java.net.*;
import java.util.*;

public class Bai7 {    
    static final String HOST = "36.50.135.242";
    static final Integer PORT = 2208;
    static final String CREDS = ";B23DCAT120;11Zd3fhC";

    public static String solve(String s) {
        String[] w = s.split(";");
        s = w[1];
        Map<Character, Integer> mp = new HashMap<>();
        for (char x : s.toCharArray()) {
            mp.put(x, mp.getOrDefault(x, 0) + 1);
        }
        char maxChar = 0;
        int maxCount = 0;
        for (char x : s.toCharArray()) {
            if (mp.get(x) > maxCount) {
                maxCount = mp.get(x);
                maxChar = x;
            }
        }
        String res = w[0] + ";" + maxChar + ":";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == maxChar) res += (i + 1) + ",";
        }
        return res;
    }

    public static void main(String[] args) throws Exception {
        DatagramSocket client = new DatagramSocket();
        byte[] s, r; 
        DatagramPacket sp, rp;
        s = CREDS.getBytes();
        sp = new DatagramPacket(s, s.length, InetAddress.getByName(HOST), PORT);
        client.send(sp);
        r = new byte[10001];
        rp = new DatagramPacket(r, r.length);
        client.receive(rp);
        String res = solve(new String(rp.getData(), 0, rp.getLength()).trim());
        sp = new DatagramPacket(res.getBytes(), res.getBytes().length, InetAddress.getByName(HOST), PORT);
        client.send(sp);
    }
}