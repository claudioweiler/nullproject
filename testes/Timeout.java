package testes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Timeout {
	
	static ExecutorService tpes = Executors.newSingleThreadExecutor();

	public static void main(String[] args) {
		Future<String> handle = tpes.submit(new ClasseDeConexao());
		
		try {
			System.out.println("Iniciando chamada com timeout.");
			String resposta = handle.get(10, TimeUnit.SECONDS);
			System.out.println("O m�todo retornou: " + resposta);
		} catch(InterruptedException e) {
			System.out.println("A thread principal foi interrompida.");
		} catch(ExecutionException e) {
			System.out.println("A execu��o do m�todo lan�ou uma exce��o.");
		} catch(TimeoutException e) {
			System.out.println("O m�todo estourou o tempo limite (timeout).");
		}
		
		System.out.println("Encerrando aplicativo.");
		System.exit(0);
	}
	
	// Esta classe apenas faz a conex�o entre o nosso c�digo e o c�digo n�o controlado
	static class ClasseDeConexao implements Callable<String> {

		@Override
		public String call() throws Exception {
			return new ExecucaoNaoControlada().executar();
		}
	}

	// Esta classe � apenas um exemplo de c�digo n�o controlado, seria a nossa API externa.
	static class ExecucaoNaoControlada {

		// O momento de retorno deste m�todo n�o � conhecido, pode ocorrer em qualquer momento.
		public String executar() throws Exception {
			System.out.println("# Informe seu nome:");
			return new BufferedReader(new InputStreamReader(System.in)).readLine();
		}
		
	}
}
