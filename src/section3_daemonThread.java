import java.math.BigInteger;

public class section3_daemonThread {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new LongComputationTask(new BigInteger("20000"), new BigInteger("10")));

        thread.setDaemon(true); // will exit even the subtask (LongComputationTask) does not finish
        thread.start();
    }

    private static class LongComputationTask implements Runnable {
        private BigInteger base;
        private BigInteger power;
        public LongComputationTask(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            System.out.println(base + "^" + power + " = " + pow(base, power));
        }

        private BigInteger pow(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;
            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
                result = result.multiply(base); // factorial 10 ^ 4 => base = 10, power = 4, = 10 * 10 * 10 * 10
            }

            return result;
        }
    }
}
