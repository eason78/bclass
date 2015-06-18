#! /usr/bin/python
# Forum - Reverse Index

import sys, heapq

keywords_times = {}
keywords_indexs = {}

for line in sys.stdin:
    data_mapped = line.split('\t')
    keyword, index = data_mapped
    index = index[:-1]
    if keywords_times.has_key(keyword):
        keywords_times[keyword] += 1
        keywords_indexs[keyword] += '||' + index
    else:
        keywords_times[keyword] = 1
        keywords_indexs[keyword] = index

top100 = heapq.nlargest(100, keywords_times, key=keywords_times.get)
for item in top100:
    indexs = keywords_indexs[item].split('||')
    val = ''
    for index in indexs[:-1]:
        val += index + '\t'
    val += indexs[-1]
    print "{0}\t{1}".format(item, val)