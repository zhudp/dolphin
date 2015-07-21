/**
 * 
 * @author ：Administrator
 * @since : Jul 7, 2008
 * @history ：（Jul 7, 2008 Administrator） *****************************************************************
 * @file : SaveLogsThread.java
 * @Copyright ® 2008 HundSun Electronic Co. Ltd. All right reserved.
 *            *****************************************************************
 */
package com.dolphin.sys.log;


/***********************************************************************************************************************
 * @author: 袁聪
 * @since: Jul 7, 2008 5:33:56 PM
 * @history: ***********************************************
 * @file: SaveLogsThread.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd. All right reserved.
 **********************************************************************************************************************/
public class SaveLogsThread extends Thread {
    private boolean stopNow;
    private LogBuffer logBuffer;
    private int saveLogsEveryTime = 5000;// 缓冲入库扫描等待时间，以毫秒为单位

    public SaveLogsThread() {
        logBuffer = LogBuffer.getInstance();
        stopNow = false;
    }

    @Override
	public void run() {
        while (!stopNow) {
            // 检查队列,如果队列里有数据,就将其插入到数据库
            logBuffer.checkAndInsertToDataBase();
            try {// 操作数据库后,再休息几秒钟
                Thread.currentThread();
				Thread.sleep(saveLogsEveryTime);
            } catch (InterruptedException e) {
                // e.printStackTrace();
            }
        }// end while
        logBuffer.checkAndInsertToDataBase();
        // 结束线程时,再次检查最后一次,如果队列里还有数据,做最后一次保存到数据库.
    }

    public boolean isStopNow() {
        return stopNow;
    }

    public void setStopNow(boolean stopNow) {
        this.stopNow = stopNow;
    }

    public int getSaveLogsEveryTime() {
        return saveLogsEveryTime;
    }

    public void setSaveLogsEveryTime(int saveLogsEveryTime) {
        this.saveLogsEveryTime = saveLogsEveryTime;
    }
}
