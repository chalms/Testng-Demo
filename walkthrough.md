#TestNG - Demo 
**SE 4452A, October 30th 2014**

This is a walkthrough for setting up unit and integration tests, and custom testing reports, for Java using TestNG. The commands are displayed for Mac OSX but can be performed using their equivalents in Linux or Windows. 

#### Getting Started 

**Download Sublime Text 3**

In the interest of learning more about these types of web applications, we are going to setup this project without using eclipse. Go to http://www.sublimetext.com/3 and install Sublime Text 3, and install the shell command 'sublime' into your bin so that you can open folders in sublime from the command line.

**Download Maven** 

If you dont have Apache's Maven, download it for Java projects. It allows you to easily download and install open source Java classes to use for your build. Make sure to add the shell command 'mvn' or 'maven' to your bin, to run maven from the command line. Download it at: 

http://maven.apache.org/download.cgi

#### Setup Project Folder 

Set up a folder for your project. This folder will need a specific architecture for running maven plugins. It will need a 'pom.xml' file, which effectively describes your maven project and any plugins you are using. Start off by setting up an initial directory structure which looks like this: 

    AppName
        |--src
        |   |--main
        |      |--java
        |         |--org
        |            |--app
        |                |--WebApplication.java
        |--test
        |   |--java
        |       |--WebApplicationTest.java
        |
        |---target 
        |---pom.xml

This would correspond to the following bash commands: 

```bash
    $ mkdir -p AppName/main/java/org/app
    $ touch YourApp/main/java/org/app/WebApplication.java
    $ mkdir -p test/java
    $ touch test/java/WebApplicationTest.java
    $ mkdir target
    $ touch pom.xml
```

And open them all in sublime with the following command: 

```bash
    sublime AppName
```

#### Setting Up JAVA_HOME environment variable

Now before we start setting up anything else, lets first set up our JAVA_HOME environment variable in the new project. Change to your project directory. 

```bash 
    $ cd AppName
```

Now check the contents of your JAVA_HOME environment variable, if nothing displays, then act accordingly. 

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

#### Setting Up TestNG

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

#### WebApplication.java

Temporarily fill in the contents of WebApplication.java like so: 

```java
    package org.app; 

    public static class WebApplication {
        public class main(String[] args) {
            System.out.println("Running!");
        }
    }
```

And fill in the contents of WebApplicationTest.java like this: 

```java
    package test.java; 

    public class WebApplicationTest {

        @Test
        public void testMain() {
            // we will fill this in later
        }
    }
```

#### Maven Setup 

Now that we have ensured our folder environment is setup correctly, lets download and install the packages we will need to use to build a web server and automated testing environment. Add the following to give your pom.xml a bit of structure moving forward: 

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <!-- Below should match the package of your WebApplication.java -->
    <groupId>org.app</groupId> 

    <!-- For below, mine was 'testng-demo'  -->
    <artifactId> '< SOME OTHER NAME >' </artifactId>
    <version>0.1-SNAPSHOT</version>
    <packaging>jar</packaging>
    <name> '< SOME NAME >' </name>

    <!-- You will put your dependencies into the tag below -->
    <dependencies>
    </dependencies>

    <build>
        <testSourceDirectory>src/test/java</testSourceDirectory>
        <!-- You will put your plugins into the tag below -->
        <plugins> 
        </plugins>
    </build>
</project>
```

#### Plugins and Dependancies 

**Jetty**

We are going to use an asynchronous server from Maven plugin 'Jetty'. Paste the following dependency into your pom.xml: 

```xml
    <!-- paste this above your dependancies -->
    <properties>
    <jettyVersion>7.2.0.v20101020</jettyVersion>
    </properties>

    <!-- the dependancies tag from above -->
    <dependencies> 
        <dependency>
            <groupId>org.eclipse.jetty</groupId>
            <artifactId>jetty-server</artifactId>
            <version>${jettyVersion}</version>
        </dependency> 
    </dependencies>
```

And then paste the plugin: 

```xml
<!-- the plugins tag from above -->
<plugins>
    <plugin>
        <groupId>org.mortbay.jetty</groupId>
        <artifactId>jetty-maven-plugin</artifactId>
        <version>${jettyVersion}</version>
        </plugin>
        <plugin>
        <groupId>org.codehaus.mojo</groupId>
        <artifactId>exec-maven-plugin</artifactId>
        <version>1.1</version>
        <executions>
            <execution><goals><goal>java</goal></goals></execution>
        </executions>
        <configuration>
            <mainClass>org.server.Start</mainClass>
        </configuration>
    </plugin>
