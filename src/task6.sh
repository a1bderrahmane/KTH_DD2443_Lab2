#!/usr/bin/env bash

# This stops the script when a command's exit code is non-zero (i.e., error).
set -e

SIZE=1000000
WARMUP=10
MEASURE=1000
SEED=42

rm *.class
javac MeasureMain.java

# reset content of results.csv and add column labels
echo "nb threads; sorting algorithm; mean; standard deviation" > ../data/results.csv


# measure sequential algorithm
java MeasureMain Sequential 1 $SIZE $WARMUP $MEASURE $SEED

# measure parallel algorithm with several thread numbers
for ALGO in ExecutorService ForkJoinPool ParallelStream; do
	for THREADS in 1 2 4 8; do
		echo "###########################################"
		java MeasureMain $ALGO $THREADS $SIZE $WARMUP $MEASURE $SEED
	done
done
