import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class Process {
    int pid;             // Process ID
    int burstTime;       // Burst Time
    int priority;        // Priority
    int arrivalTime;     // Arrival Time
    int waitingTime;     // Waiting Time
    int turnaroundTime;  // Turnaround Time
    int completionTime;  // Completion Time

    public Process(int pid, int burstTime, int priority, int arrivalTime) {
        this.pid = pid;
        this.burstTime = burstTime;
        this.priority = priority;
        this.arrivalTime = arrivalTime;
    }
}

public class PrioritySchedulingWithArrival {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Process> processes = new ArrayList<>();

        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();

        // Input each process's details from the user
        for (int i = 1; i <= numProcesses; i++) {
            System.out.println("\nEnter details for Process " + i + ":");
            System.out.print("Burst Time: ");
            int burstTime = scanner.nextInt();
            System.out.print("Priority: ");
            int priority = scanner.nextInt();
            System.out.print("Arrival Time: ");
            int arrivalTime = scanner.nextInt();

            // Create a new Process and add it to the list
            processes.add(new Process(i, burstTime, priority, arrivalTime));
        }

        // Perform priority scheduling with arrival time
        priorityScheduling(processes);

        scanner.close();
    }

    public static void priorityScheduling(List<Process> processes) {
        // Sort by arrival time, then by priority for processes that have arrived
        processes.sort(Comparator.comparingInt((Process p) -> p.arrivalTime)
                                 .thenComparingInt(p -> p.priority));

        int currentTime = 0;
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        
        List<Process> completedProcesses = new ArrayList<>();

        while (!processes.isEmpty()) {
            // Filter processes that have arrived and are not yet completed
            List<Process> availableProcesses = new ArrayList<>();
            for (Process p : processes) {
                if (p.arrivalTime <= currentTime) {
                    availableProcesses.add(p);
                }
            }

            if (availableProcesses.isEmpty()) {
                currentTime++;
                continue;
            }

            // Select the process with the highest priority (lowest priority value)
            availableProcesses.sort(Comparator.comparingInt(p -> p.priority));
            Process currentProcess = availableProcesses.get(0);

            // Update current time and process's metrics
            currentTime += currentProcess.burstTime;
            currentProcess.completionTime = currentTime;
            currentProcess.turnaroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
            currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;

            // Update totals
            totalWaitingTime += currentProcess.waitingTime;
            totalTurnaroundTime += currentProcess.turnaroundTime;

            // Move the completed process from the list of processes
            completedProcesses.add(currentProcess);
            processes.remove(currentProcess);
        }

        double avgWaitingTime = (double) totalWaitingTime / completedProcesses.size();
        double avgTurnaroundTime = (double) totalTurnaroundTime / completedProcesses.size();

        // Display results
        System.out.println("\nProcess\tArrival Time\tPriority\tBurst Time\tWaiting Time\tTurnaround Time\tCompletion Time");
        for (Process process : completedProcesses) {
            System.out.printf("P%d\t\t%d\t\t%d\t\t%d\t\t%d\t\t%d\t\t%d%n",
                process.pid, process.arrivalTime, process.priority, process.burstTime,
                process.waitingTime, process.turnaroundTime, process.completionTime);
        }

        System.out.printf("%nAverage Waiting Time: %.2f%n", avgWaitingTime);
        System.out.printf("Average Turnaround Time: %.2f%n", avgTurnaroundTime);
    }
}
