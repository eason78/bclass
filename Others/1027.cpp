#include <iostream>
#include <cstring>
#include <list>
#include <cstdio>

using namespace std;

int main() {
	int n;
	string id[21], ip[21];
	list<pair<string, string> > l;
	while(scanf("%d", &n) && n != 0) {
		l.clear();
		for (int i = 0; i < n; i++) {
			cin >> id[i] >> ip[i];
			for (int j = 0; j < i; j++) {
				if (ip[j].compare(ip[i]) == 0) {
					l.push_back(pair<string, string>(id[j],
								id[i] + " is the MaJia of " + id[j]));
					break;
				}
			}
		}
		l.sort();
		for (list<pair<string, string> >::iterator it = l.begin(); it != l.end(); it++) {
			cout << it->second << endl;
		}
		cout << endl;
	}
}