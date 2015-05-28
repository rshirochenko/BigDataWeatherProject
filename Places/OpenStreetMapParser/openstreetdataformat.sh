#!/bin/bash

#This script is used to convert the openstreetmap dataset in a plain .csv 
#TODO
#It DOESN'T handle the final reorganization of the csv (merging of the column, etc...) for time being
#what about conflict? museum can be tagged as anemity and tourism
#Change the path (currently work with Monaco, as it is a small dataset)

#Col organization of the last csv.
csvcol="@id @lat @lon name wikipedia website image tourism amenity sport"


#For each tag
#First String is the name of the tag, other one are the value of the tag we want to keep
anemity=("anemity" "cinema"  "theatre")
tourism=("tourism") 
sport=("sport")

#anemity=()
keepfilter=""

#Yeah, not modular (for time being?) because of the limitation of bash (no direct method use an array as a function input)

	if [ ${#anemity[@]} -eq 1 ]; then
		keepfilter=$keepfilter$anemity"= "
	elif [ ${#anemity[@]} -eq 0 ]; then
		printf "Please input non empty array!"
	else
		for an in "${anemity[@]:1}"
		do

			  keepfilter=$keepfilter${anemity[0]}"="$an" "
	
		done	
	fi

	if [ ${#tourism[@]} -eq 1 ]; then
		keepfilter=$keepfilter$tourism"= "
	elif [ ${#tourism[@]} -eq 0 ]; then
		printf "Please input non empty array!"
	else
		for an in "${tourism[@]:1}"
		do

			  keepfilter=$keepfilter${tourism[0]}"="$an" "
	
		done	
	fi

	
	if [ ${#sport[@]} -eq 1 ]; then
		keepfilter=$keepfilter$sport"= "
	elif [ ${#sport[@]} -eq 0 ]; then
		printf "Please input non empty array!"
	else
		for an in "${sport[@]:1}"
		do

			  keepfilter=$keepfilter${sport[0]}"="$an" "
	
		done	
	fi	

#printf '%s\n' "$keepfilter"


./osmconvert monaco-latest.osm.pbf -o=monaco-latest.o5m
./osmfilter monaco-latest.o5m  --keep="$keepfilter" > places.osm
./osmconvert  places.osm --all-to-nodes --csv="$csvcol"  --csv-separator=, --csv-headline > places.csv

#TO ADD HERE
#Call to a spark script that will reorganize the columns
#To do so, need to precesly define what we have to get (because one additonnal tag => one additional column on the original .csv)
