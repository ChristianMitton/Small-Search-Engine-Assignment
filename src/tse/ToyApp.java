package tse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ToyApp {
	
	static Scanner stdin = new Scanner(System.in);

	public static void main(String[] args) throws FileNotFoundException {
		
		ToySearchEngine tse = new ToySearchEngine();
		String noiseWordsFile = "noiseWords.txt";	
		
		System.out.print("Enter ToyEngine file name => ");
		String wordsFile = stdin.nextLine()+".txt";
		System.out.println("You entered the file => " + wordsFile+"\n");				
		
		Scanner sc = new Scanner(new File(wordsFile));
		Scanner scn = new Scanner(new File(noiseWordsFile));
		
		//loadKeyWordsFromDoc needs to be finished before buildIndex will work
		System.out.println("Enter ToyEngine docs name => ");
		String docsFile = stdin.nextLine()+".txt";
		System.out.println("\nCalling buildIndex...\n");
		tse.buildIndex(docsFile, noiseWordsFile);
		
//		System.out.println("\nNoiseWords...\n "+tse.noiseWords.toString());
		
		
		/*---------------------------------------------------
		  				Calling functions
		  ---------------------------------------------------*/
		
//		System.out.println("\nCalling loadKeysFromDocument on file '" +wordsFile+ "' ...");		
//		tse.loadKeysFromDocument(wordsFile);
		
//		System.out.println("\nCalling getKey on file '" +wordsFile+ "' ...");
//		System.out.println(tse.getKey("we're"));
		
		System.out.println("\nCalling top5search on file '" +docsFile+ "' ...");
		tse.top5search("opportunity", "world");					
		
//		System.out.println("\nCalling insertLastOccurrence on file '" +wordsFile+ "'...\n");
//		ArrayList<Occurrence> dummy = new ArrayList<Occurrence>();
//		//12  8  7  5  3  2  6
//		dummy.add(new Occurrence("",12));
//		dummy.add(new Occurrence("",8));
//		dummy.add(new Occurrence("",7));
//		dummy.add(new Occurrence("",5));
//		dummy.add(new Occurrence("",3));
//		dummy.add(new Occurrence("",2));
//		dummy.add(new Occurrence("",6));
//		System.out.println("----------- Scarlet: -----------\n");
//		System.out.println("Test Occurrences in dummy list: [12, 8, 7, 5, 3, 2, 6]");
//		System.out.println("midPoints being returned from insertLastOccurrence: "+ tse.insertLastOccurrence(dummy));
		
	}
	
//	private static void printWordsInDoc(Scanner sc) {
//		int sentenceCounter = 0; //14		
//		int paragraphCounter = 0; //5
//		int words = 0;
//		
//		while(sc.hasNext()) {
//			System.out.print(sc.next()+" ");
//			if(sentenceCounter == 14) {
//				System.out.println();
//				sentenceCounter = 0;
//				paragraphCounter++;
//			}
//			if(paragraphCounter == 5) {
//				System.out.println();
//				paragraphCounter = 0;
//			}
//			sentenceCounter++;
//			words++;
//		}
//		System.out.println();
//		System.out.println();
//		System.out.println("Number of words: " + words);
//	}
//	
//	private static void printNoiseWords(Scanner sc) {
//		int sentenceCounter = 0; //14		
//		int words = 0;
//		
//		while(sc.hasNext()) {
//			System.out.print(sc.next()+" ");
//			if(sentenceCounter == 9) {
//				System.out.println();
//				sentenceCounter = 0;
//			}
//			sentenceCounter++;
//			words++;
//		}
//		System.out.println();
//		System.out.println();
//		System.out.println("Number of words: " + words);
//	}
	
//	

}
