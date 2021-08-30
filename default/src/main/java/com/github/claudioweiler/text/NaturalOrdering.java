package com.github.claudioweiler.text;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class NaturalOrdering {

	public static void main(String[] args) {
		String[] versoes = { "v1.1", "v1.2.1.1", "v1.2.1.2", "v1.2.2.1", "v1.2.1.11", "v1.2.2.0", "v1.11", "v2.0", "v11.0", "img1", "img 2", "img11", "img2", "1.1", "1.2.1", "1.11", "2.0", "11.0", "1_1", "1_2_1", "1_11", "2_0", "1", "01", "2A", "2a", "2b", "2á", "2B", "1a", "0001", "00012", null };

		ArrayList<String> aOrdenar = new ArrayList<String>();
		for(String s : versoes) {
			aOrdenar.add(s);
		}

		Collections.shuffle(aOrdenar);

		System.out.println("---------------------------- NaturalComparator (mine)");
		System.out.println(System.currentTimeMillis());
		for(int i = 0; i < 100000; i++) {
			Collections.sort(aOrdenar, new NaturalComparator());
		}
		System.out.println(System.currentTimeMillis());
		System.out.println(aOrdenar);

		Collections.shuffle(aOrdenar);

		System.out.println("---------------------------- NaturalOrderComparator");
		System.out.println(System.currentTimeMillis());
		for(int i = 0; i < 100000; i++) {
			Collections.sort(aOrdenar, new NaturalOrderComparator());
		}
		System.out.println(System.currentTimeMillis());
		System.out.println(aOrdenar);

		Collections.shuffle(aOrdenar);

		System.out.println("---------------------------- Strings.getNaturalComparator");
		System.out.println(System.currentTimeMillis());
		for(int i = 0; i < 100000; i++) {
			Collections.sort(aOrdenar, Strings.getNaturalComparator());
		}
		System.out.println(System.currentTimeMillis());
		System.out.println(aOrdenar);
		/*
		RuleBasedCollator collator;
		try {
			collator = new RuleBasedCollator(CollationRules.DEFAULTRULES);
			System.out.println("----------------------------");
			System.out.println(System.currentTimeMillis());
			//for(int i = 0; i < 100000; i++) {
				Collections.sort(aOrdenar, collator);
			//}
			System.out.println(System.currentTimeMillis());
			System.out.println(aOrdenar);
		} catch(ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 */


	}

}

class NaturalComparator implements Comparator<String> {

	private final boolean ignoreCase;
	private final Collator collator;

	public NaturalComparator() {
		this(false);
	}
	public NaturalComparator(boolean ignoreCase) {
		super();
		this.ignoreCase = ignoreCase;
		collator = Collator.getInstance();
		//collator.setStrength(this.ignoreCase ? Collator.SECONDARY : Collator.PRIMARY);
	}


	@Override
	public int compare(String o1, String o2) {
		int result = 0;

		// care nulls, nulls first
		if(o1 == null && o2 == null) {
			return 0;
		} else if(o1 == null) {
			return -1;
		} else if(o2 == null) {
			return 1;
		}

		// equal string, ignore all the rest
//		if(ignoreCase) {
//			result = o1.compareToIgnoreCase(o2);
//		} else {
//			result = o1.compareTo(o2);
//		}
//		if(result == 0) {
//			return result;
//		}
		// empty string is lower
		if(o1.isEmpty() && o2.isEmpty()) {
			return 0;
		} else if(o1.isEmpty()) {
			return -1;
		} else if(o2.isEmpty()) {
			return 1;
		}

		// from now we consider a 'part' a sequence of digits or non-digits

		// extract first part of o1
		int o1i = 0;
		boolean o1IsDigit = Character.isDigit(o1.charAt(o1i++));
		for(; o1i < o1.length(); o1i++) {
			if(Character.isDigit(o1.charAt(o1i)) != o1IsDigit) {
				break;
			}
		}
		String o1Part = o1.substring(0, o1i);

		// extract first part of o2
		int o2i = 0;
		boolean o2IsDigit = Character.isDigit(o2.charAt(o2i++));
		for(; o2i < o2.length(); o2i++) {
			if(Character.isDigit(o2.charAt(o2i)) != o2IsDigit) {
				break;
			}
		}
		String o2Part = o2.substring(0, o2i);

		// compare both parts
		/*
		result = collator.compare(o1Part, o2Part);
		CollationKey ck = collator.getCollationKey("oi");
		ck.toString();
		 */

		// if both parts are number, then numeric test
		if(o1IsDigit && o2IsDigit) {
			try {
				Integer n1 = Integer.parseInt(o1Part);
				Integer n2 = Integer.parseInt(o2Part);
				// if numbers are equal, make numeric test, else use string test
				if(n1 != n2) {
					result = n1.compareTo(n2);
				} else {
					result = 0;
					// invert level 1 result, so leading zeros are relevant
					//resultL1 *= -1;
				}
			} catch(NumberFormatException nbe) {
				// should not enter here as we test for numeric parts
				//result = resultL1;
			}
		}

		// if parts are equal ignore numeric test
		if(result == 0) {
			if(ignoreCase) {
				result = o1Part.compareToIgnoreCase(o2Part);
			} else {
				result = o1Part.compareTo(o2Part);
			}
		}

		// if parts are not equal return
		if(result != 0) {
			return result;
		}
		// level 1 result
		int resultL1 = result;

		// parts are equal, then continue test for rest of string
		String o1Rest = o1.substring(o1i);
		String o2Rest = o2.substring(o2i);
		result = compare(o1Rest, o2Rest);
		// if test for rest of string returns equality then return level 1 result
		if(result == 0) {
			return resultL1;
		} else {
			return result;
		}
	}

}
