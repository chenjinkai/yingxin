package com.yingxin.tec.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 文件相关util
 *
 * @author chenjk
 * @since 2015年4月28日
 */
public class FileUtil {
    protected static final Logger logger = LoggerFactory.getLogger(FileUtil.class.getName());

    /**
     * 写文件
     *
     * @param filepath
     * @param content
     * @throws IOException
     */
    public static void writeFile(String filepath, String content) {
        PrintWriter pw = null;
        try {
            if (StringUtils.isEmpty(filepath)) {
                return;
            }
            File file = new File(filepath);
            if (file != null && !file.exists()) {
                String parentDirPath = file.getParent();
                if (StringUtils.isNotEmpty(parentDirPath)) {
                    File parentDir = new File(parentDirPath);
                    if (parentDir != null && !parentDir.exists()) {
                        parentDir.mkdirs();
                    }
                }
                file.createNewFile();
            }
            pw = new PrintWriter(new FileOutputStream(file));
            pw.println(content);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (pw != null) {
                pw.flush();
                pw.close();
            }
        }
    }

    /**
     * 读取文件
     *
     * @param filepath
     * @return
     * @throws IOException
     */
    public static String readFile(String filepath) {
        BufferedReader bfr = null;
        File file = new File(filepath);
        StringBuilder sb = new StringBuilder();
        if (file != null && !file.exists()) {
            return null;
        }
        try {
            bfr = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = bfr.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (bfr != null) {
                try {
                    bfr.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return null;
    }

    public static byte[] readFile2Bytes(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        if (file != null && file.exists()) {
            ByteArrayOutputStream bos = null;
            BufferedInputStream in = null;
            try {
                bos = new ByteArrayOutputStream((int) file.length());
                in = new BufferedInputStream(new FileInputStream(file));
                int buf_size = 1024;
                byte[] buffer = new byte[buf_size];
                int len = 0;
                while (-1 != (len = in.read(buffer, 0, buf_size))) {
                    bos.write(buffer, 0, len);
                }
                return bos.toByteArray();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        logger.error(e.getMessage(), e);
                    }
                }
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (IOException e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        } else {
            throw new FileNotFoundException(filePath);
        }
        return null;
    }
}
