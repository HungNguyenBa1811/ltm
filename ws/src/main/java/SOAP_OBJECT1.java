import ptit.dblab.judge.network.server.soap.ObjectService;
import ptit.dblab.judge.network.server.soap.ProductY;
import ptit.dblab.judge.network.server.soap.SoapObjectService;

public class SOAP_OBJECT1 {
    private final static String[] CREDS = {"B23DCAT120", "Kkv8ttJJ"};
    public static ProductY solve(ProductY s) {
        s.setFinalPrice(s.getPrice() * (1 + s.getTaxRate() / 100) * (1 - s.getDiscount() / 100));
        return s;
    }
    public static void main(String[] args) {
        SoapObjectService service = new ObjectService().getSoapObjectServicePort();  
        ProductY a = service.requestProductY(CREDS[0], CREDS[1]);
        a = solve(a);
        service.submitProductY(CREDS[0], CREDS[1], a);
    }

}
