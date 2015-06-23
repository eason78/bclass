#include <iostream>
#include <string>
#include <cstring>
#include <queue>

using namespace std;

int fact[] = {1, 1, 2, 6, 24, 120, 720, 5040, 40320};
string target;
bool visited[50000];

struct Node {
	string state;
	string op;
};

int cantor(Node node) {
	int count;
	int temp[8];
	int result = 1;
	for (int i = 0; i < 8; i++) {
		temp[i] = node.state[i] - '0';
	}
	for (int i = 0; i < 7; i++) {
		count = 0;
		for (int j = i + 1; j < 8; j++) {
			if (temp[i] > temp[j]) {
				count++;
			}
		}
		result += fact[7 - i] * count;
	}
	return result;
}

void operation(Node current, Node& next, int op) {
	if (op == 1) {
		for (int i = 4; i < 8; i++) {
			next.state += current.state[i];
		}
		for (int i = 0; i < 4; i++) {
			next.state += current.state[i];
		}
		next.op = current.op + 'A';
	} else if (op == 2) {
		next.state = next.state + current.state[3] + current.state[0] + current.state[1]
					+ current.state[2];
		next.state = next.state + current.state[7] + current.state[4] + current.state[5]
					+ current.state[6];
		next.op = current.op + 'B';
	} else if (op == 3) {
		next.state = next.state + current.state[0] + current.state[5] + current.state[1]
					+ current.state[3];
		next.state = next.state + current.state[4] + current.state[6] + current.state[2]
					+ current.state[7];
		next.op = current.op + 'C';
	}
}

void bfs(Node node, int n) {
	queue<Node> q;
	q.push(node);
	visited[cantor(node)] = 1;

	while (!q.empty()) {
		Node temp = q.front();
		q.pop();
		if (temp.op.size() > n) {
			cout << "-1" << endl;
			return;
		}
		if (temp.state == target) {
			cout << temp.op.size() << " " << temp.op << endl;
			return;
		}
		for (int i = 1; i < 4; i++) {
			Node next;
			operation(temp, next, i);
			if (!visited[cantor(next)]) {
				q.push(next);
				visited[cantor(next)] = 1;
			}
		}
	}
}

int main() {
	int n;
	Node start;
	start.state = "12348765";
	start.op = "";

	while (cin >> n && n != -1) {
		memset(visited, 0, sizeof(visited));
		char temp;
		target = "";

		for (int i = 0; i < 8; i++) {
			cin >> temp;
			target += temp;
		}
		bfs(start, n);
	}

	return 0;
}