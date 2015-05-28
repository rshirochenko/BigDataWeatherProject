#!/usr/bin/bash

for line in `find . -mindepth 1 -maxdepth 1 -type d`; do
    echo "Processing '"$line"'...";

    count=$(find $line/ -mindepth 1 -maxdepth 1 | wc -l)
    echo 'Found '$count' files'

    if [ $count == 1 ]; then
        path=$(find $line/ -mindepth 1 -maxdepth 1);

        if [ `stat --format=%b $path` == 0 ]; then
            echo "Removing '"$line"...";
            rm -rf $line;
            echo
            continue
        fi
    fi

    echo 'Skipping...'
    echo
done
