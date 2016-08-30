package com.ikkong.log2file;

import android.os.Environment;
import android.util.Log;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.mindpipe.android.logging.log4j.LogConfigurator;


public class ILog {
	public static String LOG_TAG = "ikkong";//log tag
	public static boolean DEBUG = BuildConfig.DEBUG;//是否打印到控制台
	private static boolean saveLog = true;//是否保存到文件
	private static String imei = "imei";//可以在生成的日志文件名中加一个标记，方便收集
	private static String folder = "ilog";//保存日志的文件夹名称
	public static String logFilePath;
	public static String logFileName;
	public static Logger gLogger;
	
	public ILog() {
	}
	
	public static void init(){
		if(saveLog) {
			final LogConfigurator logConfigurator = new LogConfigurator();
			logFileName = "log_" + imei + "_" +
					new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".log";
			String SDCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + folder;
			logFilePath = SDCardRoot + File.separator + "log" + File.separator + logFileName;
			logConfigurator.setFileName(logFilePath);
			// Set the root log level
			logConfigurator.setRootLevel(Level.DEBUG);
			// Set log level of a specific logger
			logConfigurator.setLevel("org.apache", Level.ERROR);
			logConfigurator.configure();
			//gLogger = Logger.getLogger(this.getClass());
			gLogger = Logger.getLogger(LOG_TAG);
		}
	}

	public static final void analytics(String log) {
		if (DEBUG)
			Log.d(LOG_TAG, log);
		if (saveLog)
			gLogger.debug(log);
	}

	public static final void error(String log) {
		if (DEBUG)
			Log.e(LOG_TAG, log);
		if(saveLog)
			gLogger.error(log);
	}

	public static final void log(String log) {
		if (DEBUG)
			Log.i(LOG_TAG, log);
		if (saveLog)
			gLogger.debug(log);
	}

	public static final void log(String tag, String log) {
		if (DEBUG)
			Log.i(tag, log);
		if (saveLog)
			gLogger.debug(log);
	}

	public static final void logv(String log) {
		if (DEBUG)
			Log.v(LOG_TAG, log);
		if (saveLog)
			gLogger.debug(log);
	}

	public static final void warn(String log) {
		if (DEBUG)
			Log.w(LOG_TAG, log);
		if (saveLog)
			gLogger.warn(log);
	}
}
