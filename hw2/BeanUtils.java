
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class BeanUtils {

    /**
     * Scans object "from" for all getters. If object "to"
     * contains correspondent setter, it will invoke it
     * to set property value for "to" which equals to the property
     * of "from".
     * <p/>
     * The type in setter should be compatible to the value returned
     * by getter (if not, no invocation performed).
     * Compatible means that parameter type in setter should
     * be the same or be superclass of the return type of the getter.
     * <p/>
     * The method takes care only about public methods.
     *
     * @param to   Object which properties will be set.
     * @param from Object which properties will be used to get values.
     */


    public static void assign(Object to, Object from) {
        Method[] getters = from.getClass().getMethods();
        Method[] setters = to.getClass().getMethods();

        for (Method getter : getters) {
            if (getter.getName().startsWith("get")) {
                for (Method setter : setters) {
                    if (setter.getName().startsWith("set")) {
                        String setterName = setter.getName().substring(3);
                        String getterName = getter.getName().substring(3);
                        if (setterName.equals(getterName) &&
                                setter.getAnnotatedParameterTypes().length == 1 &&
                                (!getter.getReturnType().equals(void.class)) &&
                                setter.getReturnType().equals(getter.getReturnType().getSuperclass())) {
                            try {
                                setter.invoke(to, getter.invoke(from));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

        }
    }
}