package com.study.lock.sync;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

public class MarkWord {
    static Unsafe unsafe;
    static {
        try {
            // 反射技术获取unsafe值
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{

        Object o = new Object();
        int aInt = unsafe.getInt(o, 0L);
        //System.out.println(aInt);
        byte[] intBytes = new byte[4];
        ByteUtils.int2bytes(aInt, intBytes, 0);
        System.out.println(ByteUtils.bytesToHexString(intBytes));
        synchronized (o) {
            aInt = unsafe.getInt(o, 0L);
            intBytes = new byte[4];
            ByteUtils.int2bytes(aInt, intBytes, 0);
            System.out.println(ByteUtils.bytesToHexString_BE(intBytes));
        }
        aInt = unsafe.getInt(o, 0L);
        intBytes = new byte[4];
        ByteUtils.int2bytes(aInt, intBytes, 0);
        System.out.println(ByteUtils.bytesToHexString(intBytes));

        System.out.println("轻量级锁");
        new Thread(() -> {
            synchronized (o) {
                int aIntx = unsafe.getInt(o, 0L);
                byte[] intBytesx = new byte[4];
                ByteUtils.int2bytes(aIntx, intBytesx, 0);
                System.out.println(ByteUtils.bytesToHexString_BE(intBytesx));
            }
        }).start();
        Thread.sleep(1000L);

        aInt = unsafe.getInt(o, 0L);
        intBytes = new byte[4];
        ByteUtils.int2bytes(aInt, intBytes, 0);
        System.out.println(ByteUtils.bytesToHexString(intBytes));
    }
    //  32 bits:
    //  --------
    //  hash:25 ------------>| age:4    biased_lock:1 lock:2 (normal object)
    //  JavaThread*:23 epoch:2 age:4    biased_lock:1 lock:2 (biased object)
    //  size:32 ------------------------------------------>| (CMS free block)
    //  PromotedObject*:29 ---------->| promo_bits:3 ----->| (CMS promoted object)
    //
    //  64 bits:
    //  --------
    //  unused:25 hash:31 -->| unused:1   age:4    biased_lock:1 lock:2 (normal object)
    //  JavaThread*:54 epoch:2 unused:1   age:4    biased_lock:1 lock:2 (biased object)
    //  PromotedObject*:61 --------------------->| promo_bits:3 ----->| (CMS promoted object)
    //  size:64 ----------------------------------------------------->| (CMS free block)
    static void showMarkWord(Object o){
        long markWord =unsafe.getLong(o,0);
        String bitString = getBitString(markWord);
        String tag = subString(bitString,62,2);
        switch (tag){
            case "00":showLightWeightLocked(bitString);break;
            case "01":showUnlocked(bitString);break;
            case "10":showHeavyWeightLocked(bitString);break;
            case "11":showMarkForGC(bitString);break;

        }
    }
    static void sleep(int m){
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    static String subString(String s,int start,int length){
        return s.substring(start,start+length);
    }
    static void showUnlocked(String bitString){
        System.out.print("lock:Unlocked,");
        String biasedLock = subString(bitString,61,1);
        System.out.print("biasedLock:" + biasedLock);
        String age = subString(bitString,57,4);
        System.out.println(",age:" + age);


    }
    static void showLightWeightLocked(String bitString){
        System.out.println("lock:LightWeightLocked");

    }
    static void showHeavyWeightLocked(String bitString){
        System.out.println("lock:HeavyWeightLocked");

    }
    static void showMarkForGC(String bitString){
        System.out.println("lock:MarkForGC");
    }

    static long getSubLong(long n,int start, int length){
        int curIndex = 63 - start;
        int curLength = length - 1;
        long result = 0;
        while (curLength >= 0){
            result += ((n >> curIndex) & 1) << curLength;
            curIndex--;
            curLength--;
        }
        return result;

    }

    static String getBitString(long n){
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.putLong(n);
        return getBitString(byteBuffer.array());
    }
    static String getBitString(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            for (int j = 7; j >= 0; j--)
                sb.append((bytes[i] >> j) & 1);
        }
        return  sb.toString();
    }

}
