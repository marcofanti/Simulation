package demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadDemand {
    public static void main(String[] args) throws IOException {
    	
    	int a = 300/200;
    	
    	BufferedReader br = new BufferedReader(new FileReader("./input/demandCorrected.txt"));
	    int index = 0;
    	try {
    	    StringBuilder sb = new StringBuilder();
    	    String line = br.readLine();

    	    int index2 = 0;
    	    while (line != null) {
    	    	index++; index2++;
    	    	if (index2 == 30) {
    	    		index2 = 0;
    	    		sb.append(System.lineSeparator());
    	    	}
    	        sb.append(line);
    	        sb.append(", ");
    	        line = br.readLine();
    	    }
    	    System.out.println(sb.toString() + " " + index);
    	} finally {
    	    br.close();
    	}
    }

}
