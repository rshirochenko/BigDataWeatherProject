The dataset from OpenStreetMaps.

The dataset for the USA area was downloaded from http://download.geofabrik.de/. The URL for the list of the available features:http://wiki.openstreetmap.org/wiki/Map_Features

The format of the downloaded dataset .osm.pdf. Dataset is saved in hdfs cluster directory

To convert and exctract information the Osmconvert program was used. The program can be founded in this directory.

Example of using the Osmconvert program:

1) osmconvert great_britain.osm.pbf -o=great_britain.o5m
2) osmfilter great_britain.o5m --keep="railway=station" > stations.osm
3) osmconvert stations.osm --all-to-nodes --csv="@id @lat @lon railway name" --csv-headline > stations.txt
4) Open stations.txt in your spreadsheet program of choice, and remove lines that don't have "station" in column 



