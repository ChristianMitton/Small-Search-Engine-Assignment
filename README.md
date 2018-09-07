# Small-Search-Engine-Assignment

<h2>Programming Assignment 4</h2>
<h2>Toy Search Engine</h2>
<h4>In this assignment you will implement a toy search engine for text documents
using hash tables.</h4>
<h3>Worth 100 points = 10% of your course grade</h3>
<h3>Posted Mon, July 16</h3>
<h3>Due Tue, July 31, 1:15 AM (<font color="red">WARNING!! NO GRACE PERIOD</font>)</h3>
<h3><font color="red">There is NO extended deadline!!</font></h3>
</center>
<hr>

<ul>
<li>You will work on this assignment individually.
Read <a href="https://www.cs.rutgers.edu/academic-integrity/programming-assignments">
DCS Academic Integrity Policy for Programming Assignments</a> - you are responsible for abiding 
by the policy. In particular, note that "<b>All Violations of the Academic Integrity Policy will 
be reported by the instructor to the appropriate Dean</b>".

</li><li><h3>IMPORTANT - READ THE FOLLOWING CAREFULLY!!!</h3>

<p><font color="red">Assignments emailed to the instructor or TAs will
be ignored--they will NOT be accepted for grading. <br>
We will only grade submissions in Sakai.</font><br>

</p><p><font color="red">If your program does not compile, you will not get any credit.</font> 

</p><p>Most compilation errors occur for two reasons: 
</p><ol>
<li> You 
are programming outside Eclipse, and you delete the "package" statement at the top of the file. 
If you do this, you are changing the program structure, and it will not compile when we
test it.
</li><li> You make some last minute 
changes, and submit without compiling. 
</li></ol>

<h3>To avoid these issues, (a) START EARLY, and
give yourself plenty of time to work through the assignment, and (b) Submit a version well
before the deadline so there is at least something in Sakai for us to grade. And you can
keep submitting later versions - we will
accept the LATEST version.</h3>
</li></ul>

<hr>

<a name="sum"></a><p></p><h3>Summary</h3>

You will implement a toy search engine
to do two things: (a) gather and index keys that appear
in a set of plain text documents, and (b) search for user-input keys against the index and return
a list of matching documents in which these keys occur. (A word/keyword in the text document is called a key)

<hr>
<p>

<a name="impl"></a></p><h3>Implementation</h3>

<p>Download the attached <tt>tse_project.zip</tt> file to your
computer. DO NOT unzip it. Instead, follow the instructions on the Eclipse page 
under the section "Importing a Zipped Project into Eclipse" to get the entire
project, called <tt>Toy Search Engine</tt>, into your Eclipse workspace.

</p><p>Here are the contents of the project:
</p><ul>
<li> A class, <tt>tse.ToySearchEngine</tt>. This is where you will
fill in your code, details follow.
</li><li>A supporting class, <tt>tse.Occurrence</tt>, which you will NOT change.
</li><li>Two sample text documents, 
<tt>AliceCh1.txt</tt>, and <tt>WowCh1.txt</tt>, directly under the project folder,
for preliminary testing. Be sure to get other
online text documents--or make your own--for more rigorous testing.
</li><li> A <tt>noisewords.txt</tt> file that contains a list of 
"noise" words, one per line. Noise words are commonplace words (such as "the") that
must be ignored by the search engine. You will use this file (and this file ONLY) to filter out 
noise words from the documents you read, when gathering keys.
</li><li>A <tt>docs.txt</tt> file that has a list of all documents (in this case
<tt>AliceCh1.txt</tt> and <tt>WowCh1.txt</tt>) from which the search engine should extract 
keys.
</li></ul>

<p>NOTE: You will need to write your own driver to test your implementation.
This driver can take as inputs a file that contains the names of all the documents 
(such as <tt>docs.txt</tt>), as well as the <tt>noisewords.txt</tt> file.
It can then set up a <tt>ToySearchEngine</tt>
object and call its methods as needed to test the implementation.
The <tt>docs.txt</tt> and <tt>noisewords.txt</tt> filenames will be sent 
in as the arguments to the <tt>buildIndex</tt> method
in <tt>ToySearchEngine</tt>.

</p><p>Following is the sequence of method calls that will be performed
on a <tt>ToySearchEngine</tt> object, to index and search keys. <br>

</p><ul>
<li><tt>ToySearchEngine()</tt> - Already implemented.
<p>The constructor creates new (empty) <tt>keysIndex</tt> and <tt>noiseWords</tt> hash tables.
The <tt>keysIndex</tt> hash table is the MASTER hash table, which indexes all keys
from all input documents. The <tt>noiseWords</tt> hash table stores all the noise words.
Both of these are fields in the <tt>ToySearchEngine</tt> class.

