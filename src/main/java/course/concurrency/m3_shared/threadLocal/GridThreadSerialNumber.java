package course.concurrency.m3_shared.threadLocal;

import java.util.concurrent.atomic.AtomicInteger;

class GridThreadSerialNumber {
    /** The next serial number to be assigned. */
    //private int nextSerialNum = 0;
    private AtomicInteger nextSerialNum = new AtomicInteger(0);
    /** */
//    private ThreadLocal<Integer> serialNum = new ThreadLocal<Integer>() {
//        @Override protected synchronized Integer initialValue() {
//            return nextSerialNum++;
//        }
//    };

    /**
     * @return Serial number value.
     */
    public int get() {
        //return serialNum.get();
        return nextSerialNum.incrementAndGet();
    }
}
