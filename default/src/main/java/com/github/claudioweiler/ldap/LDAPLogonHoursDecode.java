package com.github.claudioweiler.ldap;

import java.text.DateFormatSymbols;
import java.util.ArrayList;

public class LDAPLogonHoursDecode {

	public static void main(final String[] args) {
		final LDAPLogonHoursDecode ldapLogonHoursDecode = new LDAPLogonHoursDecode();

		final byte[] raw = new byte[] {(byte) 0x08, (byte) 0, (byte) 0, (byte) 0, (byte) 0xc0, (byte) 0xff, (byte) 0x07, (byte) 0xc0, (byte) 0xff, (byte) 0x07, (byte) 0xc0, (byte) 0xff, (byte) 0x07, (byte) 0xc0, (byte) 0xff, (byte) 0x07, (byte) 0xc0, (byte) 0xff, (byte) 0x07, (byte) 0, (byte) 0};

		String[] decodeLogonHours = ldapLogonHoursDecode.decodeLogonHours(raw);
		for(final String s : decodeLogonHours) {
			System.out.println(s);
		}

		System.out.println("-------------------------------");
		shiftBitsLeft(raw, 3);

		decodeLogonHours = ldapLogonHoursDecode.decodeLogonHours(raw);
		for(final String s : decodeLogonHours) {
			System.out.println(s);
		}


	}

	private String[] decodeLogonHours(final byte[] raw) {

		final DateFormatSymbols dateFormatSymbols = DateFormatSymbols.getInstance();
		//                                     0   0   0   0 192 255   7 192 255   7 192 255   7 192 255   7 192 255   7   0 0
		//final BigInteger bi = new BigInteger("00  00  00  00  c0  ff  07  c0  ff  07  c0  ff  07  c0  ff  07  c0  ff  07  00  ff".replaceAll("  ", ""), 16);

		//final byte[] raw = attr.getBytes();
		//final byte[] raw = bi.toByteArray();
		//final byte[] raw = new byte[] {(byte) 0x08, (byte) 0, (byte) 0, (byte) 0, (byte) 0xc0, (byte) 0xff, (byte) 0x07, (byte) 0xc0, (byte) 0xff, (byte) 0x07, (byte) 0xc0, (byte) 0xff, (byte) 0x07, (byte) 0xc0, (byte) 0xff, (byte) 0x07, (byte) 0xc0, (byte) 0xff, (byte) 0x07, (byte) 0, (byte) 0};
		//final String[] days = new String[] { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
		final String[] days = dateFormatSymbols.getShortWeekdays();
		final ArrayList<String> ret = new ArrayList<String>();

		for(int day = 0; day < 7; day++) {
			byte[] vBits;
			/*
			if(day == 6) {
				vBits = new byte[] { raw[19], raw[20], raw[0] };
			} else {
				vBits = new byte[] { raw[day * 3 + 1], raw[day * 3 + 2], raw[day * 3 + 3] };
			}
			 */
			vBits = new byte[] { raw[day * 3 + 0], raw[day * 3 + 1], raw[day * 3 + 2] };

			final StringBuilder sb = new StringBuilder();
			sb.append(String.format("%s:", days[day+1]));
			for(int b = 0; b < 3; b++) {
				sb.append(" ");
				sb.append(decodeLogonBits(vBits[b]));
			}
			ret.add(sb.toString());
		}

		final String[] r = new String[ret.size()];
		ret.toArray(r);
		return r;
	}

	private String decodeLogonBits(final byte b) {
		final StringBuilder sb = new StringBuilder();
		sb.append((b & 0x01) > 0 ? "1" : "0");
		sb.append((b & 0x02) > 0 ? "1" : "0");
		sb.append((b & 0x04) > 0 ? "1" : "0");
		sb.append((b & 0x08) > 0 ? "1" : "0");
		sb.append((b & 0x10) > 0 ? "1" : "0");
		sb.append((b & 0x20) > 0 ? "1" : "0");
		sb.append((b & 0x40) > 0 ? "1" : "0");
		sb.append((b & 0x80) > 0 ? "1" : "0");
		return sb.toString();
	}

