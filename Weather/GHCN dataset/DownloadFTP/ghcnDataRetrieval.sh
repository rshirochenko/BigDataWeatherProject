#!/bin/sh

# Use this script to download gz files from ftp.ncdc.noaa.gov/pub/data/ghcn/daily/by_year
# by year, in the current directory.
# You can provide one or two years, for a single file or a range of files respectively.

minYear='1763'
currentYear=`date +"%Y"`
startingYear=''
endingYear=''

# Either 1 or 2 arguments for a unique year, or a range of years
if [ $# -gt 2 ] || [ $# -lt 1 ]; then
   echo "Usage: ./ghcnDataRetrieval.sh startingYear endingYear" 1>&2
   echo "Or if interested only in one year: ./ghcnDataRetrieval.sh year" 1>&2
   exit 1
fi

testInt() {
   echo "$1" | egrep -q '^[0-9]+$'
   if [ $? -ne 0 ]; then
      echo "Arguments must be integers" 1>&2
      exit 2
   fi
}

# Bound to range and set year
year=''
cutYear() {
   year="$1"
   if [ "$1" -gt "$currentYear" ]; then
      year="$currentYear"
   elif [ "$1" -lt "$minYear" ]; then
      year="$minYear"
   fi
}

testInt "$1"
cutYear "$1"
startingYear="$year"

if [ $# -eq 1 ]; then
   endingYear="$startingYear"
else
   testInt "$2"
   cutYear "$2"
   endingYear="$year"
fi

# Getting the files (server only permits 3 connections at a time)
for file in `seq -f "%g.csv.gz" $startingYear $endingYear`; do
   if [ ! -e "${file%.*}" ]; then
      echo Downloading "$file"...
      (
         echo open ftp.ncdc.noaa.gov
         echo user anonymous weather@groupes.epfl.ch
         echo cd pub/data/ghcn/daily/by_year
         echo binary
         echo get "$file"
         echo quit
      ) | ftp -n
      echo Done. Unzipping now...
      gunzip "$file"
   fi
done
