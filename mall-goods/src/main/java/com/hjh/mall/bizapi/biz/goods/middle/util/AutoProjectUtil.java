package com.hjh.mall.bizapi.biz.goods.middle.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;

public class AutoProjectUtil {

	public static void createService() {
		try {
			// 模板页面
			String daoTemplate = getPath("template\\service.txt");
			// 写入到磁盘里面去
			String result = replaceModel(daoTemplate);
			// 要生成的根目录
			String daoRoot = getPath("src\\main\\java\\com\\hjh\\activity\\middle\\service");
			File rootPath = new File(daoRoot);
			// 如果不存在那么久创建
			if (!rootPath.exists())
				rootPath.mkdirs();
			// 产生接口文件
			File daoJava = new File(rootPath, entity + "Service.java");
			// 讲模板中替换好的数据通过写入目录中去
			FileUtils.writeStringToFile(daoJava, result, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void createDao() {
		try {
			// 模板页面
			String daoTemplate = getPath("template\\dao.txt");
			// 写入到磁盘里面去
			String result = replaceModel(daoTemplate);
			// 要生成的根目录
			String daoRoot = getPath("src\\main\\java\\com\\hjh\\activity\\middle\\dao");
			File rootPath = new File(daoRoot);
			// 如果不存在那么久创建
			if (!rootPath.exists())
				rootPath.mkdirs();
			// 产生接口文件
			File daoJava = new File(rootPath, entity + "Dao.java");
			// 讲模板中替换好的数据通过写入目录中去
			FileUtils.writeStringToFile(daoJava, result, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void createServiceImpl() {
		try {
			// 模板页面
			String serviceTemplate = getPath("template\\serviceImpl.txt");
			// 写入到磁盘里面去
			String result = replaceModel(serviceTemplate);
			// 要生成的根目录
			String daoRoot = getPath("src\\main\\java\\com\\hjh\\activity\\middle\\service\\impl");
			File rootPath = new File(daoRoot);
			// 如果不存在那么久创建
			if (!rootPath.exists())
				rootPath.mkdirs();
			// 产生接口文件
			File daoJava = new File(rootPath, entity + "ServiceImpl.java");
			// 讲模板中替换好的数据通过写入目录中去
			FileUtils.writeStringToFile(daoJava, result, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 测试类生产
	public static void createServiceTest() {
		try {
			// 模板页面
			String serviceTemplate = getPath("template\\serviceTest.txt");
			// 写入到磁盘里面去
			String result = replaceModel(serviceTemplate);
			// 要生成的根目录
			String daoRoot = getPath("src\\test\\java\\com\\hjh\\activity\\middle\\service\\impl");
			File rootPath = new File(daoRoot);
			// 如果不存在那么久创建
			if (!rootPath.exists())
				rootPath.mkdirs();
			// 产生接口文件
			File daoJava = new File(rootPath, entity + "ServiceImplTest.java");
			// 讲模板中替换好的数据通过写入目录中去
			FileUtils.writeStringToFile(daoJava, result, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String replaceModel(String path) throws IOException {
		String result = FileUtils.readFileToString(new File(path), "UTF-8");
		result = result.replaceAll("\\[entity\\]", entity).replaceAll("\\[lowEntity\\]", lowEntity)
				.replaceAll("\\[author\\]", author).replaceAll("\\[description\\]", description)
				.replaceAll("\\[date\\]", date);
		return result;
	}

	public static void creatSqlmaping() throws Exception {
		Class c = Class.forName("com.hjh.activity.middle.entity." + entity);
		Field[] f = c.getDeclaredFields();
		String entityfields = "";
		String wheresql = "";
		String setsql = "";
		String insertsql = "";
		String entityfilesValues = "";
		for (int i = 0; i < f.length; i++) {
			Field field = f[i];
			if ("serialVersionUID".equals(field.getName())) {
				continue;
			}
			wheresql += "\t\t\t<if test=\"" + field.getName() + " !=null" + "\">" + "\n\t\t\t" + "AND "
					+ field.getName() + "=" + "#{" + field.getName() + "}\n\t\t\t</if>\n";
			if (f[i].getName() != entityid) {// 如果是id 则不能赋值
				setsql += "\t\t\t<if test=\"" + field.getName() + " !=null" + "\">" + "\n\t\t\t" + field.getName()
						+ "=" + "#{" + field.getName() + "},\n\t\t\t</if>\n";
				entityfields += field.getName() + ",";
			}
			insertsql += "#{" + field.getName() + "},\n";
			entityfilesValues += "#{" + field.getName() + "},";

		}
		System.out.println(insertsql);
		String serviceTemplate = getPath("template\\sqlmapping.txt");
		String result = replacesqlmapp(serviceTemplate, entityfields, setsql, wheresql, insertsql, entityfilesValues);
		String daoRoot = getPath("src\\main\\resources\\sqlmapper");
		File rootPath = new File(daoRoot);
		File daoJava = new File(rootPath, entity + "-sqlmapper.xml");
		// 讲模板中替换好的数据通过写入目录中去
		FileUtils.writeStringToFile(daoJava, result, "UTF-8");

	}

	public static String replacesqlmapp(String path, String entityfields, String setsql, String wheresql,
			String insertsql, String entityfilesValues) throws IOException {
		String result = FileUtils.readFileToString(new File(path), "UTF-8");
		result = result.replaceAll("\\[entity\\]", entity).replaceAll("\\[entityfiles\\]", entityfields)
				.replaceAll("\\[wheresql\\]", wheresql).replaceAll("\\[tablename\\]", tablename)
				.replaceAll("\\[wheresql\\]", wheresql).replaceAll("\\[wheresql\\]", wheresql)
				.replaceAll("\\[setsql\\]", setsql).replaceAll("\\[id\\]", entityid)
				.replaceAll("\\[entityfilesValues\\]", entityfilesValues);

		return result;
	}

	// 不难很简单---javase学习的IO流+模板替换
	public static String getPath(String appendPath) {
		String path = System.getProperty("user.dir");
		if (StringUtils.isEmpty(appendPath)) {
			return path;
		} else {
			return path + "\\" + appendPath;
		}
	}

	public static void main(String[] args) throws Exception {
		// createService();
		// createServiceImpl();
		// createDao();
		creatSqlmaping();
		System.out.println("执行成功");
	}

	private static String author = "杨益桦";// 作者
	private static String description = "活动";// 中文提示
	private static String date = new SimpleDateFormat("yyyy年MM月dd日").format(new Date());// 时间格式
	private static String entity = "ActivityInfo";// 实体名 【Entity】
	private static String lowEntity = "activityInfo";// 实体对象【entity】
	private static String entityid = "activity_id";// 数据库表主键【entity_id】
	private static String tablename = "activity_info";// 表名【entity】
	private static boolean isTest = false;

}
