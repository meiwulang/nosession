package com.hjh.mall.bizapi.biz.goods.middle.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hjh.mall.common.core.util.StringUtil;

public abstract class IOUtil {

	public static final String CHARSET_NAME_UTF8 = "UTF-8";

	public static final Charset CHARSET_UTF8 = Charset.forName(CHARSET_NAME_UTF8);

	public static final String CHARSET_NAME_GBK = "GBK";

	public static final Charset CHARSET_GBK = Charset.forName(CHARSET_NAME_GBK);

	public static final String CHARSET_NAME_ISO88591 = "iso-8859-1";

	public static final Charset CHARSET_ISO88591 = Charset.forName(CHARSET_NAME_ISO88591);

	public static final byte[] EMPTY_BYTES = new byte[0];

	private static final int DEFAULT_SIZE = 4096;

	private static final Logger LOGGER = LoggerFactory.getLogger(IOUtil.class);

	public static void close(Closeable closeable) {
		if (null != closeable) {
			try {
				closeable.close();
			} catch (IOException e) {
				LOGGER.error("Close closeable faild: " + closeable.getClass(), e);
			}
		}
	}

	public static byte[] base64StringToBytes(String base64String) {
		if (StringUtils.isNotEmpty(base64String)) {
			return Base64.decodeBase64(base64String);
		} else {
			return EMPTY_BYTES;
		}
	}

	public static byte[] fileToBytes(File file) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			return streamToBytes(fis);
		} catch (Exception e) {
			LOGGER.error("read bytes failed", e);
		} finally {
			close(fis);
		}
		return null;
	}

	public static byte[] streamToBytes(InputStream is) {
		return streamToBytes(is, false);
	}

	public static byte[] streamToBytes(InputStream is, boolean closeInputStream) {
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			byte[] buff = new byte[DEFAULT_SIZE];
			int readedLen = -1;
			while (-1 != (readedLen = is.read(buff))) {
				baos.write(buff, 0, readedLen);
			}
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
			LOGGER.error("read bytes failed", e);
		} finally {
			close(baos);
			if (closeInputStream) {
				close(is);
			}
		}
		return null;
	}

	public static String bytesToBase64String(byte[] bytes) {
		if (null != bytes) {
			return Base64.encodeBase64String(bytes);
		} else {
			return StringUtil.EMPTY_STRING;
		}
	}

	public static String fileToBase64String(File file) {
		byte[] bytes = fileToBytes(file);
		return bytesToBase64String(bytes);
	}

	public static String streamToBase64String(InputStream is) {
		return streamToBase64String(is, false);
	}

	public static String streamToBase64String(InputStream is, boolean closeInputStream) {
		byte[] bytes = streamToBytes(is, closeInputStream);
		if (null != bytes) {
			return Base64.encodeBase64String(bytes);
		} else {
			return StringUtil.EMPTY_STRING;
		}
	}

	public static boolean bytesToFile(byte[] bytes, File file) {
		if (null != bytes && null != file) {
			ByteArrayInputStream bais = null;
			try {
				bais = new ByteArrayInputStream(bytes);
				return streamToFile(bais, file);
			} catch (Exception e) {
				LOGGER.error("write bytes failed", e);
			} finally {
				close(bais);
			}
		}
		return false;
	}

	public static boolean streamToFile(InputStream is, File file) {
		return streamToFile(is, file, false);
	}

	public static boolean streamToFile(InputStream is, File file, boolean closeInputStream) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			byte[] buff = new byte[DEFAULT_SIZE];
			int readedLen = -1;
			while (-1 != (readedLen = is.read(buff))) {
				fos.write(buff, 0, readedLen);
			}
			fos.flush();
			return true;
		} catch (Exception e) {
			LOGGER.error("write bytes failed", e);
		} finally {
			close(fos);
			if (closeInputStream) {
				close(is);
			}
		}
		return false;
	}

	public static boolean base64StringToFile(String base64String, File file) {
		if (null != base64String) {
			byte[] bytes = base64StringToBytes(base64String);
			return bytesToFile(bytes, file);
		}
		return false;
	}

	public static InputStream bytesToStream(byte[] bytes) {
		return new ByteArrayInputStream(bytes);
	}

	public static InputStream base64StringToStream(String base64String) {
		if (null != base64String) {
			byte[] bytes = base64StringToBytes(base64String);
			return new ByteArrayInputStream(bytes);
		}
		return null;
	}

	public static InputStream fileToStream(File file) throws FileNotFoundException {
		return new FileInputStream(file);
	}

	public static String fileToString(File file, String charset) {
		return fileToString(file, Charset.forName(charset));
	}

	public static String fileToString(File file, Charset charset) {
		byte[] bytes = fileToBytes(file);
		if (null != bytes) {
			return new String(bytes, charset);
		}
		return StringUtil.EMPTY_STRING;
	}

	public static boolean ensureDir(File dir) {
		if (!dir.exists()) {
			return dir.mkdirs();
		}
		return true;
	}

}
