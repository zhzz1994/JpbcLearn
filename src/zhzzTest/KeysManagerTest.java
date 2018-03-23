package zhzzTest;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;
import zhzzPaper.KeysManager;

public class KeysManagerTest {
    public static void main(String []args) throws Exception
    {
        encKeyGenTest();
    }

    public static void diffMethodComputePK()
    {
        KeysManager km = new KeysManager();
        km.userKeysGen(20);
        int [] userList = {13,9};
        km.encKeyGen(userList);
        Element dk = km.decKeyGen(13);
        Element [] grouppublickeysList = km.groupPublicKeysList;
        Element [] groupsecretkeysList = km.groupSecretKeysList;
        System.out.println(dk);
        System.out.println(km.EncKey);
        System.out.println(km.pairing.pairing(grouppublickeysList[2], grouppublickeysList[17]).powZn(km.EncKeyRondomValue));
        Element c1 = km.groupPublicKey.mul(grouppublickeysList[6]);
        c1 = c1.mul(grouppublickeysList[10]);
        c1 = c1.powZn(km.EncKeyRondomValue);
        Element c0 = km.generatorValue.powZn(km.EncKeyRondomValue);
        Element upper = km.pairing.pairing(grouppublickeysList[13],c1);
        System.out.println(upper);
        Element di = groupsecretkeysList[13].mul(grouppublickeysList[24]);
        Element lower = km.pairing.pairing(c0,di);
        System.out.println(lower);
        Element divs = upper.div(lower);
        System.out.println(divs);
        Element inew7 = km.pairing.getZr().newRandomElement().set(7);
        Element inew11= km.pairing.getZr().newRandomElement().set(11);
        Element inew14 = km.pairing.getZr().newRandomElement().set(14);
        Element inew25 = km.pairing.getZr().newRandomElement().set(25);
        Element inew7a = km.groupPublicKeyRondomValue.powZn(inew7);
        Element inew11a = km.groupPublicKeyRondomValue.powZn(inew11);
        Element inew14a = km.groupPublicKeyRondomValue.powZn(inew14);
        Element inew25a = km.groupPublicKeyRondomValue.powZn(inew25);
        Element c1new = km.groupPublicKey.mul(km.generatorValue.powZn(inew7a));
        c1new =  c1new.mul(km.generatorValue.powZn(inew11a));
        c1new = c1new.powZn(km.EncKeyRondomValue);
        Element uppernew = km.pairing.pairing(km.generatorValue.powZn(inew14a),c1new);
        System.out.println(uppernew);
        Element dinew = km.generatorValue.powZn(inew14a);
        dinew = dinew.powZn(km.groupManagerSecretKeyRondomValue);
        Element c1newlow =  dinew.mul(km.generatorValue.powZn(inew25a));
        Element lowernew = km.pairing.pairing(km.generatorValue.powZn(km.EncKeyRondomValue),c1newlow);
        System.out.println(lowernew);
        Element divnew = uppernew.div(lowernew);
        System.out.println(divnew);
        Element i = km.pairing.getZr().newRandomElement().set(14);
        Element i1 = km.pairing.getZr().newRandomElement().set(25);
        Element i2 = km.pairing.getZr().newRandomElement().set(21);
        Element rai = km.groupManagerSecretKeyRondomValue.mul(km.groupPublicKeyRondomValue.powZn(i));
        Element raiadd = rai.add(km.groupPublicKeyRondomValue.powZn(i1)).add(km.groupPublicKeyRondomValue.powZn(i2));
        Element g = km.pairing.pairing(km.generatorValue,km.generatorValue);
        Element endupper = g.powZn(km.EncKeyRondomValue.mul(raiadd));
        System.out.println(endupper);
        Element il = km.pairing.getZr().newRandomElement().set(14);
        Element di1 = km.pairing.getZr().newRandomElement().set(25);
        Element di2 = km.pairing.getZr().newRandomElement().set(21);
        Element drai = km.groupManagerSecretKeyRondomValue.mul(km.groupPublicKeyRondomValue.powZn(il));
        Element draiadd = drai.add(km.groupPublicKeyRondomValue.powZn(di1));
        Element gl = km.pairing.pairing(km.generatorValue,km.generatorValue);
        Element endlower = gl.powZn(km.EncKeyRondomValue.mul(draiadd));
        System.out.println(endlower);
        Element enddiv = endupper.div(endlower);
        System.out.println(enddiv);
        Element g20 = km.pairing.pairing(km.generatorValue,km.generatorValue).powZn(km.EncKeyRondomValue.mul(km.groupPublicKeyRondomValue.powZn(di2)));
        System.out.println(km.pairing.pairing(grouppublickeysList[2], grouppublickeysList[17]).powZn(km.EncKeyRondomValue));
        System.out.println(g20);
    }

    public static void encKeyGenTest()
    {
        KeysManager km = new KeysManager();
        km.userKeysGen(20);
        int [] userList = {13,9};
        km.encKeyGen(userList);
        Element dk = km.decKeyGen(13);
        System.out.println(dk);
        System.out.println(km.EncKey);
    }

    public static void divTest()
    {
        TypeACurveGenerator pg = new TypeACurveGenerator(160, 512);
        PairingParameters typeAParams = pg.generate();
        Pairing pairing = PairingFactory.getPairing(typeAParams);
        Element g1 = pairing.getG1().newRandomElement();
        Element z1 = pairing.getZr().newRandomElement();
        Element z2 = pairing.getZr().newRandomElement();
        Element z3 = pairing.getZr().newRandomElement();
        Element z4 = pairing.getZr().newRandomElement();
        Element z5 = pairing.getZr().newRandomElement();
        Element z6 = pairing.getZr().newRandomElement();
        Element k1 = pairing.pairing(g1.powZn(z1),g1.powZn(z2));
        System.out.println(k1);
        Element k2 = pairing.pairing(g1.powZn(z3),g1.powZn(z4));
        Element k3 = pairing.pairing(g1.powZn(z5),g1.powZn(z6));
        System.out.println(k2);
        Element muls = k2.mul(k3);
        System.out.println(k2);
        System.out.println(muls);
        Element divs =  muls.div(k3);
        System.out.println(divs);
    }

    public static void userKeysGenTest()
    {
        KeysManager km = new KeysManager();
        km.userKeysGen(20);
        Element [] pkl = km.groupPublicKeysList;
        Element [] skl = km.groupPublicKeysList;
        for(Element pk:pkl) {
            System.out.println(pk);
        }
        for(Element sk:skl) {
            System.out.println(sk);
        }
    }
}
