package com.hjh.mall.common.core.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.hjh.mall.common.core.util.IOUtil;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Position;

public class ImageCutUtils {

	public static String IMAGE_TYPE_GIF = "gif";// 图形交换格式
	public static String IMAGE_TYPE_JPG = "jpg";// 联合照片专家组
	public static String IMAGE_TYPE_JPEG = "jpeg";// 联合照片专家组
	public static String IMAGE_TYPE_BMP = "bmp";// 英文Bitmap（位图）的简写，它是Windows操作系统中的标准图像文件格式
	public static String IMAGE_TYPE_PNG = "png";// 可移植网络图形
	public static String IMAGE_TYPE_PSD = "psd";// Photoshop的专用格式Photoshop

	public static void main(String[] args) throws IOException {
		/*
		 * // 1-缩放图像： // 方法一：按比例缩放D:\img ImageCutUtils.scale("e:/abc.jpg",
		 * "e:/abc_scale.jpg", 2, true);//测试OK // 方法二：按高度和宽度缩放
		 * ImageCutUtils.scale2("e:/abc.jpg", "e:/abc_scale2.jpg", 500, 300,
		 * true);//测试OK
		 * 
		 * // 2-切割图像： // 方法一：按指定起点坐标和宽高切割 ImageCutUtils.cut("e:/abc.jpg",
		 * "e:/abc_cut.jpg", 0, 0, 400, 400 );//测试OK // 方法二：指定切片的行数和列数
		 * ImageCutUtils.cut2("e:/abc.jpg", "e:/", 2, 2 );//测试OK //
		 * 方法三：指定切片的宽度和高度 ImageCutUtils.cut3("e:/abc.jpg", "e:/", 300, 300
		 * );//测试OK
		 * 
		 * // 3-图像类型转换： ImageCutUtils.convert("e:/abc.jpg", "GIF",
		 * "e:/abc_convert.gif");//测试OK
		 * 
		 * // 4-彩色转黑白： ImageCutUtils.gray("e:/abc.jpg",
		 * "e:/abc_gray.jpg");//测试OK
		 * 
		 * // 5-给图片添加文字水印： // 方法一：
		 * ImageCutUtils.pressText("我是水印文字","e:/abc.jpg","e:/abc_pressText.jpg",
		 * "宋体",Font.BOLD,Color.white,80, 0, 0, 0.5f);//测试OK // 方法二：
		 * ImageCutUtils.pressText2("我也是水印文字",
		 * "e:/abc.jpg","e:/abc_pressText2.jpg", "黑体", 36, Color.white, 80, 0,
		 * 0, 0.5f);//测试OK
		 * 
		 * // 6-给图片添加图片水印： ImageCutUtils.pressImage("e:/abc2.jpg",
		 * "e:/abc.jpg","e:/abc_pressImage.jpg", 0, 0, 0.5f);//测试OK
		 */File f = new File("D:\\a.JPG");
		InputStream streamReader = null; // 文件输入流
		streamReader = new FileInputStream(f);
		// InputStream in= new File("D:/img");
		// OutputStream op=null;
		InputStream in = reSizeBywidthOrHeightScale(90, 70, streamReader);
		IOUtil.streamToFile(in, new File("d:\\img\\1.jpg"));

	}

