#!/usr/bin/python3

import matplotlib.pyplot as plt

n_thread_values = set()
mean_exec_times = {}

with open("../data/results.csv", "r") as rfile :
    rfile.readline() # ignore first line
    for line in rfile:
        line_data = line.strip().replace(',', '.').split('; ')
        n_thread_values.add(int(line_data[0]))

        algorithm_name = line_data[1]
        mean_exec_time = float(line_data[2]) / 1000000000 # convert execution time in seconds

        if (algorithm_name not in mean_exec_times):
            mean_exec_times[algorithm_name] = []
        mean_exec_times[algorithm_name].append(mean_exec_time)

n_thread_values = list(n_thread_values)
n_thread_values.sort()
y = [mean_exec_times["Sequential"][0] for _ in n_thread_values]
print(n_thread_values)
print(y)

plt.plot(n_thread_values, y, label="Sequential")

for algorithm in mean_exec_times:
    if (algorithm == "Sequential"):
        continue
    plt.plot(n_thread_values, mean_exec_times[algorithm], label=algorithm)

plt.xlabel("number of threads used")
plt.ylabel("mean computation time (s)")
plt.title("Sort performance on a 10M array on personal laptop")
plt.legend()
plt.show()