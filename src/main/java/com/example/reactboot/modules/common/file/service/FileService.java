package com.example.reactboot.modules.common.file.service;

import com.example.reactboot.common.utils.PropertyUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class FileService {

	private final PropertyUtil propertyUtil;

	public FileService(PropertyUtil propertyUtil) {
		this.propertyUtil = propertyUtil;
	}

	/**
	 * 첨부파일 업로드
	 */
	public Map<String, Object> uploadFile(MultipartFile file, String uploadDir, String thumbnailYn) {
		Map<String, Object> resultMap 						= new HashMap<>();
		String fileSeparator 								= File.separator;

		long uploadMaxSize                                  = (long) propertyUtil.getPropertyDouble("file.upload.max");
		long maxFileSizeMb                                  = uploadMaxSize / (1024 * 1024);

		String uploadPath                                   = propertyUtil.getPropery("file.upload.path") + "/" + uploadDir;
		String filePath										= "/" + uploadDir;

		String fileUrl										= propertyUtil.getPropery("site.url.file");

		int resultCode;
		String thumbnailPath                                = "";

		if ("BOARD".equals(uploadDir)) {
			Date nowDate                                    = new Date();
			SimpleDateFormat tempFormatter                  = new SimpleDateFormat("yyyyMMddHHmmss");
			String boardFolder								= tempFormatter.format(nowDate);

			uploadPath                              		= uploadPath + '/' + boardFolder;
			filePath										= filePath + '/' + boardFolder;
		}

		// os에 맞게 치환
		uploadPath 											= uploadPath.replace("/", fileSeparator);

		try {
			File fileSaveDir                            	= new File(uploadPath);
			if(!fileSaveDir.exists()) {
				boolean wasSuccessful 						= fileSaveDir.mkdirs();
				if (!wasSuccessful) {
					System.out.println("was not successfull.");
				}
			}

			String originalFileName                     	= file.getOriginalFilename();
			long fileSize                               	= file.getSize();
			String fileExt                              	= file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
			String newFileName                          	= Long.toString(System.nanoTime()) +"."+ fileExt;

			if (!ObjectUtils.isEmpty(originalFileName) && fileSize > 0 && fileSize <= uploadMaxSize) {
				fileSaveDir                             	= new File((uploadPath + "/" + newFileName).replace("/", fileSeparator));
				file.transferTo(fileSaveDir);

				resultMap.put("originalFileName"         	, originalFileName);
				resultMap.put("filePath"                 	, filePath + "/" + newFileName);
				resultMap.put("fileSize"                 	, fileSize);
				resultMap.put("fileFormat"               	, fileExt);

				resultMap.put("saveFileHwPath"           	, uploadPath);
				resultMap.put("saveFileName"             	, newFileName);
				resultMap.put("saveFileExt"              	, fileExt);

				resultMap.put("uploadDir"              		, uploadDir);

				if ("Y".equals(thumbnailYn)) {
					thumbnailPath                       	= makeThumbnail(resultMap);
				}

				resultMap.put("fileThumbPath"               , thumbnailPath);
				resultMap.put("fileUrl"						, fileUrl);
			}

			resultCode                                  	= 1;

		} catch (IllegalStateException ie) {
			ie.printStackTrace();
			resultCode                             			= -5;
		} catch (MaxUploadSizeExceededException me) {
			me.printStackTrace();
			resultCode                             			= -4;
		} catch (Exception e) {
			e.printStackTrace();
			resultCode                             			= -3;
		}
		resultMap.put("resultCode"                          , resultCode);
		resultMap.put("maxFileSize"                         , maxFileSizeMb);

		return resultMap;
	}

	/**
	 * 첨부파일 썸네일생성
	 */
	public String makeThumbnail(Map<String, Object> fileData) throws IOException {
		String fileSeparator 		= File.separator;

		Image image                 = ImageIO.read(new File((fileData.get("saveFileHwPath").toString() + "/" + fileData.get("saveFileName").toString()).replace("/", fileSeparator)));

		int imgW                    = image.getWidth(null);
		int imgH                    = image.getHeight(null);

		int thumbW                  = propertyUtil.getPropertyInt("file.thumbnail.width");
		int thumbH                  = propertyUtil.getPropertyInt("file.thumbnail.height");

		if (imgW >= imgH) {
			thumbH                  = ((imgH * thumbW) / imgW);
		} else {
			thumbW                  = ((imgW * thumbH) / imgH);
		}

		Image resizeImage           = image.getScaledInstance(thumbW, thumbH, Image.SCALE_SMOOTH);

		// image file path & name setting
		String resizeFilePath       = fileData.get("saveFileHwPath").toString() + "/" + "thumb_" + fileData.get("saveFileName").toString();

		// thumbnail save
		BufferedImage newImage      = new BufferedImage(thumbW, thumbH, BufferedImage.TYPE_INT_RGB);
		Graphics graphics           = newImage.getGraphics();
		graphics.drawImage(resizeImage, 0, 0, null);
		graphics.dispose();

		ImageIO.write(newImage, fileData.get("saveFileExt").toString(), new File(resizeFilePath));

		return "/" + fileData.get("uploadDir").toString() + "/" + "thumb_" + fileData.get("saveFileName").toString();
	}
}
