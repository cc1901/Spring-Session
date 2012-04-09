#! /usr/bin/ruby

require 'buildr/jetty'
require 'readline'
require 'fileutils'
include FileUtils

repositories.remote << "http://repository.springsource.com/maven/bundles/release"
repositories.remote << "http://repository.springsource.com/maven/bundles/external"
repositories.remote << 'http://www.ibiblio.org/maven2'
repositories.remote << 'http://download.java.net/maven/2'
repositories.remote << 'http://repo1.maven.org/maven2'
repositories.remote << 'https://repository.jboss.org/nexus/content/groups/public-jboss'

CGLIB = struct(
    :core => transitive("cglib:cglib:jar:2.2.2")
)

GUICE = 'com.google.inject:guice:jar:3.0'

BEAN_VALIDATION_API='javax.validation:validation-api:jar:1.0.0.GA'
LOG4J='log4j:log4j:jar:1.2.16'

JAVA_INJECT = transitive('javax.inject:javax.inject:jar:1')

SPRING_VERSION = '3.1.0.RELEASE'
SPRING = struct(
    :core => group('spring-core', 'spring-beans', 'spring-context', 'spring-context-support', :under=>"org.springframework", :version=>SPRING_VERSION),
    :asm => "org.springframework:spring-asm:jar:#{SPRING_VERSION}",
    :expression => "org.springframework:spring-expression:jar:#{SPRING_VERSION}",
    :jdbc => transitive("org.springframework:spring-jdbc:jar:#{SPRING_VERSION}"),
    :mvc => transitive("org.springframework:spring-webmvc:jar:#{SPRING_VERSION}"),
    :test => "org.springframework:spring-test:jar:#{SPRING_VERSION}"
)

HIBERNATE_VALIDATOR_ANNOTATION = group('hibernate-validator-annotation-processor', 'hibernate-validator',
                                       :under=>'org.hibernate', :version=>'4.1.0.Final')

JETTY_VERSION = "6.1.3"
SLF4J_VERSION = "1.4.3"

# Libraries used by Jetty.
JETTY_REQUIRES = ["org.mortbay.jetty:jetty:jar:#{JETTY_VERSION}", "org.mortbay.jetty:jetty-util:jar:#{JETTY_VERSION}",
                  "org.mortbay.jetty:servlet-api-2.5:jar:#{JETTY_VERSION}", "org.slf4j:slf4j-api:jar:#{SLF4J_VERSION}",
                  "org.slf4j:slf4j-simple:jar:#{SLF4J_VERSION}", "org.slf4j:jcl104-over-slf4j:jar:#{SLF4J_VERSION}"]
JETTY_JSP = group('jsp-api-2.1', 'jsp-2.1', :under=> 'org.mortbay.jetty', :version=> Buildr::Jetty::VERSION)

SLF4J_VERISON = "1.5.6"
SLF4J = struct(
    :api => "org.slf4j:slf4j-api:jar:#{SLF4J_VERISON}",
    :runtime => transitive("org.slf4j:slf4j-log4j12:jar:#{SLF4J_VERISON}")
)

RUNTIME_DEPENDENCY = [SLF4J]

PROJECT_ROOT = File.join(File.dirname(__FILE__), ".")
WEB_APP_DIR = "#{PROJECT_ROOT}/app/src/main/webapp"
MINIFY_TARGET = "#{PROJECT_ROOT}/app/target/temp_"

define 'spring' do
  project.version = '1.0'
  project.group = 'springSession'
  compile.options.target = '1.5'

  task :build => [:clean]

  define 'springApplication' do
    APPLICATION_DEPENDENCY = struct(
        :spring=> SPRING,
        :cglib => CGLIB
    )

    compile.with APPLICATION_DEPENDENCY
  end

  define 'springWeb' do

    WEB_DEPENDENCY = struct(
        :beanValidationApi => BEAN_VALIDATION_API,
        :log4j => LOG4J,
        :spring => SPRING,
        :validator => HIBERNATE_VALIDATOR_ANNOTATION,
        :cglib => CGLIB,
        :runtime => RUNTIME_DEPENDENCY,
        :javaInject => JAVA_INJECT,
        :injector => GUICE
    )

    compile.with WEB_DEPENDENCY
    package(:war).with :libs=> WEB_DEPENDENCY

    Java.classpath << JETTY_JSP

    task('jetty-deploy' => ["compile", "package"]) do
      jetty.deploy("http://localhost:8080", package(:war))
      Readline::readline('[Type ENTER to stop Jetty]')
    end
  end
end