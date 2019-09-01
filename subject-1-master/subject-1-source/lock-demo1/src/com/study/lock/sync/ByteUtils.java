package com.study.lock.sync;

import java.nio.ByteBuffer;

public class ByteUtils {
	public static String bytesToHexString(byte[] bs) {
		if (bs == null || bs.length == 0) {
			return null;
		}

		StringBuffer sb = new StringBuffer();
		String tmp = null;
		for (byte b : bs) {
			tmp = Integer.toHexString(Byte.toUnsignedInt(b));
			if (tmp.length() < 2) {
				sb.append(0);
			}
			sb.append(tmp);
		}
		return sb.toString();
	}

	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("") || (hexString.length() % 2) != 0) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		// 下面的代码百度来的..
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/** long转8字节数组 */
	public static void long2bytes(long v, byte[] b, int off) {
		b[off + 7] = (byte) v;
		b[off + 6] = (byte) (v >>> 8);
		b[off + 5] = (byte) (v >>> 16);
		b[off + 4] = (byte) (v >>> 24);
		b[off + 3] = (byte) (v >>> 32);
		b[off + 2] = (byte) (v >>> 40);
		b[off + 1] = (byte) (v >>> 48);
		b[off + 0] = (byte) (v >>> 56);
	}

	/**
	 * long转8字节数组
	 */
	public static long bytes2long(byte[] b) {
		ByteBuffer buffer = ByteBuffer.allocate(8);
		buffer.put(b, 0, b.length);
		buffer.flip();// need flip
		return buffer.getLong();
	}

	/** int转4字节数组 */
	public static void int2bytes(int v, byte[] b, int off) {
		b[off + 3] = (byte) v;
		b[off + 2] = (byte) (v >>> 8);
		b[off + 1] = (byte) (v >>> 16);
		b[off + 0] = (byte) (v >>> 24);
	}

	/** int转4字节数组 */
	public static byte[] int2bytes(int v) {
		int off = 0;
		byte[] b = new byte[4];
		b[off + 3] = (byte) v;
		b[off + 2] = (byte) (v >>> 8);
		b[off + 1] = (byte) (v >>> 16);
		b[off + 0] = (byte) (v >>> 24);
		return b;
	}

	/** 数值转字节 */
	public static void num2bytes(int v, byte[] b, int off, int size) {
		int bits = 0;
		for (int i = size - 1; i >= 0; i--) {
			b[off + i] = (byte) (v >>> bits);
			bits = bits + 8;
		}
	}

	/**
	 * int转换为小端byte[]（高位放在高地址中）
	 * 
	 * @param iValue
	 * @return
	 */
	public static byte[] Int2Bytes_LE(int iValue) {
		byte[] rst = new byte[4];
		// 先写int的最后一个字节
		rst[0] = (byte) (iValue & 0xFF);
		// int 倒数第二个字节
		rst[1] = (byte) ((iValue & 0xFF00) >> 8);
		// int 倒数第三个字节
		rst[2] = (byte) ((iValue & 0xFF0000) >> 16);
		// int 第一个字节
		rst[3] = (byte) ((iValue & 0xFF000000) >> 24);
		return rst;
	}

	/**
	 * short转换为小端byte[]（高位放在高地址中）
	 * 
	 * @param iValue
	 * @return
	 */
	public static byte[] short2Bytes_LE(int iValue) {
		byte[] rst = new byte[2];
		// 先写int的最后一个字节
		rst[0] = (byte) (iValue & 0xFF);
		// int 倒数第二个字节
		rst[1] = (byte) ((iValue & 0xFF00) >> 8);
		return rst;
	}
	
	/**
	 * 转换byte数组为short（小端）
	 * 
	 * @return
	 * @note 数组长度至少为2，按小端方式转换,即传入的bytes是小端的，按这个规律组织成short
	 */
	public static short Bytes2Short_LE(byte[] bytes) {
		if (bytes.length < 2)
			return -1;
		int iRst = (bytes[0] & 0xFF);
		iRst |= (bytes[1] & 0xFF) << 8;

		return (short) iRst;
	}

	/**
	 * 转换byte数组为int（小端）
	 * 
	 * @return
	 * @note 数组长度至少为4，按小端方式转换,即传入的bytes是小端的，按这个规律组织成int
	 */
	public static int Bytes2Int_LE(byte[] bytes) {
		if (bytes.length < 4)
			return -1;
		int iRst = (bytes[0] & 0xFF);
		iRst |= (bytes[1] & 0xFF) << 8;
		iRst |= (bytes[2] & 0xFF) << 16;
		iRst |= (bytes[3] & 0xFF) << 24;

		return iRst;
	}

	/**
	 * 转换byte数组为int（大端）
	 * 
	 * @return
	 * @note 数组长度至少为4，按大端方式转换，即传入的bytes是大端的，按这个规律组织成int
	 */
	public static int Bytes2Int_BE(byte[] bytes) {
		if (bytes.length < 4)
			return -1;
		int mask = 0xff;
		int temp = 0;
		int n = 0;
		for (int i = 0; i < bytes.length; i++) {
			n <<= 8;
			temp = bytes[i] & mask;
			n |= temp;
		}
		return n;

	}

	/** 十六进制字符串转 小端序字节数组 */
	public static byte[] hexStringToBytes_LE(String hexString) {
		if (hexString == null || hexString.equals("") || (hexString.length() % 2) != 0) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		int hexCharsLen = hexChars.length;
		byte[] d = new byte[length];

		for (int i = 0; i < length; i++) {
			int pos = hexCharsLen - 1 - i * 2;

			d[i] = (byte) (charToByte(hexChars[pos - 1]) << 4 | charToByte(hexChars[pos]));
		}

		return d;
	}

	/** 小端序字节数组转大端序十六进制字符串 */
	public static String bytesToHexString_BE(byte[] bs) {
		if (bs == null || bs.length == 0) {
			return null;
		}

		StringBuffer sb = new StringBuffer();
		String tmp = null;
		int len = bs.length;
		for (int i = len - 1; i >= 0; i--) {
			tmp = Integer.toHexString(Byte.toUnsignedInt(bs[i]));
			if (tmp.length() < 2) {
				sb.append(0);
			}
			sb.append(tmp);
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		byte[] result = new byte[4];
		ByteUtils.num2bytes(257, result, 0, 2);
		// 00000101

		System.out.println(bytesToHexString(result));

		result = new byte[4];
		ByteUtils.int2bytes(257, result, 0);
		// 00000101

		System.out.println(bytesToHexString(result));

	}
}
