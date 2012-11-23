#! /usr/bin/ruby

require 'buildr/jetty'
require 'readline'
require 'fileutils'
include FileUtils

repositories.remote << 'http://mvnrepository.com/artifact'
repositories.remote << "http://repository.springsource.com/maven/bundles/release"
repositories.remote << "http://repository.springsource.com/maven/bundles/external"
repositories.remote << 'http://www.ibiblio.org/maven2'
repositories.remote << 'http://download.java.net/maven/2'
repositories.remote << 'http://repo1.maven.org/maven2'
repositories.remote << 'https://repository.jboss.org/nexus/content/groups/public-jboss'

EH_CACHE_VERSION = "2.5.0"
EH_CACHE = "net.sf.ehcache:ehcache-core:jar:#{EH_CACHE_VERSION}"

CGLIB = struct(
    :core => transitive("cglib:cglib:jar:2.2.2")
)

GUICE = 'com.google.inject:guice:jar:3.0'
GUAVA = transitive('com.google.guava:guava:jar:13.0.1')

BEAN_VALIDATION_API='javax.validation:validation-api:jar:1.0.0.GA'
LOG4J='log4j:log4j:jar:1.2.16'

JAVA_INJECT = transitive('javax.inject:javax.inject:jar:1')

MOCKITO = 'org.mockito:mockito-all:jar:1.8.5'

SPRING_VERSION = '3.1.0.RELEASE'
SPRING = struct(
    :core => group('spring-core', 'spring-beans', 'spring-context', 'spring-context-support', :under => "org.springframework", :version => SPRING_VERSION),
    :asm => "org.springframework:spring-asm:jar:#{SPRING_VERSION}",
    :expression => "org.springframework:spring-expression:jar:#{SPRING_VERSION}",
    :jdbc => transitive("org.springframework:spring-jdbc:jar:#{SPRING_VERSION}"),
    :mvc => transitive("org.springframework:spring-webmvc:jar:#{SPRING_VERSION}"),
    :test => "org.springframework:spring-test:jar:#{SPRING_VERSION}"
)

JSON_VERSION = "1.9.9"
JSON = group("jackson-core-asl", "jackson-mapper-asl", :under => 'org.codehaus.jackson', :version => JSON_VERSION)


XML = struct(
    :xml_jackson_core => transitive("com.fasterxml.jackson.core:jackson-core:jar:2.0.6"),
    :xml_jackson_data_bind => transitive("com.fasterxml.jackson.core:jackson-databind:jar:2.0.6"),
    :xml_jackson_data_format => transitive("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:jar:2.0.5")
)

HIBERNATE_VERSION = '4.1.7.Final'

HIBERNATE = struct(
    :entity_manager => "org.hibernate:hibernate-entitymanager:jar:#{HIBERNATE_VERSION}",
    :validator_annotation => group('hibernate-validator-annotation-processor', 'hibernate-validator', :under => 'org.hibernate', :version => "4.1.0.Final"),
    :jpa_api => "org.hibernate.javax.persistence:hibernate-jpa-2.0-api:jar:1.0.1.Final"
)

JETTY_VERSION = "6.1.3"
SLF4J_VERSION = "1.4.3"

JETTY_REQUIRES = ["org.mortbay.jetty:jetty:jar:#{JETTY_VERSION}", "org.mortbay.jetty:jetty-util:jar:#{JETTY_VERSION}",
                  "org.mortbay.jetty:servlet-api-2.5:jar:#{JETTY_VERSION}", "org.slf4j:slf4j-api:jar:#{SLF4J_VERSION}",
                  "org.slf4j:slf4j-simple:jar:#{SLF4J_VERSION}", "org.slf4j:jcl104-over-slf4j:jar:#{SLF4J_VERSION}"]

JERSEY_VERSION = "1.10"
JERSEY = struct(
    :client => transitive("com.sun.jersey:jersey-client:jar:#{JERSEY_VERSION}"),
    :jersey_core => transitive("com.sun.jersey:jersey-core:jar:#{JERSEY_VERSION}"),
    :jersey_json => transitive("com.sun.jersey:jersey-json:jar:#{JERSEY_VERSION}"),
    :apache => transitive("com.sun.jersey.contribs:jersey-apache-client:jar:#{JERSEY_VERSION}")
)


JETTY_VERSION = "6.1.3"
SLF4J_VERSION = "1.4.3"

# Libraries used by Jetty.
JETTY_REQUIRES = ["org.mortbay.jetty:jetty:jar:#{JETTY_VERSION}", "org.mortbay.jetty:jetty-util:jar:#{JETTY_VERSION}",
                  "org.mortbay.jetty:servlet-api-2.5:jar:#{JETTY_VERSION}", "org.slf4j:slf4j-api:jar:#{SLF4J_VERSION}",
                  "org.slf4j:slf4j-simple:jar:#{SLF4J_VERSION}", "org.slf4j:jcl104-over-slf4j:jar:#{SLF4J_VERSION}"]
JETTY_JSP = group('jsp-api-2.1', 'jsp-2.1', :under => 'org.mortbay.jetty', :version => Buildr::Jetty::VERSION)

SLF4J_VERISON = "1.5.6"
SLF4J = struct(
    :api => "org.slf4j:slf4j-api:jar:#{SLF4J_VERISON}",
    :runtime => transitive("org.slf4j:slf4j-log4j12:jar:#{SLF4J_VERISON}")
)

FREEMARKER = 'org.freemarker:freemarker:jar:2.3.18'

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
        :spring => SPRING,
        :cglib => CGLIB
    )

    compile.with APPLICATION_DEPENDENCY
  end

  define 'springWeb' do

    WEB_DEPENDENCY = struct(
        :beanValidationApi => BEAN_VALIDATION_API,
        :log4j => LOG4J,
        :spring => SPRING,
        :validator => HIBERNATE.validator_annotation,
        :entity_manager => HIBERNATE.entity_manager,
        :cglib => CGLIB,
        :runtime => RUNTIME_DEPENDENCY,
        :javaInject => JAVA_INJECT,
        :injector => GUICE,
        :freemarker => FREEMARKER,
        :guava => GUAVA,
        :json => JSON,
        :xml => XML,
        :cache => EH_CACHE,
        :jersey => JERSEY
    )

    TEST_DEPENDENCY =struct(
        :mock => MOCKITO,
        :jetty => JETTY_REQUIRES
    )

    compile.with WEB_DEPENDENCY
    package(:war).with :libs => WEB_DEPENDENCY

    test.with TEST_DEPENDENCY

    Java.classpath << JETTY_JSP

    task('jetty-deploy' => ["compile", "package"]) do
      jetty.deploy("http://localhost:8080/chat-engine", package(:war))
      Readline::readline('[Type ENTER to stop Jetty]')
    end
  end
end