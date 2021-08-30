package com.github.claudioweiler.random;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

public class NumerosAleatorios {

	public static void main(String[] args) {
		System.currentTimeMillis();
		Random rng;
		byte[] array = ByteBuffer.allocate(16).putLong(System.currentTimeMillis()).putLong(Runtime.getRuntime().freeMemory()).array();
		System.out.println(Arrays.toString(array));

		for(int i = 0; i < 50; i++) {
			System.out.print(Math.random() + " ");
		}
		System.out.print("\n");
		// Gerando números inteiros de 15 até 24
		for(int i = 0; i < 50; i++) {
			int n = (int)(Math.random() * 10) + 15;
			System.out.print(n + " ");
		}

		System.out.print("\n\n");
		rng = new Random(1234567890);
		for(int i = 0; i < 50; i++) {
			System.out.print(rng.nextInt(20) + " ");
		}

		System.out.print("\n");
		rng.setSeed(1234567890);
		for(int i = 0; i < 50; i++) {
			System.out.print(rng.nextInt(20) + " ");
		}

		System.out.print("\n\n");
		rng = new Random(Runtime.getRuntime().freeMemory());
		for(int i = 0; i < 50; i++) {
			System.out.print(rng.nextInt(20) + " ");
		}

		byte[] b = new byte[100000];
		Arrays.fill(b, (byte)255);

		System.out.print("\n");
		rng.setSeed(Runtime.getRuntime().freeMemory());
		for(int i = 0; i < 50; i++) {
			System.out.print(rng.nextInt(20) + " ");
		}

		System.out.print("\n\n");
		SecureRandom srng = new SecureRandom(new byte[] {0,1,2,3,4,5,6,7,8,9});
		for(int i = 0; i < 20; i++) {
			byte[] a = new byte[1];
			srng.nextBytes(a);
			System.out.print((short)a[0] + " ");
		}
		System.out.print("\n");
		srng = new SecureRandom(new byte[] {0,1,2,3,4,5,6,7,8,9});
		for(int i = 0; i < 20; i++) {
			byte[] a = new byte[1];
			srng.nextBytes(a);
			System.out.print((short)a[0] + " ");
		}

	}
}
