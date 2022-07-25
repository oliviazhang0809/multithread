import java.util.Random;

public class section6_deadlock {
    /**
     * To avoid dead lock -- break any of below!
     *
     * Mutual exclusion - only one thread can have exclusive access to a resource
     * Hold and wait - at least one thread is holding a resource and is waiting for another resource
     * Non-preemptive allocation - A resource is released only after the thread is done using it
     * Circular wait - a chain of at least two threads each one is holding one resource and is waiting for another resource --- easiest
     *
     */
    public static void main(String[] args) {
        Intersection intersection = new Intersection();
        Thread trainAThread = new Thread(new TrainA(intersection));
        Thread trainBThread = new Thread(new TrainB(intersection));

        trainAThread.start();
        trainBThread.start();
    }

    public static class TrainA implements Runnable {
        private Intersection intersection;
        private Random random = new Random();

        public TrainA(Intersection intersection) {
            this.intersection = intersection;
        }

        @Override
        public void run() {
            while (true) {
                long sleepingTime = random.nextInt(5);
                try {
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException e) {

                }
                intersection.takeRoadA();
            }
        }
    }

    public static class TrainB implements Runnable {
        private Intersection intersection;
        private Random random = new Random();

        public TrainB(Intersection intersection) {
            this.intersection = intersection;
        }

        @Override
        public void run() {
            while (true) {
                long sleepingTime = random.nextInt(5);
                try {
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException e) {

                }
                intersection.takeRoadB();
            }
        }
    }

    public static class Intersection {
        private Object roadA = new Object();
        private Object roadB = new Object();

        public void takeRoadA() {
            synchronized (roadA) {
                System.out.println("Road A is locked by thread " + Thread.currentThread().getName());

                synchronized (roadB) { // train cannot go to road B while road A is used
                    System.out.println("Train is passing road A");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {

                    }
                }
            }
        }

        public void takeRoadB() {
//            synchronized (roadA) { ----> RESOLVE THE PROBLEM, MAKE SURE SAME ORDER
//                System.out.println("Road A is locked by thread " + Thread.currentThread().getName());
//
//                synchronized (roadB) { // train cannot go to road B while road A is used
//                    System.out.println("Train is passing road A");
//                    try {
//                        Thread.sleep(1);
//                    } catch (InterruptedException e) {
//
//                    }
//                }
//            }

            synchronized (roadB) {
                System.out.println("Road B is locked by thread " + Thread.currentThread().getName());

                synchronized (roadA) { // train cannot go to road A while road B is used
                    System.out.println("Train is passing road B");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {

                    }
                }
            }
        }
    }
}
