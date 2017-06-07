package com.hjh.mall.bizapi.biz.goods.middle.util;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * @Project: hjy-middle
 * @Description 添加数据库表的model，修改generator.xml <table tableName="agent"
 *              domainObjectName="Agent"... 详情见该配置文件
 * @author 李慧峰
 * @date 2016年7月27日
 * @version V1.0
 */
public class MybatisGeneratorUtil {

	public static void main(String[] args) {
		try {
			System.out.println("start generator ...");
			List<String> warnings = new ArrayList<String>();
			boolean overwrite = true;
			File configFile = new File(MybatisGeneratorUtil.class
					.getResource("/generator.xml").getFile());
			ConfigurationParser cp = new ConfigurationParser(warnings);
			Configuration config = cp.parseConfiguration(configFile);
			DefaultShellCallback callback = new DefaultShellCallback(overwrite);
			MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
					callback, warnings);
			myBatisGenerator.generate(null);
			System.out.println("end generator!");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XMLParserException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}