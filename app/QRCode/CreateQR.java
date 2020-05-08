package QRCode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.ByteMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import play.db.jpa.Blob;


public class CreateQR{
	
	public static Blob generateQrCodeBlob(String conteudo) {
		try {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			String charset = "UTF-8";
			String qrCodeData = conteudo;
			
			Hashtable < EncodeHintType, ErrorCorrectionLevel > hintMap = new Hashtable < EncodeHintType, ErrorCorrectionLevel > ();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            
            System.out.println(qrCodeData);
            
            ByteMatrix matrix = new MultiFormatWriter().encode(qrCodeData, BarcodeFormat.QR_CODE, 400, 400, hintMap);
			MatrixToImageWriter.writeToStream(matrix, "PNG", stream);
			
			Blob qrCodeBlob = null;
			
			InputStream is = new ByteArrayInputStream(stream.toByteArray());
			
			qrCodeBlob = new Blob();
			qrCodeBlob.set(is, "PNG");

			return qrCodeBlob;
		} catch (IOException | WriterException e) {
			// Given that this operation is entirely in memory, any such exceptions are indicative of bad input.
			throw new IllegalArgumentException("Invalid URI", e);
		}
	}
}
	