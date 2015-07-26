package quoters;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.lang.annotation.Annotation;

/**
 * Created by Bohdan Kolesnyk on 7/26/2015.
 */
public class DeperecationHandlerBeanFactoruPostProcessor implements BeanFactoryPostProcessor{
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //get all defenitions by name
        String [] names = beanFactory.getBeanDefinitionNames();
        for (String name : names) {
            //get bean defenition by name
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(name);
            // bean class name from bean defenition
            String beanClassName = beanDefinition.getBeanClassName();
            try {
                // get class
                Class<?> beanClass = Class.forName(beanClassName);
                // if this bean has annotation then set new class to bean defenition
                DeprecatedClass annotation = beanClass.getAnnotation(DeprecatedClass.class);
                if (annotation != null){
                    beanDefinition.setBeanClassName(annotation.newImpl().getName());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
