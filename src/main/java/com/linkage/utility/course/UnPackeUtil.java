package com.linkage.utility.course;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;
import net.lingala.zip4j.core.ZipFile;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class UnPackeUtil {

	private static final Logger logger = LoggerFactory.getLogger(UnPackeUtil.class);

	public static String uploadZip(MultipartFile uploadFile) {
		
		// step-1: //参数检查及解压路径设置
		if (uploadFile == null) {
			throw new RuntimeException("请上传文件");
		}
		
		String packFileStr = PropertiesReadUtils.readConfigFileProp("application.properties", "upload.file.path"); // 解压目录
		
		File packFile = new File(packFileStr);
		if (!packFile.exists()) { // 不存在则创建
			boolean mkdirs = packFile.mkdirs();
		}

		String contentType = uploadFile.getContentType();
		String filename = uploadFile.getOriginalFilename();
		
		//TODO: T1: 压缩包保存路径的设定，加上日期参数，或者上传文件
		String packFilePath = packFileStr + File.separator + filename;// 压缩包保存路径

		File file = new File(packFilePath);

		// step-2: //传输接受到的文件到目标文件
		try {
			uploadFile.transferTo(file);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("压缩文件到:" + packFileStr + " 失败!");
		}

		// step-3: //解压目标文件
		if ("application/x-zip-compressed".equals(contentType)) {
			UnPackeUtil.unPackZip(file, packFileStr);
		} else if ("application/vnd.rar".equals(contentType)) {
			UnPackeUtil.unPackRar(file, packFileStr);
		} else {
			throw new RuntimeException("上传的压缩包格式不正确,仅支持rar和zip压缩文件!");
		}

		// 获取压缩包名称
		filename = filename.substring(0, filename.lastIndexOf("."));
		String subfix = PropertiesReadUtils.readConfigFileProp("application.properties", "upload.file.subfix");
		// 可以根据解压路径、压缩包名称、文件名称，取出对应文件进行操作
		String url = packFilePath = filename + subfix; // 解压目录
		
		System.out.println("-------uploadZip-----" + url);
		return url;
	}
	
	public static String uploadPdfDoc(MultipartFile uploadFile) {
		
		// step-1: //参数检查及解压路径设置
		if (uploadFile == null) {
			throw new RuntimeException("请上传文件");
		}
		
		String packFileStr = PropertiesReadUtils.readConfigFileProp("application.properties", "upload.file.path"); // 解压目录
		
		File packFile = new File(packFileStr);
		if (!packFile.exists()) { // 不存在则创建
			boolean mkdirs = packFile.mkdirs();
		}

		String contentType = uploadFile.getContentType();
		String filename = uploadFile.getOriginalFilename();
		
		//TODO: T1: 压缩包保存路径的设定，加上日期参数，或者上传文件
		String packFilePath = packFileStr + File.separator + filename;// 压缩包保存路径

		File file = new File(packFilePath);

		// step-2: //传输接受到的文件到目标文件
		try {
			uploadFile.transferTo(file);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("压缩文件到:" + packFileStr + " 失败!");
		}
		
		// 上传文件为.pdf后缀的虚拟访问路径
		//TODO: add reqest param into upload interface
		//req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort() + "api/resource"
		String url = filename; // 解压目录
		
		System.out.println("------uploadPdfDoc------" + url);
		return url;
	}

	/**
	 * zip文件解压
	 *
	 * @param destPath 解压文件路径
	 * @param zipFile  压缩文件
	 * @param password 解压密码(如果有)
	 */
	public static void unPackZip(File zipFile, String destPath) {
		try {
			ZipFile zip = new ZipFile(zipFile);
			/* zip4j默认用GBK编码去解压,这里设置编码为GBK的 */
			zip.setFileNameCharset("GBK");
			logger.info("begin unpack zip file....");
			zip.extractAll(destPath);

		} catch (Exception e) {
			logger.error("解压失败：", e.getMessage(), e);
		}
	}

	/**
	 * rar文件解压(不支持有密码的压缩包)
	 *
	 * @param rarFile  rar压缩包
	 * @param destPath 解压保存路径
	 */
	public static void unPackRar(File rarFile, String destPath) {
		try (Archive archive = new Archive(rarFile)) {
			if (null != archive) {
				FileHeader fileHeader = archive.nextFileHeader();
				File file = null;
				while (null != fileHeader) {
					// 防止文件名中文乱码问题的处理
					String fileName = fileHeader.getFileNameW().isEmpty() ? fileHeader.getFileNameString()
							: fileHeader.getFileNameW();
					if (fileHeader.isDirectory()) {
						// 是文件夹
						file = new File(destPath + File.separator + fileName);
						file.mkdirs();
					} else {
						// 不是文件夹
						file = new File(destPath + File.separator + fileName.trim());
						if (!file.exists()) {
							if (!file.getParentFile().exists()) {
								// 相对路径可能多级，可能需要创建父目录.
								file.getParentFile().mkdirs();
							}
							file.createNewFile();
						}
						FileOutputStream os = new FileOutputStream(file);
						archive.extractFile(fileHeader, os);
						os.close();
					}
					fileHeader = archive.nextFileHeader();
				}
			}
		} catch (Exception e) {
			logger.error("解压失败：", e.getMessage(), e);
		}
	}

}
