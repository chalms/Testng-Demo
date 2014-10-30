




### Setting Up JAVA_HOME environment variable

```bash
$: echo $JAVA_HOME # <- show current location of JAVA_HOME
# if nothing is diplayed
$: vim ~/.bash_profile 
```

'vim' will open current bash_profile, or create one. The ~./bash_profile file  will display (and set) the users custom environment variables. The vim command displays your .bash_profile and thus displayed my computers environment variables. This is my .bash_profile:

```bash
export PATH=/usr/local/bin:$PATH
[[ -s "$HOME/.profile" ]] && source "$HOME/.profile" # Load the default .profile
[[ -s "$HOME/.rvm/scripts/rvm" ]] && source "$HOME/.rvm/scripts/rvm" # Load RVM into a shell session *as a function*
export PATH=/usr/local/git/bin:$PATH
export PGDATA=/usr/share/postgresql/data
export PATH=/bin:/usr/bin:/usr/sbin:/usr/local/bin:/usr/share/postgresql/data:$PATH
```

To edit your .bash_profile using 'vim', hit 'a'. Now scroll down to the bottom and append the following: 

```bash
export JAVA_HOME=$(/usr/libexec/java_home)
```

Thats it! now hit 'esc' to exit insert mode, and then ':' to enter a the vim command mode. Type 'wq' to write ('w') the new text, then quit ('q') editing the document. We can now test if we added the $JAVA_HOME to the classpath. Yours should look something like the following: 

```bash 
$ echo $JAVA_HOME
/Library/Java/JavaVirtualMachines/jdk1.8.0_05.jdk/Contents/Home
```

For more vim commands, visit: http://worldtimzone.com/res/vi.html

### Setting Up TestNG 

In order to set up TestNG correctly, first download the TESTNG jar file from the following link: http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22testng%22

Then save somewhere on your computer. I saved mine as ~/Downloads/testng-6.8.8. Yours may be a different version than mine, so just change '6.8.8' to your version for the rest of the commands. 

Now, create a folder on your computer to store testng. My folder was /Library/TESTNG. You can create your folder with the command. 

```bash
$ mkdir /Library/TESTNG
```

Then copy the newly downloaded testng file to your new directory. 

```bash
$ cp ~/Downloads/testng-6.8.8 /Library/TESTNG/testng-6.8.8
```

Now, you want to add this testng JAR file to the end of your java project classpath. In MAC OSX this classpath is defined by the '$CLASSPATH' environment variable. You can append the testng framework this environment variable by performing the following:

```bash
$ export CLASSPATH=$CLASSPATH:/Library/TESTNG/testng-6.8.8.jar
```

Your can 