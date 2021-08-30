package com.github.claudioweiler.number;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class Arredondamentos {

	public static void main(final String[] args) {
		System.out.println("|   Valor    |   floor    |    rint    |   round    | -round(-d) |    ceil    |");
		for(int i = -45; i <= 45; i += 10) {
			for(int j = -1; j <= 1; j++) {
				final double d = (i + j) / 10.0;
				System.out.println(String.format("| %10f | %10f | %10f | %10d | %10d | %10f |", d, Math.floor(d), Math.rint(d), Math.round(d), -Math.round(-d), Math.ceil(d)));
			}
		}

		System.out.println("\n");

		System.out.println(new BigDecimal(new BigInteger("1"), 0).toPlainString());
		System.out.println(new BigDecimal(new BigInteger("1"), -1).toPlainString());
		System.out.println(new BigDecimal(new BigInteger("1"), 1).toPlainString());
		System.out.println(new BigDecimal(new BigInteger("10"), 0).toPlainString());
		System.out.println(new BigDecimal(new BigInteger("10"), -1).toPlainString());
		System.out.println(new BigDecimal(new BigInteger("10"), 1).toPlainString());
		System.out.println(new BigDecimal(new BigInteger("10"), 1).setScale(2).toPlainString());

		System.out.println(new BigDecimal(1).add(new BigDecimal(new BigInteger("1002"),2)).toPlainString());

		System.out.println("|   Valor    |   DOWN     |   FLOOR    |  HALF_EVEN |   HALF_UP  |  HALF_DOWN |  CEILING   |     UP     |");
		for(int i = -45; i <= 45; i += 10) {
			for(int j = -1; j <= 1; j++) {
				final BigDecimal bigDec = BigDecimal.valueOf(i + j).movePointLeft(3).setScale(4);
				System.out.println(String.format("| %10s | %10s | %10s | %10s | %10s | %10s | %10s | %10s |",
						bigDec, bigDec.setScale(2, RoundingMode.DOWN), bigDec.setScale(2, RoundingMode.FLOOR), bigDec.setScale(2, RoundingMode.HALF_EVEN), bigDec.setScale(2, RoundingMode.HALF_UP), bigDec.setScale(2, RoundingMode.HALF_DOWN), bigDec.setScale(2, RoundingMode.CEILING), bigDec.setScale(2, RoundingMode.UP)));
			}
		}
	}

}
