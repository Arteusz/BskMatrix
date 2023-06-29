public class Matrix2AEncoder {

    public String encrypt(int[] key, String phrase) {
        final int keySize = key.length;
        final int rows = (int) Math.ceil((double) phrase.length() / keySize);

        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keySize * rows; i++) {
            int indexInBucket = key[i % keySize] - 1;
            int bucket = i / keySize;
            int index = bucket * keySize + indexInBucket;
            if (index > phrase.length() - 1) {
                sb.append('-');
            } else {
                char source = phrase.charAt(index);
                sb.append(source);
            }
        }

        return sb.toString();
    }

    public String decrypt(int[] key, String phrase) {
        final int keySize = key.length;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < phrase.length(); i++) {
            int decryptIndex = keySize - (i % keySize) - 1;
            int indexInBucket = key[decryptIndex] - 1;
            int bucket = i / keySize;
            int index = bucket * keySize + indexInBucket;
            if (index > phrase.length() - 1) {
                sb.append('-');
            } else {
                char source = phrase.charAt(index);
                sb.append(source);
            }
        }

        return sb.toString();
    }


}
