#! /usr/bin/python

import sys

oldID = None
dict_ID = {}		#每次接收的时间用一个字典来保存
biggest = 0

for line in sys.stdin:

	data_mapped = line.split("\t")
	if len(data_mapped) != 2:
		continue

	thisID, time = data_mapped

	if len(dict_ID) == 0:
		dict_ID[time] = 1
		oldID = thisID
	elif oldID == thisID:
		if dict_ID.has_key(time):
			dict_ID[time] += 1		#如果时间已经存在字典里面，则++
		else: 
			dict_ID[time] = 1		#否则，插入新的时间
	else:							#当于遇到新的ID的时候，统计旧ID里面最多的hour是哪一个，并且输出
		for key, value in dict_ID.items():
			if value > biggest:
				biggest = value
		for key, value in dict_ID.items():
			if value == biggest:	
				print "{0}\t{1}".format(oldID, key)
		biggest = 0					#完成操作，清空字典，然后加入新的ID对应的hour
		dict_ID.clear()
		dict_ID[time] = 1
		oldID = thisID