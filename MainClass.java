import java.util.List;
import java.util.concurrent.*;

public class MainClass {
    public static final int CARS_COUNT = 4;
    public static CyclicBarrier cb = new CyclicBarrier(CARS_COUNT);
    public static CountDownLatch cdlStart = new CountDownLatch(CARS_COUNT);
    public static CountDownLatch cdlFinish = new CountDownLatch(CARS_COUNT);
    public static Semaphore carLimit = new Semaphore(CARS_COUNT/2);

    public static void main(String[] args) throws InterruptedException {
        List<Callable<String>> win;//Не смог применить invokeAny для вывода победителя
        ExecutorService service = Executors.newFixedThreadPool(CARS_COUNT);
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
        for (int i = 0; i < cars.length; i++) {
                service.execute(cars[i]);
        }MainClass.cdlStart.await();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        MainClass.cdlFinish.await();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        service.shutdown();
    }
}
