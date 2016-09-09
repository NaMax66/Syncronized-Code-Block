package ru.namax.sync;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Naidovich on 09.09.2016.
 */
public class Worker
{

    private ArrayList<Integer> list1 = new ArrayList<>();
    private ArrayList<Integer> list2 = new ArrayList<>();
    final private Object object1 = new Object();
    final private Object object2 = new Object();

    private Random random = new Random();

    private void partOne()
    {
        synchronized (object1)
        {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list1.add(random.nextInt(100));
        }

    }
    private void partTwo()
    {
        synchronized (object2)
        {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list2.add(random.nextInt(100));
        }

    }
    private void proceed()
    {
        for (int i = 0; i < 1000; i++)
        {
            partOne();
            partTwo();
        }
    }


    public void start()
    {
        System.out.println("Начинаем...");
        long startTime = System.currentTimeMillis();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                proceed();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                proceed();
            }
        });

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Потраченное время " + (endTime - startTime) +"\n" +
                "Лист 1 содержит " + list1.size() + " элементов\n" +
                "Лист 2 содержит " + list2.size() + " элементов\n");

//        System.out.printf("Потраченное время %d\n", endTime - startTime);
//        System.out.printf("Лист 1 содержит %d элемент(ов)\n", list1.size());
//        System.out.printf("Лист 2 содержит %d элемент(ов)\n", list2.size());

    }
}
