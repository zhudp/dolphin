package com.dolphin.web;

import org.apache.log4j.Logger;

/** 
 * 需要线程处理的父类,子类实现perform()方法
 * @author: yanghb
 * @since: 2009-1-5  上午11:32:53
 * @history:
 ************************************************
 * @file: ThreadAction.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd.
 * All right reserved.
 ************************************************/
public abstract class ThreadAction
    implements Runnable
{

    public ThreadAction()
    {
    }

    public void run()
    {
        try
        {
            perform();
        }
        catch(Exception e)
        {
            log.error("ThreadAction.run() error:" + e);
        }
    }

    protected abstract void perform()
        throws Exception;

    static Logger log = Logger.getLogger("PLATFORM");

}
