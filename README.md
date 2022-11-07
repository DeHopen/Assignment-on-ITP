# Assignment-on-ITP
Assignment 1:

Given a text file input.txt starting with number N followed by N names of maximum length of L English letters each. Each name starts with an uppercase letter followed by non-negative number of lowercase letters without spaces or symbols in between. You have to sort them in non-decreasing Lexicographic Order and write them to another file output.txt.

In case of finding any problems inside the input file (non-English letters, value N which does not correspond to the number of names, or anything violating the assignment rules), the output file should cointain only the next string: Error in the input.txt

Input
The first line of input file input.txt should contain only N (1≤N≤100), the total amount of names. The next N lines should contain list of names (1 per line) with length L (1≤L≤100).

Output
The output file output.txt should contain the list of lexicographically sorted names.

Examples:

input:

5
Munir
Alaa
Mohamad
Mike
Alexander

out:

Alaa
Alexander
Mike
Mohamad
Munir
