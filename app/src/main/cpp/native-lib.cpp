#include <jni.h>
#include <string>

std::string thilva = "PL42zYQiifYHB-jTB1bXKhryhUI_4J3g8D";
std::string signLanguage = "PL42zYQiifYHDAIvYVmDtn4axkaYK84UBE";
std::string generalPrograms = "PL42zYQiifYHAW0ZqVDJrcCnbvIFGOCwOh";

std::string generalAnnounce = "PL42zYQiifYHC6PaZUC-6NoWQVAYclJRio";


std::string stdOne = "PL42zYQiifYHB6RbtVuLuUF-LEnnoVq5ZQ";
std::string stdTwo = "PL42zYQiifYHBnFwBviGc5YAf85kZ6wyVw";
std::string stdThree = "PL42zYQiifYHCKdSpA6acdxu0I1Dj8BKeF";
std::string stdFour = "PL42zYQiifYHAFfF2MzzLuZ3VR2u0dQ25-";
std::string stdFive = "PL42zYQiifYHDaaLTegGigKkAtX-na4mc-";
std::string stdSix = "PL42zYQiifYHDyxATndSVaMauBAXlDmgE0";
std::string stdSeven = "PL42zYQiifYHB8Xjh7UCH_5mYtoiAV5ycy";
std::string stdEight = "PL42zYQiifYHCcqpBnsCvPLX8sxCixn7jV";
std::string stdNine = "PL42zYQiifYHCvsnV9Jfq3xaXPXZEBKBFE";
std::string stdTen = "PL42zYQiifYHAQWDVd5FuxtypAnX9UJb0q";
std::string stdEleven = "PL42zYQiifYHDouZq9peR2ig_S78UIV7u6";
std::string stdTwelve = "PL42zYQiifYHBVHMcNDqC3EQajuVQNAKyW";

extern "C" JNIEXPORT jstring JNICALL
Java_com_onlinemadrasa_VideoListingActivity_getAPIKey(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "AIzaSyDZX72a6O7Zs-CXfecn7bHLGgjdtpU6YSU";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_onlinemadrasa_YouTubeFailureRecoveryActivity_getAPIKey(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "AIzaSyDZX72a6O7Zs-CXfecn7bHLGgjdtpU6YSU";
    return env->NewStringUTF(hello.c_str());
}



extern "C" JNIEXPORT jstring JNICALL
Java_com_onlinemadrasa_network_GetTask_getAPIKey(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "AIzaSyDZX72a6O7Zs-CXfecn7bHLGgjdtpU6YSU";
    return env->NewStringUTF(hello.c_str());
}


extern "C" JNIEXPORT jstring JNICALL
Java_com_onlinemadrasa_ui_home_HomeFragment_getArrayIDS(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello =
            "Attendance," + thilva + "," + signLanguage + "," + generalPrograms + "," + stdOne +
            "," + stdTwo + "," + stdThree + "," + stdFour + "," + stdFive + "," + stdSix + "," +
            stdSeven + "," + stdEight + "," + stdNine + "," + stdTen + "," + stdEleven + "," +
            stdTwelve + ",PL42zYQiifYHDbyNA3aXyvivF2z-D5B6QQ,PL42zYQiifYHDbH0GX3EFJUtvmcviiBbe9," +
            generalAnnounce + "";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_onlinemadrasa_ui_direct_DirectFragment_getArrayIDS(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello =
            "Submit Attendance," + thilva + "," + signLanguage + "," + generalPrograms + "," +
            stdOne + "," + stdTwo + "," + stdThree + "," + stdFour + "," + stdFive + "," + stdSix +
            "," + stdSeven + "," + stdEight + "," + stdNine + "," + stdTen + "," + stdEleven + "," +
            stdTwelve + ",PL42zYQiifYHDbyNA3aXyvivF2z-D5B6QQ,PL42zYQiifYHDbH0GX3EFJUtvmcviiBbe9," +
            generalAnnounce + "";
    return env->NewStringUTF(hello.c_str());
}
