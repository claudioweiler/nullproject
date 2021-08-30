package com.github.claudioweiler.errout;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class SysErrOutRedirect {

	public static void main(final String[] args) {

		try (var efos = new FileOutputStream("errors.txt", true)) {
			final var ps = new PrintStream(efos);
			// aqui ocorre o redirecionamento
			System.setErr(ps);
		} catch (final IOException e) {
			// tratamento do erro
		}

		try (var ofos = new FileOutputStream("out.txt", true)) {
			final var ps = new PrintStream(ofos);
			System.setOut(ps);
		} catch (final IOException e) {
			// tratamento do erro
		}

		System.err.println("Enviando mensagem para um arquivo.");
		System.out.println("Enviando mensagem para outro arquivo.");

		try {
			throw new Exception("");
		} catch (final Exception e) {
			// e.printStackTrace() também é enviado para System.err
			e.printStackTrace();
		}

	}
}
