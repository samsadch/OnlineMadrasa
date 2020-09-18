#include <jni.h>
#include <string>

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
    std::string hello = "Attendance,PL42zYQiifYHB-jTB1bXKhryhUI_4J3g8D,PL42zYQiifYHDAIvYVmDtn4axkaYK84UBE,PL42zYQiifYHBVnxyWEf9IEkvUfFM37dMc,PL42zYQiifYHCNPEzImPqPqmSpxrQf2Kdt,PL42zYQiifYHCvyUUGRZwZ0_MBXxG-lhcS,PL42zYQiifYHDeVy6jmIapGD1ElcdAEgll,PL42zYQiifYHCqc0jbrPvpdMuzpFLHGF5_,PL42zYQiifYHBhD7_Pvelpe90GrO243fZ_,PL42zYQiifYHApFb0-vfPjwNGtb7S8tAb8,PL42zYQiifYHDNn2PcZrc4LuLyiTAp7kxF,PL42zYQiifYHBcYtQdIG9ro_I7FqEiLQIg,PL42zYQiifYHCMc8sF6m5dzkkrG9drKAAC,PL42zYQiifYHBu1gdGHrYNdwoSd9SFhTnk,PL42zYQiifYHDy7FLW4-Og6RmLo1qM10q_,PL42zYQiifYHC6PaZUC-6NoWQVAYclJRio";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_onlinemadrasa_ui_direct_DirectFragment_getArrayIDS(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Submit Attendance,PL42zYQiifYHB-jTB1bXKhryhUI_4J3g8D,PL42zYQiifYHDAIvYVmDtn4axkaYK84UBE,PL42zYQiifYHBVnxyWEf9IEkvUfFM37dMc,PL42zYQiifYHCNPEzImPqPqmSpxrQf2Kdt,PL42zYQiifYHCvyUUGRZwZ0_MBXxG-lhcS,PL42zYQiifYHDeVy6jmIapGD1ElcdAEgll,PL42zYQiifYHCqc0jbrPvpdMuzpFLHGF5_,PL42zYQiifYHBhD7_Pvelpe90GrO243fZ_,PL42zYQiifYHApFb0-vfPjwNGtb7S8tAb8,PL42zYQiifYHDNn2PcZrc4LuLyiTAp7kxF,PL42zYQiifYHBcYtQdIG9ro_I7FqEiLQIg,PL42zYQiifYHCMc8sF6m5dzkkrG9drKAAC,PL42zYQiifYHBu1gdGHrYNdwoSd9SFhTnk,PL42zYQiifYHDy7FLW4-Og6RmLo1qM10q_,PL42zYQiifYHC6PaZUC-6NoWQVAYclJRio";
    return env->NewStringUTF(hello.c_str());
}
