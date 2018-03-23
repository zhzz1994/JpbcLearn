package zhzzTest;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;
import zhzzPaper.PolynomialMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PolynomialMethodTest {

    public static List<Element> polyGen(int len, Pairing pairing){
        List<Element> poly = new ArrayList<>();
        for(int i=0;i<len;i++){
            poly.add( pairing.getZr().newRandomElement().getImmutable());
        }
        return poly;
    }

    public static List<Element> polyAddTest(List<Element> p1, List<Element> p2){
        System.out.println("---------p1---------");
        for(Element el:p1){
            System.out.println(el);
        }
        System.out.println("---------p2---------");
        for(Element el:p2){
            System.out.println(el);
        }
        List<Element> ret = PolynomialMethod.polyAdd(p1,p2);
        System.out.println("---------return---------");
        for(Element el:ret){
            System.out.println(el);
        }
        return ret;
    }

    public static List<Element> polyMinusTest(List<Element> p1, List<Element> p2){
        System.out.println("---------p1---------");
        for(Element el:p1){
            System.out.println(el);
        }
        System.out.println("---------p2---------");
        for(Element el:p2){
            System.out.println(el);
        }
        List<Element> ret = PolynomialMethod.polyMinus(p1,p2);
        System.out.println("---------return---------");
        for(Element el:ret){
            System.out.println(el);
        }
        return ret;
    }

    public static List<Element> polyMultyTest(List<Element> p1, List<Element> p2){
        System.out.println("---------Multy First---------");
        System.out.println(p1.get(0).duplicate().mul(p2.get(0)));
        System.out.println("---------Multy Last---------");
        System.out.println(p1.get(p1.size()-1).duplicate().mul(p2.get(p2.size()-1)));
        System.out.println("---------p1---------");
        for(Element el:p1){
            System.out.println(el);
        }
        System.out.println("---------p2---------");
        for(Element el:p2){
            System.out.println(el);
        }
        List<Element> ret = PolynomialMethod.polyMulti(p1,p2);
        System.out.println("---------return---------");
        for(Element el:ret){
            System.out.println(el);
        }
        return ret;
    }

    public static Map<String, List<Element>> polyDivTest(List<Element> p1, List<Element> p2){
        System.out.println("---------p1---------");
        for(Element el:p1){
            System.out.println(el);
        }
        System.out.println("---------p2---------");
        for(Element el:p2){
            System.out.println(el);
        }
        Map<String, List<Element>> ret = PolynomialMethod.polyDiv(p1,p2);
        System.out.println("---------return quotient---------");
        for(Element el:ret.get("quotient")){
            System.out.println(el);
        }
        System.out.println("---------return remainder---------");
        for(Element el:ret.get("remainder")){
            System.out.println(el);
        }
        System.out.println("---------return multy---------");
        List<Element> multy = PolynomialMethod.polyAdd(ret.get("remainder"),PolynomialMethod.polyMulti(p2,ret.get("quotient")));
        for(Element el:multy){
            System.out.println(el);
        }
        return ret;
    }

    public static void polyEuclidTest(List<Element> p1, List<Element> p2){
        System.out.println("---------p1---------");
        for(Element el:p1){
            System.out.println(el);
        }
        System.out.println("---------p2---------");
        for(Element el:p2){
            System.out.println(el);
        }
        Map<String, List<Element>> Euclidresult = PolynomialMethod.polyEuclid(p1,p2);
        System.out.println("---------A---------");
        for(Element el:Euclidresult.get("A")){
            System.out.println(el);
        }
        System.out.println("---------B---------");
        for(Element el:Euclidresult.get("B")){
            System.out.println(el);
        }
        List<Element> test = PolynomialMethod.polyAdd(PolynomialMethod.polyMulti(p1,Euclidresult.get("A")),PolynomialMethod.polyMulti(p2,Euclidresult.get("B")));
        System.out.println("---------test---------");
        for(Element el:test){
            System.out.println(el);
        }
    }

    public static void main(String[] args) {
        TypeACurveGenerator pg = new TypeACurveGenerator(160, 512);
        PairingParameters typeAParams = pg.generate();
        Pairing pairing = PairingFactory.getPairing(typeAParams);
        List<Element> polyA = polyGen(17,pairing);
        List<Element> polyB = polyGen(19,pairing);
//        List<Element> adds = polyAddTest(polyA,polyB);
//        List<Element> sub = polyMinusTest(polyA,polyA);
//        List<Element> mul = polyMultyTest(polyA,polyB);
//        Map<String, List<Element>> div = polyDivTest(polyA,polyB);
        polyEuclidTest(polyA,polyB);
    }
}
