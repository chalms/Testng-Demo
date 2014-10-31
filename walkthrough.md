
**Setting Up JAVA_HOME environment variable**

```bash
    $ echo $JAVA_HOME # <- show current location of JAVA_HOME
    # if nothing is diplayed
    $ vim ~/.bash_profile
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

### Automatically Display Test Results

When I enter the command,

```bash
$ runtests
```

from my project folder, chrome automatically displays the test results to me. It also saves the test results as a page that can be served up by the website. In order to do this, first run:

```bash
$ mvn test
```

This will create a new folder in your '/target' directory named '/surefire-reports'. If you open the file '/target/surefire-reports/index.html' in chrome, it will display the html results of your last unit test.

Create a folder for the test results to be served as a webpage:

```bash
$ mkdir src/main/webapp/test-results
```


Now, lets build the executable.

**Creating the .sh file**

Create a file in the root your project folder called 'test.sh'. Make this folder and executable and open it in sublime. This will be used to run the processes required to write and display test result content.

```bash
$ touch test.sh
$ chmod +x test.sh
$ sublime test.sh
```

We now want to fill this shell script with some code to automate running the test and displaying the outputs. We need to add some conditionals to avoid attempting to display or copy the test contents if they are not created due to a failure in maven.


In test.sh:
```bash
#!/bin/sh
if mvn test ; then
  if cp -r target/surefire-reports/* src/main/webapp/test-results ; then
    if cp -r src/main/webapp/my-css.css src/main/webapp/test-results/testng.css; then
      open src/main/webapp/test-results/index.html
    fi
  fi
fi
```

The line '#!/bin/sh' tells the computer that the file is a shell script. 'mvn test' runs the test cases. The command 'cp -r target/surefire-reports/* src/main/webapp/test-results' copy's the contents of the surefire output directory to a directory that can be easily served by the web app. The command after that replaces the stylesheet produced by surefire, with my custom stylesheet. The line 'open src/main/webapp/test-results/index.html' opens the 'index.html' file from that directory in your browser.

You can test that this file works correctly by entering the command:

```bash
$ ./test.sh
```

**Creating a set of shell commands to run in any java project**

To save this file as a shell command, there are a few things we must do. First, create an area in your computer where you want to store call shell commands for your java projects. I set mine to '.java'.

```bash
$ mkdir ~/.java
```

Next, create a folder called 'bin' inside the '.java' folder, where you will put all your executable files.

```bash
$ mkdir ~/.java/bin
```

Then add this new 'bin' folder to your $PATH environment variable. This $PATH environment variable stores the custom commands from different '.sh' files, in many different 'bin' folders across your computer. You can append to the PATH variable with the following command: 'PATH=$PATH:{absolute path to the bin folder you want to add}'. So for my '.java/bin', I ran the following command:

```bash
$ PATH=$PATH:/Users/achalmers/.java/bin
```

**Add the .sh file your bin**

The last step is to move our './tests.sh' file to our newly created bin, and give this command the name we desire. When placing the tests.sh in my 'bin' folder, I will no longer need the '.sh' at the end to run it. I can effectively call it whatever I want, and in this case I chose the name *runtests*.

All you have to do is move the './tests.sh' file to the new 'bin' with your custom filename:

```bash
$ mv tests.sh /Users/achalmers/.java/bin/runtests
```


**Now run it.**

Enter the command:

```bash
$ runtests
```

*And Voila.*









