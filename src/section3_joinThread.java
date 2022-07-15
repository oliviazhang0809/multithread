import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class section3_joinThread {
    public static void main(String[] args) throws InterruptedException {
        List<Long> inputNumbers = Arrays.asList(0L, 3435L, 234288L, 4656L, 23L, 2345L, 555677L); // factorial parallel

        List<FactorialThread> threads = new ArrayList<>();
        for (long input : inputNumbers) {
            threads.add(new FactorialThread(input));
        }

        for (Thread thread : threads) {
            thread.setDaemon(true); // as long as main thread finish, this thread will be exited
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join(2000); // the most time we should wait for computation to finish is 2sec. After 2sec, we will return whatever we got
        }

        for (int i = 0; i < inputNumbers.size(); i++) {
            FactorialThread thread = threads.get(i);
            if (thread.getIsFinished()) {
                System.out.println("Factorial of " + inputNumbers.get(i) + " is " + thread.getResult());
            } else {
                System.out.println("The calculation of " + inputNumbers.get(i) + " is still in progress");
            }
        }
    }

    private static class FactorialThread extends Thread {
        private long inputNumber;
        private BigInteger result = BigInteger.ZERO;
        private boolean isFinished = false;

        public FactorialThread(long inputNumber) {
            this.inputNumber = inputNumber;
        }

        @Override
        public void run() {
            this.result = factorial(inputNumber);
            this.isFinished = true;
        }

        public BigInteger factorial(long n) {
            BigInteger tmp = BigInteger.ONE;
            for (long i = n; i > 0; i--) {
                tmp = tmp.multiply(new BigInteger(Long.toString(i)));
            }
            return tmp;
        }

        public boolean getIsFinished() {
            return isFinished;
        }

        public BigInteger getResult() {
            return result;
        }
    }
}
