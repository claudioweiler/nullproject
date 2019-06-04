import java.nio.ByteBuffer;
import java.security.SecureRandom;


public class SecureRNG {

	private static SecureRandom sRNG = new SecureRandom();
	private static long lastTimeSeeded = 0;
	private static final long SEED_TIMEOUT = 10000; // reseed ap�s 10 segundos

	// A redefini��o da semente ser� feita na primeira chamada ap�s o timeout definido.
	// O momento exato � de dif�cil previs�o, vai depender do c�digo e, normalmente, de uma a��o do usu�rio.
	private synchronized static void reseed() {
		lastTimeSeeded = System.currentTimeMillis();
		// nanoTime � de dif�cil previs�o pois trabalha com aproxima��o
		final long nanoTime = System.nanoTime();
		// mem�ria livre para a JRE � de dif�cil previs�o pois depende do ambiente e varia de momento para momento
		final long freeMemory = Runtime.getRuntime().freeMemory();

		// monta um array de 16 bytes com os valores anteriores
		final byte[] seed = ByteBuffer.allocate(16).putLong(nanoTime).putLong(freeMemory).array();

		// a redefini��o da semente na classe SecureRandom trabalha de forma adicional e n�o por substitui��o
		sRNG.setSeed(seed);
	}

	public synchronized static int getInt(final int n) {
		if(System.currentTimeMillis() > (lastTimeSeeded + SEED_TIMEOUT)) {
			reseed();
		}

		return sRNG.nextInt(n);
	}

}
