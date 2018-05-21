package com.report.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author weiQiang
 * @date 2018/5/18
 */
@Slf4j
public class ZipFileTest {

    @Test
    public void addFileToZipFile() throws IOException {
        String zipFilePath = "E:\\logs\\logs.zip";
        String filePath = "E:\\logs\\测试打包.txt";
        ZipFileUtil.addFileToZip(new File(zipFilePath), new File(filePath), "resources");
    }
}
