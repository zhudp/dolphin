package com.dolphin.web;


import org.apache.log4j.Logger;

import EDU.oswego.cs.dl.util.concurrent.BoundedBuffer;
import EDU.oswego.cs.dl.util.concurrent.DirectExecutor;
import EDU.oswego.cs.dl.util.concurrent.PooledExecutor;
import EDU.oswego.cs.dl.util.concurrent.ThreadFactory;

/** 
 * 线程管理类
 * @author: yanghb
 * @since: 2009-1-5  上午11:01:02
 * @history:
 ************************************************
 * @file: ThreadManager.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd.
 * All right reserved.
 ************************************************/
public class ThreadManager {
	private PooledExecutor backgroundExecutor;
	private DirectExecutor nodelayExecutor;
	private static ThreadManager threadManager = null;
	static Logger log = Logger.getLogger("PLATFORM");

	public static ThreadManager getInstance() {
		if (threadManager == null) {
			threadManager = new ThreadManager();
			log.info("Load ThreadManager successful!");
		}
		return threadManager;
	}

	private ThreadManager() {
		backgroundExecutor = new PooledExecutor(new BoundedBuffer(10), 25);
		backgroundExecutor.setMinimumPoolSize(4);
		backgroundExecutor.setKeepAliveTime(0x493e0L);
		backgroundExecutor.waitWhenBlocked();
		backgroundExecutor.createThreads(5);
		backgroundExecutor.setThreadFactory(new test());
		nodelayExecutor = new DirectExecutor();
	}

	private final class test implements ThreadFactory {
		public test() {
		}
		
		public Thread newThread(Runnable command) {
			Thread t = new Thread(command);
			t.setDaemon(false);
			t.setName("Background Execution Threads");
			t.setPriority(5);
			return t;
		}		
	}

	/** 
	 * 后台运行，返回较快
	 * ThreadManager.getInstance().executeInBackground(runnable)
	 * @param runnable 
	 * @create  2009-1-5 上午11:01:25 yanghb
	 * @history  
	 */
	public void executeInBackground(Runnable runnable) {
		try {
			backgroundExecutor.execute(runnable);
		} catch (InterruptedException e) {
			log.error("ThreadManager.executeInBackground() error:" + e);
		}
	}

	/** 
	 * 在前台执行
	 * ThreadManager.getInstance().executeInForeground(runnable)
	 * @param runnable 
	 * @create  2009-1-5 上午11:01:28 yanghb
	 * @history  
	 */
	public void executeInForeground(Runnable runnable) {
		try {
			nodelayExecutor.execute(runnable);
		} catch (InterruptedException e) {
			log.error("ThreadManager.executeInForeground() error:" + e);
		}
	}

	public void shutdown() {
		backgroundExecutor.shutdownAfterProcessingCurrentlyQueuedTasks();
		log.info("Shutdown ThreadManager");
	}

	public void release() {
	}

}
