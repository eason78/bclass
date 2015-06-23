#include <iostream>
#include <stack>
#include <cstdio>

using namespace std;

int *couple;

int main() {
	int n;
	int c1, c2;

	while (scanf("%d", &n) && n != 0) {
		stack<int> s;
		couple = new int[2 * n + 1];
		for (int i = 0; i < n; i++) {
			scanf("%d %d", &c1, &c2);
			couple[c1] = c2;
			couple[c2] = c1;
		}
		for (int i = 1; i <= 2 * n; i++) {
			if (s.empty()) {
				s.push(i);
			} else if (couple[i] == s.top()) {
				s.pop();
			} else {
				s.push(i);
			}
		}
		if (s.empty()) {
			printf("Yes\n");
		} else {
			printf("No\n");
		}
		delete [] couple;
	}
}