#include <jni.h>
#include "najkraciputevi_FloydWarshallAlgorithm.h"
#include <vector>
#include <climits>
using namespace std;

JNIEXPORT jint JNICALL Java_najkraciputevi_FloydWarshallAlgorithm_runAlgorithmNative(JNIEnv *env, jobject obj, jint start, jint end) {
    // Potrebne JNI methode.
    jclass thisClass = env->GetObjectClass(obj);
    jfieldID fidG = env->GetFieldID(thisClass, "g", "Lnajkraciputevi/Graph;");
    jobject gObject = env->GetObjectField(obj, fidG);
    jclass gClass = env->GetObjectClass(gObject);
    jclass integerClass = env->FindClass("Ljava/lang/Integer;");
    jmethodID midGetN = env->GetMethodID(gClass, "getN", "()I");
    jmethodID midGetNeighbourAt = env->GetMethodID(gClass, "getNeighbourAt", "(II)Ljava/lang/Integer;");
    jmethodID midGetWeightAt = env->GetMethodID(gClass, "getWeightAt", "(II)Ljava/lang/Integer;");
    jmethodID midIntValue = env->GetMethodID(integerClass, "intValue", "()I");
    jmethodID midGetNumNeighbours = env->GetMethodID(gClass, "getNumNeighbours", "(I)I");
    jmethodID midSetMinDistAt = env->GetMethodID(thisClass, "setMinDistAt", "(II)V");

    // Stvaranje grafa.
    jint n = env->CallIntMethod(gObject, midGetN);
    vector<vector<int> > minDist(n, vector<int>(n, INT_MAX));
    for (int i = 0; i < n; ++i) {
        jint m = env->CallIntMethod(gObject, midGetNumNeighbours, i);
        for (int j = 0; j < m; ++j) {
            jobject neighbourInteger = env->CallObjectMethod(gObject, midGetNeighbourAt, i, j);
            jint neighbour = env->CallIntMethod(neighbourInteger, midIntValue);
            jobject weightInteger = env->CallObjectMethod(gObject, midGetWeightAt, i, j);
            jint weight = env->CallIntMethod(weightInteger, midIntValue);
            minDist[i][neighbour] = weight;
        }
        minDist[i][i] = 0;
    }

    // Algoritam.
    for (int k = 0; k < n; ++k) {
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (minDist[i][k] != INT_MAX && minDist[k][j] != INT_MAX && minDist[i][k]+minDist[k][j] < minDist[i][j]) {
                    minDist[i][j] = minDist[i][k] + minDist[k][j];
                }
            }
        }
    }

    // Za rekonstrukciju najkraćeg puta.
    for (int i = 0; i < n; ++i) {
        env->CallVoidMethod(obj, midSetMinDistAt, i, minDist[start][i]);
    }

    return minDist[start][end];
}
