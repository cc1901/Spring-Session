g++ -fPIC -I /Developer/SDKs/MacOSX10.6.sdk/System/Library/Frameworks/JavaVM.framework/Versions/A/Headers -c ChatEngine.cpp -o ChatEngine.o
g++ -fPIC -I /System/Library/Frameworks/JavaVM.framework/Versions/CurrentJDK/Headers -I /Developer/SDKs/MacOSX10.6.sdk/System/Library/Frameworks/JavaVM.framework/Versions/A/Headers -c springWeb_controller_HelloWorld.cpp -o HelloWorld.o
g++ -dynamiclib -fPIC -o libhelloworld.jnilib HelloWorld.o ChatEngine.o