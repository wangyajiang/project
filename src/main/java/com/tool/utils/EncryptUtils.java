package com.tool.utils;


import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

@SuppressWarnings("restriction")
public class EncryptUtils {
	
	private static final String Algorithm = "DESede"; //定义 加密算法,可用 DES,DESede,Blowfish      
	
	/**
	 * 用预转换密钥算加密
	 * @param str
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String str, final String key) throws Exception {
	   	return EncryptUtils.byte2Base64(EncryptUtils.encryptMode(EncryptUtils.GetKeyBytes(key), str.getBytes()));
	}
	
	public static String decrypt(String encrypt, final String key) throws Exception {
	   	return new String(EncryptUtils.decryptMode(EncryptUtils.GetKeyBytes(key), Base64.decode(encrypt)));
	}
	 
	/**
	 * 3des解码  
	 * @param value:待解密字符串
	 * @param key:原始密钥字符串
	 */
	public static String decrypt3DES(String encrypt, String key) throws Exception {  
		byte[] b = decryptMode(GetKeyBytes(key), Base64.decode(encrypt));  
        return new String(b);  
  
    }
	
	/**
	 * 3des加密  
	 * @param value:待加密字符串
	 * @param key:原始密钥字符串
	 * @return
	 * @throws Exception
	 */
	public static String encrypt3DES(String value, String key) throws Exception {  
        String str = byte2Base64(encryptMode(GetKeyBytes(key), value.getBytes()));  
        return str;  
  
    }
  
	/**
	 * 计算24位长的密码byte值,首先对原始密钥做MD5算hash值，再用前8位数据对应补全后8位  
	 * @param strKey
	 * @return
	 * @throws Exception
	 */
    public static byte[] GetKeyBytes(String strKey) throws Exception {  
        if (null == strKey || strKey.length() < 1)  
            throw new Exception("key is null or empty!");  
        java.security.MessageDigest alg = java.security.MessageDigest.getInstance("MD5");  
        alg.update(strKey.getBytes());  
        byte[] bkey = alg.digest();  
  
        int start = bkey.length;  
        byte[] bkey24 = new byte[24];  
        for (int i = 0; i < start; i++) {  
            bkey24[i] = bkey[i];  
        }  
  
        for (int i = start; i < 24; i++) {
            bkey24[i] = bkey[i - start];  
        }  
        return bkey24;  
  
    }  
  
    /**
     * @param keybyte:为加密密钥，长度为24字节  
     * @param src:为被加密的数据缓冲区（源）    
     * @return
     */
    public static byte[] encryptMode(byte[] keybyte, byte[] src) {  
        try {  
            //生成密钥  
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm); //加密   
            Cipher c1 = Cipher.getInstance(Algorithm);  
            c1.init(Cipher.ENCRYPT_MODE, deskey);  
            return c1.doFinal(src);  
        } catch (java.security.NoSuchAlgorithmException e1) {  
            e1.printStackTrace();  
        } catch (javax.crypto.NoSuchPaddingException e2) {  
            e2.printStackTrace();  
        } catch (java.lang.Exception e3) {  
            e3.printStackTrace();  
        }  
        return null;  
  
    }  
  
    /**
     * @param keybyte:为加密密钥，长度为24字节   
     * @param src:为加密后的缓冲区  
     * @return
     */
    public static byte[] decryptMode(byte[] keybyte, byte[] src) {  
        try { //生成密钥     
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);  
            //解密       
            Cipher c1 = Cipher.getInstance(Algorithm);  
            c1.init(Cipher.DECRYPT_MODE, deskey);  
            return c1.doFinal(src);  
        } catch (java.security.NoSuchAlgorithmException e1) {  
            e1.printStackTrace();  
        } catch (javax.crypto.NoSuchPaddingException e2) {  
            e2.printStackTrace();  
        } catch (java.lang.Exception e3) {  
            e3.printStackTrace();  
        }  
        return null;  
  
    }  
  
    /**
     * 转换成base64编码 
     * @param b
     * @return
     */
    public static String byte2Base64(byte[] b) {  
        return Base64.encode(b);  
    }  
  
    /**
     * 转换成十六进制字符串    
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) {  
        String hs = "";  
        String stmp = "";  
        for (int n = 0; n < b.length; n++) {  
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));  
            if (stmp.length() == 1)  
                hs = hs + "0" + stmp;  
            else  
                hs = hs + stmp;  
            if (n < b.length - 1)  
                hs = hs + ":";  
        }  
        return hs.toUpperCase();  
  
    }
    
    public static String md5(String str) {
    	char  hexDigits[] = {'0' , '1', '2' , '3' , '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    	try {
    		byte  [] strTemp = str.getBytes();  
		    MessageDigest mdTemp = MessageDigest.getInstance("MD5" );  
		    mdTemp.update(strTemp);  
		    byte [] md = mdTemp.digest();  
		    int j = md.length;  
		    char s[] =  new   char [j *  2 ];  
		    int  k =  0 ;  
		    for  (int  i =  0 ; i < j; i++) {  
			    byte  byte0 = md[i];  
			    s[k++] = hexDigits[byte0 >>> 4  &  0xf ];  
			    s[k++] = hexDigits[byte0 & 0xf ];
		    }
		    return new  String(s).toUpperCase();  
		} catch (Exception e) {
			return null;
		}
    }
    
	public static void main(String[] args) {
		String key = "private_sec";
		String password = "我";
		System.out.println("key=" + key + ",password=" + password);
		try {
			String encrypt = ""; 
            encrypt = EncryptUtils.encrypt(password, key);
            System.out.println();
//          System.out.println("加密后base64表示：" + EncryptUtils.byte2hex(Base64.decode(encrypt)));  
            System.out.println("用预转换密钥算加密结果：" + encrypt + "\t长度：" + encrypt.length());
            System.out.println("用预转换密钥算解密结果：" + EncryptUtils.decrypt(encrypt, key));
            System.out.println("-------------------------------------------------------------");
            System.out.println("调用原始密钥算加密结果：" + EncryptUtils.encrypt3DES(password, key));  
            System.out.println("调用原始密钥算解密结果：" + EncryptUtils.decrypt3DES(encrypt, key));
            System.out.println("-------------------------------------------------------------");
            System.out.println("md5算法：" + EncryptUtils.md5(password));
            System.out.println();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
