#include <iostream>
#include <fstream>
#include <sstream>
#include "decision_tree.h"

using namespace std;

decision_tree				*root;

vector<TupleData>	train,			
					test;			

vector<int>			attributes;	
ifstream			fin;			
int trainAllNum=0;														
int testAllNum=0;								
int MaxAttr;														
int *ArrtNum;
const int leafattrnum=-1;
int TP=0,
	FN=0,
	FP=0,
	TN=0,
	TestP=0,
	TestN=0;

void init(char * trainname, char * testname)
{
	trainAllNum=readData(train, trainname);
	testAllNum=readData(test, testname);
	calculate_attributes();
	calculate_ArrtNum();
	
}

int readData(vector<TupleData> &data, const char* fileName)
{
	fin.open(fileName);
	string line;
	
	int datanum=0;
	
	while(getline(fin,line))
	{
		TupleData d;
        istringstream stream(line);
        string str;
		while(stream>>str)
		{
			if(str.find('+')==0)
			{
				d.label='+';
			}
			else if(str.find('-')==0)
			{
				 d.label='-';
			}
			else
			{
				int j=stringtoint(str);
				d.A.push_back(j);
			}
		}
		
		data.push_back(d);	
		datanum++;
	}
	
	fin.close();
	return datanum;
}

void calculate_attributes()
{
	TupleData d=train.at(0);
	MaxAttr=d.A.size();
	for (int i = 0; i < MaxAttr; i++)
	{
		attributes.push_back(i);
	}
}
	

int stringtoint(string s)
{
	int sum=0;
	for(int i=0; s[i]!='\0';i++)
	{
		int j=int(s[i])-48;
		sum=sum*10+j;
	}
	return sum;
}

void calculate_ArrtNum()
{
	ArrtNum=new int[MaxAttr];
	for(int i=0; i<MaxAttr;i++) ArrtNum[i]=0;
	for (vector<TupleData>::const_iterator it = train.begin(); it != train.end(); it++)	
	{
		int i=0;
		for (vector<int>::const_iterator intt=(*it).A.begin(); intt!=(*it).A.end();intt++)
		{
			int valuemax=(*intt)+1;   //(*it).A.at(i)???
			if(valuemax>ArrtNum[i]) ArrtNum[i]=valuemax;
			i++;
		}
	}
}


double Entropy(double p, double s)
{
	double n = s - p;
	double result = 0;
	if (n != 0)
		result += - double(n) / s * log(double(n) / s) / log(2.0);
	if (p != 0)
		result += double(-p) / s * log(double(p) / s) / log(2.0);
	return result;
}

int creat_classifier(decision_tree *&p, const vector<TupleData> &samples, vector<int> &attributes)
{
	if (p == NULL)
		p = new decision_tree();
	if (Allthesame(samples, '+'))
	{
		p->node.label = '+';
		p->node.attrNum = leafattrnum;
		p->childs.clear();
		return 1;
	}
	if (Allthesame(samples, '-'))
	{
		p->node.label = '-';
		p->node.attrNum = leafattrnum;
		p->childs.clear();
		return 1;
	}
	if (attributes.size() == 0)
	{
		p->node.label = Majorityclass(samples);
		p->node.attrNum = leafattrnum;
		p->childs.clear();
		return 1;
	}
	p->node.attrNum = BestGainArrt(samples, attributes);

	p->node.label = ' ';
	
	vector<int> newAttributes;
	for (vector<int>::iterator it = attributes.begin(); it != attributes.end(); it++)
		if ((*it) != p->node.attrNum)
			newAttributes.push_back((*it));

	int maxvalue=ArrtNum[p->node.attrNum];
	vector<TupleData> subSamples[maxvalue];
	for (int i = 0; i < maxvalue; i++)
		subSamples[i].clear();

	for (vector<TupleData>::const_iterator it = samples.begin(); it != samples.end(); it++)
	{
		subSamples[(*it).A.at(p->node.attrNum)].push_back((*it));
	}

	decision_tree *child;
	for (int i = 0; i < maxvalue; i++)
	{
		child = new decision_tree;
		child->node.attr = i;
		if (subSamples[i].size() == 0)
			child->node.label = Majorityclass(samples);
		else
			creat_classifier(child, subSamples[i], newAttributes);
		p->childs.push_back(child);
	}
	return 0;
}

