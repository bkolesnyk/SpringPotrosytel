package quoters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import sun.awt.AppContext;

import java.lang.reflect.Method;

/**
 * Created by Bohdan Kolesnyk on 7/26/2015.
 */
public class PostProxyInvokerContextListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ConfigurableListableBeanFactory factory;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        // get all beans name, if there is some proxy then they will have proxy names, not real names
        String [] names = context.getBeanDefinitionNames();

        for (String name : names) {
            BeanDefinition beanDefinition =  factory.getBeanDefinition(name); // get BeanDefenition for current bean name
            String originalClassName = beanDefinition.getBeanClassName(); // get name of original bean class
            try {
                Class<?> originalClass = Class.forName(originalClassName);//get original bean class
                Method [] methods = originalClass.getMethods();//get method from original bean class
                for (Method method : methods) {
                    if (method.isAnnotationPresent(PostProxy.class)){
                        Object bean = context.getBean(name); // get current bean object, it might be proxy
                        //get method from current bean, which has same name and signature like original bean class
                        Method currentMethod = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
                        //run current method
                        currentMethod.invoke(bean);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
