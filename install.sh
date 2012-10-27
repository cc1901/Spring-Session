#!/bin/sh
#variables
src_path=`pwd`
java_pack=$src_path/jdk-6u31-linux-i586.bin
java_path=$src_path/jdk1.6.0_31
tomcat_pack=$src_path/apache-tomcat-6.0.35.tar.gz
tomcat_path=$src_path/apache-tomcat-6.0.35
usr=$USER
bashrc=/$USER/.bashrc
bashrc_tmp=/$USER/.bashrc.temp
lib_path=`echo .:$src_path/bin`
#functions
function installJava(){
	if [ ! -d "$java_path" ]
	then
		echo "extracting java..."
		./jdk-6u31-linux-i586.bin
		echo "java installed path:$java_path"
	fi
}
function setUserEnv(){
    echo "initializing user environment..."
	cat $bashrc | awk '{print $0}END{env="export JAVA_HOME='$java_path'\nexport PATH=$PATH:$JAVA_HOME/bin\nexport LD_LIBRARY_PATH=.:'$src_path'/bin\n";print env}' > $bashrc_tmp
	cp -f $bashrc_tmp $bashrc
	source $bashrc
	echo "initialization success."
}

function installTomcat(){
	if [ ! -d "$tomcat_path" ]
	then
		echo "extracting tomcat..."
		tar zxvf $tomcat_pack
		cp $src_path/log4j-1.2.8.jar $tomcat_path/lib
		echo "tomcat installed path:$tomcat_path"
	fi
}
function startup(){
	echo "starting tomcat..."
	cd $tomcat_path/bin
	./shutdown.sh
	./startup.sh
}
function check(){
	if [ $java_path = $JAVA_HOME ] 
	then
		echo "java home set correctly!"
	else
		echo "java home set incorrectly!"
	fi
	if [ "$lib_path" = "$LD_LIBRARY_PATH" ]
	then
		echo "library path set correctly!"
	else
		echo "library path set incorrectly!"
	fi
}

function installWar(){
	echo "installing web application..."
	cp $src_path/RobotServices.war $tomcat_path/webapps
	echo "web application install finish!"
}

function installBinForTemp(){
	echo "installing bin(this is a temporary method)..."
	cp -r $src_path/bin/* $tomcat_path/bin/
	cp -r $src_path/data $tomcat_path/data
	echo "bin install finish!"
}
function remind(){
	echo "###########################################"
	echo "#YOU STILL NEED TO DO BELOW STEPS MANUALLY#"
	echo "###########################################"
	echo "1, Open $tomcat_path/conf/server.xml"
	echo "2, Find string [connectionTimeout]"
	#useBodyEncodingForURI="true" URIEncoding="UTF-8"
	echo "3, Add string [useBodyEncodingForURI=\"true\"] and [URIEncoding=\"UTF-8\"] to this xml node as attribute"
	echo "4, Save and exit"
	echo "5, Open $tomcat_path/webapps/RobotServices/TestClinet.jsp, and modify the IP address and Port"
	echo "6, Logout current user: $USR then login again to enable the envrioment variables"
	echo "7, Goto $tomcat_path/bin, run [sh shutdown.sh; sh startup.sh]"
}

function checkRequiredFiles()
{
	if [ -e $java_pack ]
	then
		echo "$java_pack exist."
	else
		echo "$java_pack dose not exist, please download \"jdk-6u31-linux-i586.bin\" from http://www.oracle.com/technetwork/java/javase/downloads/jdk-6u31-download-1501634.html"
		exit
	fi
	
	if [ -e $tomcat_pack ]
	then
		echo "$tomcat_pack exist."
	else
		echo "$tomcat_pack dose not exist, please download \"apache-tomcat-6.0.35.tar.gz\" from http://apache.etoak.com/tomcat/tomcat-6/v6.0.35/bin/apache-tomcat-6.0.35.tar.gz"
		exit
	fi 
}

#command
setUserEnv