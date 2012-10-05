#include "springWeb_controller_HelloWorld.h"
#include "ChatEngine.h"
#include <string>
#include <iostream>

ChatEngine *chatEngine;

JNIEXPORT jstring JNICALL Java_springWeb_controller_HelloWorld_test
  (JNIEnv *env, jobject obj){
    
    std::string result = chatEngine->talk();
    return env->NewStringUTF(result.c_str());
  }



// JNIEXPORT void JNICALL Java_springWeb_controller_HelloWorld_setChatEnginePointer
//   (JNIEnv *env, jobject obj){
//     // jclass clazz = env->GetObjectClass(env, obj);
//     // jmethodID methodId = env->GetMethodID(env, clazz, "setChatEnginePointer", "int");
//     // env->CallVoidMethod(env, obj, methodId, 100);
//   }

JNIEXPORT jint JNICALL JNI_OnLoad( JavaVM *vm, void *pvt ){
        std::cout<<"************************************* JNI_OnLoad called\n";
        chatEngine = new ChatEngine();
        return JNI_VERSION_1_1;
  }

JNIEXPORT void JNICALL JNI_OnUnload( JavaVM *vm, void *pvt )
        {
        delete chatEngine;
        std::cout<<"************************************* JNI_OnUnload called\n";
  }




