package course.concurrency.m3_shared.threadLocal;

public class TL_Grid {

    public static void main(String[] args) {
        var gridThreadSerialNumber = new GridThreadSerialNumber();
        for (int i = 0; i < 5; i++) {
            var thread = new Thread(() -> System.out.println(gridThreadSerialNumber.get()));
            thread.start();
        }
    }
}
