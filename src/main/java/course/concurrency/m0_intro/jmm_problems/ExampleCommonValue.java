package course.concurrency.m0_intro.jmm_problems;

/**
 * проблема: изменения общей переменной не видны другим потокам;
 */
public class ExampleCommonValue {
    public static int value;

    public static void main(String[] args) {
        var thread1 = new Thread(
                ()->{
                    int temp = 0;
                    while(true) {
                        if (temp != value) {
                            temp = value;
                            System.out.println("reader: value = " + value);
                        }
                    }
                }
        );
        var thread2 = new Thread(
                ()->{
                    for(int i = 0; i < 1000000; i++) {
                        value++;
                        System.out.println("writer: changed to " + value);
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        );

        thread1.start();
        thread2.start();

    }
}
