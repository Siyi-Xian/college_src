# Exercise 1 

## Overview

* Setup your git repository (watch the videos provided on Canvas first)
* Write a C program, run it, and commit it back to your repository. 

## 1 Setting up your Repositories

### 1.1 Setting up an SSH Key

To set up an SSH key, follow the directions at
[The Github SSH Key Generation Tutorial](https://help.github.com/articles/generating-ssh-keys). 

**Note: when executing command "ssh -T git@github.com" from the
tutorial, make sure you're using "ssh -T git@github.iu.edu".**

### 1.2 Making Your Github Repository

* Create a private repository on IU github called
  **C291-Fall1-2017**. You should have read chapter 1-2 of the
  [Pro Git Book](https://git-scm.com/book/en/v2). You will need to
  know how to clone a repository, add files, commit them, and push
  them. Add **aabhutta**, **ranagara**, **bmerugur**, **rahpsing** as collaborators so that they
  can grade the work that you commit to your repository.

### 1.3 Duplication of the Course Repository

The [course repository](https://github.iu.edu/SOIC-C-Unix/Fall1-2017)
will be duplicated using the steps. 

* Change directories to a scratch area. Create a bare clone of the
  Fall1-2017 repository using:

```bash
git clone --bare git@github.iu.edu:SOIC-C-UNIX/Fall1-2017.git
```

Note: If you did not configure SSH yet, you can use the HTTP link to the repository instead (DON'T DO BOTH)
```bash
git clone --bare https://github.iu.edu/SOIC-C-UNIX/Fall1-2017.git
```

* Enter the Fall1-2017.git directory

```bash
cd Fall1-2017.git
```

* Push this repo data to your new repository replacing **yourreponame** with the appropriate git@github.iu.edu address or http address from step 1.2. You can find the exact address by clicking on the green "clone or download" button in your repository on gitHub site. 

```bash
git push --mirror yourreponame
```

* If all went well and your shared repository has the data, you can remove the Fall1-2017.git directory because it is no longer needed.

```bash
cd ..
rm -rf Fall1-2017.git
```

### 1.4) Remote Setup

You will need to keep the original course repository as an upstream
remote for your repository. This repository will be updated
periodically with new lab material. Adding an upstream remote will
allow you to keep your copy synchronized with updated lab material.

* Clone your **C291-Fall1-2017** to your home directory.
```bash
git clone yourreponame
```
* cd into the C291-Fall1-2017 directory. Add an upstream remote to your repository. Be sure to use the correct repo link/name.

```bash
git remote add upstream git@github.iu.edu:SOIC-C-Unix/Fall1-2017.git
```

* In the future, you can pull in updates to the course repository using a fetch command.

```bash
git fetch upstream
```
## 2 C Programming Warmup 

### 2.1 Hello World
In this section of the lab assignment you will
write, compile, and run a simple C program. Additionally, you will
learn how to control its process from the shell.

Create a directory named **exercise/wk1** in the root of your repository. Create a file named **hello.c**, write a C
program that prints "Hello, world" once (and then starts a new line), and then prints a dot (.) every second, forever, or rather until it is killed. You can copy a file to get you started from exercise/wk1. You should copy this file rather than working in this directory. 

You can use an infinite while or for loop (with syntax similar to
Java or found in A Short Tutorial on C.pdf on Canvas) to loop forever. For the five second delay, use the function
call **sleep(3)**. This function lives in the system unistd.h module,
which of course you need to include at the start of the program.

You can use a command sequence such as the following to build and run
your code.

``` bash
$ gcc -o hello hello.c
$ ./hello
hello world
$
```

When you have successfully compiled and run your program, you will
find that until you kill the hello program process it is a real pain
trying to use the same terminal window for anything else. Closing the
terminal window and opening another would solve the problem, but there
are times you don't want to do that because you don't want to loose
things like the command history and current directory associated with
the terminal. So use this opportunity to practice using the shell
commands introduced in the Unix class notes to put the process in the
background, find out its process id, and kill it. Also try using the
**&** command suffix to start it in the background to begin with.

#### 2.2 Push Completed Program

Push the program to git using the following commands:

* Note: this is pushing the entire ex1 folder to your repo. You can also push the individual file too. Just be sure that you are in appropriate folder before you add-commit-push!
~~~
git add ex1
git commit -m 'your comments here'
git push origin master
~~~

Go to the github interface for your repository and confirm that you
have successfully pushed your code.

## 3 Learning Git

As with most of the tools we use in this class, git can either be a
great help or a source of sorrow. One of the most important things you
can learn is how to go back to a previous version of your code. Look
up how to use the following commands **git log** and **git
checkout**. [Here](http://githowto.com/aliases) is also a really good
tutorial about aliases (also a good guide as to how to make sense of
**git log**). There is a lot of documentation about git online. Feel
free to take advantage of these many resources.

We also recommend looking up **git stash** and understanding it's
functionality, as well as setting up a **.gitignore** file so that
certain types of files won't be added to your repository.

## 4 What to turn in

Git labels each commit that you make to your repository with a unique
SHA-1 hash number. This is a 40 character checksum of your commit plus
a header.

In turning in your code, your will need to take two actions.

* Commit and push your code to your repository IU github.
* Submit a link to this commit in canvas. You can get a link to the commit through IU github.
   * Go to your repo's IU github website.
   * Click on "commits". This link will also include the total commits completed for this repo.
   * For the specific commit that includes your assigment, click on the hash id for this commit. You can then grab the link from the address bar in the browser.
   * Submit this link in Canvas for the assignment.
