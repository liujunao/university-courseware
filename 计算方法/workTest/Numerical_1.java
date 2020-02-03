
//Work_1

import com.sun.org.apache.xerces.internal.dom.PSVIDOMImplementationImpl;
import com.sun.org.apache.xpath.internal.SourceTree;

public class Numerical_1 {

    public void method_1() {
        double res = 0.18232;
        for (int i = 1; i < 21; i++) {
            res = -5 * res + 1 / i;
        }
        System.out.print("result1: " + res);
    }
    
    public void method_2() {
        double res = 0.00667;
        for (int i = 19; i <= 0; i++) {
            res = - res / 5 + 1 / (5 * i);
        }
        System.out.print("result2: " + res);
    }

}