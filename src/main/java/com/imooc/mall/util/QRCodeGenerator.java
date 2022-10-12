package com.imooc.mall.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.imooc.mall.common.Constant;


import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * 生成二维码的工具
 */
public class QRCodeGenerator {

    public static void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        //encode方法生成一个编码后的比特矩阵
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        //获取Path形式的本地磁盘地址，用于保存生成的图片
        Path path = FileSystems.getDefault().getPath(filePath);
        //从矩阵变成图片，保存到指定位置
        MatrixToImageWriter.writeToPath(bitMatrix,"PNG",path);
    }

//    public static void main(String[] args) {
//        try {
//            generateQRCodeImage("hello", 350, 350, "D:/imooc_mall_static/QRTest.png");
//        } catch (WriterException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
