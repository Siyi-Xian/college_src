## Objective:

This assignment is designed to provide you with an opportunity to look at the following capabilities of the GNU debugger.

* Compiling a program with the debugging information needed by GDB. 
* Running a program using GDB. 
* Setting a breakpoint and a watchpoint
* Examining Variables
  * Local
  * Global
* Stepping a program.
  * Continue
  * Step
  * Next
  * Fin

## Resources:

These are some helpful resources for learning more about GDB. 

* [GDB TUI Mode Keys](https://sourceware.org/gdb/onlinedocs/gdb/TUI-Keys.html#TUI-Keys)
* [GDB Cheat Sheet](http://darkdust.net/files/GDB%20Cheat%20Sheet.pdf)
* [Helpful Book on GDB](https://www.amazon.com/gp/product/1593271743/ref=pd_sbs_14_t_0?ie=UTF8&psc=1&refRID=JK0XBPQR2TKQSXCZMXSZ)
* [Official Documentation](https://www.gnu.org/software/gdb/documentation/)

## Preliminary Steps:
	
### Pull Debugging Code from Repository

The code that you will be using for this assignment has been posted to the course repository in the directory **exercises/gdb_src**.

* git fetch
* git pull upstream master

## Running the code

### Building the code

```bash
$ make clean all
rm -f dbgme
gcc -g -O0 -o dbgme myprog.c
```

### Running the code

```bash
$ ./dbgme 
zyxwvutsrqponmlkjihgfedcba
Segmentation fault
```

### Debugging the code

In this step, you will be running gdb in terminal user mode (TUI
mode). It provides an ncurses style text window interface for gdb.

```bash
$ gdb -tui dbgme
```

You will be presented with the source code for dbgme in the top window
and a gdb command window in the bottom window. You can run the program by entering the following command at the gdb prompt. 

```bash
(gdb) run
```
Notice that you again get a segmentation fault. This is an error that is typically caused by a memory access that is outside of the memory provided to this program. This is often caused by access to a bad pointer.

### Backtrace

You can find out more about about specifically where the error occured by tracing the function calls that led up to the error. This is called a backtrace (bt). 

```gdb 
(gdb) bt
#0  0x0000000000400554 in initialize_alphabet (a=0x0) at myprog.c:11
#1  0x00000000004005ae in reverse_print_alpha (b=0x601050 <alphabet> "abcdefghijklmnopqrstuvwxyz") at myprog.c:20
#2  0x00000000004005fc in main () at myprog.c:30
(gdb)
```

To read this listing, start at the bottom. The function main() at line
30 of myprog.c called reverse_print_alpha with the argument b set to
the starting address of alphabet. The function reverse_print_alpha
called initialize_alphabet with the argument a set to 0.

### Setting a breakpoint

You can set a breakpoint in gdb. A break point is a location where you
would like the code to stop. Let's set a breakpoint at the beginning
of the program so that we can single step the program.

```gdb
(gdb) b main
Breakpoint 1 at	0x4005f0: file myprog.c, line 29.
```

In this example, the name of a function was used to specifiy the
location. We could have also used myprog.c:29 to specify the specific
location in the file.

Let's set a breakpoint at the beginning of the reverse_print_alpha function using the line number.

```gdb
(gdb) b myprog.c:15
Breakpoint 2 at	0x400577: file myprog.c, line 15.
```

To run the code from the beginning, type the following:

```gdb
(gdb) r
The program being debugged has been started already.
Start it from the beginning? (y	or n)  

Starting program: /nfs/nfs6/home/aabhutta/forge/SOIC-C-UNIX/Spring1-2017/debugging_src/dbgme 

Breakpoint 1, main () at myprog.c:29
(gdb)
```

### Examining Variables

The code is now stopped at the first executable line in main.c. With the program stopped, we can inspect the variables that are in this function.

```gdb
(gdb) p	i
$1 = 0
```

The **p** stands for print. This prints the value of the variable of i
which is 0 in this case. The $1 is a gdb notation that allows for the
reuse of this value later in the debugging session.

You could also look at the value of global variables such as alphabet. Initially this variable is uninitialized and has all 0s for values.

```gdb
(gdb) p	alphabet
$2 = '\000' <repeats 25	times>
(gdb)
```

### Stepping the program

It is helpful to execute the program one line at at a time. There are several options for accomplishing this task.

* **s**: Step the program and step into functions.
* **n**: Step the program and step over functions.
* **fin**: Finish the current function. 
* **c**: Run the program normally from the current location. 

Because we set a breakpoint inside of **reverse_print_alpha**, let's continue to this breakpoint.

```gdb
(gdb) c
Continuing.

Breakpoint 2, reverse_print_alpha (b=0x601050 <alphabet> "") at myprog.c:18
```

Single step the program, until the program gets to the call to **initialize_alpha()**.

```gdb
(gdb) s
(gdb) s
(gdb) 
```

*Tip: Hitting enter on a blank line will cause gdb to execute the last command.* 
Let's step one more time into the function **initialize_alpha()*.

```gdb
(gdb) s
initialize_alphabet (a=0x601050 <alphabet> "") at myprog.c:9
(gdb)
```

Notice that gdb shows you that a function was called and additionally what the arguments are at the call time.

Step several times inside of initialize_alphabet.

```gdb
(gdb) s
(gdb) s
(gdb) s
(gdb) s
(gdb) s
(gdb) s
(gdb) 
```

This code is modifying the array alphabet through the passed pointer a. You can inspect the value of alphabet using the print command.

```gdb
(gdb) p	alphabet
$3 = "ab", '\000' <repeats 23 times>
(gdb)
```

This loop would be tedious to step all the way through. Let's go ahead and finish this function and return to the calling location.

```gdb
(gdb) fin 
Run till exit from #0  initialize_alphabet (a=0x601050 <alphabet> "ab")	at myprog.c:11
reverse_print_alpha (b=0x601050 <alphabet> "abcdefghijklmnopqrstuvwxyz") at myprog.c:21
```

Notice that we are now back to the calling location. Let's go ahead and finish this function as well.

```gdb
(gdb) fin
Run till exit from #0  reverse_print_alpha (b=0x601050 <alphabet> "abcdefghijklmnopqrstuvwxyz")	at myprog.c:21
main ()	at myprog.c:29
```

We are now back at the loop in main. However, we are on the second iteration. You can print the value of i for confirmation.

```gdb
(gdb) p	i
$4 = 1
```

As a reminder about where the breakpoints are set, let's inspect them.

```gdb
(gdb) info break 
Num    	Type   	       Disp Enb	Address	       	   What
1      	breakpoint     keep y  	0x00000000004005f0 in main at myprog.c:29
        breakpoint already hit 1 time
2      	breakpoint     keep y  	0x0000000000400577 in reverse_print_alpha at myprog.c:15
        breakpoint already hit 1 time
3      	breakpoint     keep y  	0x00000000004005fc in main at myprog.c:29
(gdb)
```

This report shows the breakpoints and the number of times that they
have been hit.

Let's continue again to try to hit the breakpoint at reverse_print_alpha.

```gdb
(gdb) c
Continuing.

Breakpoint 2, reverse_print_alpha (b=0x601050 <alphabet> "abcdefghijklmnopqrstuvwxyz") at myprog.c:18
(gdb)
```

Let's step a couple of times to get to the initialize_alphabet() function. Let's print the value of the pointer p prior to the call.

```gdb
(gdb) p	p
$5 = 0x0
```

This indicates that p is NULL. This is going to be a problem. Let's inspect the value of b.

(gdb) p	b
$6 = 0x601050 <alphabet> "abcdefghijklmnopqrstuvwxyz"
(gdb)

The variable b looks correct as it is pointing to alphabet.

### Exiting GDB

To modify and rebuild your code, you will need to leave gdb. Exit the debugger by typing **quit**.

### Rerunning the corrected code

```bash
gdb -tui dbgme
```
Now set a breakpoint at the reverse_print_alpha() call in the main function.

```gdb
(gdb) b	myprog.c:30
Breakpoint 1 at	0x4005f2: file myprog.c, line 30.
(gdb) 
```
Hit **r** to run to the first breakpoint. Then type **c** several times to cause the loop to run and break at this function call. Notice how the bug has been corrected.

```gdb
(gdb) r
Starting program: /nfs/nfs6/home/aabhutta/forge/SOIC-C-UNIX/Spring1-2017/debugging_src/dbgme 

Breakpoint 1, main () at myprog.c:30
Missing separate debuginfos, use: debuginfo-install glibc-2.17-106.el7_2.8.x86_64
(gdb) c
Continuing.
zyxwvutsrqponmlkjihgfedcba

Breakpoint 1, main () at myprog.c:30
(gdb) c
Continuing.
zyxwvutsrqponmlkjihgfedcba

Breakpoint 1, main () at myprog.c:30
```
## What to turn in

In this assignment, you will be doing a demo of the the following for
the AI's. While the AI is watching, do the following operations:

* Start GDB on the corrected version of the code. GDB should be started
  in TUI mode.
* Set a break point at line 22 of the code.
* Continue the code to hit the breakpoint.
* Single single step the code using either **s** or **n**.
* On each pass through the loop, print out the dereferenced value of p
  using **p *p**.
* When \*p is equal to **w**, finish the function using **fin**.
* Print the value of i.
* Continue several time inspecting the value of i until it reaches 3.
* Clear the breakpoint by typing **d 1**. This deletes breakpoint #1.
* Run the code to completion using **r**.

Successful completion of this demo will allow you to earn points for this quiz/assignment.









