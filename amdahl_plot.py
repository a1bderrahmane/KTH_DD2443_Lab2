#!/usr/bin/python3

import matplotlib.pyplot as plt

def our_amdahl(n, p):
    match n:
        case 2:
            return 1 / (1 - (p / 2))
        case 4:
            return 1 / (1 - (5 * p / 8))
        case 8:
            return 1 / (1 - (17 * p / 24))
        case 16:
            return 1 / (1 - (49 * p / 64))
        case _:
            raise ValueError(f"Wrong n value : {n}, expected 2, 4, 8, or 16")


p_values = [0.2, 0.4, 0.6, 0.8]
n_values = [2, 4, 8, 16]
S_values = [[our_amdahl(n, p) for n in n_values] for p in p_values]

print(S_values)

plt.plot(n_values, S_values[0], label='p = 0.2')
plt.plot(n_values, S_values[1], label='p = 0.4')
plt.plot(n_values, S_values[2], label='p = 0.6')
plt.plot(n_values, S_values[3], label='p = 0.8')
plt.legend()
plt.xlabel('number of threads')
plt.ylabel('speedup')
plt.show()