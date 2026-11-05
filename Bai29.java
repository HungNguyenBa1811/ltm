import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import RMI.DataService;

public class Bai29 {
    private static final String HOST = "36.50.135.242";
    private static final String[] CREDS = {"B23DCAT120","9q9PhlcZ"}; 
    public static List<Integer> solve(int[] a) {
        List<Integer> res = new ArrayList<>();
        if (a[0] > a[1]) res.add(1);
        for(int i = 1; i < a.length - 1; i++) {
            if (a[i] > a[i - 1] && a[i] > a[i + 1]) {
                res.add(i + 1);
            }
        }
        if (a[a.length - 1] > a[a.length - 2]) res.add(a.length);
        return res;
    }
    public static void main(String[] args) throws Exception {
        try {
            Registry client = LocateRegistry.getRegistry(HOST);
            DataService service = (DataService) client.lookup("RMIDataService");
            int[] res = (int[]) service.requestData(CREDS[0], CREDS[1]);
            service.submitData(CREDS[0], CREDS[1], solve(res));
        } catch (Exception e) {
        }
    }
}
