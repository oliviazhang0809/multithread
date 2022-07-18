public class section5_race_condition {
    public static void main(String[] args) throws InterruptedException {
        InventoryCounter inventoryCounter = new InventoryCounter();
        IncrementingThread incrementingThread = new IncrementingThread(inventoryCounter);
        DecrementingThread decrementingThread = new DecrementingThread(inventoryCounter);

        incrementingThread.start();


        decrementingThread.start();

        incrementingThread.join();
        decrementingThread.join();

        System.out.println("We currently have " + inventoryCounter.getItem() + " items");
    }

    public static class DecrementingThread extends Thread {

        private InventoryCounter inventoryCounter;
        public DecrementingThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                inventoryCounter.decrement();
            }
        }
    }

    public static class IncrementingThread extends Thread {

        private InventoryCounter inventoryCounter;
        public IncrementingThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                inventoryCounter.increment();
            }
        }
    }

    public static class InventoryCounter {
        private static int item = 0;

        Object lock = new Object();

        public void increment() {
            synchronized (this.lock) { // synchronized is locking on object level, using this way instead of public synchronized void (to lock all methods) is more efficient
                item++;
            }
        }

        public void decrement() {
            synchronized (this.lock) {
                item--;
            }
        }

        public int getItem() {
            synchronized (this.lock) {
                return item;
            }
        }
    }
}