	public final static void scale(InputStream inp, OutputStream result, int scale, boolean flag) {
		try {
			BufferedImage src = ImageIO.read(inp); // 读入文件
			int width = src.getWidth(); // 得到源图宽
			int height = src.getHeight(); // 得到源图长
			if (flag) {// 放大
				width = width * scale;
				height = height * scale;
			} else {// 缩小
				width = width / scale;
				height = height / scale;
			}
			Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			ImageIO.write(tag, "JPEG", result);// 输出到文件流.
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public final static void scale(String srcImageFile, String result, int scale, boolean flag) {
		try {
			BufferedImage src = ImageIO.read(new File(srcImageFile)); // 读入文件
			int width = src.getWidth(); // 得到源图宽
			int height = src.getHeight(); // 得到源图长
			if (flag) {// 放大
				width = width * scale;
				height = height * scale;
			} else {// 缩小
				width = width / scale;
				height = height / scale;
			}
			Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			ImageIO.write(tag, "JPEG", new File(result));// 输出到文件流.
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("static-access")
	public final static void scale2(String srcImageFile, String result, int height, int width, boolean bb) {
		try {
			double ratio = 0.0; // 缩放比例
			File f = new File(srcImageFile);
			BufferedImage bi = ImageIO.read(f);
			Image itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);
			// 计算比例
			if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
				if (bi.getHeight() > bi.getWidth()) {
					ratio = (new Integer(height)).doubleValue() / bi.getHeight();
				} else {
					ratio = (new Integer(width)).doubleValue() / bi.getWidth();
				}
				AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
				itemp = op.filter(bi, null);
			}
			if (bb) {// 补白
				BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = image.createGraphics();
				g.setColor(Color.white);
				g.fillRect(0, 0, width, height);
				if (width == itemp.getWidth(null))
					g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null),
							itemp.getHeight(null), Color.white, null);
				else
					g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null),
							itemp.getHeight(null), Color.white, null);
				g.dispose();
				itemp = image;
			}
			ImageIO.write((BufferedImage) itemp, "JPEG", new File(result));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public final static void cut(String srcImageFile, String result, int x, int y, int width, int height) {
		try {
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			int srcWidth = bi.getHeight(); // 源图宽度
			int srcHeight = bi.getWidth(); // 源图高度
			if (srcWidth > 0 && srcHeight > 0) {
				Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
				// 四个参数分别为图像起点坐标和宽高
				// 即: CropImageFilter(int x,int y,int width,int height)
				ImageFilter cropFilter = new CropImageFilter(x, y, width, height);
				Image img = Toolkit.getDefaultToolkit()
						.createImage(new FilteredImageSource(image.getSource(), cropFilter));
				BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics g = tag.getGraphics();
				g.drawImage(img, 0, 0, width, height, null); // 绘制切割后的图
				g.dispose();
				// 输出为文件
				ImageIO.write(tag, "JPEG", new File(result));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public final static void cut2(String srcImageFile, String descDir, int rows, int cols) {
		try {
			if (rows <= 0 || rows > 20)
				rows = 2; // 切片行数
			if (cols <= 0 || cols > 20)
				cols = 2; // 切片列数
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			int srcWidth = bi.getHeight(); // 源图宽度
			int srcHeight = bi.getWidth(); // 源图高度
			if (srcWidth > 0 && srcHeight > 0) {
				Image img;
				ImageFilter cropFilter;
				Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
				int destWidth = srcWidth; // 每张切片的宽度
				int destHeight = srcHeight; // 每张切片的高度
				// 计算切片的宽度和高度
				if (srcWidth % cols == 0) {
					destWidth = srcWidth / cols;
				} else {
					destWidth = (int) Math.floor(srcWidth / cols) + 1;
				}
				if (srcHeight % rows == 0) {
					destHeight = srcHeight / rows;
				} else {
					destHeight = (int) Math.floor(srcWidth / rows) + 1;
				}
				// 循环建立切片
				// 改进的想法:是否可用多线程加快切割速度
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < cols; j++) {
						// 四个参数分别为图像起点坐标和宽高
						// 即: CropImageFilter(int x,int y,int width,int height)
						cropFilter = new CropImageFilter(j * destWidth, i * destHeight, destWidth, destHeight);
						img = Toolkit.getDefaultToolkit()
								.createImage(new FilteredImageSource(image.getSource(), cropFilter));
						BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
						Graphics g = tag.getGraphics();
						g.drawImage(img, 0, 0, null); // 绘制缩小后的图
						g.dispose();
						// 输出为文件
						ImageIO.write(tag, "JPEG", new File(descDir + "_r" + i + "_c" + j + ".jpg"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public final static void cut3(String srcImageFile, String descDir, int destWidth, int destHeight) {
		try {
			if (destWidth <= 0)
				destWidth = 200; // 切片宽度
			if (destHeight <= 0)
				destHeight = 150; // 切片高度
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			int srcWidth = bi.getHeight(); // 源图宽度
			int srcHeight = bi.getWidth(); // 源图高度
			if (srcWidth > destWidth && srcHeight > destHeight) {
				Image img;
				ImageFilter cropFilter;
				Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
				int cols = 0; // 切片横向数量
				int rows = 0; // 切片纵向数量
				// 计算切片的横向和纵向数量
				if (srcWidth % destWidth == 0) {
					cols = srcWidth / destWidth;
				} else {
					cols = (int) Math.floor(srcWidth / destWidth) + 1;
				}
				if (srcHeight % destHeight == 0) {
					rows = srcHeight / destHeight;
				} else {
					rows = (int) Math.floor(srcHeight / destHeight) + 1;
				}
				// 循环建立切片
				// 改进的想法:是否可用多线程加快切割速度
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < cols; j++) {
						// 四个参数分别为图像起点坐标和宽高
						// 即: CropImageFilter(int x,int y,int width,int height)
						cropFilter = new CropImageFilter(j * destWidth, i * destHeight, destWidth, destHeight);
						img = Toolkit.getDefaultToolkit()
								.createImage(new FilteredImageSource(image.getSource(), cropFilter));
						BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
						Graphics g = tag.getGraphics();
						g.drawImage(img, 0, 0, null); // 绘制缩小后的图
						g.dispose();
						// 输出为文件
						ImageIO.write(tag, "JPEG", new File(descDir + "_r" + i + "_c" + j + ".jpg"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public final static void convert(String srcImageFile, String formatName, String destImageFile) {
		try {
			File f = new File(srcImageFile);
			f.canRead();
			f.canWrite();
			BufferedImage src = ImageIO.read(f);
			ImageIO.write(src, formatName, new File(destImageFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public final static void gray(String srcImageFile, String destImageFile) {
		try {
			BufferedImage src = ImageIO.read(new File(srcImageFile));
			ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
			ColorConvertOp op = new ColorConvertOp(cs, null);
			src = op.filter(src, null);
			ImageIO.write(src, "JPEG", new File(destImageFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public final static void pressText(String pressText, String srcImageFile, String destImageFile, String fontName,
			int fontStyle, Color color, int fontSize, int x, int y, float alpha) {
		try {
			File img = new File(srcImageFile);
			Image src = ImageIO.read(img);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			g.setColor(color);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
			// 在指定坐标绘制水印文字
			g.drawString(pressText, (width - (getLength(pressText) * fontSize)) / 2 + x, (height - fontSize) / 2 + y);
			g.dispose();
			ImageIO.write((BufferedImage) image, "JPEG", new File(destImageFile));// 输出到文件流
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public final static void pressText2(String pressText, String srcImageFile, String destImageFile, String fontName,
			int fontStyle, Color color, int fontSize, int x, int y, float alpha) {
		try {
			File img = new File(srcImageFile);
			Image src = ImageIO.read(img);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			g.setColor(color);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
			// 在指定坐标绘制水印文字
			g.drawString(pressText, (width - (getLength(pressText) * fontSize)) / 2 + x, (height - fontSize) / 2 + y);
			g.dispose();
			ImageIO.write((BufferedImage) image, "JPEG", new File(destImageFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public final static void pressImage(String pressImg, String srcImageFile, String destImageFile, int x, int y,
			float alpha) {
		try {
			File img = new File(srcImageFile);
			Image src = ImageIO.read(img);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, wideth, height, null);
			// 水印文件
			Image src_biao = ImageIO.read(new File(pressImg));
			int wideth_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
			g.drawImage(src_biao, (wideth - wideth_biao) / 2, (height - height_biao) / 2, wideth_biao, height_biao,
					null);
			// 水印文件结束
			g.dispose();
			ImageIO.write((BufferedImage) image, "JPEG", new File(destImageFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public final static int getLength(String text) {
		int length = 0;
		for (int i = 0; i < text.length(); i++) {
			if (new String(text.charAt(i) + "").getBytes().length > 1) {
				length += 2;
			} else {
				length += 1;
			}
		}
		return length / 2;
	}

	/**
	 * size(width,height) 若图片横比200小，高比300小，不变 若图片横比200小，高比300大，高缩小到300，图片比例不变
	 * 若图片横比200大，高比300小，横缩小到200，图片比例不变 若图片横比200大，高比300大，图片按比例缩小，横为200或高为300
	 * 
	 * @throws IOException
	 */
	public static void reSizeBywidthOrHeightScale(int width, int height, String sourceFilePath, String targetFilePath)
			throws IOException {
		Thumbnails.of(sourceFilePath).size(200, 300).toFile(targetFilePath);
	}

	public static InputStream reSizeBywidthOrHeightScale(int width, int height, InputStream inputStream)
			throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		Thumbnails.of(inputStream).size(width, height).toOutputStream(byteArrayOutputStream);
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
		return byteArrayInputStream;
	}

	/**
	 * 按照比例进行缩放
	 * 
	 * @throws IOException
	 */
	public static void reSizeByScale(double scale, String sourceFilePath, String targetFilePath) throws IOException {
		Thumbnails.of(sourceFilePath).scale(scale).toFile(targetFilePath);
	}

	/**
	 * 不按照比例，指定大小进行缩放
	 * 
	 * @throws IOException
	 */
	public static void reSizeByWidthAndHeight(int width, int height, String sourceFilePath, String targetFilePath)
			throws IOException {
		Thumbnails.of(sourceFilePath).size(width, height).keepAspectRatio(false).toFile(targetFilePath);
	}

	/**
	 * 按比例重置大小并旋转方向
	 * 
	 * @throws IOException
	 */
	public static void reSizeAndRotate(int width, int height, double angle, String sourceFilePath,
			String targetFilePath) throws IOException {
		Thumbnails.of(sourceFilePath).size(width, height).rotate(angle).toFile(targetFilePath);
	}

	/**
	 * 旋转
	 * 
	 * @throws IOException
	 */
	public static void rotate(double angle, String sourceFilePath, String targetFilePath) throws IOException {
		Thumbnails.of(sourceFilePath).rotate(angle).toFile(targetFilePath);
	}

	/**
	 * @date 2016年8月29日
	 * @Description:重置文件大小并在原图上生成水印
	 * @param width
	 * @param height
	 * @param angle
	 * @param sourceFilePath
	 * @param targetFilePath
	 * @param watermarkFilePath
	 *            :水印文件路径
	 * @param opacity
	 *            :透明度
	 * @param position
	 *            :水印位置
	 * @param watermarkFilePath
	 *            :压缩质量
	 * @throws IOException
	 */
	public static void reSizeAndWaterMark(int width, int height, String sourceFilePath, String targetFilePath,
			String watermarkFilePath, float opacity, float quality, Position position) throws IOException {
		Thumbnails.of(sourceFilePath).size(width, height)
				.watermark(position, ImageIO.read(new File(watermarkFilePath)), opacity).outputQuality(quality)
				.toFile(targetFilePath);
	}

	/**
	 * @date 2016年8月29日
	 * @Description: 生成水印
	 * @param sourceFilePath
	 * @param targetFilePath
	 * @param watermarkFilePath
	 * @param opacity
	 *            ：透明度
	 * @param quality
	 *            :压缩质量
	 * @param position
	 *            :水印位置
	 * @throws IOException
	 */
	public static void waterMark(String sourceFilePath, String targetFilePath, String watermarkFilePath, float opacity,
			float quality, Position position) throws IOException {
		Thumbnails.of(sourceFilePath).watermark(position, ImageIO.read(new File(watermarkFilePath)), opacity)
				.outputQuality(quality).toFile(targetFilePath);
	}

	/**
	 * @date 2016年8月29日
	 * @Description: 裁剪图片
	 * @param sourceFilePath
	 * @param targetFilePath
	 * @param position
	 * @param regionWidth
	 * @param regionHeight
	 * @param targetWidth
	 * @param targetHeight
	 * @throws IOException
	 */
	public static void reSizeByCut(String sourceFilePath, String targetFilePath, Position position, int regionWidth,
			int regionHeight, int targetWidth, int targetHeight) throws IOException {
		Thumbnails.of(sourceFilePath).sourceRegion(position, regionWidth, regionHeight).size(targetWidth, targetHeight)
				.keepAspectRatio(false).toFile(targetFilePath);
	}

	/**
	 * @date 2016年8月29日
	 * @Description: 图像重置大小并格式转换
	 * @param sourceFilePath
	 * @param targetFilePath
	 * @param width
	 * @param height
	 * @param targetFileType
	 * @throws IOException
	 */
	public static void reSizeAndChangeImgType(String sourceFilePath, String targetFilePath, int width, int height,
			String targetFileType) throws IOException {
		Thumbnails.of(sourceFilePath).size(width, height).outputFormat(targetFileType).toFile(targetFilePath);
	}

	/**
	 * @date 2016年8月29日
	 * @Description: 图像格式转换
	 * @param sourceFilePath
	 * @param targetFilePath
	 * @param width
	 * @param height
	 * @param targetFileType
	 * @throws IOException
	 */
	public static void changeImgType(String sourceFilePath, String targetFilePath, String targetFileType)
			throws IOException {
		Thumbnails.of(sourceFilePath).outputFormat(targetFileType).toFile(targetFilePath);
	}

	/**
	 * @date 2016年8月29日
	 * @Description: 图片重置大小并输出到OutputStream
	 * @param sourceFilePath
	 * @param width
	 * @param height
	 * @param os
	 * @return OutputStream
	 * @throws IOException
	 */
	public static OutputStream fileResizeAndToOutPutStream(String sourceFilePath, int width, int height,
			OutputStream os) throws IOException {
		Thumbnails.of(sourceFilePath).size(width, height).toOutputStream(os);
		return os;
	}

	/**
	 * @date 2016年8月29日
	 * @Description: 图片输出到OutputStream
	 * @param sourceFilePath
	 * @param os
	 * @return OutputStream
	 * @throws IOException
	 */
	public static OutputStream fileToOutPutStream(String sourceFilePath, OutputStream os) throws IOException {
		Thumbnails.of(sourceFilePath).toOutputStream(os);
		return os;
	}

	/**
	 * @date 2016年8月29日
	 * @Description:图片输出到BufferedImage
	 * @param sourceFilePath
	 * @return BufferedImage
	 * @throws IOException
	 */
	public static BufferedImage fileToBufferedImage(String sourceFilePath) throws IOException {
		return Thumbnails.of(sourceFilePath).asBufferedImage();
	}

	/**
	 * @date 2016年8月29日
	 * @Description: 图片重置大小输出到BufferedImage
	 * @param sourceFilePath
	 * @param width
	 * @param height
	 * @return BufferedImage
	 * @throws IOException
	 * 
	 */
	public static BufferedImage fileResizeAndToBufferedImage(String sourceFilePath, int width, int height)
			throws IOException {
		return Thumbnails.of(sourceFilePath).size(width, height).asBufferedImage();
	}
}