</plugins>
```

**TestNG**

The dependancy:  

```xml
<dependency>
    <groupId>org.testng</groupId>
    <artifactId>testng</artifactId>
    <version>6.3.1</version>
    <scope>test</scope>
</dependency>
```

And the Maven Surefire plugin, to automatically generate html containing information about your testNG results. This declares the group names we will use in our java test files, it specifies that we will allow some tests to run concurrently, and that the maximum number of concurrent threads is 10. 

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>2.17</version>
    <configuration>
        <systemPropertyVariables>
            <propertyName>firefox</propertyName>
        </systemPropertyVariables>
        <groups>running,get-server,server-running,start,stopping</groups>
        <parallel>methods</parallel>
        <threadCount>10</threadCount>
    </configuration>
</plugin>
```

**Apache HTTP Components**

Add this to automate HTTP Clients for testing purposes. 

```xml
<dependency>
    <groupId>org.apache.httpcomponents</groupId>
    <artifactId>httpclient</artifactId>
    <version>4.1.1</version>
</dependency>
```

#### Setup Jetty Server

Create a folder (package) next to your package 'app'. This server should have two files: 'Start.java' and 'Stop.java'. 

```bash
$ mkdir src/main/java/org/server
$ touch src/main/java/org/server/Start.java
$ touch src/main/java/org/server/Stop.java
```

Paste the following code to create a multithreaded process that creates a server and then monitors it. 

```java
package org.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.demo.WebApplication;
import org.demo.TestResultServlet;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.io.IOException;

public class Start {

  private static Server server;

  public static void main(String[] args) throws Exception
  {
      Server server = pairServerHandler(getServer(), getDefaultHandler());
      startMonitor();
      server.start();
      server.join();
  }

  public static Server getServer() { return new Server(getServerPort()); }

  public static int getServerPort() { return 8080; }

  public static int getServerSocketPort() { return 8079; }

  public static void startMonitor() { getMonitor().start();  }

  public static Server pairServerHandler(Server s, AbstractHandler h) {
    s.setHandler(h);
    return s;
  }

  public static AbstractHandler getDefaultHandler() { return new WebApplication(); }

  public static InetAddress getInetAddress(String name)  throws UnknownHostException { return InetAddress.getByName(name); }

  public static ServerSocket getServerSocket(String address) {
    try {
      return new ServerSocket(getServerSocketPort(), 1, getInetAddress(address));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  static class MonitorThread extends Thread {

    private ServerSocket socket;
    private Boolean daemon = true;
    public Boolean started = false;

    public MonitorThread(String name, String address) {

        setDaemon(daemon);
        setName(name);
        socket = getServerSocket(address);
    }

    @Override
    public void run() {

        System.out.println("*** running jetty 'stop' thread");
        started = true;
        Socket accept;

        try {
            accept = socket.accept();
            BufferedReader reader = new BufferedReader(new InputStreamReader(accept.getInputStream()));

            reader.readLine();
            System.out.println("*** stopping jetty embedded server");

            server.stop();
            accept.close();
            socket.close();

        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
  }

  public static Thread getMonitor() { return new MonitorThread("StopMonitor", "127.0.0.1"); }
}
```

Then add the following code to 'Stop.java' to stop the server when the class is called:

```java
package org.server;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Stop {

    public static void main(String[] args) throws Exception {
        Socket s = new Socket(InetAddress.getByName("127.0.0.1"), 8079);
        OutputStream out = s.getOutputStream();
        System.out.println("*** sending jetty stop request");
        out.write(("\r\n").getBytes());
        out.flush();
        s.close();
    }
}
```

Now, create a default handler for the server in 'WebApplication.java'. Remove the current code and replace it with the following: 

```java
package org.demo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import org.server.Start;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class WebApplication extends AbstractHandler
{
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response)
        throws IOException, ServletException
    {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        response.getWriter().println("<h1>Hello World</h1>");
    }
}
```

You can test that this server works (and before, clean and install all your packages) by typing:

```
$ mvn clean packages
$ mvn install packages
$ mvn jetty:run
```

You can view the created webpage at http://localhost:8080/ 

#### TestNG Classes 

Now to test the functionality of this server, we should set up three classes:

- StartTest.java
- StopTest.java
- WebApplicationTest.java

Each of these three classes will be tested with TestNG, and will monitor the current state of our code. Create them with the following commands: 

```bash 
$ touch src/test/java/StartTest.java
$ touch src/test/java/StopTest.java
$ touch src/test/java/WebApplicationTest.java
```

**StartTest.java:** 

In StartTest.java, we will enter the methods to test the server, the request handler and the server monitor thread. Start off by pasting the following imports and skeleton class: 

