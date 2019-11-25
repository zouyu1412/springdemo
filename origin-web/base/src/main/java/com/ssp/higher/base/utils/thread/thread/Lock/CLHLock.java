package com.ssp.higher.base.utils.thread.thread.Lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 1.1 SMP(Symmetric Multi-Processor)
 *
 * 对称多处理器结构，指服务器中多个CPU对称工作，每个CPU访问内存地址所需时间相同。其主要特征是共享，包含对CPU，内存，I/O等进行共享。
 *
 * SMP能够保证内存一致性，但这些共享的资源很可能成为性能瓶颈，随着CPU数量的增加，每个CPU都要访问相同的内存资源，可能导致内存访问冲突，
 *
 * 可能会导致CPU资源的浪费。常用的PC机就属于这种。
 *
 * 1.2 NUMA(Non-Uniform Memory Access)
 *
 * 非一致存储访问，将CPU分为CPU模块，每个CPU模块由多个CPU组成，并且具有独立的本地内存、I/O槽口等，模块之间可以通过互联模块相互访问，
 *
 * 访问本地内存的速度将远远高于访问远地内存(系统内其它节点的内存)的速度，这也是非一致存储访问的由来。NUMA较好地解决SMP的扩展问题，
 *
 * 当CPU数量增加时，因为访问远地内存的延时远远超过本地内存，系统性能无法线性增加。
 *
 *
 * CLH(Craig, Landin, and Hagersten  locks): 是一个自旋锁，能确保无饥饿性，提供先来先服务的公平性。
 *
 * CLH锁也是一种基于链表的可扩展、高性能、公平的自旋锁，申请线程只在本地变量上自旋，它不断轮询前驱的状态，如果发现前驱释放了锁就结束自旋。
 *
 *
 *
 * 当一个线程需要获取锁时：
 *
 * 1.创建一个的QNode，将其中的locked设置为true表示需要获取锁
 *
 * 2.线程对tail域调用getAndSet方法，使自己成为队列的尾部，同时获取一个指向其前趋结点的引用myPred
 *
 * 3.该线程就在前趋结点的locked字段上旋转，直到前趋结点释放锁
 *
 * 4.当一个线程需要释放锁时，将当前结点的locked域设置为false，同时回收前趋结点
 *
 */
public class CLHLock implements Lock {
    AtomicReference<QNode> tail ;
    ThreadLocal<QNode> myPred;  
    ThreadLocal<QNode> myNode;  
  
    public CLHLock() {  

         tail = new AtomicReference<QNode>(new QNode());  
        myNode = new ThreadLocal<QNode>() {  
            protected QNode initialValue() {  
                return new QNode();  
            }  
        };  
        myPred = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return null;
            }
        };  
    }  
  
    @Override  
    public void lock() {  
        QNode qnode = myNode.get();  
        qnode.locked = true;  
        QNode pred = tail.getAndSet(qnode);  
        myPred.set(pred);  
        while (pred.locked) {  
        }  
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override  
    public void unlock() {  
        QNode qnode = myNode.get();  
        qnode.locked = false;  
        myNode.set(myPred.get());  
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    static class QNode{
        private boolean locked;
    }
}