</p><p>Every key in the <tt>keysIndex</tt> hash table is a keyword. The associated value for
a key is an array list of (document,frequency) pairs for the documents in which the key
occurs, <em>arranged in descending order of frequencies</em>. A (document,frequency) pair is 
held in an <tt>Occurrence</tt> object. The <tt>Occurrence</tt> class is defined in 
the <tt>ToySearchEngine.java</tt> file, at the top. In an <tt>Occurrence</tt>
object, the <tt>document</tt> field is the
name of the document, which is basically the file name, e.g. AliceCh1.txt.

</p></li><li><tt>void buildIndex(String docsFile, String noiseWordsFile)</tt> - Already implemented.

<p>Indexes all the keys in all the input documents.
See the method documentation and body in the <tt>ToySearchEngine.java</tt> file for
details. 

</p><p>If you want to index the given sample documents, the first parameter would
be the file <tt>docs.txt</tt> and the second parameter would be the
noise words file, <tt>noisewords.txt</tt> 

</p><p>After this method finishes executing, the full index of all keys found
in all input documents will be in the <tt>keysIndex</tt> hash table.

</p><p>The <tt>buildIndex</tt> methods calls methods <tt>loadKeysFromDocument</tt> and
<tt>mergeKeys</tt>, both of which you need to implement.

</p><ul>
<li><tt>HashMap&lt;String,Occurrence&gt; loadKeysFromDocument(String docFile)</tt> -
<span class="impl">You implement</span>.

<p>This method creates a hash table for all keys in a single given document.
See the method documentation for details. 

</p><p>This method MUST call the 
<tt>getKey</tt> method, which you need to implement.

</p><ul>
<li><tt>String getKey(String word)</tt> - <span class="impl">You implement</span>.
<p>Given an input word read from a document, it checks if the word is a key, and
returns the key equivalent if it is.

</p><p>FIRST, see the method documentation in the code for details, including a specific short list of
punctuations to consider for filtering out. THEN, look at the following
illustrative examples of input word, and returned value.
<br>&nbsp;<br>
<table>
<tbody><tr><th>Input Parameter</th><th>Returned value</th></tr>
<tr><td>distance.</td><td>distance (strip off period)</td></tr>
<tr><td>equi-distant</td><td>null (not all alphabetic characters)</td></tr>
<tr><td>Rabbit</td><td>rabbit (convert to lowercase)</td></tr>
<tr><td>Through</td><td>null (noise word)</td></tr>
<tr><td>we're</td><td>null (not all alphabetic characters)</td></tr>
<tr><td>World...</td><td>world (strip trailing periods)</td></tr>
<tr><td>World?!</td><td>world (strip trailing ? and !)</td></tr>
<tr><td>What,ever</td><td>null (not all alphabetic characters)</td></tr>
</tbody></table>
</p></li></ul>
Observe that (as per the rules described in the method documentation),
if there is more than one trailing punctuation (as in the "World..." and "World?!"
examples above), the
method strips all of them. Also, the last example makes it clear that punctuation appearing
anywhere but at the end is not stripped, and the word is rejected. 

<p>Note that this is a much simplified filtering mechanism, and will
reject certain words that might be accepted by a real-world
engine. But the idea is to not unduly complicate this process,
focusing instead on hash tables, which is the point of this
assignment.  So, just stick to the rules described here.

</p><p></p></li><li><tt>void mergeKeys<hashmap&lt;string,occurrence></hashmap&lt;string,occurrence></tt> -
<span class="impl">You implement</span>.

<p>Merges the keys loaded from a single document (in method <tt>loadKeysFromDocument</tt>)
into the global <tt>keysIndex</tt> hash table.

</p><p>See the method documentation for details. This method MUST call the 
<tt>insertLastOccurence</tt> method, which you need to implement.

</p><ul>
<li><tt>ArrayList&lt;Integer&gt; insertLastOccurrence(ArrayList&lt;Occurrence&gt; occs)</tt> - 
<span class="impl">You implement</span>.
<p>See the method documentation for details. Note that this method uses binary
search on frequency values to do the insertion. The return value is the sequence of mid 
points encountered
during the search, using the regular (not lazy) binary search we covered in class. 
This return value is not used by the calling method-it is only going to be used
for grading this method.

</p><p>For example, suppose the list had the following frequency values (including the last
one, which is to be inserted):
</p><pre>     --------------------
     12  8  7  5  3  2  <font color="red">6</font>
     --------------------
      0  1  2  3  4  5  6
