import csv
import sys

#input file argument
input_file = sys.argv[1]

#Open CSV file and write the header
writer = csv.writer(open('stations.csv', 'wb'), delimiter=';', quotechar='|', quoting=csv.QUOTE_MINIMAL)
writer.writerow(["id","latitude","longtitude","elevation","state","name"])

#Parse station.txt file by lines and write the US only stations in .csv file
with open(input_file, "r") as file:
    for line in file:
	id = line[:11]
	latitude = line[13:20]
	longtitude = line[22:30]
	elevation = line[32:37]
	state = line[38:40]
	name = line[41:71]
	if ("US" in id[:2]):
		writer.writerow([id,latitude,longtitude,elevation,state,name])
	

