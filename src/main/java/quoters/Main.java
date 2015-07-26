package quoters;

import com.sun.org.apache.xpath.internal.operations.Quo;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Bohdan Kolesnyk on 7/25/2015.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
//        while (true){
//            Thread.sleep(100);
//            context.getBean(Quoter.class).sayQuote(); //get by class it's not good, better is by interface
//            //because if you use some proxy, object will have different class name something lie ... . proxy.$Proxy7
//        }
        context.getBean(Quoter.class).sayQuote();
    }
}
