import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Loader {

    private static final int FILE_NUMBERS = 4;
    private static final String[] FILES_NAME = {"res/numbers1.txt", "res/numbers2.txt",
            "res/numbers3.txt", "res/numbers4.txt"};
    private static PrintWriter[] writers = new PrintWriter[FILE_NUMBERS];

    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();
        ExecutorService service = Executors.newFixedThreadPool(FILE_NUMBERS);

        for (int i = 0; i < FILE_NUMBERS; i++) {
            writers[i] = new PrintWriter(FILES_NAME[i]);
        }

        char letters[] = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};
        int regionCode = 199;

        for (int number = 1; number < 1000; number++) {
            int index = (number - 1) % FILE_NUMBERS;
            int numberCar = number;

            service.submit(() -> {
                StringBuffer buffer = new StringBuffer();

                for (char firstLetter : letters) {
                    for (char secondLetter : letters) {
                        for (char thirdLetter : letters) {
                            buffer.append(firstLetter)
                                    .append(padNumber(numberCar, 3))
                                    .append(secondLetter)
                                    .append(thirdLetter)
                                    .append(padNumber(regionCode, 2))
                                    .append("\n");
                        }
                    }
                }
                writers[index].write(buffer.toString());
            });
        }
        service.shutdown();
        while (!service.isTerminated()) {
        }

        for (PrintWriter writer : writers) {
            writer.flush();
            writer.close();
        }
        System.out.println((System.currentTimeMillis() - start) + " ms");
    }

    private static String padNumber(int number, int numberLength) {
        String numberStr = Integer.toString(number);
        StringBuffer buffer = new StringBuffer(numberStr);
        int padSize = numberLength - buffer.length();

        for (int i = 0; i < padSize; i++) {
            buffer.insert(0, "0");
        }
        return buffer.toString();
    }
}
