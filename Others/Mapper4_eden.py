#! /usr/bin/python
# Forum - Student Groups
import sys, re, string

curr_line = None

def take_quotes(item):
	if item[0] == '\"' and item[-1] == '\"':
		return item[1:-1]
	else:
		return item

def print_KeyValue(line):
	items = line.split("\t")
	author_id = take_quotes(items[3])
	node_type = take_quotes(items[5])
	if node_type == 'question':
		print "{0}\t{1}".format(take_quotes(items[0]), author_id)
	else:
		print "{0}\t{1}".format(take_quotes(items[7]), author_id)

i=0

for line in sys.stdin:
	'''
	if i == 0:
		i += 1
		continue
	'''
	if not line.strip():
		continue
	attr = line.split("\t")
	if len(attr) >= 5 and take_quotes(attr[0]).isdigit() and take_quotes(attr[3]).isdigit():
		# new line
		if curr_line != None:
			print_KeyValue(curr_line)
		curr_line = line
	else:
		curr_line += line

print_KeyValue(curr_line)