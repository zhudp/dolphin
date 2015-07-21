package com.dolphin.sys.log;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 利用ServletContextListener接口,使得在容器启动时就能启用一个线程,来扫描日志队列.
 * @author: 袁聪
 * @since: Jul 7, 2008 5:10:11 PM
 * @history: ***********************************************
 * @file: LogContextListener.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd. All right reserved. **********************************************
 */
public class LogContextListener implements ServletContextListener {
    SaveLogsThread saveLogsThread = new SaveLogsThread();

    public void contextInitialized(ServletContextEvent arg0) {
        saveLogsThread.start();
    }

    public void contextDestroyed(ServletContextEvent arg0) {
        saveLogsThread.setStopNow(true);
    }
}
