#!/bin/bash

BASEDIR=$(dirname "$0")

cd $BASEDIR || exit

javac -d out -sourcepath src/ src/**/*.java

cp -a resource out/
