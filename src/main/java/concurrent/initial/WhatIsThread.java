package concurrent.initial;

import javax.swing.plaf.TableHeaderUI;

/**
 * Description: 什么是线程，进程，协程等 Program:tablo-way concurrent.initial Created on 2020/8/23 17:11
 * 概念：线程是CPU分配资源的最小单位，，进程是资源分配的最小单位，它是程序运行时的一个实例
 *
 * @author <a href="mailto: Tablo_Jhin1996@outlook.com">Tablo</a>
 * @version 1.0
 */
public class WhatIsThread {
  public static void main(String[] args) {

    // 通过实现Runnable接口重写run方法，最终需要通过newThread类来调用其start方法执行
    T1 t1 = new T1();
    // 以该对象为参数new一个Thread来调用start
    Thread thread1 = new Thread(t1);
    // 调用run方法也可以执行代码，但和多线程无关了
    thread1.run();
    thread1.start();

    // 通过继承Thread方法重写run方法，直接new该对象并调用start方法即可
    T2 t2 = new T2();
    t2.start();
    // 同上
    t2.run();
    new Thread(() -> System.out.println("Lambda模式线程")).start();

    //
  }

  static class T1 implements Runnable {
    @Override
    public void run() {
      System.out.println("这是一个通过实现Runnable接口执行的线程，run方法中是线程要执行的具体代码");
    }
  }

  static class T2 extends Thread {
    @Override
    public void run() {
      System.out.println("这是一个通过集成Thread类执行的线程，run方法中是线程要执行的具体代码");
    }
  }
}

// ~ Formatted by Jindent --- http://www.jindent.com
