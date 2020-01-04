package com.fact.modulo.vista.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ModuladorComprobanteComprimido {

	public static byte[] comprimirXML(String xml, String nomArchivo)
			throws IOException {
		File file = File.createTempFile(nomArchivo, "." + new Date().getTime());
		FileOutputStream fos = new FileOutputStream(file);
		ZipOutputStream archivoZip = new ZipOutputStream(fos);
		archivoZip.putNextEntry(new ZipEntry(nomArchivo + ".xml"));
		archivoZip.write(xml.getBytes(StandardCharsets.UTF_8));
		archivoZip.closeEntry();
		archivoZip.finish();
		fos.close();
		archivoZip.close();
		byte[] filesbytes = Files.readAllBytes(file.toPath());
		file.delete();
		
		return filesbytes;
	}

	public static String descomprimirXML(byte[] comprimido) throws IOException {
		String xml = null;
		InputStream inputStream = null;
		ByteArrayInputStream bis = null;
		ZipInputStream zis = null;
		ByteArrayOutputStream out = null;
		try{
			bis = new ByteArrayInputStream(comprimido);
			zis = new ZipInputStream(bis);
			while (zis.getNextEntry() != null) {
				byte data[] = new byte[1];
				out = new ByteArrayOutputStream();
				while (zis.read(data) != -1) {
					out.write(data);
				}
				inputStream = new ByteArrayInputStream(out.toByteArray());
				xml = getStringFromInputStream(inputStream);
				out.close();
				inputStream.close();
			}
			zis.close();
			bis.close();
		}finally{
			try{
				if(inputStream != null) inputStream.close();
				if(bis != null) bis.close();
				if(zis != null) zis.close();
				if(out != null) out.close();
			}catch(IOException io){
				io.printStackTrace();
			}
		}
		return xml;
	}

	public static String getStringFromInputStream(InputStream is) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String line;
		InputStreamReader isr = null;
		try {
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sb.toString();
	}

}