</pre>
Then, the binary search (on the list <em>excluding</em> the last item)
would encounter the following sequence of midpoint indexes:
<pre>    2  4  3
</pre>
<b>Note that if a subarray has an even number of items, then the midpoint
is the last item in the first half.</b>

<p>After inserting <tt>6</tt>, the input list would be updated to this:
</p><pre>     --------------------
     12  8  7  <font color="red">6</font>  5  3  2
     --------------------
      0  1  2  3  4  5  6
</pre>
and the sequence <tt>2  4 3</tt> would be returned. 

<p><b>If the new item is a duplicate of something that already exists, it doesn't
matter if the new item is placed before or after the existing item.</b>

</p><p><font color="red">Note that the items are in DESCENDING order, so the binary
search would have to be done accordingly.</font>

</p></li></ul>
</li></ul>

<p></p></li><li><tt>ArrayList&lt;String&gt; top5search(String kw1, String kw2)</tt> - 
<span class="impl">You implement</span>.

<p>This method computes the search result for the input "kw1 OR kw2", using
the <tt>keysIndex</tt> hash table. The result is a list of NAMES of documents
(limited to the top 5) in
which either of the words "kw1" or "kw2" occurs, <b>arranged in descending order
of frequencies.</b> 
See the method documentation in the code for additional details. 

</p><p>As an example, suppose the search is for "deep or world", in the given test
documents, <tt>AliceCh1.txt</tt> (call it <tt>A</tt>) and <tt>WowCh1.txt</tt> (call it
<tt>W</tt>).  The word "deep" occurs twice in <tt>A</tt> and once in <tt>W</tt>,
and the word "world" occurs once in <tt>A</tt> and 7 times in <tt>W</tt>:
</p><pre>    deep:  (A,2), (W,1)
    world: (W,7), (A,1)
</pre>
The result of the search is:
<pre>    WowCh1.txt, AliceCh1.txt
</pre>
in that order. (Recall that the name of a document is the same as the name of the document file.)

<p>NOTE: 
</p><ul>
<li>If there are no matches for either key, return null or empty list, either
is fine.
</li><li>If a document occurs in both keys' match list, consider the one with the higher
frequency - do NOT add frequencies.
</li><li>Return AT MOST 5 non-duplicate entries. This means 
if there are more than 5 non-duplicate entries, then return the five top frequency entries, 
but if there are fewer than 5 non-duplicate entries, then return all of them. 
</li><li>If a document in the first match list (for the first key)
has the same frequency as a document in the second
match list (for the second key), and both are candidates for inclusion in the output
(they are not the same document), then pick the document in the first list before 
the document in the second list.
<p></p></li></ul>

<h4>Implementation Rules</h4>
<ul>
<li>Do NOT change the package name on the first line.
</li><li>Do NOT add any import statements.
With  the existing imports, you may use any of the 
classes in packages <tt>java.lang</tt>, <tt>java.io</tt> and <tt>java.util</tt>.
</li><li>Do NOT change the <tt>Occurrence</tt> class in ANY way.
</li><li>Do NOT change the headers of any of the existing methods in <tt>ToySearchEngine</tt> in ANY way.
</li><li>Do NOT change the code in any of the implemented methods in ANY way.
</li><li>Do NOT add any class fields in <tt>ToySearchEngine</tt>.
</li><li>You MAY add helper methods to <tt>ToySearchEngine</tt>, but you must make them
<tt>private</tt>.
</li></ul>

<hr>
<p>
<a name="grading"></a></p><h3>Grading</h3>

<table>
<tbody><tr><th>Method</th><th>Points</th></tr>
<tr><td><tt>getKey</tt></td><td class="pts">15</td></tr>
<tr><td><tt>loadKeysFromDocument</tt></td><td class="pts">20</td></tr>
<tr><td><tt>insertLastOccurrence</tt></td><td class="pts">15</td></tr>
<tr><td><tt>mergeKeys</tt></td><td class="pts">25</td></tr>
<tr><td><tt>top5search</tt></td><td class="pts">25</td></tr>
</tbody></table>

<p>When a method is graded, the correct versions of other methods will be used.
Also, all data structures will be set to their correct (expected) states 
before a method is called.

</p><p>You need not do any error checking in your program for bad inputs. 

</p><hr>
<p>
<a name="submit"></a></p><h3>Submission</h3>

<p>Submit your <tt>ToySearchEngine.java</tt> file.

</p><hr>


</li></ul></body></html>
