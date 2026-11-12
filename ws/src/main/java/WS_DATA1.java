import java.util.ArrayList;
import java.util.List;

import ptit.dblab.judge.network.server.soap.DataService;
import ptit.dblab.judge.network.server.soap.SoapDataService;

public class WS_DATA1 {
    private static final String[] CREDS = {"B23DCAT120", "AhPtwlhL"};
    public static String solve(List<Integer> a) {
        List<String> s = new ArrayList<>();
        for (int x : a) s.add(String.valueOf(x));
        s.sort((x, y) -> (y + x).compareTo(x + y));
        StringBuilder sb = new StringBuilder();
        for (String x : s) sb.append(x);
        return sb.charAt(0) == '0' ? "0" : sb.toString();
    }
    public static void main(String[] args) throws Exception {
        SoapDataService service = new DataService().getSoapDataServicePort();
        List<Integer> data = service.getData(CREDS[0], CREDS[1]);
        String res = solve(data);
        service.submitDataString(CREDS[0], CREDS[1], res);
    }
}
