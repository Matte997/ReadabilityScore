# ReadabilityScore

This code allows to calculate some readability index form a given text who must be added from the command line after the code compilation
Indexes:
 -  Automated Readability Score : https://en.wikipedia.org/wiki/Automated_readability_index
 -  Flesch–Kincaid readability tests : https://en.wikipedia.org/wiki/Flesch%E2%80%93Kincaid_readability_tests
 -  Simple Measure of Gobbledygook : https://en.wikipedia.org/wiki/SMOG
 -  Coleman–Liau index : https://en.wikipedia.org/wiki/Coleman%E2%80%93Liau_index

Rules to count syllables are the following:
   1. Count the number of vowels in the word.
   2. Do not count double-vowels (for example, "rain" has 2 vowels but is only 1 syllable)
   3. If the last letter in the word is 'e' do not count it as a vowel (for example, "side" is 1 syllable)
   4. If at the end it turns out that the word contains 0 vowels, then consider this word as 1-syllable.
   
Polysyllable are words that contains more than 2 syllables

It's important to note that 'y' is considered a vowel 97.5% but in the cases where it is seen as a consonant it's followed by a vowel (es. yello, yacth, yesterday..) so the indexes accuracy is 100%

To run + compile the code use the shortcut "java Main.java textName" in the right directory to prevent the creation of .class file
