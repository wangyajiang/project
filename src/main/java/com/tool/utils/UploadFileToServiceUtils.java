package com.tool.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.tool.comm.BaseUtils;

public class UploadFileToServiceUtils {
	private static UploadFileToServiceUtils uploadFileToServiceUtils = null;
	private Session session = null;
	private UploadFileToServiceUtils() {}
	
	public static UploadFileToServiceUtils newInstance() {
		if (uploadFileToServiceUtils == null) {
			uploadFileToServiceUtils = new UploadFileToServiceUtils();
		}
		try {
			uploadFileToServiceUtils.getConnection();
		} catch (JSchException e) {
			System.out.println("连接远程服务器失败");
			e.printStackTrace();
		}
		return uploadFileToServiceUtils;
	}
	
	private void getConnection() throws JSchException {
		JSch jsch = new JSch(); // 创建JSch对象  
        String userName = BaseUtils.getVal("service.name");// 用户名  
        String password = BaseUtils.getVal("service.pwd");// 密码  
        String host = BaseUtils.getVal("service.ip");// 服务器地址  
        int port = ConvertUtils.getInt(BaseUtils.getVal("service.port"), 22);// 端口号  
        session = jsch.getSession(userName, host, port); // 根据用户名，主机ip，端口获取一个Session对象  
        session.setPassword(password); // 设置密码  
        Properties config = new Properties();  
        config.put("StrictHostKeyChecking", "no");  
        session.setConfig(config); // 为Session对象设置properties  
        int timeout = 1000 * 60 * 5;  
        session.setTimeout(timeout); // 设置timeout时间  
        session.connect(); // 通过Session建立链接  
	}
	
	public Session getSession() {
		return session;
	}
	
	public void close() {
		if (null != session) {  
            session.disconnect();  
            session = null;
            uploadFileToServiceUtils = null;
        }  
	}
	
	public void exec(String cmd) throws JSchException, IOException {
		ChannelExec channelExec = (ChannelExec) session.openChannel("exec");  
        channelExec.setCommand(cmd);  
        channelExec.setInputStream(null);  
        channelExec.setErrStream(System.err);  
        channelExec.connect();  
//        InputStream in = channelExec.getInputStream();  
//        BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));  
//        String buf = null;  
//        StringBuffer sb = new StringBuffer();  
//        while ((buf = reader.readLine()) != null) {  
//            sb.append(buf);  
//            System.out.println(buf);// 打印控制台输出  
//        }  
//        reader.close();  
        channelExec.disconnect();  
	}
	
	/**
	 * @param uploadFilePath 本地文件绝对路径
	 * @param directory 目标服务器目录
	 * @param fileName 目标服务器的文件名称
	 */
	public void upload() throws JSchException, SftpException, FileNotFoundException {
		String uploadFilePath = BaseUtils.getVal("location.filePath");
		String directory = BaseUtils.getVal("service.directory");
		String fileName = BaseUtils.getVal("service.fileName");
		
		Channel channel = session.openChannel("sftp");
		channel.connect();
		ChannelSftp sftp = (ChannelSftp) channel;
		if (sftp != null) {
			System.out.println("sftp 已连接！");
			sftp.cd(directory);
			File file = new File(uploadFilePath);
			if (CheckUtils.isBlank(fileName)) {
				fileName = file.getName();
			}
			sftp.put(new FileInputStream(file), fileName);
		}
//		if (sftp == null) {
//			System.out.println("sftp 连接失败！");
//		}
	}
	
	/**
	 * @param uploadFilePath 本地文件绝对路径
	 * @param directory 目标服务器目录
	 * @param fileName 目标服务器的文件名称
	 */
	public void uploadByPathAndName(String uploadFilePath, String directory, String fileName) throws JSchException, SftpException, FileNotFoundException {
		Channel channel = session.openChannel("sftp");
		channel.connect();
		ChannelSftp sftp = (ChannelSftp) channel;
		if (sftp != null) {
			System.out.println("sftp 已连接！");
		} else {
			System.out.println("sftp 连接失败！");
			return;
		}
		sftp.cd(directory);
		File file = new File(uploadFilePath);
		if (CheckUtils.isBlank(fileName)) {
			fileName = file.getName();
		}
		sftp.put(new FileInputStream(file), fileName);
	}
	
	public void removeLog(UploadFileToServiceUtils service) {
		String cmd = "rm -rf " + BaseUtils.getVal("tomcat.log.directory") + "/*";
		System.out.println("执行删除日志！命令：" + cmd);
		try {
			service.exec(cmd);
			System.out.println("删除完成！等待2秒后执行备份！");
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void up() {
		BaseUtils.newInstance();
		UploadFileToServiceUtils service = UploadFileToServiceUtils.newInstance();
		try {
//			service.removeLog(service);
//			String cmd = "cp " + BaseUtils.getVal("service.directory") + "/" + BaseUtils.getVal("service.fileName") + " " + BaseUtils.getVal("service.backup.directory") + "/" + BaseUtils.getVal("service.fileName") + DTUtils.format(DTUtils.getNowDate(),"yyyyMMddHHmmss");
//			System.out.println("执行备份！命令：" + cmd);
//			service.exec(cmd);
//			System.out.println("备份完成！等待5秒后执行删除！");
//			Thread.sleep(5000);
			String cmd = "rm -f " + BaseUtils.getVal("service.directory") + "/" + BaseUtils.getVal("service.fileName");
			System.out.println("执行删除！命令：" + cmd);
			service.exec(cmd);
			Thread.sleep(1000);
			cmd = "rm -rf " + BaseUtils.getVal("service.directory") + "/" + BaseUtils.getVal("service.fileName").replace(".war", "");
			System.out.println("执行删除！命令：" + cmd);
			service.exec(cmd);
			System.out.println("删除完成！等待1秒后执行上传文件！");
			Thread.sleep(1000);
			service.upload();
			System.out.println("上传完成！等待3秒后执行关闭tomcat！");
			Thread.sleep(3000);
			cmd = "./" + BaseUtils.getVal("tomcat.bin.directory") + "/shutdown.sh";
			System.out.println("执行关闭tomcat！命令：" + cmd);
			service.exec(cmd);
			System.out.println("上传完成！等待3秒后重启tomcat！");
			Thread.sleep(3000);
			cmd = "./" + BaseUtils.getVal("tomcat.bin.directory") + "/startup.sh";
			System.out.println("执行启动tomcat！命令：" + cmd);
//			cmd = "tail -f " + BaseUtils.getVal("tomcat.log.directory") + "/catalina.out "; 
//			System.out.println("查看命令：" + cmd);
			service.exec(cmd);
			Thread.sleep(8000);
			System.out.println("完毕================>");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			service.close();
		}
	}
	public static void main(String[] args) {
		up();
	}
}