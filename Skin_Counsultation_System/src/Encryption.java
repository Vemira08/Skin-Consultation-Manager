public class Encryption {
    private static int key = 6;

    // Encrypting a text message
    public String encryptData(String str) {

        String x = "";

        char[] chars = str.toCharArray();

        for (char c : chars) {
            c += key;
            x += c;
        }

        return x;
    }

    // Decrypting a text message
    public String decryptData(String str) {

        String x = "";
        char[] chars = str.toCharArray();

        for (char c : chars) {
            c -= key;
            x += c;
        }

        return x;
    }
}
