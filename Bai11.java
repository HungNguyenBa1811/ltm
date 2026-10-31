import java.net.*;

public class Bai11 {
    private static final String HOST = "36.50.135.242";
    private static final int PORT = 2207;
    private static final String CREDS = ";B23DCAT120;v9DcKoZr"; 
    public static String solve(String s) {
        String[] w = s.split("[;,]");
        String res = w[0] + ";";
        int cmin = Integer.MAX_VALUE;
        int cmax = -1;
        for (int i = 1; i <= 50; i++) {
            cmin = Math.min(cmin, Integer.valueOf(w[i]));
            cmax = Math.max(cmax, Integer.valueOf(w[i]));
        }
        res += cmax + "," + cmin;
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