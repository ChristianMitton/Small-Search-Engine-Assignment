package tse;

import java.io.*;
import java.util.*;

/**
 * This class builds an index of keywords. Each keyword maps to a set of pages in
 * which it occurs, with frequency of occurrence in each page.
 *
 */
public class ToySearchEngine {
	
	/**
	 * This is a hash table of all keys. The key is the actual keyword, and the associated value is
	 * an array list of all occurrences of the keyword in documents. The array list is maintained in 
	 * DESCENDING order of frequencies.
	 */
	HashMap<String,ArrayList<Occurrence>> keysIndex;
	
	/**
	 * The hash set of all noise words.
	 */
	HashSet<String> noiseWords;
	
	/**
	 * Creates the keysIndex and noiseWords hash tables.
	 */
	public ToySearchEngine() {
		keysIndex = new HashMap<String,ArrayList<Occurrence>>(1000,2.0f);
		noiseWords = new HashSet<String>(100,2.0f);
	}
	
	private Occurrence createNewOccurence(String document, int Wordfreq ) {
		return new Occurrence(document, Wordfreq);
	}
	
	/**
	 * Scans a document, and loads all keywords found into a hash table of key occurrences
	 * in the document. Uses the getKey method to separate keywords from other words.
	 * 
	 * @param docFile Name of the document file to be scanned and loaded
	 * @return Hash table of keys in the given document, each associated with an Occurrence object
	 * @throws FileNotFoundException If the document file is not found on disk
	 */
	public HashMap<String,Occurrence> loadKeysFromDocument(String docFile) 
	throws FileNotFoundException {
		/** COMPLETE THIS METHOD **/
//		System.out.println(getKey("through"));
//		System.out.println("--------------------------------------------------------------------------");
		Scanner sc = new Scanner(new File(docFile));	
		HashMap<String, Occurrence> hashTable = new HashMap<String, Occurrence>();
		
		while(sc.hasNext()) {
			String token = getKey(sc.next());			
			if(!hashTable.containsKey(token) && token != null) {
				//if the table doesn't contain word, add a new occurrence of it 
				hashTable.put(token, createNewOccurence(docFile, 1));
			} else if(hashTable.containsKey(token)) {
				//update frequency				
				hashTable.get(token).frequency += 1;				
			}						
		}
		sc.close();
		/*----------------------
		 	testing 
		 ----------------------*/
//		for(Entry<String, Occurrence> e : hashTable.entrySet()) {
//	        String key = e.getKey();
//	        Occurrence value = e.getValue();
//	        System.out.println(key + " : " + value);
//	    }
		return hashTable;
	}
	
	/**
	 * Merges the keys for a single document into the master keysIndex
	 * hash table. For each key, its Occurrence in the current document
	 * must be inserted in the correct place (according to descending order of
	 * frequency) in the same key's Occurrence list in the master hash table. 
	 * This is done by calling the insertLastOccurrence method.
	 * 
	 * @param kws Keywords hash table for a document
	 */
	public void mergeKeys(HashMap<String,Occurrence> kws) {
		/** COMPLETE THIS METHOD **/
		if(kws == null) {
			return;	
		}
		for (String key : kws.keySet()) {			
			Occurrence frequency = kws.get(key);			
			if (!keysIndex.containsKey(key)) {							
				ArrayList<Occurrence> list = new ArrayList<Occurrence>();				
				list.add(frequency);
				keysIndex.put(key, list);				
				insertLastOccurrence(list);				
			} 
			else if(keysIndex.containsKey(key)) {						
				ArrayList<Occurrence> list = keysIndex.get(key);
				list.add(frequency);
				insertLastOccurrence(list);				
				keysIndex.put(key, list);
			}
		}
		
	}
	
	private boolean isAPunctuationMark(char letter) {
		if(letter == '.' || letter == '?' || letter == '!' || letter == '(' || letter == ')' || 
				letter == ';' || letter == ':' || letter == '\"' || letter =='[' || letter == ']' || letter =='_') {
			return true;
		}
		else return false;
	}
	
