package quoters;

import javax.annotation.PostConstruct;

/**
 * Created by Bohdan Kolesnyk on 7/25/2015.
 */
@Profiling
@DeprecatedClass(newImpl = T1000.class)
public class TerminatorQuoter implements Quoter {

    @InjectRandomInt(min = 2, max = 7)
    private int repeat;

    private String message;

    public TerminatorQuoter() {
        System.out.println("Phase 1 - Constructor");
    }

    @PostConstruct
    public void init(){
        System.out.println("Phase 2 - PostConstruct");
        System.out.println(repeat);
    }

    @Override
    @PostProxy
    public void sayQuote() {
        System.out.println("Phase 3 - ApplicationListener");
        for (int i = 0; i < repeat; i++) {
            System.out.println("message = " + message);
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
