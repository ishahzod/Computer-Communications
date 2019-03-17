The first project was the implementation of a 10x3 switching element. I used Java to simulate 
how the switching element functions in receiving packets and outputting them through a Bernoulli Process.  

The program generates n timeslots based on user input, and randomly generates packets for each slot. 
Then through a Bernoulli process, which packets pass to output are determined, and the average number of packets passed 
as well as average number of packets dropped are calculated with respect to p. 

I used the BufferedWriter class to save the data to a file based on the user's input. 
The user is prompted to save the file and they can do so by simply typing in the file name followed 
by the extension of their choosing (.csv, .xlsx, .txt, etc)  and can graph the points to see the distribution visually.
