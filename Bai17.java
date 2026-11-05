import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;

import RMI.ByteService;

public class Bai17 {
    private static final String HOST = "36.50.135.242";
    private static final String[] CREDS = {"B23DCAT120","FsumPw0i"}; 
    public static byte[] solve(byte[] a) {
        Map<Byte, Integer> mp = new HashMap<>();
        for (byte x : a) {
            mp.put(x, mp.getOrDefault(x, 0) + 1);
        }
        byte jmax = a[0];
        int jmaxval = Integer.MIN_VALUE;
        for (byte x : a) {
            int y = mp.get(x);
            if (y > jmaxval) {
                jmaxval = y;
                jmax = x;
            }
        }
        return new byte[] { 
            jmax, 
            (byte) jmaxval 
        };
    }
    public static void main(String[] args) throws Exception {
        try {
            Registry client = LocateRegistry.getRegistry(HOST);
            ByteService service = (ByteService) client.lookup("RMIByteService");
            byte[] res = service.requestData(CREDS[0], CREDS[1]);
            service.submitData(CREDS[0], CREDS[1], solve(res));
        } catch (Exception e) {
        }
    }
}