	private static void shiftBitsRight(final byte[] bytes, final int rightShifts) {
		assert rightShifts >= 1 && rightShifts <= 7;

		final int leftShifts = 8 - rightShifts;

		for(int j = 0; j < bytes.length; j++) {
			bytes[j] = bitReverseTable[bytes[j]&0xff];
		}

		int i = 0;

		final byte firstByte = bytes[i]; // keep the byte before modification
		byte previousByte = bytes[i]; // keep the byte before modification
		bytes[i] = (byte) ( (byte)((bytes[i]>>>rightShifts)&0xff) | (byte)((bytes[bytes.length - 1]<<leftShifts)&0xff));
		for (i++; i < bytes.length -1; i++) {
			final byte tmp = bytes[i];
			bytes[i] = (byte) ( (byte)((bytes[i] >>> rightShifts)&0xff) | (byte)((previousByte<<leftShifts)&0xff));
			previousByte = tmp;
		}
		bytes[bytes.length-1] = (byte) ( (byte)((bytes[bytes.length-1]>>>rightShifts)&0xff) | (byte)((firstByte<<leftShifts)&0xff));

		for(int j = 0; j < bytes.length; j++) {
			bytes[j] = bitReverseTable[bytes[j]&0xff];
		}

	}
	private static void shiftBitsLeft(final byte[] bytes, final int leftShifts) {
		assert leftShifts >= 1 && leftShifts <= 7;

		final int rightShifts = 8 - leftShifts;

		for(int j = 0; j < bytes.length; j++) {
			bytes[j] = bitReverseTable[bytes[j]&0xff];
		}

		int i = bytes.length-1;

		final byte lastByte = bytes[i]; // keep the byte before modification
		byte previousByte = bytes[i]; // keep the byte before modification
		bytes[i] = (byte) ( (byte)((bytes[i]<<leftShifts)&0xff) | (byte)((bytes[0]>>>rightShifts)&0xff));
		for (i--; i > 0; i--) {
			final byte tmp = bytes[i];
			bytes[i] = (byte) ( (byte)((bytes[i] << leftShifts)&0xff) | (byte)((previousByte>>>rightShifts)&0xff));
			previousByte = tmp;
		}
		bytes[0] = (byte) ( (byte)((bytes[0]<<leftShifts)&0xff) | (byte)((lastByte>>>rightShifts)&0xff));

		for(int j = 0; j < bytes.length; j++) {
			bytes[j] = bitReverseTable[bytes[j]&0xff];
		}

	}

