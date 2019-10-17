package com.ssp.higher.base.utils.thread.thread.Unsafe;

import com.ssp.higher.base.utils.common.StringUtils;
import sun.misc.Unsafe;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 *
 * Unsafe
 *
 * 1.通过Unsafe类可以分配内存，可以释放内存
 *  public native long allocateMemory(long l);
 * public native long reallocateMemory(long l, long l1);
 * public native void freeMemory(long l);
 *
 * 2.可以定位对象某字段的内存位置，也可以修改对象的字段值，即使它是私有的
 *
 * 3.整个并发框架中对线程的挂起操作被封装在LockSupport类中，LockSupport类中有各种版本pack方法，但最终都调用了Unsafe.park()
 *
 * 4.CAS操作
 *
 *
 *
 *
 * @Author:zouyu
class A {
private long a; // not initialized value
public A() {
this.a = 1; // initialization
}
public long a() { return this.a; }
}
以下是构造方法、反射方法和allocateInstance()的对照

A o1 = new A(); // constructor
o1.a(); // prints 1

A o2 = A.class.newInstance(); // reflection
o2.a(); // prints 1

A o3 = (A) unsafe.allocateInstance(A.class); // unsafe
o3.a(); // prints 0
allocateInstance()根本没有进入构造方法，在单例模式时，我们似乎看到了危机。* @Date:2019/10/16 15:50
 */
public class UnsafeTest implements Serializable {

    private String name;
    private int age;
    private static String[] tag = new String[]{"zouyu","dashuai","yuancao"};

    private static long nameOffset;
    private static long ageOffset;
    private static long tagOffset;

    private static Unsafe unsafe = null;
    static{
        try{
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);

            nameOffset = unsafe.objectFieldOffset(UnsafeTest.class.getDeclaredField("name"));
            ageOffset = unsafe.objectFieldOffset(UnsafeTest.class.getDeclaredField("age"));
            tagOffset = unsafe.arrayBaseOffset(String[].class);

        }catch (Exception e){
        }
    }

    public static void main(String[] args) {
        UnsafeTest test = new UnsafeTest();
        unsafe.putInt(test,ageOffset,1);
        System.out.println("dddddddd"+test.getAge());

        System.out.println("数组的偏移量:"+tagOffset);

        long scale = unsafe.arrayIndexScale(String[].class);
        System.out.println("元素间隔:"+scale);
        printStrings();
        unsafe.putObject(tag,tagOffset+0*scale,"哈哈哈哈");
        printStrings();
        unsafe.putObject(tag,tagOffset+1*scale,"哈哈哈哈");
        printStrings();
    }

    private static void printStrings(){
        System.out.println("-----------------");
        for(String s:tag){
            System.out.println(s);
        }
        System.out.println("-----------------");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String[] getTag() {
        return tag;
    }

    public void setTag(String[] tag) {
        this.tag = tag;
    }
}
