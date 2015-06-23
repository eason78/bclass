#include <iostream>
#include <queue>
#include <map>
#include <string>
#include <cstring>

using namespace std;

int n;
string target;
map<string, int> visited;

struct Node {
	string state;
	string op;
};

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

void bfs(Node node) {
	queue<Node> q;
	q.push(node);
	visited[node.state] = 1;

	while (!q.empty()) {
		Node temp = q.front();
		q.pop();
		if (temp.op.size() > n) {
			cout << "-1" << endl;
			return;
		}
		for (int i = 0; i < 4; i++) {
			Node next;
			operation(temp, next, i);
			if (next.state == target) {
				cout << next.op.size() << " " << next.op << endl;
				return;
			}
			if (visited[next.state] != 1) {
				q.push(next);
				visited[next.state] = 1;
			}
		}
	}
}

int main() {
	Node start;
	start.state = "12348765";
	start.op = "";

	while (cin >> n && n != -1) {
		char temp;
		visited.clear();
		target = "";

		for (int i = 0; i < 8; i++) {
			cin >> temp;
			target += temp;
		}
		bfs(start);
	}

	return 0;
}