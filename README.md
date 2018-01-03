# FILE WATCHER SERVICE

* The file watcher service as the name implies consumer files in the incoming folders recursively and writes them into the target directory.
* The source and target directories can be configured external to the build artifact.


## How to build the application

* The application needs to be built using **MAVEN**.
* The maven shade plugin has been used and an **UBER(FAT)** jar would be generated under filewatcher-app target folder.
* The below command should be run in any machine which has maven installed in it. Go to the directory of the code and execute the below maven command.

```
cd /****CODE_DIRECTORY****/

mvn clean install
```

* Please refer -https://maven.apache.org/install.html if you haven't had maven installed in the machine.


## How to run the application

* Once the previous step has been completed, we can run the application using the below command.
* The **file-watcher.jar** jar would be located under filewatcher-app target folder.
* The configuration file **application.conf** and the logback file **logback.xml** location should be mentioned while running the jar. 

```
cd /****CODE_DIRECTORY/filewatcher-app/target****/

java -jar -Dconfig.file=***PATH_OF_THE_CONFIG_FILE***/application.conf -Dlogback.configurationFile=***PATH_OF_THE_LOGBACK_FILE***/logback.xml file-watcher.jar
```
