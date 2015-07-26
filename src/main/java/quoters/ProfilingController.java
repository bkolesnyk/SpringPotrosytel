package quoters;

/**
 * Created by Bohdan Kolesnyk on 7/25/2015.
 */
public class ProfilingController implements ProfilingControllerMBean{
    private boolean enabled = true;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