	/**
	 * Given a word, returns it as a keyword if it passes the keyword test,
	 * otherwise returns null. A keyword is any word that, after being stripped of any
	 * trailing punctuation, consists only of alphabetic letters, and is not
	 * a noise word. All words are treated in a case-INsensitive manner.
	 * 
	 * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'
	 * Note: No other punctuation characters will appear in grading testcases
	 * 
	 * @param word Candidate word
	 * @return Key (word without trailing punctuation, LOWER CASE)
	 */
	public String getKey(String word) {
		/** COMPLETE THIS METHOD **/
		//in order for noiseWords to exsist, loadKeysFromDocument needs to be populated
		word = word.toLowerCase();
		if(noiseWords.contains(word)) {
			return null;
		}
		//handle words like ap.ple
		if(word.contains(",")) {			
			int symbolLocation = word.indexOf(",");
			if (symbolLocation < word.length()-1) {
				return null;
			}
		}
		if(word.contains("_")) {			
			int symbolLocation = word.indexOf("_");
			if (symbolLocation < word.length()-1) {
				return null;
			}
		}
		
		String cleanedWord = "";
		for(char letter: word.toCharArray()) {
			if(isAPunctuationMark(letter)) {
				continue;
			}
			cleanedWord += letter;
		}
		
		if(word.contains("-") || word.contains("'") || word.contains(",")) {
			//TODO: a comma doesn't meean the word is invalid
			return null;
		}
		if(cleanedWord.equals("")) {
			return null;
		}
		if(noiseWords.contains(cleanedWord)) {
			return null;
		}
		for(char letter: cleanedWord.toCharArray()) {
			if(Character.isDigit(letter)) {
				return null;
			}
		}
		return cleanedWord.toLowerCase();
	}
				
	/**
	 * Inserts the last occurrence in the parameter list in the correct position in the
	 * list, based on ordering occurrences on descending frequencies. The elements
	 * 0..n-2 in the list are already in the correct order. Insertion is done by
	 * first finding the correct spot using binary search, then inserting at that spot.
	 * 
	 * @param occs List of Occurrences
	 * @return Sequence of mid point indexes in the input list checked by the binary search process,
	 *         null if the size of the input list is 1. This returned array list is only used to test
	 *         your code - it is not used elsewhere in the program.
	 */
	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {
		/** COMPLETE THIS METHOD **/
		ArrayList<Integer> midPoints = new ArrayList<Integer>();
		ArrayList<Integer> frequencies = new ArrayList<Integer>();					
		//--------------------------------------------------------
		//add occs to frequencies
		for (int i = 0; i < occs.size(); i++) {
			frequencies.add(occs.get(i).frequency);
		}	
		int low = 0;
		int mid = 0;	
		int high = occs.size()-2;		
		int target = occs.get(occs.size()-1).frequency;		
//		System.out.println("\nlow: "+low+"\nhigh: "+ high+ "\ntarget: "+target);
		while(high >= low) {
			mid = (high + low)/2;	
//			System.out.println("mid: "+mid);
			midPoints.add(mid);	
			if(frequencies.get(mid) < target) {
				high = mid - 1;
//				System.out.println("high: "+high);
			}			
			else if(frequencies.get(mid) > target) {
				low = mid + 1;
//				System.out.println("low: "+low);
			}
			else {
				break;
			}
		}
		
		return midPoints;
	}
	
