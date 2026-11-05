import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;

import RMI.ByteService;

public class RMI_BYTE1 {
    private static final String HOST = "36.50.135.242";
    private static final String[] CREDS = {"B23DCAT120","Z8hytJ6x"}; 
    public static byte[] solve(byte[] a) {
        Map<Byte, Integer> mp = new HashMap<>();
        for (byte x : a) {
            mp.put(x, mp.getOrDefault(x, 0) + 1);
        }
        byte jmin = a[0];
        int jminval = Integer.MAX_VALUE;
        for (byte x : a) {
            int y = mp.get(x);
            if (y < jminval) {
                jminval = y;
                jmin = x;
            }
        }
        return new byte[] { 
            jmin, 
            (byte) jminval 
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
