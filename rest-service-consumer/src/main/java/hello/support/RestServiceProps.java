package hello.support;

public class RestServiceProps {
    private boolean daemon;

    private boolean async;

    private boolean lock;

    public RestServiceProps(){

    }

    public RestServiceProps(boolean daemon, boolean async, boolean lock) {
        this.daemon = daemon;
        this.async = async;
        this.lock = lock;
    }

    public boolean isDaemon() {
        return daemon;
    }

    public void setDaemon(boolean daemon) {
        this.daemon = daemon;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }
}
