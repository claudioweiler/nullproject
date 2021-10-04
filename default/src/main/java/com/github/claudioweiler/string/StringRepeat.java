package com.github.claudioweiler.string;
import java.util.Collections;

public class StringRepeat {
	public static void main(String[] args) {
		int n = 5;
		String parameter = "\"?\"";

		System.out.println(String.join(",", Collections.nCopies(n, parameter)));
	}
}
