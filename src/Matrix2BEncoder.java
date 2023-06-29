import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Matrix2BEncoder {

    public String encode(String message, String key) {
        final Integer[] keyArray = createKey(key);
        System.out.println("Encryption key:" + Arrays.toString(keyArray));

        final Integer[] columnOrder = createColumnOrder(keyArray);
        System.out.println("Column order:" + Arrays.toString(columnOrder));

        final String strippedMessage = message.replaceAll("\\s", "");

        final int col = key.length();
        final int rows = (int) Math.ceil((double) strippedMessage.length() / col);

        final char[][] encodeArary = encodeArray(strippedMessage, col, rows);

        System.out.println("Encoded array:");
        System.out.println(prettyPrintArray(encodeArary));

        return Arrays.stream(columnOrder)
                .map(column -> getColumn(encodeArary, column, rows))
                .collect(Collectors.joining(" "));
    }

    public String decode(String message, String key) {
        final Integer[] keyArray = createKey(key);
        System.out.println("Decryption key:" + Arrays.toString(keyArray));

        final Integer[] columnOrder = createColumnOrder(keyArray);
        System.out.println("Column order:" + Arrays.toString(columnOrder));

        final String strippedMessage = message.replaceAll("\\s", "");

        final int col = key.length();
        final int rows = (int) Math.ceil((double) strippedMessage.length() / col);

        final char[][] decodeArray = decodeArray(message, col, rows, columnOrder);

        System.out.println("Decode array:");
        System.out.println(prettyPrintArray(decodeArray));

        return IntStream.range(0, rows)
                .mapToObj(row -> getRow(decodeArray, row, col))
                .collect(Collectors.joining(""));
    }

    private String prettyPrintArray(char[][] decodeArray) {
        return Arrays.deepToString(decodeArray)
                .replace("],", "\n")
                .replace(",", " | ")
                .replaceAll("[\\[\\]]", " ");
    }


    private char[][] encodeArray(String message, int col, int rows) {
        final char[][] encodeArary = new char[rows][col];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < col; j++) {
                final int index = (i * col) + j;
                if (index >= message.length()) {
                    encodeArary[i][j] = ' ';
                } else {
                    encodeArary[i][j] = message.charAt(index);
                }
            }
        }
        return encodeArary;
    }

    private char[][] decodeArray(String message, int totalColumns, int totalRows, Integer[] columnOrder) {
        String[] columns = message.split(" ");
        final char[][] encodeArary = new char[totalRows][totalColumns];
        for (int col = 0; col < totalColumns; col++) {
            for (int row = 0; row < totalRows; row++) {
                final String column = columns[col];
                final int columnPlace = columnOrder[col];
                if (row >= column.length()) {
                    encodeArary[row][columnPlace] = ' ';
                } else {
                    encodeArary[row][columnPlace] = column.charAt(row);
                }
            }
        }

        return encodeArary;
    }

    private String getColumn(char[][] encodeArary, Integer column, int totalRows) {
        return IntStream.range(0, totalRows)
                .mapToObj(row -> encodeArary[row][column])
                .collect(Collector.of(
                        StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append,
                        stringBuilder -> stringBuilder.toString().stripTrailing())
                );
    }

    private String getRow(char[][] decodeArray, int row, int totalColumns) {
        return IntStream.range(0, totalColumns)
                .mapToObj(column -> decodeArray[row][column])
                .collect(Collector.of(
                        StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append,
                        StringBuilder::toString)
                );
    }

    private Integer[] createColumnOrder(Integer[] key) {
        Integer[] result = new Integer[key.length];
        IntStream.range(0, key.length)
                .forEachOrdered(index -> result[key[index] - 1] = index);
        return result;

    }

    private Integer[] createKey(String key) {
        String sortedKey = key.chars()
                .sorted()
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        Map<Character, Queue<Integer>> indexMap = IntStream.range(0, sortedKey.length())
                .boxed()
                .collect(
                        Collectors.groupingBy(
                                sortedKey::charAt,
                                Collectors.toCollection(LinkedList::new)
                        )
                );

        return IntStream.range(0, key.length())
                .boxed()
                .map(key::charAt)
                .map(character -> indexMap.get(character).poll() + 1)
                .toArray(Integer[]::new);
    }
}
