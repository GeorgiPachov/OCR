# OCR/FmiTextReader
A university-project OCR Software

#### Code Browsing
It is an Eclipse based project, doesnt have native console-based build  
Import in either Eclipse/Netbeans/Idea  

#### Build 
Use the jar provided in /build  
or build a 'fatjar' from your favourite IDE  

#### Usage
Usage: textrecognizer.jar
```
 -file <arg>      Mandatory!: path to the file  
 -sampleX <arg>   Width of the samples that will be used  
 -sampleY <arg>   Height of the samples that will be used  
 -threads <arg>   Number of threads to use  
 -verbose         Detailed verbose of recognition  
 ```
Lacks the 'finishing' touches, yet can be imported in eclipse and ran.   
Just supply the 'input' photo as the first command line argument
