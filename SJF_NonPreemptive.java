1. Shortest Job First:
import java.util.Scanner;

class Process {
    int pid, at, bt, ct, tat, wt;
}

public class SJF_NonPreemptive {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
       
        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();
        Process[] processes = new Process[n];

        for (int i = 0; i < n; i++) {
            processes[i] = new Process();
            processes[i].pid = i + 1;
            System.out.print("Enter Arrival Time and Burst Time for process " + (i + 1) + ": ");
            processes[i].at = sc.nextInt();
            processes[i].bt = sc.nextInt();
        }

        calculateTimes(processes, n);

        sc.close();
    }

    static void calculateTimes(Process[] p, int n) {
        int currentTime = 0, completed = 0;
        boolean[] isCompleted = new boolean[n];
        float totalTAT = 0, totalWT = 0;

        while (completed != n) {
            int minIndex = -1;
            int minBurst = Integer.MAX_VALUE;

            // Select the process with minimum burst time
            for (int i = 0; i < n; i++) {
                if (p[i].at <= currentTime && !isCompleted[i] && p[i].bt < minBurst) {
                    minBurst = p[i].bt;
                    minIndex = i;
                }
            }

            if (minIndex == -1) {
                currentTime++;
            } else {
                currentTime += p[minIndex].bt;
                p[minIndex].ct = currentTime;
                p[minIndex].tat = p[minIndex].ct - p[minIndex].at;
                p[minIndex].wt = p[minIndex].tat - p[minIndex].bt;

                totalTAT += p[minIndex].tat;
                totalWT += p[minIndex].wt;
                isCompleted[minIndex] = true;
                completed++;
            }
        }

        printResults(p, n, totalTAT, totalWT);
    }

    static void printResults(Process[] p, int n, float totalTAT, float totalWT) {
        System.out.println("\nProcess\tAT\tBT\tCT\tTAT\tWT");
        for (int i = 0; i < n; i++) {
            System.out.println("P" + p[i].pid + "\t" + p[i].at + "\t" + p[i].bt + "\t" + p[i].ct + "\t" + p[i].tat + "\t" + p[i].wt);
        }
        System.out.println("Average Turnaround Time: " + totalTAT / n);
        System.out.println("Average Waiting Time: " + totalWT / n);
    }
}

2. Shortest Remaining Time First
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

class Process {
    int pid;          // Process ID
    int arrivalTime;  // Arrival Time
    int burstTime;    // Burst Time
    int remainingTime; // Remaining Time
    int completionTime;
    int turnaroundTime;
    int waitingTime;

    public Process(int pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }
}

public class SRTFScheduling {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = scanner.nextInt();
        ArrayList<Process> processes = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time and burst time for process " + (i + 1) + ": ");
            int arrivalTime = scanner.nextInt();
            int burstTime = scanner.nextInt();
            processes.add(new Process(i + 1, arrivalTime, burstTime));
        }

        srtfScheduling(processes);

        scanner.close();
    }

    public static void srtfScheduling(ArrayList<Process> processes) {
        int time = 0, completed = 0;
        int n = processes.size();

        while (completed != n) {
            // Select the process with the smallest remaining time that has arrived
            Process currentProcess = null;
            for (Process process : processes) {
                if (process.arrivalTime <= time && process.remainingTime > 0) {
                    if (currentProcess == null || process.remainingTime < currentProcess.remainingTime) {
                        currentProcess = process;
                    }
                }
            }

            if (currentProcess == null) {
                time++;
                continue;
            }

            // Process the selected process for one time unit
            currentProcess.remainingTime--;
            time++;

            // Check if the process has completed
            if (currentProcess.remainingTime == 0) {
                currentProcess.completionTime = time;
                currentProcess.turnaroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
                currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
                completed++;
            }
        }

        printProcessDetails(processes);
    }

    public static void printProcessDetails(ArrayList<Process> processes) {
        System.out.println("\nPID\tArrival Time\tBurst Time\tCompletion Time\tTurnaround Time\tWaiting Time");

        double totalTurnaroundTime = 0;
        double totalWaitingTime = 0;

        for (Process process : processes) {
            totalTurnaroundTime += process.turnaroundTime;
            totalWaitingTime += process.waitingTime;
            System.out.println(process.pid + "\t" + process.arrivalTime + "\t\t" + process.burstTime + "\t\t" 
                + process.completionTime + "\t\t" + process.turnaroundTime + "\t\t" + process.waitingTime);
        }

        System.out.printf("\nAverage Turnaround Time: %.2f\n", (totalTurnaroundTime / processes.size()));
        System.out.printf("Average Waiting Time: %.2f\n", (totalWaitingTime / processes.size()));
    }
}