	/**
	 * This method indexes all words found in all the input documents. When this
	 * method is done, the keysIndex hash table will be filled with all keys,
	 * each of which is associated with an array list of Occurrence objects, arranged
	 * in decreasing frequencies of occurrence.
	 * 
	 * @param docsFile Name of file that has a list of all the document file names, one name per line
	 * @param noiseWordsFile Name of file that has a list of noise words, one noise word per line
	 * @throws FileNotFoundException If there is a problem locating any of the input files on disk
	 */
	public void buildIndex(String docsFile, String noiseWordsFile) 
	throws FileNotFoundException {
		// load noise words to hash table
		Scanner sc = new Scanner(new File(noiseWordsFile));
		while (sc.hasNext()) {
			String word = sc.next();
			noiseWords.add(word);
		}
		
		// index all words		
		sc = new Scanner(new File(docsFile));
		while (sc.hasNext()) {
			String docFile = sc.next();
			HashMap<String,Occurrence> kws = loadKeysFromDocument(docFile);
			mergeKeys(kws);
		}
		sc.close();
	}
	
	/**
	 * Search result for "kw1 or kw2". A document is in the result set if kw1 or kw2 occurs in that
	 * document. Result set is arranged in descending order of document frequencies. (Note that a
	 * matching document will only appear once in the result.) Ties in frequency values are broken
	 * in favor of the first keyword. (That is, if kw1 is in doc1 with frequency f1, and kw2 is in doc2
	 * also with the same frequency f1, then doc1 will take precedence over doc2 in the result. 
	 * The result set is limited to 5 entries. If there are no matches at all, result is null.
	 * 
	 * @param kw1 First keyword
	 * @param kw1 Second keyword
	 * @return List of documents in which either kw1 or kw2 occurs, arranged in descending order of
	 *         frequencies. The result size is limited to 5 documents. If there are no matches, returns null.
	 */
	public ArrayList<String> top5search(String kw1, String kw2) {
		/** COMPLETE THIS METHOD **/ 
		//HashMap<String,ArrayList<Occurrence>> keysIndex;
		
		kw1 = getKey(kw1); //deep : (text2.txt, 4), (text1.txt, 3) , (a.txt, 2), (W.txt,1)
	    kw2 = getKey(kw2); //world : (W.txt,7) , (a.txt,1)
	    
	    ArrayList<String> result = new ArrayList<String>();
	    
	    ArrayList<Occurrence> word1 = new ArrayList<Occurrence>();
	    ArrayList<Occurrence> word2 = new ArrayList<Occurrence>();
	    	    
	    //scan through the keysIndex hashTable and put the words and their occurrences into their respective list
	    //
	    for(String entry: keysIndex.keySet()) {
//    		System.out.println("ENTRY KEY: "+entry);
    			//Word1:
    			if(entry.equals(kw1)) {
    			//loop through the arrayList of the entry, add each item to the word1 list
//    			System.out.println("ENTRY VALUE: "+keysIndex.get(entry).toString());
	    			//for each occurrence in ArrayList, add it to the word1 arrayList
    				word1 = keysIndex.get(entry);
    			}
    			//Word2:
    			if(entry.equals(kw2)) {
    			//loop through the arrayList of the entry, add each item to the word2 list
//    			System.out.println("ENTRY VALUE: "+keysIndex.get(entry).toString());
	    			//for each occurrence in ArrayList, add it to the word2 arrayList
    				word2 = keysIndex.get(entry);
    			}	    		
	    }
	    //
	    
	    if(word1.isEmpty() && word2.isEmpty()) {
//	    		System.out.println("No matches found, returning null");
	    		return null;
	    }
	    
	    
		//each list now contaings the word and its list of occurences
	    
	    //ONCE YOU SORT THE WORDS, ONLY KEEP THE FIRST 5
	    
	    int[] combo = new int[(word1.size()+word2.size())];
//	    System.out.println("Combo Size: "+combo.length);
	    int counter = 0;
	    int indexPtr;
	    
	    //loop through each array, add frequencies to combo
	    //go through word1 list
	    for(indexPtr = 0; indexPtr < word1.size(); indexPtr++) {
	    		combo[indexPtr] = word1.get(counter).frequency;
	    		counter++;
	    }
//	    for(int item : combo) {
//	    	System.out.println("INSIDE COMBO: "+item);
//	    }
	    
//	    System.out.println();
	    
	    counter = 0;
	    for(int indexPtr2 = indexPtr; counter != word2.size(); indexPtr2++) {
//	    	System.out.println("INDEX PTR2: "+indexPtr2);
//	    	System.out.println("FREQUENCY: "+word2.get(counter).frequency);
    			combo[indexPtr2] = word2.get(counter).frequency;
    			counter++;
	    }
	    
	    //go through list 2
//	    System.out.println("INSIDE COMBO BEFORE SORT:\n");
//	    for(int item : combo) {
//	    		System.out.print(item + " ");
//	    }
	    
	    //combo not contains a list of all frequencies of both words 
	    //Use Binary Search to order array in descending order 
	    
	    combo = descendingInsertionSort(combo);
	    System.out.println();
//	    System.out.println("INSIDE COMBO After SORT:\n");
//	    for(int item : combo) {
//	    		System.out.print(item + " ");
//	    }
	    
	    //combo now contains the descending order of the frequencies	    
	    ArrayList<Occurrence> word1and2 = new ArrayList<Occurrence>();
	    word1and2.addAll(word1);
	    word1and2.addAll(word2);
	    //word1and2 now contains a combination of word1 and word2
	    
	    //go through word1and2, get corresponding title of combo index, add it to result
	    int ptr = 0;
	    System.out.println();
	    while(ptr < word1and2.size()) {
	    		int currentFreq = combo[ptr]; //7
//	    		System.out.println("currentFreq: "+currentFreq);
	    		String document = "";
	    		//search through word1and2 for freq that has 7
	    		for(int p = 0; p < word1and2.size(); p++) {
	    			if(word1and2.get(p).frequency == currentFreq) {
	    				document = word1and2.get(p).document;	    				
	    				break;
	    			}
	    		}
	    		if(result.contains(document)) {
	    			ptr++;
	    			continue;
	    		}
	    		result.add(document);
	    		ptr++;
	    }	    

	    
	    
	    /*-----------------------
	     		Testing 
	     ------------------------*/
//		System.out.println("\nTesting for word1 arrayList, aka deep:");
//		System.out.print("deep: ");
//	    for(Occurrence item : word1) {
//	    		System.out.print("(Doc: "+item.document + " ,freq: " + item.frequency +") ");
//	    }
//	    
//	    System.out.println();
//	    
//	    System.out.println("\nTesting for word2 arrayList, aka world:");
//		System.out.print("world: ");
//	    for(Occurrence item : word2) {
//	    		System.out.print("(Doc: "+item.document + " ,freq: " + item.frequency +") ");
//	    }   
	    
//	    System.out.println();
//	    
//	    System.out.println("\nTesting for word1and2 arrayList:");
//		System.out.print("word1and2: ");
//	    for(Occurrence item : word1and2) {
//	    		System.out.print("(Doc: "+item.document + " ,freq: " + item.frequency +") ");
//	    }   
	       
//	    System.out.println("\nShould be: W.txt, text2.txt, text1.txt, a.txt");
//	    
//	    
//	    System.out.println("What it is:\n");   
//	    for(String item: result) {
//	    		System.out.print(item + ", ");
//		}
	    
	    //Keep only 5
	    if(result.size()>5) {
	    		int count = 5;
	    		while(count < result.size()) {
	    			result.remove(count);
	    		}
	    		
	    }
//	    result.remove(0);
	    
//	    System.out.println("\n\nRESULT: "+result.toString());
//	    System.out.println("\nRESULT SIZE: "+result.size());
	       return result;	   
	
	}
	
	private int[] descendingInsertionSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int valueToSort = array[i];
            int j = i;
            while (j > 0 && array[j - 1] < valueToSort) {
            	array[j] = array[j - 1];
                j--;
            }
            array[j] = valueToSort;
        }
        return array;
    }
	
}
