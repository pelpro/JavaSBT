
import java.util.concurrent.Callable;

public class Task<T> {
    private Callable<? extends T> callable;
    private T object = null;

    public Task(Callable<? extends T> callable) {
        this.callable = callable;
    }

    public T get() {
        if (object == null) {
            synchronized (this) {
                if (object == null) {
                    try {
                        object = callable.call();
                        String name_thread = Thread.currentThread().getName();
                        System.out.println(name_thread);
                        return object;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        System.out.println("Returned by "
                + Thread.currentThread().getName());
        return object;
    }
}