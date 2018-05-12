package com.jk.thread;

/**
 * 我的第一个多线程
 */
public class threads extends Thread {

    public void run(){
        System.out.println("123");
    }

    public static void main(String[] args) {
        Thread thread = new Thread(){
            public void run(){
                for (int i = 0; i < 2; i ++){
                    System.out.println("thread0" + ":" + i);
                }
            }
        };
        Thread thread1 = new Thread(){
            public void run(){
                for (int i = 0; i < 2; i ++){
                    System.out.println("thread1" + ":" + i);
                }
            }
        };
        Thread thread2 = new Thread(){
            public void run(){
                for (int i = 0; i < 2; i ++){
                    System.out.println("thread2" + ":" + i);
                }
            }
        };
        thread.start();
        thread1.start();
        thread2.start();

        for (int i = 0; i < 2; i ++){
            System.out.println("thread3" + ":" + i);
        }
        for (int i = 0; i < 2; i ++){
            System.out.println("thread4" + ":" + i);
        }
        for (int i = 0; i < 2; i ++){
            System.out.println("thread5" + ":" + i);
        }
    }

}
