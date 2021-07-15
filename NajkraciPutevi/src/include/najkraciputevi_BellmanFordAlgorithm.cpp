#include <jni.h>
#include "najkraciputevi_BellmanFordAlgorithm.h"
#include <vector>
#include <utility>
#include <climits>
using namespace std;

JNIEXPORT jint JNICALL Java_najkraciputevi_BellmanFordAlgorithm_runAlgorithmNative(JNIEnv *env, jobject obj, jint start, jint end) {
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
    jmethodID midSetParentAt = env->GetMethodID(thisClass, "setParentAt", "(II)V");


    // Stvaranje grafa.
    jint n = env->CallIntMethod(gObject, midGetN);
    vector<int> minDist(n, INT_MAX), parent(n, -1);
    minDist[start] = 0;
    vector<vector<pair<int,int> > > g(n); // (neighbour, weight)
    for (int i = 0; i < n; ++i) {
        jint m = env->CallIntMethod(gObject, midGetNumNeighbours, i);
        for (int j = 0; j < m; ++j) {
            jobject neighbourInteger = env->CallObjectMethod(gObject, midGetNeighbourAt, i, j);
            jint neighbour = env->CallIntMethod(neighbourInteger, midIntValue);
            jobject weightInteger = env->CallObjectMethod(gObject, midGetWeightAt, i, j);
            jint weight = env->CallIntMethod(weightInteger, midIntValue);
            g[i].push_back(make_pair(neighbour, weight));
        }
    }

    // Algoritam.
    for (int numLoops = 0; numLoops < n-1; ++numLoops) {
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < g[i].size(); ++j) {
                int v = g[i][j].first, w = g[i][j].second;
                if (minDist[i] != INT_MAX && minDist[i] + w < minDist[v]) {
                    parent[v] = i;
                    minDist[v] = minDist[i] + w;
                }
            }
        }
    }

    // Za rekonstrukciju najkraćeg puta.
    for (int i = 0; i < n; ++i) {
        env->CallVoidMethod(obj, midSetParentAt, i, parent[i]);
    }

    return minDist[end];
}
