package com.study.jmm;

public class VisibilityDemo {
    public static void main(String[] args) throws InterruptedException {
        Temp temp = new Temp();
        Test t1 = new Test(temp);
        Test t2 = new Test(temp);
        t1.run();
        t2.run();
        Thread.sleep(200);
        System.out.println(temp.count);
    }
}

class Temp {
    int count = 0;
}

class Test extends Thread {
    private Temp temp;

    public Test(Temp temp) {
        this.temp = temp;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            temp.count++;
        }
    }
}

