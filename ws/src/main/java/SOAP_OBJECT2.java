import java.util.*;

import ptit.dblab.judge.network.server.soap.Customer;
import ptit.dblab.judge.network.server.soap.ObjectService;
import ptit.dblab.judge.network.server.soap.SoapObjectService;

public class SOAP_OBJECT2 {
    private final static String[] CREDS = {"B23DCAT120", "NHFZbAMH"};
    public static List<Customer> solve(List<Customer> s) {
        List<Customer> res = new ArrayList<>();
        for (Customer x : s) 
            if (x.getPurchaseCount() >= 6 && x.getTotalSpent() >= 4000) 
                res.add(x);
        Collections.sort(res, (Customer a, Customer b) -> {
            if (a.getTotalSpent() == b.getTotalSpent()) 
                return a.getCustomerId().compareTo(b.getCustomerId()); 
            return -Float.compare(a.getTotalSpent(), b.getTotalSpent());
        });
        return res;
    }
    public static void main(String[] args) {
        SoapObjectService service = new ObjectService().getSoapObjectServicePort();  
        List<Customer> a = service.requestListCustomer(CREDS[0], CREDS[1]);
        a = solve(a);
        service.submitListCustomer(CREDS[0], CREDS[1], a);
    }

}