	static byte bitReverseTable[] = {
		(byte) 0x00, (byte) 0x80, (byte) 0x40, (byte) 0xc0, (byte) 0x20, (byte) 0xa0, (byte) 0x60, (byte) 0xe0,
		(byte) 0x10, (byte) 0x90, (byte) 0x50, (byte) 0xd0, (byte) 0x30, (byte) 0xb0, (byte) 0x70, (byte) 0xf0,
		(byte) 0x08, (byte) 0x88, (byte) 0x48, (byte) 0xc8, (byte) 0x28, (byte) 0xa8, (byte) 0x68, (byte) 0xe8,
		(byte) 0x18, (byte) 0x98, (byte) 0x58, (byte) 0xd8, (byte) 0x38, (byte) 0xb8, (byte) 0x78, (byte) 0xf8,
		(byte) 0x04, (byte) 0x84, (byte) 0x44, (byte) 0xc4, (byte) 0x24, (byte) 0xa4, (byte) 0x64, (byte) 0xe4,
		(byte) 0x14, (byte) 0x94, (byte) 0x54, (byte) 0xd4, (byte) 0x34, (byte) 0xb4, (byte) 0x74, (byte) 0xf4,
		(byte) 0x0c, (byte) 0x8c, (byte) 0x4c, (byte) 0xcc, (byte) 0x2c, (byte) 0xac, (byte) 0x6c, (byte) 0xec,
		(byte) 0x1c, (byte) 0x9c, (byte) 0x5c, (byte) 0xdc, (byte) 0x3c, (byte) 0xbc, (byte) 0x7c, (byte) 0xfc,
		(byte) 0x02, (byte) 0x82, (byte) 0x42, (byte) 0xc2, (byte) 0x22, (byte) 0xa2, (byte) 0x62, (byte) 0xe2,
		(byte) 0x12, (byte) 0x92, (byte) 0x52, (byte) 0xd2, (byte) 0x32, (byte) 0xb2, (byte) 0x72, (byte) 0xf2,
		(byte) 0x0a, (byte) 0x8a, (byte) 0x4a, (byte) 0xca, (byte) 0x2a, (byte) 0xaa, (byte) 0x6a, (byte) 0xea,
		(byte) 0x1a, (byte) 0x9a, (byte) 0x5a, (byte) 0xda, (byte) 0x3a, (byte) 0xba, (byte) 0x7a, (byte) 0xfa,
		(byte) 0x06, (byte) 0x86, (byte) 0x46, (byte) 0xc6, (byte) 0x26, (byte) 0xa6, (byte) 0x66, (byte) 0xe6,
		(byte) 0x16, (byte) 0x96, (byte) 0x56, (byte) 0xd6, (byte) 0x36, (byte) 0xb6, (byte) 0x76, (byte) 0xf6,
		(byte) 0x0e, (byte) 0x8e, (byte) 0x4e, (byte) 0xce, (byte) 0x2e, (byte) 0xae, (byte) 0x6e, (byte) 0xee,
		(byte) 0x1e, (byte) 0x9e, (byte) 0x5e, (byte) 0xde, (byte) 0x3e, (byte) 0xbe, (byte) 0x7e, (byte) 0xfe,
		(byte) 0x01, (byte) 0x81, (byte) 0x41, (byte) 0xc1, (byte) 0x21, (byte) 0xa1, (byte) 0x61, (byte) 0xe1,
		(byte) 0x11, (byte) 0x91, (byte) 0x51, (byte) 0xd1, (byte) 0x31, (byte) 0xb1, (byte) 0x71, (byte) 0xf1,
		(byte) 0x09, (byte) 0x89, (byte) 0x49, (byte) 0xc9, (byte) 0x29, (byte) 0xa9, (byte) 0x69, (byte) 0xe9,
		(byte) 0x19, (byte) 0x99, (byte) 0x59, (byte) 0xd9, (byte) 0x39, (byte) 0xb9, (byte) 0x79, (byte) 0xf9,
		(byte) 0x05, (byte) 0x85, (byte) 0x45, (byte) 0xc5, (byte) 0x25, (byte) 0xa5, (byte) 0x65, (byte) 0xe5,
		(byte) 0x15, (byte) 0x95, (byte) 0x55, (byte) 0xd5, (byte) 0x35, (byte) 0xb5, (byte) 0x75, (byte) 0xf5,
		(byte) 0x0d, (byte) 0x8d, (byte) 0x4d, (byte) 0xcd, (byte) 0x2d, (byte) 0xad, (byte) 0x6d, (byte) 0xed,
		(byte) 0x1d, (byte) 0x9d, (byte) 0x5d, (byte) 0xdd, (byte) 0x3d, (byte) 0xbd, (byte) 0x7d, (byte) 0xfd,
		(byte) 0x03, (byte) 0x83, (byte) 0x43, (byte) 0xc3, (byte) 0x23, (byte) 0xa3, (byte) 0x63, (byte) 0xe3,
		(byte) 0x13, (byte) 0x93, (byte) 0x53, (byte) 0xd3, (byte) 0x33, (byte) 0xb3, (byte) 0x73, (byte) 0xf3,
		(byte) 0x0b, (byte) 0x8b, (byte) 0x4b, (byte) 0xcb, (byte) 0x2b, (byte) 0xab, (byte) 0x6b, (byte) 0xeb,
		(byte) 0x1b, (byte) 0x9b, (byte) 0x5b, (byte) 0xdb, (byte) 0x3b, (byte) 0xbb, (byte) 0x7b, (byte) 0xfb,
		(byte) 0x07, (byte) 0x87, (byte) 0x47, (byte) 0xc7, (byte) 0x27, (byte) 0xa7, (byte) 0x67, (byte) 0xe7,
		(byte) 0x17, (byte) 0x97, (byte) 0x57, (byte) 0xd7, (byte) 0x37, (byte) 0xb7, (byte) 0x77, (byte) 0xf7,
		(byte) 0x0f, (byte) 0x8f, (byte) 0x4f, (byte) 0xcf, (byte) 0x2f, (byte) 0xaf, (byte) 0x6f, (byte) 0xef,
		(byte) 0x1f, (byte) 0x9f, (byte) 0x5f, (byte) 0xdf, (byte) 0x3f, (byte) 0xbf, (byte) 0x7f, (byte) 0xff
	};
}
