package hello.support;

public class WorkerThread extends Thread{

	private final RestServiceInvoker restServiceInvoker;
	
	private Object result;
	
	private final Object monitor = new Object();

	public WorkerThread(RestServiceInvoker restServiceInvoker) {
		this.restServiceInvoker = restServiceInvoker;
		this.setName(this.getClass().getSimpleName());
	}

	public Object sendAndWait() {
		this.start();
		waitForResult();
		return this.result;
	}

	public void waitForResult() {
		synchronized (monitor) {
			try {
				monitor.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void notifyResult() {
		synchronized (monitor) {
			monitor.notifyAll();
		}
	}

	@Override
	public void run() {
		try{
			this.result = restServiceInvoker.doGreet();
		}finally{
			notifyResult();
		}
	}
}
