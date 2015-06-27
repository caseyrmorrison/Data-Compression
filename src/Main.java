import java.io.*;
import java.util.*;

public class Main {

	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();
		long duration = 0;
		
		FileReader inputStream = null;
		String fileName = "Rand, Ayn - Atlas Shrugged.txt";
		// Uncomment for other book.
//		String fileName = "Adventuresof HuckleberryFinn.txt";
		FileOutputStream outputStream = new FileOutputStream(new File("compressed.txt"));
		FileOutputStream codeStream = new FileOutputStream(new File("codes.txt"));
		try {
				inputStream = new FileReader(fileName);
				int c;
				StringBuffer str = new StringBuffer();
				
				// read characters into a list of characters
				while ((c = inputStream.read()) != -1) {
					str.append((char)c);
				}
				inputStream.close();
				

				// pass list of characters to constructor
				// constructor carries out all operations 
				// necessary for creating the encoding
				CodingTree ct = new CodingTree(new String(str));
				// write the code file
				// codeStr is a String member of CodingTree
				// consisting of one (char, binary-codeword) pair
				codeStream.write(ct.codeStr.getBytes());
				codeStream.close();

//				System.out.println(ct);
				
				List<String> codes = new ArrayList<String>();
				MyHashTable<String, String> ht = ct.codes;
				ht.stats();
				StringBuffer codeBuffer = new StringBuffer();
				StringBuffer wordBuffer = new StringBuffer();
				long asciiCost = str.length()*8;
				long compressedCost = 0;
				for(int i = 0; i < str.length(); i++){
					Character ch = str.charAt(i);
					if((ch.compareTo('A') >= 0 && ch.compareTo('Z') <= 0) || (ch.compareTo('a') >= 0 && ch.compareTo('z') <= 0) || ch.equals('\'')){
						wordBuffer.append(ch);
					}
					else { // separator
						String codeStr = new String(wordBuffer);
						// add the word's codeword to the buffer
						if(codeStr.length()>0){
							codeBuffer.append(ht.get(codeStr));		
						}
						// add the separator's codeword to the buffer
						codeBuffer.append(ht.get(ch.toString()));
						wordBuffer = new StringBuffer();
					}
					if(codeBuffer.length() > 256){
						while(codeBuffer.length() > 8){
							int chr = Integer.parseInt(codeBuffer.substring(0, 8),2);
							outputStream.write(chr);
							codeBuffer.delete(0, 8);
							compressedCost += 8;
						}
					}					
				}
				compressedCost += codeBuffer.length();
				while(codeBuffer.length() > 8){
					int chr = Integer.parseInt(codeBuffer.substring(0, 8),2);
					outputStream.write(chr);
					codeBuffer.delete(0, 8);
				}
				outputStream.close();
				
				duration = System.currentTimeMillis() - start;
				
				System.out.println("Uncompressed file size: " + asciiCost/8 + " bytes");
				System.out.println("Compressed file size: " + compressedCost/8 + " bytes");
				System.out.println("Compression ratio: " + compressedCost*100/asciiCost + "%");
				System.out.println("Running Time: " + duration + " milliseconds");
		} finally {}
	}
}
