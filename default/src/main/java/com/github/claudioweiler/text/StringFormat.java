package com.github.claudioweiler.text;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Formatter;

public class StringFormat {

	public static void main(String[] args) {

		int int1 = 1;
		int int2 = 2000;
		float float1 = 1.0f;
		float float2 = 2000.02f;
		String string1 = "string1";
		String string2 = null;
		Date data = new Date();
		Object todosArgumentos[] = new Object[]{int1, int2, float1, float2, string1, string2, data};
		Object numeros[] = new Object[]{int1, int2, float1, float2};

		System.out.println(String.format("|%s|%s|%s|%s|%s|%s|%s|", int1, int2, float1, float2, string1, string2, data));
		System.out.println(new Formatter().format("|%d|%d|%f|%f|%s|%s|%tc|", todosArgumentos));
		System.out.printf("|%d|%d|%f|%f|%S|%S|%Tc|%n", todosArgumentos);

		System.out.println("");

		System.out.printf("|%10d|%10d|%10f|%20f|%10s|%10s|%30tc|%n", todosArgumentos);
		System.out.format("|%-10d|%-10d|%-10f|%-20f|%-10s|%-10s|%-30tc|\n", todosArgumentos);
		System.out.printf("|%010d|%010d|%010f|%020f|%10s|%10s|%30tT|\n", todosArgumentos);
		System.out.format("|%010d|%010d|%010.2f|%020.4f|%10s|%10s|%30tD|\n", todosArgumentos);
		System.out.printf("|%010d|%,010d|%010.2f|%,020.4f|%10s|%10s|%30tF|\n", todosArgumentos);
		System.out.format("|%10d|%,10d|%10.2f|%,20.4f|%10s|%10s|%30tr|\n", todosArgumentos);

		System.out.println("");

		System.out.printf("Data: %td de %<tB de %<tY\n", data);
		System.out.format("Data: %1$td de %1$tB de %1$tY\n", data);

		System.out.println("");

		System.out.printf("string1(%s=%<S), int2(%d=%<X), float2(%f), string1(%1$s)\n", string1, int2, float2);
		System.out.printf("string1(%s=%1$S), int2(%d=%2$X), float2(%f), string1(%1$s)\n", string1, int2, float2);

		System.out.println("");

		System.err.println(MessageFormat.format("|{0}|{1}|{2}|{3}|{4}|{5}|{6}|", todosArgumentos));
		System.err.println(MessageFormat.format("|{0,number}|{1,number,integer}|{2,number,percent}|{3,number,currency}|{4}|{5}|{6,date}|{6,time}|", todosArgumentos));

		System.err.println("");

		System.err.println(MessageFormat.format("|{0,number,000000}|{1,number,######}|{2,number,00,000.0}|{3,number,#,###.####}|", numeros));

		System.err.println("");

		MessageFormat mf = new MessageFormat("{0,choice,0#ninguém|1#uma pessoa|1<{0,number,00} pessoas} aguardando atendimento na sala de emergência");
		for(int i = 10; i >= 0; i--) {
			System.err.println(mf.format(new Object[]{i}));
		}

		System.out.println(String.format("%-8s%,030.2f", "valor", 5.0));

		NumberFormat formatadorPercentual = NumberFormat.getPercentInstance();
		formatadorPercentual.setMinimumFractionDigits(2);
		formatadorPercentual.setMaximumFractionDigits(2);

		BigDecimal valueOf = new BigDecimal(999999999).divide(new BigDecimal(1000000000), 4, RoundingMode.DOWN);
		System.out.println(formatadorPercentual.format(valueOf));

		valueOf = BigDecimal.valueOf(99.99999f).movePointLeft(2).setScale(4, RoundingMode.DOWN);
		System.out.println(formatadorPercentual.format(valueOf));
	}

}
