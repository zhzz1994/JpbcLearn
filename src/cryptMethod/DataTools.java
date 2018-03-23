package cryptMethod;

public class DataTools {

    //    作者：A_客
    //    链接：https://www.jianshu.com/p/17e771cb34aa
    //    來源：简书
    //    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    public static String byteArrayToHexStr(byte[] byteArray) {
        if (byteArray == null){
            return null;
        }
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[byteArray.length * 2];
        for (int j = 0; j < byteArray.length; j++) {
            int v = byteArray[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }


}