int BestGainArrt(const vector<TupleData> &samples, vector<int> &attributes)
{
	int attr, 
		bestAttr = 0,
		p = 0,
		s = (int)samples.size();
		
	for (vector<TupleData>::const_iterator it = samples.begin(); it != samples.end(); it++)
	{
		if ((*it).label == '+')
			p++;
	}
	
	double infoD;
	double bestResult = 0;
	infoD=Entropy(p, s);
	
	for (vector<int>::iterator it = attributes.begin(); it != attributes.end(); it++)
	{
		attr = (*it);
		double result = infoD;
		
		int maxvalue=ArrtNum[attr];
		int subN[maxvalue], subP[maxvalue], sub[maxvalue];
		for (int i = 0; i < maxvalue; i++)
		{
			subN[i] = 0;
			subP[i] = 0;
			sub[i]=0;
		}
		for (vector<TupleData>::const_iterator jt = samples.begin(); jt != samples.end(); jt++)
		{
			if ((*jt).label == '+')
				subP[(*jt).A.at(attr)] ++;
			else
				subN[(*jt).A.at(attr)] ++;
			sub[(*jt).A.at(attr)]++;
		}
		
		double SplitInfo=0;
		for(int i=0; i<maxvalue; i++)
		{
			double partsplitinfo;
			partsplitinfo=-double(sub[i])/s*log(double(sub[i])/s)/log(2.0);
			SplitInfo=SplitInfo+partsplitinfo;
		}
		
		double infoattr=0;
		for (int i = 0; i < maxvalue; i++)
		{
			double partentropy;
			partentropy=Entropy(subP[i], subP[i] + subN[i]);
			infoattr=infoattr+((double)(subP[i] + subN[i])/(double)(s))*partentropy;
		}
		result=result-infoattr;
		result=result/SplitInfo;
		
		if (result > bestResult)
		{
			bestResult = result;
			bestAttr = attr;
		}
	}

	if (bestResult == 0)
	{
		bestAttr=attributes.at(0);
	}
	return bestAttr;
}

bool Allthesame(const vector<TupleData> &samples, char ch)
{
	for (vector<TupleData>::const_iterator it = samples.begin(); it != samples.end(); it++)
		if ((*it).label != ch)
			return false;
	return true;
}

char Majorityclass(const vector<TupleData> &samples)
{
	int p = 0, n = 0;
	for (vector<TupleData>::const_iterator it = samples.begin(); it != samples.end(); it++)
		if ((*it).label == '+')
			p++;
		else
			n++;
	if (p >= n)
		return '+';
	else
		return '-';
}

char testClassifier(decision_tree *p, TupleData d)
{
	if (p->node.label != ' ')
		return p->node.label;
	int attrNum = p->node.attrNum;
	if (d.A.at(attrNum) < 0)
		return ' ';
	return testClassifier(p->childs.at(d.A.at(attrNum)), d);
}

void testData()
{
	for (vector<TupleData>::iterator it = test.begin(); it != test.end(); it++)
	{
		if((*it).label=='+') TestP++;
		else TestN++;
		if(testClassifier(root, (*it))=='+')
		{
			if((*it).label=='+') TP++;
			else FP++;
		}
		else
		{
			if((*it).label=='+') FN++;
			else TN++;
		}
	}
}

void freeClassifier(decision_tree *p)
{
	if (p == NULL)
		return;
	for (vector<decision_tree*>::iterator it = p->childs.begin(); it != p->childs.end(); it++)
	{
		freeClassifier(*it);
	}
	delete p;
}

void freeArrtNum()
{
	delete[] ArrtNum;
}

void showResult()
{
	//cout<<"Train size:	"<< trainAllNum<<endl;
	//cout<<"Test size:	"<<testAllNum<<endl;
	//cout<<"Test positive:	"<<TestP<<endl;
	//cout<<"Test negative:	"<<TestN<<endl;														
	//cout << "True positive:	" << TP << endl;
	//cout << "False negative:	"<< FN<<endl;
	//cout << "False positive:	"<<FP<<endl;
	//cout << "True negative:	"<<TN<<endl;
	
	cout<<TP<< endl;
	cout<<FN<<endl;
	cout<<FP<<endl;
	cout<<TN<<endl;
}

int main(int argc, char **argv)
{
	char * trainfile=argv[1];
	char * testfile=argv[2];
	
	init(trainfile, testfile);
	creat_classifier(root, train, attributes);
	testData();
	freeClassifier(root);
	freeArrtNum();
	showResult();
	return 0;
}
