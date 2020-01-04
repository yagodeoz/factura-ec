/**
 * @author BYRON
 * 
 */
package onix.fe.microservicios.utils;



import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;



/**
 * The Class Criptografica.
 */
public class Criptografica 
{
	
	/** The salt bytes. */
	private static byte[] SALT_BYTES = {  
		(byte)0xA9, (byte)0x9B, (byte)0xC8, 
		(byte)0x32,  (byte)0x56, (byte)0x35, 
		(byte)0xE3, (byte)0x03      };  
	
	/** The iteration count. */
	private static int ITERATION_COUNT = 19;  
	
	/** The Constant CLAVE. */
	private final static String CLAVE="seed7moDulo7$eguRidad7jee";
	
	/**
	 * Encriptar.
	 *
	 * @param str the str
	 * @return the string
	 * @throws Throwable the throwable
	 */
	public static String encriptar(String str) throws Throwable
	{
		return encriptar(CLAVE, str);
	}
	
	/**
	 * Encriptar.
	 *
	 * @param passPhrase the pass phrase
	 * @param str the str
	 * @return the string
	 * @throws Throwable the throwable
	 */
	@SuppressWarnings("restriction")
	public static String encriptar(String passPhrase, String str) throws Throwable  
	{
		try
		{
			Cipher ecipher = null;   
			KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(),SALT_BYTES,ITERATION_COUNT);    
			SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);    
			ecipher = Cipher.getInstance(key.getAlgorithm());    
			AlgorithmParameterSpec paramSpec = new PBEParameterSpec(SALT_BYTES,ITERATION_COUNT);
			ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);    
  
			byte[] utf8 = str.getBytes("UTF8");         
			byte[] enc = ecipher.doFinal(utf8);         
			return new sun.misc.BASE64Encoder().encode(enc);   
		}
		catch (Exception e) 
		{
			throw new Throwable(e);
		}
	}       
	
	/**
	 * Desencriptar.
	 *
	 * @param passPhrase the pass phrase
	 * @param str the str
	 * @return the string
	 * @throws Throwable the throwable
	 */
	@SuppressWarnings("restriction")
	public static String desencriptar(String passPhrase, String str) throws Throwable 
	{   
		try
		{
  
			Cipher dcipher = null;        
			KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(),SALT_BYTES, ITERATION_COUNT);    
			SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);    
   
			dcipher = Cipher.getInstance(key.getAlgorithm());         
			AlgorithmParameterSpec paramSpec = new PBEParameterSpec(SALT_BYTES,ITERATION_COUNT);         
			dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);       
			byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);         
			byte[] utf8 = dcipher.doFinal(dec);         
			return new String(utf8, "UTF8");   	
		}
		catch (Exception e) 
		{
			throw new Throwable(e);
		}
	}
	
	/**
	 * Desencriptar.
	 *
	 * @param str the str
	 * @return the string
	 * @throws Throwable the throwable
	 */
	public static String desencriptar(String str) throws Throwable
	{
		return desencriptar(CLAVE,str);
	}
	
	public static void main(String [] args)throws Throwable
	{
		System.out.println(desencriptar("-1d8a60dfc66d69ee1e9ccb41f7f6d9b207a6df87216de44"));
		
		
	}
	
}
