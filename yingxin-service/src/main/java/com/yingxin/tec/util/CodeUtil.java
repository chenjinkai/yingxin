package com.yingxin.tec.util;

import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class CodeUtil {
	
	/**
	 * 数据库对象生成器
	 * 
	 * @param packagePath
	 * @param outputPath
	 * @param tablename
	 * @param className
	 * @param conn
	 */
	public static void beanJavaFileGen(String packagePath, String outputPath, String tablename, String className, Connection conn){
		if(outputPath == null || tablename == null || className == null || conn == null) {
			throw new IllegalArgumentException("连接不能为空");
		}
		StringBuilder sb = new StringBuilder();
		sb.append("package ").append(packagePath).append(";\r\n\r\n");
		Map<String, String> columns = new LinkedHashMap<String, String>();
		Set<String> needImport = new LinkedHashSet<String>();
		String sql = "select * from " + tablename + " limit 0, 1";
		PreparedStatement pstate = null;
		ResultSet resultSet = null;
		try {
			pstate = conn.prepareStatement(sql);
			resultSet = pstate.executeQuery();
			ResultSetMetaData metaData = resultSet.getMetaData();
			for(int i = 1;  i <= metaData.getColumnCount(); i++){
				columns.put(metaData.getColumnName(i), metaData.getColumnClassName(i));
				needImport.add(metaData.getColumnClassName(i));
			}
			Set<Entry<String, String>> entrys = columns.entrySet();//import 部分
			for(String typeStr : needImport){
				if(typeStr.trim().equalsIgnoreCase("java.math.BigInteger")){
					sb.append("import java.math.BigInteger;\r\n");
				}else if(typeStr.trim().equalsIgnoreCase("java.lang.Integer")){
					sb.append("import java.lang.Integer;\r\n");
				}else if(typeStr.trim().equalsIgnoreCase("java.lang.Float")){
					sb.append("import java.lang.Float;\r\n");
				}else if(typeStr.trim().equalsIgnoreCase("java.util.Date") 
						|| typeStr.trim().equalsIgnoreCase("java.sql.Date")
						|| typeStr.trim().equalsIgnoreCase("java.sql.TimeStamp")){
					sb.append("import java.util.Date;\r\n");
				}else if(typeStr.trim().equalsIgnoreCase("java.lang.Long")){
					sb.append("import java.lang.Long;\r\n");
				}
			}
			sb.append("\r\n");
			/**
			 * class主体
			 */
			sb.append("public class ").append(className).append("{\r\n\r\n");
			
			/**
			 * 属性
			 */
			for(Entry<String, String> entry : entrys){
				sb.append("\tprivate ");
				genType(entry, sb);
				sb.append(entry.getKey()).append(";\r\n\r\n");
			}
			
			/**
			 * getter and setter
			 */
			for(Entry<String, String> entry : entrys){
				sb.append("\tpublic void set").append(firstUppcase(entry.getKey()))
				.append("(");
				genType(entry, sb);
				sb.append(entry.getKey()).append("){\r\n");
				sb.append("\t\tthis.").append(entry.getKey()).append(" = ").append(entry.getKey()).append(";\r\n");
				sb.append("\t}\r\n\r\n");
				
				sb.append("\tpublic ");
				genType(entry, sb);
				sb.append("get").append(firstUppcase(entry.getKey()));
				sb.append("(){\r\n");
				sb.append("\t\treturn ").append(entry.getKey()).append(";\n");
				sb.append("\t}\r\n\r\n");
			}
			sb.append("}");
			FileUtil.writeFile(outputPath + firstUppcase(className) + ".java", sb.toString());
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.close(resultSet, pstate, null);
		}
	}
	
	public static void genType(Entry<String, String> entry, StringBuilder sb){
		if(entry.getValue().trim().equalsIgnoreCase("java.math.BigInteger")){
			sb.append("BigInteger ");
		}else if(entry.getValue().trim().equalsIgnoreCase("java.lang.Integer")){
			sb.append("Integer ");
		}else if(entry.getValue().trim().equalsIgnoreCase("java.lang.Float")){
			sb.append("Float ");
		}else if(entry.getValue().trim().equalsIgnoreCase("java.util.Date") 
				|| entry.getValue().trim().equalsIgnoreCase("java.sql.Date")
				|| entry.getValue().trim().equalsIgnoreCase("java.sql.TimeStamp")){
			sb.append("Date ");
		}else if(entry.getValue().trim().equalsIgnoreCase("java.lang.String")){
			sb.append("String ");
		}else if(entry.getValue().trim().equalsIgnoreCase("java.lang.Long")){
			sb.append("Long ");
		}
	}
	
	public static String firstUppcase(String value){
		if(StringUtils.isEmpty(value) || value.length() < 1){
			throw new IllegalArgumentException();
		}
		return value.substring(0, 1).toUpperCase() + value.substring(1);
	}
	
	public static void genMybatisMapperInterface(String packagePath, String outputPath, String tablename, Class<?> beanClazz, Connection conn){
		if(outputPath == null || tablename == null || beanClazz == null) {
			throw new IllegalArgumentException("连接不能为空");
		}
		List<String> columns = null;

		String sql = "select * from " + tablename + " limit 0, 1";
		PreparedStatement pstate = null;
		ResultSet resultSet = null;
		try{
			pstate = conn.prepareStatement(sql);
			resultSet = pstate.executeQuery();
			ResultSetMetaData metaData = resultSet.getMetaData();
			columns = new ArrayList<String>(metaData.getColumnCount());
			for(int i = 1;  i <= metaData.getColumnCount(); i++){
				columns.add(metaData.getColumnName(i));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.close(resultSet, pstate, null);
		}
		StringBuilder sb = new StringBuilder();
		sb.append("package ").append(packagePath).append(";\r\n\r\n");
		
		sb.append("import ").append(beanClazz.getName()).append(";\r\n");
		sb.append("import java.util.List;\r\n");
		sb.append("import org.springframework.stereotype.Repository;\r\n");
		sb.append("import org.apache.ibatis.annotations.Param;\r\n");
		sb.append("import java.math.BigInteger;\r\n");
		sb.append("import java.util.Map;\r\n\r\n");
		
		/**
		 * interface主体
		 */
		sb.append("@Repository\n");
		sb.append("public interface ").append(firstUppcase(beanClazz.getSimpleName())).append("Mapper").append("{\r\n\r\n");
		
		/**
		 * 函数生成
		 */
		sb.append("\tpublic void insert(@Param(value=\"").append(beanClazz.getSimpleName().toLowerCase()).append("\")").append(beanClazz.getSimpleName())
			.append(" ").append(beanClazz.getSimpleName().toLowerCase()).append(");\r\n\r\n");
		
		sb.append("\tpublic void update(@Param(value=\"").append(beanClazz.getSimpleName().toLowerCase()).append("\")").append(beanClazz.getSimpleName())
			.append(" ").append(beanClazz.getSimpleName().toLowerCase()).append(");\r\n\r\n");
		
		sb.append("\tpublic void delete(@Param(value=\"").append(beanClazz.getSimpleName().toLowerCase()).append("\")").append(beanClazz.getSimpleName())
		.append(" ").append(beanClazz.getSimpleName().toLowerCase()).append(");\r\n\r\n");
		
		sb.append("\tpublic BigInteger").append(" count(").append("Map<String, Object>")
		.append(" params").append(");\r\n\r\n");
		
		sb.append("\tpublic List<").append(beanClazz.getSimpleName()).append("> query(").append("Map<String, Object>")
		.append(" params").append(");\r\n\r\n");
		
		sb.append("}");
		
		FileUtil.writeFile(outputPath + firstUppcase(beanClazz.getSimpleName()) + "Mapper.java", sb.toString());
		
		/**
		 * 生成对应mapper.xml
		 */
		StringBuilder sb2 = new StringBuilder();
		sb2.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n");
		sb2.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\r\n");
		sb2.append("<mapper namespace=\"").append("com.yongli.dao.").append(beanClazz.getSimpleName()).append("Mapper\">\r\n\r\n");
		
		sb2.append("\t<insert id=\"insert\" parameterType=\"com.yongli.model.").append(beanClazz.getSimpleName()).append("\">\r\n");
		sb2.append("\t\tinsert into ").append(tablename).append("(").append(StringUtils.join(columns, ",")).append(") values ").append("(");
		for(int i = 0; i < columns.size(); i++){
			if(columns.get(i).equalsIgnoreCase("createdat") 
					|| columns.get(i).equalsIgnoreCase("updatedat")){
				continue;
			}
			sb2.append("#{").append(beanClazz.getSimpleName().toLowerCase()).append(".").append(columns.get(i)).append("}");
			if(i != columns.size() - 1){
				sb2.append(", ");
			}
		}
		sb2.append(")\r\n");
		sb2.append("\t</insert>\r\n\r\n");
		
		sb2.append("\t<update id=\"update\" parameterType=\"com.yongli.model.").append(beanClazz.getSimpleName()).append("\">\r\n");
		sb2.append("\t\tupdate ").append(tablename).append(" set ");
		for(int i = 0; i < columns.size(); i++){
			if(columns.get(i).equalsIgnoreCase("createdat") 
					|| columns.get(i).equalsIgnoreCase("updatedat") || columns.get(i).equalsIgnoreCase("id")){
				continue;
			}
			sb2.append(columns.get(i)).append("=#{").append(beanClazz.getSimpleName().toLowerCase()).append(".").append(columns.get(i)).append("}");
			if(i != columns.size() - 1){
				sb2.append(", ");
			}
		}
		sb2.append(" \r\n\t\twhere id=#{").append(beanClazz.getSimpleName().toLowerCase()).append(".id}");
		sb2.append("\r\n");
		sb2.append("\t</update>\r\n\r\n");
		
		sb2.append("\t<delete id=\"delete\" parameterType=\"com.yongli.model.").append(beanClazz.getSimpleName()).append("\">\r\n");
		sb2.append("\t\tdelete from ").append(tablename);
		sb2.append(" \r\n\t\twhere id=#{").append(beanClazz.getSimpleName().toLowerCase()).append(".id}");
		sb2.append("\r\n");
		sb2.append("\t</delete>\r\n\r\n");
		
		sb2.append("\t<select id=\"count\" parameterType=\"map\" resultType=\"java.math.BigInteger").append("\">\r\n");
		sb2.append("\t\tselect ").append("count(0) count");
		sb2.append(" from ").append(tablename);
		sb2.append(" \r\n\t\twhere 1 = 1 \r\n\t\t<if test=\" ").append(".{field} != null and ").append(".{field} != ''\">\r\n\r\n\t\t</if>");
		sb2.append("\r\n");
		sb2.append("\t</select>\r\n");
		
		sb2.append("\t<select id=\"query\" parameterType=\"map\" resultType=\"com.yongli.model.").append(beanClazz.getSimpleName()).append("\">\r\n");
		sb2.append("\t\tselect ");
		if(columns != null && columns.size() > 0){
			for(int i = 0; i < columns.size(); i ++){
				if(columns.get(i).equalsIgnoreCase("createdat") 
						|| columns.get(i).equalsIgnoreCase("updatedat")){
					continue;
				}
				sb2.append(columns.get(i));
				if(i != columns.size() - 1){
					sb2.append(", ");
				}
			}
		}
		
		
		sb2.append(" from ").append(tablename);
		sb2.append(" \r\n\t\twhere 1 = 1 \r\n\t\t<if test=\" ").append(".{field} != null and ").append(".{field} != ''\">\r\n\r\n\t\t</if>");
		sb2.append("\r\n\t\tlimit #{start}, #{length}");
		sb2.append("\r\n");
		sb2.append("\t</select>\r\n");
		sb2.append("</mapper>");
		FileUtil.writeFile(outputPath + firstUppcase(beanClazz.getSimpleName()) + "Mapper.xml", sb2.toString());
	}
	
	public static void genServiceJavaFile(String daoBase, String packagePath, String outputPath, String tablename, Class<?> beanClass, String serviceZhName, Connection conn){
		StringBuilder sb = new StringBuilder();
		String mapperVaName = beanClass.getSimpleName().toLowerCase() + "Mapper";
		Map<String, String> columns = new LinkedHashMap<String, String>();
		Set<String> needImport = new LinkedHashSet<String>();
		String sql = "select * from " + tablename + " limit 0, 1";
		PreparedStatement pstate = null;
		ResultSet resultSet = null;
		try{
			pstate = conn.prepareStatement(sql);
			resultSet = pstate.executeQuery();
			ResultSetMetaData metaData = resultSet.getMetaData();
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				columns.put(metaData.getColumnName(i), metaData.getColumnClassName(i));
				needImport.add(metaData.getColumnClassName(i));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.close(resultSet, pstate, null);
		}
		sb.append("package ").append(packagePath).append(";\r\n\r\n");
		sb.append("import ").append(beanClass.getName()).append(";\r\n");
		sb.append("import java.math.BigInteger;\r\n");
		sb.append("import java.util.List;\r\n");
		sb.append("import java.util.Map;\r\n");
		sb.append("import org.springframework.beans.factory.annotation.Autowired;\r\n");
		sb.append("import org.springframework.stereotype.Service;\r\n");
		sb.append("import org.springframework.transaction.annotation.Transactional;\r\n");
		sb.append("import com.alibaba.fastjson.JSONArray;\r\n");
		sb.append("import com.alibaba.fastjson.JSONObject;\r\n");
		sb.append("import ").append(daoBase).append(".").append(beanClass.getSimpleName()).append("Mapper;\r\n");
		sb.append("import com.yongli.exception.BusinessException;\r\n");
		sb.append("import com.yongli.factory.ResponseFactory;\r\n");
		sb.append("import com.yongli.model.DatatableRequest;\r\n");
		sb.append("import com.yongli.model.controller.ResultResponse;\r\n");
		sb.append("import com.yongli.util.MybatisUtil;\r\n\r\n");
		
		sb.append("@Service\r\n");
		sb.append("public class ").append(beanClass.getSimpleName()).append("Service {\r\n\r\n");
		sb.append("\t@Autowired\r\n");
		sb.append("\tprivate ").append(beanClass.getSimpleName()).append("Mapper").append(" ").append(beanClass.getSimpleName().toLowerCase()).append("Mapper").append(";\r\n");
		sb.append("\r\n");
		
		sb.append("\t@Transactional\r\n");
		sb.append("\tpublic ResultResponse add(").append(beanClass.getSimpleName()).append(" ").append(beanClass.getSimpleName().toLowerCase()).append(") throws BusinessException{\r\n");
		sb.append("\t\ttry{\r\n");
		sb.append("\t\t\t").append(mapperVaName).append(".").append("insert(").append(beanClass.getSimpleName().toLowerCase()).append(");\r\n");
		sb.append("\t\t}catch(Exception e){\r\n");
		sb.append("\t\t\tthrow new BusinessException(\"增加"+ serviceZhName + "失败\", e);\r\n");
		sb.append("\t\t}\r\n");
		sb.append("\t\treturn ResponseFactory.createSuccessResponserFactory(\"增加" + serviceZhName + "成功\");\r\n");
		sb.append("\t}\r\n\r\n");
		
		sb.append("\t@Transactional\r\n");
		sb.append("\tpublic ResultResponse delete(BigInteger id)").append(" throws BusinessException{\r\n");
		sb.append("\t\ttry{\r\n");
		sb.append("\t\t\t").append(beanClass.getSimpleName()).append(" ").append(beanClass.getSimpleName().toLowerCase()).append(" = new ").append(beanClass.getSimpleName()).append("();\r\n");
		sb.append("\t\t\t").append(beanClass.getSimpleName().toLowerCase()).append(".setId(id);\r\n");
		sb.append("\t\t\t").append(mapperVaName).append(".").append("delete(").append(beanClass.getSimpleName().toLowerCase()).append(");\r\n");
		sb.append("\t\t}catch(Exception e){\r\n");
		sb.append("\t\t\tthrow new BusinessException(\"删除"+ serviceZhName + "失败\", e);\r\n");
		sb.append("\t\t}\r\n");
		sb.append("\t\treturn ResponseFactory.createSuccessResponserFactory(\"删除" + serviceZhName + "成功\");\r\n");
		sb.append("\t}\r\n\r\n");
		
		sb.append("\t@Transactional\r\n");
		sb.append("\tpublic ResultResponse update(").append(beanClass.getSimpleName()).append(" ").append(beanClass.getSimpleName().toLowerCase()).append(") throws BusinessException{\r\n");
		sb.append("\t\ttry{\r\n");
		sb.append("\t\t\t").append(mapperVaName).append(".").append("update(").append(beanClass.getSimpleName().toLowerCase()).append(");\r\n");
		sb.append("\t\t}catch(Exception e){\r\n");
		sb.append("\t\t\tthrow new BusinessException(\"更新"+ serviceZhName + "失败\", e);\r\n");
		sb.append("\t\t}\r\n");
		sb.append("\t\treturn ResponseFactory.createSuccessResponserFactory(\"更新" + serviceZhName + "成功\");\r\n");
		sb.append("\t}\r\n\r\n");
		
		sb.append("\t@Transactional\r\n");
		sb.append("\tpublic JSONObject getTableData(DatatableRequest datatableRequest) throws BusinessException{\r\n");
		sb.append("\t\tJSONObject data = new JSONObject();\r\n");
		sb.append("\t\tJSONArray dataResult = new JSONArray();\r\n");
		sb.append("\t\tdata.put(\"data\", dataResult);\r\n");
		sb.append("\t\ttry{\r\n");
		sb.append("\t\t\tMap<String, Object> params = MybatisUtil.datatableRequest2Map(datatableRequest);\r\n");
		sb.append("\t\t\tList<").append(beanClass.getSimpleName()).append("> ").append(beanClass.getSimpleName().toLowerCase()).append("s").append(" = ").append(mapperVaName).append(".query(params);\r\n");
		sb.append("\t\t\tif(").append(beanClass.getSimpleName().toLowerCase()).append("s").append(" != null && ").append(beanClass.getSimpleName().toLowerCase()).append("s").append(".size() > 0){\r\n");
		sb.append("\t\t\t\tfor(").append(beanClass.getSimpleName()).append(" ").append(beanClass.getSimpleName().toLowerCase()).append(" : ").append(beanClass.getSimpleName().toLowerCase()).append("s").append("){\r\n");
		sb.append("\t\t\t\t\tJSONObject result = new JSONObject();\r\n");
		Set<Entry<String, String>> entrys = columns.entrySet();
		if(entrys != null && entrys.size() > 0){
			for(Entry<String, String> entry : entrys){
				sb.append("\t\t\t\t\tresult.put(\"" + entry.getKey() + "\", ");
				sb.append(beanClass.getSimpleName().toLowerCase()).append(".get").append(firstUppcase(entry.getKey())).append("()");
				sb.append(");\r\n");
			}
		}
		sb.append("\t\t\t\t\tdataResult.add(result);\r\n");
		sb.append("\t\t\t\t}\r\n");
		sb.append("\t\t\t}\r\n");
		sb.append("\t\t}catch(Exception e){\r\n");
		sb.append("\t\t\tthrow new BusinessException(\"获取"+ serviceZhName + "数据失败\", e);\r\n");
		sb.append("\t\t}\r\n");
		sb.append("\t\treturn data;\r\n");
		sb.append("\t}\r\n\r\n");
		sb.append("}");
		FileUtil.writeFile(outputPath + firstUppcase(beanClass.getSimpleName()) + "Service.java", sb.toString());
	}
	
	public static void genControllerJavaFile(String serviceBasePath, String packagePath, String outputPath, String tablename, Class<?> beanClass) {
		StringBuilder sb = new StringBuilder();
		String serviceName = beanClass.getSimpleName().toLowerCase() + "Service";
		sb.append("package ").append(packagePath).append(";\r\n\r\n");
		sb.append("import java.math.BigInteger;\r\n");
		sb.append("import org.springframework.beans.factory.annotation.Autowired;\r\n");
		sb.append("import javax.servlet.http.HttpServletRequest;\r\n");
		sb.append("import javax.servlet.http.HttpServletResponse;\r\n");
		sb.append("import javax.validation.Valid;\r\n");
		sb.append("import org.springframework.stereotype.Controller;\r\n");
		sb.append("import org.springframework.validation.BindingResult;\r\n");
		sb.append("import org.springframework.validation.annotation.Validated;\r\n");
		sb.append("import org.springframework.web.bind.annotation.ModelAttribute;\r\n");
		sb.append("import org.springframework.web.bind.annotation.PathVariable;\r\n");
		sb.append("import org.springframework.web.bind.annotation.RequestMapping;\r\n");
		sb.append("import org.springframework.web.bind.annotation.RequestMethod;\r\n");
		sb.append("import org.springframework.web.bind.annotation.RequestParam;\r\n");
		sb.append("import org.springframework.web.bind.annotation.ResponseBody;\r\n");
		sb.append("import com.yongli.exception.BusinessException;\r\n");
		sb.append("import com.yongli.factory.ValidateResponseFactory;\r\n");
		sb.append("import com.yongli.model.DatatableRequest;\r\n");
		sb.append("import com.yongli.model.validate.group.DatatableQuery;\r\n");
		sb.append("import com.yongli.util.HttpUtil;\r\n");
		sb.append("import ").append(beanClass.getName()).append(";\r\n");
		sb.append("import ").append(serviceBasePath).append(".").append(beanClass.getSimpleName()).append("Service").append(";\r\n");
		sb.append("\r\n");
		
		sb.append("@Controller\r\n");
		sb.append("public class ").append(beanClass.getSimpleName()).append("Controller {\r\n\r\n");
		sb.append("\t@Autowired\r\n");
		sb.append("\tprivate ").append(firstUppcase(serviceName)).append(" ").append(serviceName).append(";\r\n");
		sb.append("\r\n");
		/**
		 * get api
		 */
		sb.append("\t@RequestMapping(value=\"/" + beanClass.getSimpleName().toLowerCase() + "s\", method = {RequestMethod.GET}, produces=\"application/json;charset=UTF-8\")\r\n");
		sb.append("\t@ResponseBody\r\n");
		sb.append("\tpublic Object index(@Validated(DatatableQuery.class) @ModelAttribute DatatableRequest datatableRequest,\r\n");
		sb.append("\t\t\t BindingResult binding, \r\n");
		sb.append("\t\t\t HttpServletRequest request, \r\n");
		sb.append("\t\t\t HttpServletResponse response) throws BusinessException {\r\n");
		sb.append("\t\tif(binding.hasErrors()){\r\n");
		sb.append("\t\t\tresponse.setHeader(\"Access-Control-Allow-Origin\", \"*\");\r\n");
		sb.append("\t\t\treturn ValidateResponseFactory.createValidateFailureResponse(binding);\r\n");
		sb.append("\t\t}\r\n");
		sb.append("\t\treturn HttpUtil.getJSONPResponse("+ beanClass.getSimpleName().toLowerCase() +"Service.getTableData(datatableRequest), true, request, response);\r\n");
		sb.append("\t}\r\n\r\n");
		
		/**
		 * add api
		 */
		sb.append("\t@RequestMapping(value=\"/" + beanClass.getSimpleName().toLowerCase() + "s\", method = {RequestMethod.POST}, produces=\"application/json;charset=UTF-8\")\r\n");
		sb.append("\t@ResponseBody\r\n");
		sb.append("\tpublic Object add(@Valid @RequestParam "+ beanClass.getSimpleName() + " " + beanClass.getSimpleName().toLowerCase() + ",\r\n");
		sb.append("\t\t\t BindingResult binding, \r\n");
		sb.append("\t\t\t HttpServletRequest request, \r\n");
		sb.append("\t\t\t HttpServletResponse response) throws BusinessException{\r\n");
		sb.append("\t\tif(binding.hasErrors()){\r\n");
		sb.append("\t\t\tresponse.setHeader(\"Access-Control-Allow-Origin\", \"*\");\r\n");
		sb.append("\t\t\treturn ValidateResponseFactory.createValidateFailureResponse(binding);\r\n");
		sb.append("\t\t}\r\n");
		sb.append("\t\treturn HttpUtil.getJSONPResponse("+ beanClass.getSimpleName().toLowerCase() +"Service.add("+ beanClass.getSimpleName().toLowerCase() +"), true, request, response);\r\n");
		sb.append("\t}\r\n\r\n");
		
		/**
		 * update api
		 */
		sb.append("\t@RequestMapping(value=\"/" + beanClass.getSimpleName().toLowerCase() + "s\", method = {RequestMethod.PUT}, produces=\"application/json;charset=UTF-8\")\r\n");
		sb.append("\t@ResponseBody\r\n");
		sb.append("\tpublic Object update(@Valid @RequestParam "+ beanClass.getSimpleName() + " " + beanClass.getSimpleName().toLowerCase() + ",\r\n");
		sb.append("\t\t\t BindingResult binding, \r\n");
		sb.append("\t\t\t HttpServletRequest request, \r\n");
		sb.append("\t\t\t HttpServletResponse response) throws BusinessException{\r\n");
		sb.append("\t\tif(binding.hasErrors()){\r\n");
		sb.append("\t\t\tresponse.setHeader(\"Access-Control-Allow-Origin\", \"*\");\r\n");
		sb.append("\t\t\treturn ValidateResponseFactory.createValidateFailureResponse(binding);\r\n");
		sb.append("\t\t}\r\n");
		sb.append("\t\treturn HttpUtil.getJSONPResponse("+ beanClass.getSimpleName().toLowerCase() +"Service.update(" + beanClass.getSimpleName().toLowerCase() +"), true, request, response);\r\n");
		sb.append("\t}\r\n\r\n");
		
		/**
		 * delete api
		 */
		sb.append("\t@RequestMapping(value=\"/" + beanClass.getSimpleName().toLowerCase() + "s/{id}\", method = {RequestMethod.DELETE}, produces=\"application/json;charset=UTF-8\")\r\n");
		sb.append("\t@ResponseBody\r\n");
		sb.append("\tpublic Object delete(@PathVariable(value = \"id\") BigInteger id, \r\n");
		sb.append("\t\t\t HttpServletRequest request, \r\n");
		sb.append("\t\t\t HttpServletResponse response) throws BusinessException{\r\n");
		sb.append("\t\treturn HttpUtil.getJSONPResponse("+ beanClass.getSimpleName().toLowerCase() +"Service.delete(id), true, request, response);\r\n");
		sb.append("\t}\r\n\r\n");
		sb.append("}");
		FileUtil.writeFile(outputPath + firstUppcase(beanClass.getSimpleName()) + "Controller.java", sb.toString());
	}
}
