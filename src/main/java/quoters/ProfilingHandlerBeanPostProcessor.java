package quoters;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bohdan Kolesnyk on 7/25/2015.
 */
public class ProfilingHandlerBeanPostProcessor implements BeanPostProcessor {
    private Map<String, Class> map = new HashMap<String, Class>();
    private ProfilingController profilingController = new ProfilingController();

    public ProfilingHandlerBeanPostProcessor() throws Exception {
        //register MBeanServer for changing parameters on Runtime
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        mBeanServer.registerMBean(profilingController, new ObjectName("profiling", "name", "controller"));
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        // get current bean class and put it to map
        if (beanClass.isAnnotationPresent(Profiling.class)){
            map.put(beanName, beanClass);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, String beanName) throws BeansException {
        //get bean class and create proxy for it with changed method
        Class beanClass = map.get(beanName);
        if (beanClass != null){
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if(profilingController.isEnabled()){
                        System.out.println("Profiling...");
                        long before = System.nanoTime();
                        Object retVal = method.invoke(bean, args);
                        long after = System.nanoTime();
                        System.out.println(after-before);
                        System.out.println("The end ....");
                        return retVal;
                    } else{
                        return method.invoke(bean, args);
                    }
                }
            });
        }
        return bean;
    }
}
