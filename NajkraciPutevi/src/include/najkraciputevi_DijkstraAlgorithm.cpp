#include <jni.h>
#include "najkraciputevi_DijkstraAlgorithm.h"
#include <queue>
#include <vector>
#include <utility>
#include <functional>
#include <climits>
using namespace std;

JNIEXPORT jint JNICALL Java_najkraciputevi_DijkstraAlgorithm_runAlgorithmNative(JNIEnv *env, jobject obj, jint start, jint end) {
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

    // Stvaranje grafa.
    jint n = env->CallIntMethod(gObject, midGetN);
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
    vector<int> minDist(n, -1), parent(n, -1);
    // U prioriti queue je (udaljenost, (trenutni čvor, prethodni čvor)).
    priority_queue<pair<int,pair<int,int> >, vector<pair<int,pair<int,int> > >, greater<pair<int,pair<int,int> > > > pq;
    pq.push(make_pair(0, make_pair(start, -1)));
    while (pq.size()) {
        int dist = pq.top().first;
        int u = pq.top().second.first, v = pq.top().second.second;
        pq.pop();
        if (minDist[u] != -1) continue;
        else {
            minDist[u] = dist;
            parent[u] = v;
            if (u == end) break;
            for (int l = 0; l < g[u].size(); ++l) {
                pq.push(make_pair(dist+g[u][l].second, make_pair(g[u][l].first, u)));
            }
        }
    }

    // TODO: JNI za spremanje informacija o rekonstrukciji puta.

    return minDist[end];
}
