package zhzzPaper;

import it.unisa.dia.gas.jpbc.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PolynomialMethod {
    //多项式表示为数组时，定义p[0]为常数项，p[n]为n次项

    public static List<Element> polyStripZero(List<Element> p1){
        if(p1.size()==0) {
            return p1;
        }
        while (p1.get(p1.size() - 1).isZero()) {
            p1.remove(p1.size() - 1);
            if(p1.size()==0){
                return p1;
            }
        }
        return p1;
    }

    public static List<Element> polyAdd(List<Element> p1, List<Element> p2) {
        List<Element> addresult = new ArrayList<>();
        if (p1.size()  > p2.size()) {
            addresult.addAll(p1);
            for (int i = 0; i < p2.size(); i++) {
                addresult.set(i,addresult.get(i).add(p2.get(i)));
            }
        } else {
            addresult.addAll(p2);
            for (int i = 0; i < p1.size(); i++) {
                addresult.set(i,addresult.get(i).add(p1.get(i)));
            }
        }
        addresult = polyStripZero(addresult);
        return addresult;
    }

    public static List<Element> polyMinus(List<Element> p1, List<Element> p2) {
        List<Element> minusresult = new ArrayList<>();
        if (p1.size()  > p2.size()) {
            minusresult.addAll(p1);
            for (int i = 0; i < p2.size(); i++) {
                minusresult.set(i,minusresult.get(i).sub(p2.get(i)));
            }
            return minusresult;
        } else {
            minusresult.addAll(p2);
            for (int i = 0; i < p2.size(); i++) {
                minusresult.set(i,minusresult.get(i).negate());
            }
            minusresult = polyAdd(minusresult, p1);
            minusresult = polyStripZero(minusresult);
            return minusresult;
        }
    }

    public static List<Element> polyMulti(List<Element> p1, List<Element> p2){
        List<Element> mulresult = new ArrayList<>();
        for(int i = 0; i < p2.size() + p1.size() - 1; i++){
            mulresult.add(p1.get(0).duplicate().setToZero());
        }
        List<Element> p1muled = new ArrayList<>();
        for(int i = 0; i < p2.size(); i++){
            p1muled.clear();
            p1muled.addAll(p1);
            for(int j = 0; j < p1.size(); j++){
                p1muled.set(j,p1muled.get(j).duplicate().mul(p2.get(i)));
                mulresult.set(i+j,mulresult.get(i+j).duplicate().add(p1muled.get(j).duplicate()));
            }
        }
        mulresult = polyStripZero(mulresult);
        return mulresult;
    }

    public static Map<String, List<Element>> polyDiv(List<Element> p1, List<Element> p2){
        List<Element> quotient = new ArrayList<>();
        List<Element> mulsingle = new ArrayList<>();
        List<Element> dividend = new ArrayList<>();;
        p1 = polyStripZero(p1);
        p2 = polyStripZero(p2);
        Map<String, List<Element>> divresult = new HashMap<String, List<Element>>();
        dividend.addAll(p1);
        int divresultLen = p1.size() - p2.size() + 1;
        if(p1.size() >= p2.size()) {
            for (int i = divresultLen - 1; i >= 0; i--) {
                mulsingle.add(p1.get(0).duplicate().setToZero());
                quotient.add(p1.get(0).duplicate().setToZero());
            }
            for (int i = divresultLen - 1; i >= 0; i--) {
                for (Element ele:mulsingle) {
                    ele.setToZero();
                }
                quotient.set(i, dividend.get(dividend.size() - 1).duplicate().div(p2.get(p2.size() - 1)));
                mulsingle.set(i, quotient.get(i).duplicate());
                dividend = polyMinus(dividend, polyMulti(p2, mulsingle));
            }
        }else {
           System.out.println("----------被除数阶次高于除数-----------");
        }
        divresult.put("quotient",quotient);
        divresult.put("remainder",dividend);
        return divresult;
    }

    public static Map<String, List<Element>> polyEuclid(List<Element> a, List<Element> b)
    {
        Map<String, List<Element>> Euclidresult = new HashMap<String, List<Element>>();
        List<Element> r1a = new ArrayList<>();
        List<Element> r1b = new ArrayList<>();
        List<Element> r2a = new ArrayList<>();
        List<Element> r2b = new ArrayList<>();
        List<Element> r3a = new ArrayList<>();
        List<Element> r3b = new ArrayList<>();
        List<Element> r = new ArrayList<>();
        List<Element> q = new ArrayList<>();
        Map<String, List<Element>> result;
        if(a.size()>b.size()){
            r1a.add(0,a.get(0).duplicate().setToOne());
            r2b.add(0,a.get(0).duplicate().setToOne());
        } else {
            r2a.add(0,a.get(0).duplicate().setToOne());
            r1b.add(0,a.get(0).duplicate().setToOne());
        }
        do{
            result = polyDiv(polyAdd(polyMulti(a, r1a), polyMulti(b, r1b)), polyAdd(polyMulti(a, r2a), polyMulti(b, r2b)));
            q = result.get("quotient");
            r = result.get("remainder");
            r3a = polyMinus(r1a, polyMulti(q, r2a));
            r3b = polyMinus(r1b, polyMulti(q, r2b));
            r1a = r2a;
            r1b = r2b;
            r2a = r3a;
            r2b = r3b;
        }while (r.size() != 1);
        r3a = polyDiv(r3a,r).get("quotient");
        r3b = polyDiv(r3b,r).get("quotient");
        Euclidresult.put("A",r3a);
        Euclidresult.put("B",r3b);
        return Euclidresult;
    }
}
