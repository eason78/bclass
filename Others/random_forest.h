#ifndef _DECISION_TREE_H_
#define _DECISION_TREE_H_
#include <string>
#include <vector>
#include <set>
#include <ctime> 
#include <algorithm>
#include <cmath>

using namespace std;

//the data structure for a tuple
struct TupleData
{
	vector<int> A;
	char label;
};

struct TNode
{
	int attrNum;	
	int attr;	
	char label;
};

struct decision_tree
{
	TNode node;
	vector<decision_tree*> childs;
};

void init(char * trainname, char * testname);
int readData(vector<TupleData> &data, const char* fileName);
int stringtoint(string s);
void sub_init();
void calculate_ArrtNum();
void calculate_attributes();
void RandomSelectData(vector<TupleData> &data, vector<TupleData> &subdata);
double Entropy(double p, double s);
int creat_classifier(decision_tree *&p, const vector<TupleData> &samples, vector<int> &attributes);
int BestGainArrt(const vector<TupleData> &samples, vector<int> &attributes);
bool Allthesame(const vector<TupleData> &samples, char ch);
char Majorityclass(const vector<TupleData> &samples);
void RandomSelectAttr(vector<int> &data, vector<int> &subdata);
char testClassifier(decision_tree *p, TupleData d);
void testData();
void freeClassifier(decision_tree *p);
void freeArrtNum();
void showResult();



#endif //_DECISION_TREE_H_
