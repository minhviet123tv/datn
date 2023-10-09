package model;

import java.util.Random;

public class RandomCode {
	private int leftLimit = 97; // letter 'a'
	private int rightLimit = 122; // letter 'z'
	private int targetStringLength = 6;
    
    public String getRandomCodeNow() {
    	
    	Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String random_code = buffer.toString();
        
        return random_code;
    }
}
