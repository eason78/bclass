#! /usr/bin/python

import sys, re, string

tag_list = []
got_tag = False

for line in sys.stdin:

	attr = line.split("\t")
	if(got_tag == False):						#判断此时是否已经获得tag
		try:									 
			node_id = string.atoi(attr[0])	 	#判断是否记录的头部
			user_id = string.atoi(attr[3])
			for item in attr[2].split(" "):		#存入tag，删去空的tag
				if item != '':
					tag_list.append(item)
			got_tag = True
			try:
				pattern = re.compile(r'\t((question)|(comment)|(answer))')		#判断node_type是否在同一行
				match = pattern.search(line)
				node_type = match.group(1)
				if node_type == 'question':
					for item in tag_list:
						print "{0}\t{1}".format(item, int(1))					#若是，则输出
					tag_list = []
					got_tag = False
					continue
				else:
					tag_list = []
					got_tag = False
					continue
			except:
				continue
		except:
			continue
	else:
		try:
			pattern = re.compile(r'\t((question)|(comment)|(answer))')			#若记录头部和node_type不在同一行
			match = pattern.search(line)										#找到node_type一行进行判断
			node_type = match.group(1)
			if node_type == 'question':								
				for item in tag_list:
					print "{0}\t{1}".format(item, int(1))
				tag_list = []
				got_tag = False
				continue
			else:
				tag_list = []
				got_tag = False
				continue
		except:
			continue
