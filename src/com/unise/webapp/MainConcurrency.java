package com.unise.webapp;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    private int counter;
    private static final Object LOCK = new Object();
    private static final int THREADS_NUMBER = 10000;

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
            }
        };
        thread0.start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
            }

            private void inc() {
                synchronized (this) {
//                    counter++;
                }
            }

        }).start();

        System.out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
            });
            thread.start();
            threads.add(thread);
        }

        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(mainConcurrency.counter);

        final String lock1 = "lock1";
        final String lock2 = "lock2";
        DeadLock.deadlock(lock1, lock2);
        DeadLock.deadlock(lock2, lock1);
    }

    private static class DeadLock {
        private static void deadlock(Object lock1, Object lock2) {
            startLockedThread(lock1, lock2,
                    "Поток 1: Захватил ресурс 1.",
                    "Поток 1: Ждет ресурс 2.",
                    () -> {
                        synchronized (lock1) {
                            System.out.println("Поток 1: Захватил ресурс 1.");
                            sleep(100);
                        }
                    });

            startLockedThread(lock2, lock1,
                    "Поток 2: Захватил ресурс 2.",
                    "Поток 2: Ждет ресурс 1.",
                    () -> {
                        synchronized (lock1) {
                            System.out.println("Поток 2: Захватил ресурс 2.");
                            sleep(100);
                        }
                    });
        }

        private static void startLockedThread(Object firstLock, Object secondLock, String successMsg,
                                              String waitMsg, Runnable lockBlock) {
            new Thread(() -> {
                lockBlock.run();
                System.out.println(waitMsg);
                synchronized (secondLock) {
                    System.out.println(successMsg);
                }
            }).start();
        }

        private static void sleep(long millis) {
            try {
                Thread.sleep(millis);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private synchronized void inc() {
        counter++;
    }
}
