package com.medshield.medshieldbackend.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class QrCodeGenerator {

    /**
     * Generates a Base64 encoded PNG image of a QR code.
     * The QR code contains a full URL pointing to the patient's profile
     * using the developer's local IP address.
     */
    public static String getQrCodeImage(String qrCodeId) {
        // UPDATED: Using your specific IPv4 address so mobile devices can find your server
        String myIpAddress = "10.86.74.136";
        String fullUrl = "http://" + myIpAddress + ":8080/profile.html?id=" + qrCodeId;

        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();

            // We encode the 'fullUrl' instead of just the 'qrCodeId'
            // This triggers the phone's "Open in Browser" action.
            BitMatrix bitMatrix = qrCodeWriter.encode(fullUrl, BarcodeFormat.QR_CODE, 250, 250);

            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            byte[] pngData = pngOutputStream.toByteArray();

            return Base64.getEncoder().encodeToString(pngData);
        } catch (Exception e) {
            System.err.println("QR Generation Error: " + e.getMessage());
            return "";
        }
    }
}