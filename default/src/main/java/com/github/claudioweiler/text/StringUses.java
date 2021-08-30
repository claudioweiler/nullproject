package com.github.claudioweiler.text;


public class StringUses {

	static int limite = 200000;
	static int repeticoes = 50;
	static long start = 0;
	static String texto = "String string stringstring string string stringstring string string stringstringstringstring string!\n";
	static int numero = 1234567890;
	
	public static void main(String[] args) {
		//teste1();
		teste2();
		teste3();
		teste4();
	}
	
	private static void teste1() {
		
		int tempo1 = 0;
		int tempo2 = 0;
		String resultado = "";
		
		for(int tirateima = 0; tirateima < repeticoes; tirateima++) {
			start = System.currentTimeMillis();
			String teste1 = "";
			for(int i = 0; i < Math.min(limite,1000); i++) {
				teste1 += texto;
				teste1 += numero;
			}
			tempo1 += System.currentTimeMillis() - start;;
			resultado = teste1;
			tempo2 += System.currentTimeMillis() - start;;
		}
		
		System.out.println(String.format("Teste %s levou %s milissegundos para concatenar, e %s milissegundos para construir uma string de %s caracteres", 1, tempo1/repeticoes, tempo2/repeticoes, resultado.length()));
	}

	private static void teste2() {
		
		int tempo1 = 0;
		int tempo2 = 0;
		String resultado = "";
		
		for(int tirateima = 0; tirateima < repeticoes; tirateima++) {
			start = System.currentTimeMillis();
			StringBuffer teste2 = new StringBuffer();
			for(int i = 0; i < limite; i++) {
				teste2.append(texto);
				teste2.append(numero);
			}
			tempo1 += System.currentTimeMillis() - start;;
			resultado = teste2.toString();
			tempo2 += System.currentTimeMillis() - start;;
		}
		
		System.out.println(String.format("Teste %s levou %s milissegundos para concatenar, e %s milissegundos para construir uma string de %s caracteres", 2, tempo1/repeticoes, tempo2/repeticoes, resultado.length()));
	}

	private static void teste3() {
		
		int tempo1 = 0;
		int tempo2 = 0;
		String resultado = "";
		
		for(int tirateima = 0; tirateima < repeticoes; tirateima++) {
			start = System.currentTimeMillis();
			StringBuilder teste3 = new StringBuilder();
			for(int i = 0; i < limite; i++) {
				teste3.append(texto);
				teste3.append(numero);
			}
			tempo1 += System.currentTimeMillis() - start;;
			resultado = teste3.toString();
			tempo2 += System.currentTimeMillis() - start;;
		}
		
		System.out.println(String.format("Teste %s levou %s milissegundos para concatenar, e %s milissegundos para construir uma string de %s caracteres", 3, tempo1/repeticoes, tempo2/repeticoes, resultado.length()));
	}

	private static void teste4() {
		
		int tempo1 = 0;
		int tempo2 = 0;
		String resultado = "";
		
		for(int tirateima = 0; tirateima < repeticoes; tirateima++) {
			start = System.currentTimeMillis();
			StringBuilder teste4 = new StringBuilder();
			for(int i = 0; i < limite; i++) {
				teste4.append(texto + numero);
			}
			tempo1 += System.currentTimeMillis() - start;;
			resultado = teste4.toString();
			tempo2 += System.currentTimeMillis() - start;;
		}
		
		System.out.println(String.format("Teste %s levou %s milissegundos para concatenar, e %s milissegundos para construir uma string de %s caracteres", 4, tempo1/repeticoes, tempo2/repeticoes, resultado.length()));
	}
}
