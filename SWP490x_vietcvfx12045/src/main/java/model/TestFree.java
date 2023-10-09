package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Random;

public class TestFree {
	public static void main(String [] args) {
		/*
		 * LocalDateTime myTime = LocalDateTime.now();
		 * System.out.println("Thời gian định dạng gốc: " + myTime);
		 * 
		 * DateTimeFormatter myFormat1 =
		 * DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); String viewDate =
		 * myTime.format(myFormat1);
		 * 
		 * System.out.println("Thời gian định dạng lại: " + viewDate);
		 * 
		 * Calendar calendar = Calendar.getInstance();
		 * System.out.println("Thời gian định dạng 2: " + calendar.getTime());
		 */
		
		/*
		 * int leftLimit = 97; // letter 'a' int rightLimit = 122; // letter 'z' int
		 * targetStringLength = 6; Random random = new Random(); StringBuilder buffer =
		 * new StringBuilder(targetStringLength);
		 * 
		 * for (int i = 0; i < targetStringLength; i++) { int randomLimitedInt =
		 * leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
		 * buffer.append((char) randomLimitedInt); } String generatedString =
		 * buffer.toString();
		 * 
		 * System.out.println(generatedString);
		 */
		
		RandomCode randomCode = new RandomCode();
		String randomCodeNow = randomCode.getRandomCodeNow();
		System.out.println(randomCodeNow);
	}
}
