public class section6_atomic_data_race {
    public static void main(String[] args) {
        SharedClass sharedClass = new SharedClass();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sharedClass.increment();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sharedClass.checkDataRace();
            }
        });

        thread1.start();
        thread2.start();
    }

    public static class SharedClass {
        private int x = 0;
        private int y = 0;

        // solved the data race problem -- guarantee order
//        private volatile int x = 0;
//        private volatile int y = 0;

        public void increment() {
            x++;
            y++;
        }

        public void checkDataRace() {
            if (y > x) {
                System.out.println("y > x - Data race is detected");
            }
        }
    }
}
