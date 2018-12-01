package com.yingxin.tec;

import com.yingxin.tec.util.CodeUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CodeGenerator {
    public static void main(String[] args) throws SQLException, InterruptedException {
        String beanPackage = "com.yingxin.tec.model";
        String daoPackage = "com.yingxin.tec.dao";
        String controllerPackage = "com.yingxin.tec.controller";
        String servicePackage = "com.yingxin.tec.service";
        String outPutPath = "e://";
        String tableName = "appointment";
        String beanClassName = "Appointment";
        String zhname = "预约";
        String url = "jdbc:mysql://localhost:3306/yingxin?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&serverTimezone=UTC";
        String user = "root";
        String password = "123456";
        Connection conn = DriverManager.getConnection(url, user, password);
        CodeUtil.beanJavaFileGen(beanPackage, outPutPath, tableName, beanClassName, conn);//生成javabean文件
        Class<?> clazz = null;
        while (true) {
            try {
                clazz = Class.forName(beanPackage + "." + beanClassName);
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Thread.sleep(2000);
        }
        CodeUtil.genMybatisMapperInterface(daoPackage, outPutPath, tableName, clazz, conn);//生成mybits文件
        CodeUtil.genServiceJavaFile(daoPackage, servicePackage, outPutPath, tableName, clazz, zhname, conn);
        CodeUtil.genControllerJavaFile(servicePackage, controllerPackage, outPutPath, tableName, clazz);
        conn.close();
    }
}
