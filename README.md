# OCR/FmiTextReader
OCR software for an university project, with multithreading support

#### Code Browsing
It is an Eclipse based project, doesnt have native console-based build  
Import in either Eclipse/Netbeans/Idea  

#### Build 
```
git clone https://github.com/GeorgiPachov/OCR.git
cd OCR
ant
```
OR you can the jar provided (ocr.jar)
OR you can build/run a 'fatjar' from your favourite IDE  

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
