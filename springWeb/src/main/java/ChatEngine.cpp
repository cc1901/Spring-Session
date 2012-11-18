#include "./ChatEngine.h"
#include <iostream>

std::string aisms::ChatEngine::process(std::string question, std::string context){
    std::cout<<"question:"<<question<<std::endl<<"context:"<<context<<std::endl;
    std::string result("engine test");
    return result;
}

bool aisms::ChatEngine::init(const std::string fileLocation){
    std::cout<<"file location:"<<fileLocation<<std::endl;
}