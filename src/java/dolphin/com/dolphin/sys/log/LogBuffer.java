package com.dolphin.sys.log;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dolphin.domain.SystemLog;
import com.dolphin.service.SystemLogService;



public class LogBuffer {
    private static LogBuffer logBuffer = null;
    private List<SystemLog> bufferList = new ArrayList<SystemLog>();
    private SystemLogService systemLogService;
    protected static final Log log = LogFactory.getLog(LogBuffer.class);

    private LogBuffer() {
    }

    public synchronized static LogBuffer getInstance() {
        if (logBuffer == null) {
            logBuffer = new LogBuffer();
        }
        return logBuffer;
    }

    /**
     * 检查队列,如果队列里有数据,就将其插入到数据库
     * @create Jul 9, 2008 9:01:32 AM 袁聪
     * @history （Jul 9, 2008 9:01:32 AM 袁聪 ）
     */
    public synchronized void checkAndInsertToDataBase() {
        if (null != bufferList && bufferList.size() > 0) {
            List<SystemLog> tmpList = new ArrayList<SystemLog>();
            tmpList.addAll(bufferList);
            this.clearBuffer();
            // 将logBuffer清空
            systemLogService.insertSystemLogBatch(tmpList);
            log.debug("检查队列,队列里有数据,将其插入到数据库:\n当前缓冲队列有日志：" + bufferList.toString());
        }
    }

    public synchronized void insertLogToBuffer(SystemLog systemLog) {
        logBuffer.bufferList.add(systemLog);
    }

    public List<SystemLog> getBufferList() {
        return bufferList;
    }

    public void setBufferList(List<SystemLog> bufferList) {
        this.bufferList = bufferList;
    }

    /**
     * 清空buffer缓冲
     * @create Jul 9, 2008 10:08:58 AM 袁聪
     * @history （Jul 9, 2008 10:08:58 AM 袁聪 ）
     */
    public void clearBuffer() {
        this.bufferList.clear();
    }

    public void setSystemLogService(SystemLogService LogSersystemLogServicevice) {
        this.systemLogService = LogSersystemLogServicevice;
    }
}
