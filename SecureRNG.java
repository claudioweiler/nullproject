import java.nio.ByteBuffer;
import java.security.SecureRandom;


public class SecureRNG {

	private static SecureRandom sRNG = new SecureRandom();
	private static long lastTimeSeeded = 0;
	private static final long SEED_TIMEOUT = 10000; // reseed após 10 segundos

	// A redefinição da semente será feita na primeira chamada após o timeout definido.
	// O momento exato é de difícil previsão, vai depender do código e, normalmente, de uma ação do usuário.
	private synchronized static void reseed() {
		lastTimeSeeded = System.currentTimeMillis();
		// nanoTime é de difícil previsão pois trabalha com aproximação
		final long nanoTime = System.nanoTime();
		// memória livre para a JRE é de difícil previsão pois depende do ambiente e varia de momento para momento
		final long freeMemory = Runtime.getRuntime().freeMemory();

		// monta um array de 16 bytes com os valores anteriores
		final byte[] seed = ByteBuffer.allocate(16).putLong(nanoTime).putLong(freeMemory).array();

		// a redefinição da semente na classe SecureRandom trabalha de forma adicional e não por substituição
		sRNG.setSeed(seed);
	}

	public synchronized static int getInt(final int n) {
		if(System.currentTimeMillis() > (lastTimeSeeded + SEED_TIMEOUT)) {
			reseed();
		}

		return sRNG.nextInt(n);
	}

}
