import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.Locale;

import RMI.DataService;

public class Bai30 {
    private static final String HOST = "36.50.135.242";
    private static final String[] CREDS = {"B23DCAT120","UUkUtpEf"}; 
public static String solve(String csvData) {
        String[] parts = csvData.trim().split("\\s*,\\s*");
        int n = parts.length;
        double[] a = new double[n];
        double sum = 0;
        for (int i = 0; i < n; i++) {
            a[i] = Double.parseDouble(parts[i]);
            sum += a[i];
        }
        double avg = sum / n;
        double varianceSum = 0;
        for (double val : a) {
            varianceSum += Math.pow(val - avg, 2);
        }
        double stddev = Math.sqrt(varianceSum / n);
        Arrays.sort(a);
        int p95Index = (int) Math.ceil(n * 0.95) - 1;
        double p95 = a[p95Index];
        return String.format(Locale.US, "average=%.2f;stddev=%.2f;p95=%.2f", avg, stddev, p95);
    }
    public static void main(String[] args) throws Exception {
        try {
            Registry client = LocateRegistry.getRegistry(HOST);
            DataService service = (DataService) client.lookup("RMIDataService");
            String res = service.requestData(CREDS[0], CREDS[1]).toString();
            service.submitData(CREDS[0], CREDS[1], solve(res));
        } catch (Exception e) {
        }
    }
}
