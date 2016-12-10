package domain.helpers;

public class PeselHelper {

	public static boolean check(String correctPesel) {
		
		try {
			int pesel = Integer.parseInt(correctPesel);
		} catch (NumberFormatException nfe) {
			return false; 
		}
		
		if( correctPesel.length() != 11) {
			return false; 
		}
		
		String pattern = "9731973197";
		int checksum = 0;
		for (int i=0; i<10; i++) {
			checksum += Integer.parseInt(Character.toString(correctPesel.charAt(i)))
						* Integer.parseInt(Character.toString(pattern.charAt(i)));
		}
		
		if (Integer.toString(checksum%10) != Character.toString(correctPesel.charAt(11))) {
			return false;
		}
		
		return true;
	}

}
