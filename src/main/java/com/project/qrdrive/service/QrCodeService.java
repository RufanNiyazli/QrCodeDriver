package com.project.qrdrive.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import org.springframework.stereotype.Service;
import com.google.zxing.common.BitMatrix;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import java.io.ByteArrayOutputStream;

@Service
public class QrCodeService {

    public byte[] generateQRCodeImage(String text,int width ,int height) throws Exception{
        BitMatrix bitMatrix = new MultiFormatWriter().encode(
                text, BarcodeFormat.QR_CODE, width, height);
        ByteArrayOutputStream pngOutputStream =  new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return pngOutputStream.toByteArray();


    }
}
