package hello.support;

import hello.service.RestServiceInvoker;

public class WorkerThread extends Thread{

	private final RestServiceInvoker restServiceInvoker;
	
	private Object result;
	
	private final Object monitor = new Object();

	public WorkerThread(RestServiceInvoker restServiceInvoker, boolean daemon) {
		this.restServiceInvoker = restServiceInvoker;
		this.setName(this.getClass().getSimpleName());
		this.setDaemon(daemon);
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
			this.result = restServiceInvoker.doGreeting();
		}finally{
			notifyResult();
		}
	}
}
