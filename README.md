# Project 2: CFG to PDA conversion

* Author: Rubal Goyal
* Class: CS561

## Overview

In this project, I have implemented a program to convert CFG (Context Free Grammar) to PDA (Push Down Dutomata). My program's executable is `CFGDeriver.java` which creates an instance of `CFGDecoder.java` for any given encoding of a CFG file. The `CFGDecoder.java` would convert the CFG to PDA and then the executable `CFGDeriver.java` will call the `deriveString()` method of `PDA.java` to check if the input string can be derived or not.

Ultimately, the program will return __yes__ if the string is derived and __no__ if the string is not derived from the CFG encoding. Since the conversion of the CFG to PDA is non-deterministic, hence, I am exploring all the possible copies of the PDA in the `deriveString()` method where I am storing all the intermediate PDA copies and the corresponding string read so far into an ArrayList `pdaCopies`. Therefore, if the string is not derivable from the CFG, then it might be possible the Java heap for `pdaCopies` may run out of memory and will print an exception message _Ohhhh!! OutOfMemoryError error. Input string couldn't be derived, loops forever._ The `deriveString()` would explore all possible copies of the PDA and would stop immediately if the string is derived from any of the PDA copy o/w will keep exploring until all such copies are not explored.

As this conversion is non-deterministic and one copy of the PDA may loop forever, hence, I have included an upper bound on a maximum number of derivation steps that can be applied on a single copy of the PDA before it will move to another copy. The bounds are described as below:
  - **1**: A maximum limit is set to 100.
  - **2**: A maximum step limit is calculated using $(b^h - 1)/(b-1)$ where $b$ is the maximum number of children the CFG can derive and $h$ is the height of the parse tree. I have used $log_{2}^n$ as the maximum length of $h$ of the parse tree where $n$ is the length of the input string.
  - **3**: If the given is in CNF (Chomsky Normal Form) then the maximum bound will be $2n-1$ where $n$ is the length of the input string.

## Program Execution Instructions:
Please follow the below instructions to execute the program. 

1. From the terminal or command prompt, change the pwd to where the Java program is downloaded and then navigate to `..CFGDeriver/src/` folder.
2. Please compile the Java files first using the below instructions.
```
javac *.java
```
_(note: this may show some warnings depending the java version, please ignore these warnings)_
3. Once, the program is complied, run the program using below instructions:
```
java CFGDeriver boundValue cfgEncodingFile inputString
```
where __boundValue will take one value from {1,2,3}__, __cfgEncodingFile file path to CFG encoding__, __inputString file path to input string__

I have added another executable `Benchmark.java` which tests the evaluations available in `../evals/`. To run that, please use below command:
```
java Benchmark pathToEvals
```
_(note: please ensure that pathToEvals is path to folder containing evals file and pathToEvals should end with '/')_