```
package test.java;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.server.Start;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.io.IOException;
import org.eclipse.jetty.server.Connector;
import org.demo.WebApplication;

public class StartTest {
  public static org.eclipse.jetty.server.Server server;
  public static Thread monitor;
}
```

Now, lets add some methods to test the server. 

The following method will first test that the server is of the correct type and exists. 

```java
  @Test(groups={"get-server"})
  public static void testStart() throws Exception {
    server = Start.getServer();
    org.testng.Assert.assertEquals(server.getClass(), Server.class, "Server object exists");
  }
```

Next, we can use two methods to run in concurrent threads as soon as the above method is complete. We can synchronize the execution due to the 'dependsOnGroups tag'. These will test that the monitoring thread was created and has the appropriate thread name, and that the handler was assigned correctly to the server: 

```java 
  @Test(groups={"server-running"}, dependsOnGroups={"get-server"})
 public static void testMonitor() throws Exception {
    monitor = Start.getMonitor();
    org.testng.Assert.assertEquals("StopMonitor", monitor.getName(), "Monitor Name");
  }

  @Test(groups={"server-running"}, dependsOnGroups={"get-server"})
  public static  void testSetHandler() throws Exception {

    server.setHandler(((AbstractHandler) Start.getDefaultHandler()));
    
    org.testng.Assert.assertNotSame(server.getHandler().getClass(), AbstractHandler.class, "Server has a handler");
  }
```

Now add this last method to start the monitor thread and the server thread after the monitor created and the handler assigned. 

```java
  @Test(groups={"start"}, dependsOnGroups={"server-running"})
  public static void testThreadStart() throws Exception {

    monitor.start();
    server.start();

    org.testng.Assert.assertEquals(true, monitor.isAlive(), "Monitor has started");

    org.testng.Assert.assertEquals(true, (server.isStarted() || server.isStarting()), "Server has started");
  }
```

**WebApplicationTest.java**

The following test will run after your server has started, and it will issue requests to your website at the specified address. When the webpage is returned by the server, it tests if the webpage contents match what you expect they will:

```java
package test.java;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.server.Start;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class WebApplicationTest {

  @Test(groups = {"running"}, dependsOnGroups={"start"})
  public void test() throws Exception {

    String userId = "invalid";
    HttpClient client = new DefaultHttpClient();
    HttpGet mockRequest = new HttpGet("http://localhost:8080/");
    mockRequest.setHeader("http-user", userId);
    HttpResponse mockResponse = client.execute(mockRequest);
    BufferedReader rd = new BufferedReader(new InputStreamReader(mockResponse.getEntity().getContent()));
    String line;
    String str = "";
    String testString =  "<h1>Hello World</h1>";

    while ((line = rd.readLine()) != null) str += line;
    org.testng.Assert.assertEquals(true, str.contains(testString), "Website Output Correct!");
  }
}
```

**StopTest.java**

Adding the following to StopTest.java will close the server after our WebApplicationTest is complete. 

```java 

package test.java;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.server.Stop;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.io.IOException;

public class StopTest {

  @Test(groups = {"stopping"}, dependsOnGroups = { "running" })
   public void testStop() throws Exception {
      Stop.main(null);
     // org.testng.Assert.assertEquals(true, true, "Server is stopped!");
  }
}
```

**Running The Tests**

Now, you can run the above tests with the command:

```
$ mvn test
```

#### Automatically Display Test Results
When I enter the command,

```bash
$ runtests
```

from my project folder, chrome automatically displays the test results to me. It also saves the test results as a page that can be served up by the website. In order to do this, first run:

```bash
$ mvn test
```

This will create a new folder in your '/target' directory named '/surefire-reports'. If you open the file '/target/surefire-reports/index.html' in chrome, it will display the html results of your last unit test.

Now, lets build the executable.

#### Creating the .sh file

Before we go any furthur, create a folder for the test results to be served as a webpage:

```bash
$ mkdir -p src/main/webapp/test-results
```

Now, create a file in the root your project folder called 'test.sh'. Make this folder and executable and open it in sublime. This will be used to run the processes required to write and display test result content.

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

#### Creating a set of shell commands to run in any java project

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

#### Adding a .sh file your bin

The last step is to move our './tests.sh' file to our newly created bin, and give this command the name we desire. When placing the tests.sh in my 'bin' folder, I will no longer need the '.sh' at the end to run it. I can effectively call it whatever I want, and in this case I chose the name *runtests*.

All you have to do is move the './tests.sh' file to the new 'bin' with your custom filename:

```bash
$ mv tests.sh /Users/achalmers/.java/bin/runtests
```

#### Run Your Testing Application!

Enter the command:

```bash
$ runtests
```

*And Voila.*


