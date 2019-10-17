package com.ssp.higher.base.utils.thread.thread.Lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * MSC与CLH最大的不同并不是链表是显示还是隐式，而是线程自旋的规则不同:CLH是在前趋结点的locked域上自旋等待，而MCS是在自己的
 *
 * 结点的locked域上自旋等待。正因为如此，它解决了CLH在NUMA系统架构中获取locked域状态内存过远的问题。
 *
 * MCS队列锁的具体实现如下：
 *
 * a. 队列初始化时没有结点，tail=null
 *
 * b. 线程A想要获取锁，于是将自己置于队尾，由于它是第一个结点，它的locked域为false
 *
 * c. 线程B和C相继加入队列，a->next=b,b->next=c。且B和C现在没有获取锁，处于等待状态，所以它们的locked域为true，
 *
 * 尾指针指向线程C对应的结点
 *
 * d. 线程A释放锁后，顺着它的next指针找到了线程B，并把B的locked域设置为false。这一动作会触发线程B获取锁
 *
 */
public class MCSLock implements Lock {
    AtomicReference<QNode> tail;
    ThreadLocal<QNode> myNode;

    @Override
    public void lock() {
        tail = new AtomicReference<QNode>(new QNode());  
        QNode qnode = myNode.get();
        QNode pred = tail.getAndSet(qnode);
        if (pred != null) {
            qnode.locked = true;
            pred.next = qnode;

            // wait until predecessor gives up the lock
            while (qnode.locked) {
            }
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
        if (qnode.next == null) {
            if (tail.compareAndSet(qnode, null)) {
                return;
            }
                // wait until predecessor fills in its next field
            while (qnode.next == null) {
            }

            qnode.next.locked = false;
            qnode.next = null;
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }


    class QNode {
        boolean locked = false;
        QNode next = null;
    }

}