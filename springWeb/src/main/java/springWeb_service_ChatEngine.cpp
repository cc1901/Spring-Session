#include "springWeb_service_ChatEngine.h"
#include "ChatEngine.h"
#include <string>
#include <iostream>

aisms::ChatEngine *chatEngine;

JNIEXPORT jstring JNICALL Java_springWeb_service_ChatEngine_chat
  (JNIEnv *env, jobject obj, jstring text, jstring context){
    const char* textInCache = env->GetStringUTFChars(text, JNI_FALSE);
    const char* contextInCache = env->GetStringUTFChars(context, JNI_FALSE);
    std::string textString = textInCache;
    std::string contextString = contextInCache;
    env->ReleaseStringUTFChars( text, textInCache);
    env->ReleaseStringUTFChars( context, contextInCache);
    std::string result = chatEngine->process(textString, contextString);
    return env->NewStringUTF(result.c_str());
  }

JNIEXPORT jint JNICALL JNI_OnLoad( JavaVM *vm, void *pvt ){
        std::cout<<"************************************* JNI_OnLoad called\n";

        JNIEnv *env;
        jint onLoad_err = -1;
        if(vm->GetEnv((void**) &env, JNI_VERSION_1_6) != JNI_OK){
           return onLoad_err;
        }
        if (env == NULL) {
           return onLoad_err;
        }
        jclass javaChatEngine = env->FindClass("springWeb/service/ChatEngine");
        jmethodID mid = env->GetStaticMethodID(javaChatEngine, "configFile", "()Ljava/lang/String;");
        if (env->ExceptionCheck()) {
           return onLoad_err;
        }
        jstring configFile = (jstring)env->CallStaticObjectMethod(javaChatEngine, mid);
        if (env->ExceptionCheck()) {
           return onLoad_err;
        }
        const char* configFileString = env->GetStringUTFChars(configFile, 0);
        std::cout<<"config file: "<<configFileString<<"=====================\n";
        chatEngine = new aisms::ChatEngine();
        chatEngine->init(configFileString);
        return JNI_VERSION_1_6;
  }

JNIEXPORT void JNICALL JNI_OnUnload( JavaVM *vm, void *pvt ){
        delete chatEngine;
        std::cout<<"************************************* JNI_OnUnload called\n";
  }