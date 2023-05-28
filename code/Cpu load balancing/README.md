## Simulation of a distributed CPU load balancing algorithm. ##

### [Problem description](https://www.ii.pwr.edu.pl/~juszczyszyn/so.htm) ###

_There are N identical processors in the system. New tasks appear on each of them, with **_different_**
frequency and **_different_** requirements (each task needs some computing power) . We simulate
the following assignment strategies:_

**The job appears on the given processor. Then:**

* cpu data asks random cpu for current load:

     * If it is less than the p threshold, the process is sent there.

     * If not, we draw and ask the next one, trying at most times.

     * If all drawn are loaded above p, the process executes on that CPU.


* If the load of a given cpu exceeds the threshold value p, the process is sent to a randomly selected other cpu o
   load smaller than p (if the drawn y has a load>p, the draw is repeated until successful). If it does not exceed -
   the process is running on a given cpu.


* As in point 2, except that processors with a load less than the minimum threshold r ask randomly selected processors and if
   load is greater than p, the asker takes over some of his tasks (assume what).

**Simulate strategies 1-3 for N=approx. 50-100 and a long series of tasks to be performed (select parameters
to make it all work :). In each case, give as result:**

* Average CPU load.


* Average deviation from the value in point A.


* Number of load requests and job migrations.

__The user should be able to specify (change) p, r, z, N values.__

### What variable values to test for? ###

* **Example data:**

| Nr. | task amount | max. load | cpu amount | max. phase length |  z  |  p  |  r  |
|:---:|:-----------:|:---------------:|:----------------:|:--------------:|:---:|:---:|:---:|
| 1.  |   100000    |       60        |        50        |      170       | 10  | 80  | 30  |
| 2.  |    50000    |       90        |        70        |      200       |  4  | 70  | 10  |
| 3.  |    1000     |       80        |        60        |      250       |  3  | 80  | 40  |

* **What the individual variables mean:**

**Maximum load:**
the maximum computing power the task needs

**Maximum Phase Time:**
maximum task completion time

**With:**
the maximum number of times we will try to look for a processor that can take over the task

**p:**
maximum load threshold

**r:**
minimum load threshold
                                                          

