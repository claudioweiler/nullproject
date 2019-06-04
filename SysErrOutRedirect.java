import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;


public class SysErrOutRedirect {
	public static void main(final String[] args) {
		final HashSet<String> set = new HashSet<String>();
		final HashMap<String,String> map = new HashMap<String, String>();


		FileOutputStream efos = null;
		try {
			efos = new FileOutputStream("errors.txt", true);
			final PrintStream ps = new PrintStream(efos);
			// aqui ocorre o redirecionamento
			System.setErr(ps);
		} catch(final FileNotFoundException e) {
			// tratamento do erro
		}

		FileOutputStream ofos = null;
		try {
			ofos = new FileOutputStream("out.txt", true);
			final PrintStream ps = new PrintStream(ofos);
			System.setOut(ps);
		} catch(final FileNotFoundException e) {
			// tratamento do erro
		}

		System.err.println("Enviando mensagem para um arquivo.");
		System.out.println("Enviando mensagem para outro arquivo.");

		try {
			throw new Exception("");
		} catch(final Exception e) {
			// e.printStackTrace() também é enviado para System.err
			e.printStackTrace();
		}

		// fechando streams
		try {
			if(efos != null) {
				efos.close();
			}
			if(ofos != null) {
				ofos.close();
			}
		} catch(final IOException e) {}
	}
}
