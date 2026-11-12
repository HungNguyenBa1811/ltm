import java.util.*;

import ptit.dblab.judge.network.server.soap.DataService;
import ptit.dblab.judge.network.server.soap.SoapDataService;

public class WS_DATA2 {
    private static final String[] CREDS = {"B23DCAT120", "QCylD57b"};
    public static String solve(List<Integer> a) {
        long s = 0;
        for (int x : a) s += 1L * x;
        return String.valueOf(s);
    }
    public static void main(String[] args) {
        SoapDataService service = new DataService().getSoapDataServicePort();
        List<Integer> data = service.getData(CREDS[0], CREDS[1]);
        String res = solve(data);
        service.submitDataString(CREDS[0], CREDS[1], res);
    }
}
