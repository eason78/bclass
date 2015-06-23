// 12330378
// Ye Wanyi
// 1152

#include <iostream>
#include <list>
#include <cstring>

using namespace std;

#define COL 6
#define ROW 5

int current = 0;
int path[COL * ROW];
bool visited[COL][ROW];
int movement[8][2] = {{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
					{1, -2}, {1, 2}, {2, -1}, {2, 1}};

bool canMove(int c, int r) {
	return (c >= 0 && c < COL && r >= 0 && r < ROW) && !visited[c][r];
}

int getDegree(int c, int r) {
	int degree = 0;
	for (int i = 0; i < 8; i++) {
		int nextC = c + movement[i][0];
		int nextR = r + movement[i][1];
		if (canMove(nextC, nextR)) {
			degree++;
		}
	}
	return degree;
}

bool dfs() {
	if (current == COL * ROW - 1) {
		return true;
	}
	int c = (path[current] - 1) % COL;
	int r = (path[current] - 1) / COL;
	list<pair<int, int> > l;
	for (int i = 0; i < 8; i++) {
		int nextC = c + movement[i][0];
		int nextR = r + movement[i][1];
		if (canMove(nextC, nextR)) {
			l.push_back(pair<int, int>(getDegree(nextC, nextR), i));
		}
	}
	l.sort();
	for (list<pair<int, int> >::iterator it = l.begin();
			it != l.end(); it++) {
		int nextC = c + movement[it->second][0];
		int nextR = r + movement[it->second][1];
		visited[nextC][nextR] = true;
		current++;
		path[current] = nextR * COL + nextC + 1;
		if (dfs()) {
			return true;
		}
		visited[nextC][nextR] = false;
		current--;
	}
	return false;
}

int main() {
	int n;
	while (cin >> n && n != -1) {
		current = 0;
		path[0] = n;
		memset(visited, false, sizeof(visited));
		visited[(n - 1) % COL][(n - 1) / COL] = true;
		dfs();
		for (int i = 0; i < COL * ROW - 1; i++) {
			cout << path[i] << " ";
		}
		cout << path[COL * ROW - 1] << endl;
	}
}