import java.net.*;

public class UDP_DATA1 {
    static final String HOST = "36.50.135.242";
    static final Integer PORT = 2207;
    static final String CREDS = ";B23DCAT120;7SgBVwrd";

    public static String solve(String s) {
        String[] w = s.split("[;,]");
        int n = Integer.valueOf(w[1]);
        int[] a = new int[105];
        for (int i = 2; i < w.length; i++) a[Integer.valueOf(w[i].trim())]++;
        String res = w[0] + ";";
        for (int i = 1; i <= n; i++) {
            if(a[i] < 1) res += String.valueOf(i) + ",";
        }
        if (res.endsWith(",")) res = res.substring(0, res.length() - 1);
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