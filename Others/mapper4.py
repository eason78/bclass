#! /usr/bin/python

import sys, re, string

got_author_id = False
author_id = None

for line in sys.stdin:

	attr = line.split("\t")
	if(got_author_id == False):							#判断是否获得author_id
		try:									 
			node_id = string.atoi(attr[0])	 
			author_id = string.atoi(attr[3])
			got_author_id = True
			try:
				pattern = re.compile(r'\t((question)|(comment)|(answer))')		#判断是否是question还是其他
				match = pattern.search(line)
				node_type = match.group(1)
				if node_type == 'question':
					print "{0}\t{1}".format(node_id, author_id)
					got_author_id = False
					author_id = None
					continue
				else:
					print "{0}\t{1}".format(attr[6], author_id)					#如果是其他，则把abs_id当作key值
					got_author_id = False
					author_id = None
					continue
			except:
				continue
		except:
			continue
	else:
		try:
			pattern = re.compile(r'\t((question)|(comment)|(answer))')			#碰到body多行情况处理
			match = pattern.search(line)
			node_type = match.group(1)
			if node_type == 'question':
				print "{0}\t{1}".format(node_id, author_id)						#处理方法同上
				got_author_id = False
				author_id = None
				continue
			else:
				print "{0}\t{1}".format(attr[3], author_id)
				got_author_id = False
				author_id = None
				continue				
		except:
			continue
