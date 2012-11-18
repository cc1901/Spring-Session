#ifndef CHAT_ENGINE_H
#define CHAT_ENGINE_H
#include <string>

namespace aisms{
    class ChatEngine {
    public:
        std::string process(std::string, std::string);
        bool init(const std::string);
    };
}
#endif

