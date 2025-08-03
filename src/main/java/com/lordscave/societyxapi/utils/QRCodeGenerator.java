package com.lordscave.societyxapi.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;


import java.io.IOException;

@Component
public class QRCodeGenerator {

    public static byte[] generateQRCode(String text, int width, int height) throws WriterException, IOException {
        BitMatrix matrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, width, height);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "PNG", out);
        return out.toByteArray();
    }
}

