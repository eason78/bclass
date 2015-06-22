#! /usr/bin/python
# Forum - Top10 tags

import sys

def take_quotes(item):
    if item[0] == '\"' and item[-1] == '\"':
        return item[1:-1]
    else:
        return item

def print_KeyValue(line):
	items = line.split("\t")
	tags = take_quotes(items[2]).split(' ')
	node_type = take_quotes(items[5])
	if node_type == 'question':
		for tag in tags:
			if tag != '':
				print "{0}\t{1}".format(tag, 1)

curr_line = None
i = 0

for line in sys.stdin:
	# on eden there is no header
	if i == 0:
		i += 1
		continue
	if not line.strip():
		continue
	attr = line.split("\t")
	if len(attr) >= 5 and take_quotes(attr[0]).isdigit() and take_quotes(attr[3]).isdigit():
		if curr_line != None:
			print_KeyValue(curr_line)
		curr_line = line
	else:
		curr_line += line

if curr_line != None:
	print_KeyValue(curr_line)