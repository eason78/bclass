#! /usr/bin/python
# Forum - Students and Posting Time
import sys

oldID = None
Hour_Times = {}

def take_zero_return(x):
	if x[-1] == '\n':
		x = x[:-1]
	if x[0] == '0':
		return x[1:]	
	else:
		return x

def output_biggest(Hour_Times):
	Biggest = 0
	Key = None
	for key, value in Hour_Times.items():
		if value > Biggest:
			Biggest = value
			Key = key
		elif value == Biggest and int(key) < int(Key):
			Key = key
	print "{0}\t{1}".format(oldID, Key)
	# Here is a problem
	# different from the original answer, the aswer on the eden will be the smallest hour
	# if their count numbers are the same
	'''
	for key, value in Hour_Times.items():
		if value == Biggest:	
			print "{0}\t{1}".format(oldID, key)
	'''

for line in sys.stdin:
	data_mapped = line.split('\t')

	thisID, hour = data_mapped
	hour = take_zero_return(hour)

	if len(Hour_Times) == 0:
		Hour_Times[hour] = 1
		oldID = thisID
	elif oldID == thisID:
		if Hour_Times.has_key(hour):
			Hour_Times[hour] += 1
		else:
			Hour_Times[hour] = 1
	else:
		# new key, output the old one
		output_biggest(Hour_Times)
		Hour_Times.clear()
		#insert new one
		Hour_Times[hour] = 1
		oldID = thisID

output_biggest(Hour_Times)