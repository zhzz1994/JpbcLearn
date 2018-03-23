package zhzzPaper;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;
import stdlib.Out;

public class KeysManager {

    public Element accKey;
    public Pairing pairing;
    public Element generatorValue;                            //g
    public Element groupManagerSecretKeyRondomValue;       //γ
    public Element groupPublicKeyRondomValue;               //alpha
    public Element groupPublicKey;                           //v
    public Element EncKeyRondomValue;                        //t
    public Element EncKey;
    public Element [] groupSecretKeysList;
    public Element [] groupPublicKeysList;
    public Element  EncPublicKeysC1;
    public Element  EncPublicKeysC2;
    public int userRange;
    public int [] userList;

    public  KeysManager()
    {
        TypeACurveGenerator pg = new TypeACurveGenerator(160, 512);
        PairingParameters typeAParams = pg.generate();
        pairing = PairingFactory.getPairing(typeAParams);
        pairing.getZr().newRandomElement();
        generatorValue = pairing.getG1().newRandomElement().getImmutable();
        groupPublicKeyRondomValue = pairing.getZr().newRandomElement().getImmutable();
        EncKeyRondomValue = pairing.getZr().newRandomElement().getImmutable();
        groupManagerSecretKeyRondomValue = pairing.getZr().newRandomElement().getImmutable();
        groupPublicKey = generatorValue.powZn(groupManagerSecretKeyRondomValue);
        accKey = pairing.getZr().newRandomElement().getImmutable();
    }

    public void userKeysGen(int userrange)
    {
        userRange = userrange;
        Element [] grouppublickeysList = new Element [userRange * 2 + 1];
        Element [] groupsecretkeysList = new Element [userRange * 2 + 1];
        for(int i = 0;i < userRange;i ++){
            Element Element_i = pairing.getZr().newRandomElement().set(i+1).getImmutable();
            Element publickey = generatorValue.powZn(groupPublicKeyRondomValue.powZn(Element_i));
            grouppublickeysList[i] = publickey;
            Element secretkey = publickey.powZn(groupManagerSecretKeyRondomValue);
            groupsecretkeysList[i] = secretkey;

        }
        for(int i = userRange;i < userRange * 2 ;i ++){
            Element Element_i = pairing.getZr().newRandomElement().set(i+1).getImmutable();
            Element publickey = generatorValue.powZn(groupPublicKeyRondomValue.powZn(Element_i));
            grouppublickeysList[i] = publickey;
        }
        grouppublickeysList[userRange * 2] = groupPublicKey;
        groupPublicKeysList = grouppublickeysList;
        groupSecretKeysList = groupsecretkeysList;
    }

    public void encKeyGen(int [] userlist)
    {
        userList = userlist;
        EncKey = pairing.pairing(generatorValue,groupPublicKeysList[userRange]).powZn(EncKeyRondomValue);
        EncPublicKeysC1 = generatorValue.powZn(EncKeyRondomValue);
        Element c2 = groupPublicKeysList[userRange * 2];
        for(int user : userList){
            c2 = c2.mul(groupPublicKeysList[userRange - user - 1]);
        }

        EncPublicKeysC2 = c2.powZn(EncKeyRondomValue);
    }

    public Element decKeyGen(int userID)
    {
        Element userSK = groupSecretKeysList[userID];
        Element a = pairing.pairing(groupPublicKeysList[userID],EncPublicKeysC2);
        for(int user : userList){
            if(user != userID)
            {
                userSK = userSK.mul(groupPublicKeysList[userRange - user + userID]);
            }
        }

        Element b = pairing.pairing(userSK,EncPublicKeysC1);
        Element decKey = a.div(b);
        return decKey;
    }
}
