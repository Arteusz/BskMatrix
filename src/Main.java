import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Matrix2BEncoder mat = new Matrix2BEncoder();

        encode(mat, "HERE IS A SECRET MESSAGE ENCIPHERED BY TRANSPOSITION", "CONVENIENCE");
        encode(mat, "ABCDEFGHIJKLM", "ACBD");
        encode(mat, "FRANCJA", "BCADA");
        encode(mat, "DOBRYDZIEN", "BCADA");
        encode(mat, "POLITECHNIKA", "BCADA");
        encode(mat, "UNIVERSITY", "BCADA");
    }

    public static void main2(String[] args) {
        Matrix2AEncoder matrix2AEncoder = new Matrix2AEncoder();

        final int[] key = new int[]{3, 1, 4, 2};
        System.out.println("Encryption key: " + Arrays.toString(key));
        final String phrase = "CRYPTOGRAPHYOSA";
        System.out.println("Encrypted phrase: " + phrase);
        String encrypted = matrix2AEncoder.encrypt(key, phrase);
        System.out.println("Encrypted: " + encrypted);
        String decrypted = matrix2AEncoder.decrypt(key, encrypted);
        System.out.println("Decrypted: " + decrypted);
    }

    private static void encode(Matrix2BEncoder mat, String message, String key) {
        System.out.println("Encoding");
        System.out.println("Message: " + message);
        System.out.println("Key: " + key);
        String encodedMessage = mat.encode(message, key);
        System.out.println("Encoded message:");
        System.out.println(encodedMessage);
        System.out.println();

        System.out.println("Decoding");
        System.out.println("Message: " + encodedMessage);
        System.out.println("Key: " + key);
        String decodedMessage = mat.decode(encodedMessage, key);
        System.out.println("Decoded message:");
        System.out.println(decodedMessage);
        System.out.println();

    }


}
