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
