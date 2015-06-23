// 12330378
// Ye Wanyi
// 1020

#include <iostream>
#include <string>

#define N 100

using namespace std;

int main() {
	int t, n;
	int basis[N], r[N];
	string x;

	cin >> t;

	while (t > 0) {
		cin >> n;
		for (int i = 0; i < n; i++) {
			cin >> basis[i];
		}

		cin >> x;
		for (int i = 0; i < n; i++) {
			int temp = 0;
			for (int j = 0; j < x.length(); j++) {
				temp = (temp * 10 + (x[j] - '0')) % basis[i];
			}
			r[i] = temp;
		}
		cout << "(";
		for (int i = 0; i < n - 1; i++) {
			cout << r[i] << ",";
		}
		cout << r[n - 1] << ")" << endl;
		t--;
	}
}