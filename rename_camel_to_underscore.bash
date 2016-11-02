#!/bin/bash

FILES=./*.jpg


for f in $FILES
do
  
  # take action on each file. $f store current file name
  renameFrom="$f"
  #renameTo=echo $renameFrom | sed -r 's/([A-Z])/_\L\1/g'

  echo "Processing $renameFrom file..."
  renameTo=`echo $renameFrom | sed -r 's/([A-Z])/_\L\1/g'`

  mv $renameFrom $renameTo


done