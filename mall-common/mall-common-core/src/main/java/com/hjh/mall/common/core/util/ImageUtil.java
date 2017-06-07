package com.hjh.mall.common.core.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hjh.mall.common.core.filed.ImageType;



public class ImageUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImageUtil.class);

	private static final byte[] HEAD_JPEG = { (byte) 0xFF, (byte) 0xD8, (byte) 0xFF };

	private static final byte[] HEAD_JPEG_UPPERCASE = { (byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE1 };

	private static final byte[] HEAD_PNG = { (byte) 0x89, (byte) 0x50, (byte) 0x4E, (byte) 0x47 };

	private static final byte[] HEAD_GIF = { (byte) 0x47, (byte) 0x49, (byte) 0x46, (byte) 0x38 };

	private static final byte[] HEAD_BMP = { (byte) 0x42, (byte) 0x4D };

	private static final byte[] HEAD_JPEG1 = { (byte) 0x52, (byte) 0x49, (byte) 0x46, };
	private static String[] supportedImageTypeNames;

	static {
		init();
	}

	public static void main(String[] args) {
		System.out.println((byte) 0x52);
	}

	private static void init() {
		ImageType[] imageTypes = ImageType.values();
		supportedImageTypeNames = new String[imageTypes.length];
		for (int i = 0; i < imageTypes.length; i++) {
			supportedImageTypeNames[i] = imageTypes[i].getName();
		}
	}

	public static String[] getSupportedImageTypeNames() {
		return supportedImageTypeNames;
	}

	public static ImageType getImageType(byte[] imageBytes) {
		if (null != imageBytes) {
			if (startsWith(imageBytes, HEAD_JPEG)) {
				return ImageType.JPG;
			} else if (startsWith(imageBytes, HEAD_JPEG_UPPERCASE)) {
				return ImageType.JPG;
			} else if (startsWith(imageBytes, HEAD_PNG)) {
				return ImageType.PNG;
			} else if (startsWith(imageBytes, HEAD_GIF)) {
				return ImageType.GIF;
			} else if (startsWith(imageBytes, HEAD_BMP)) {
				return ImageType.BMP;
			} else if (startsWith(imageBytes, HEAD_JPEG1)) {
				return ImageType.JPG;
			}
		}
		return null;
	}

	private static boolean startsWith(byte[] testBytes, byte[] head) {
		if (testBytes.length >= head.length) {
			for (int i = 0; i < head.length; i++) {
				if (testBytes[i] != head[i]) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public static boolean writeBytes(byte[] imageBytes, OutputStream os) {
		ImageType type = getImageType(imageBytes);
		if (null != type) {
			ByteArrayInputStream bais = null;
			try {
				bais = new ByteArrayInputStream(imageBytes);
				BufferedImage bi = ImageIO.read(bais);
				ImageIO.write(bi, type.getName(), os);
				return true;
			} catch (Exception e) {
				LOGGER.error("write image bytes to output stream failed", e);
			} finally {
				IOUtil.close(bais);
			}
		}
		return false;
	}

	public static BufferedImage imageBytesToBufferedImage(byte[] imageBytes) {
		if (null != imageBytes) {
			ByteArrayInputStream bais = null;
			try {
				bais = new ByteArrayInputStream(imageBytes);
				return ImageIO.read(bais);
			} catch (Exception e) {
				LOGGER.error("read image bytes to BufferedImage failed", e);
			} finally {
				IOUtil.close(bais);
			}
		}
		return null;
	}

	public static boolean writeBytesToFile(byte[] imageBytes, String filePathWithoutSuffix) {
		ImageType type = getImageType(imageBytes);
		if (null != type) {
			String filePath = filePathWithoutSuffix + '.' + type.getSuffix();
			File file = new File(filePath);
			ByteArrayInputStream bais = null;
			try {
				bais = new ByteArrayInputStream(imageBytes);
				BufferedImage bi = ImageIO.read(bais);
				return ImageIO.write(bi, type.getName(), file);
			} catch (Exception e) {
				LOGGER.error("write image bytes to output stream failed", e);
			} finally {
				IOUtil.close(bais);
			}
		}
		return false;
	}

}
