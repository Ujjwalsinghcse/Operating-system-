import java.util.ArrayList;
import java.util.Scanner;

class ProcessWithArrival {
    int pid;
    int arrivalTime;
    int burstTime;
    int completionTime;
    int turnaroundTime;
    int waitingTime;

    public ProcessWithArrival(int pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }
}

public class FCFSWithArrival {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = scanner.nextInt();
        ArrayList<ProcessWithArrival> processes = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time and burst time for process " + (i + 1) + ": ");
            int arrivalTime = scanner.nextInt();
            int burstTime = scanner.nextInt();
            processes.add(new ProcessWithArrival(i + 1, arrivalTime, burstTime));
        }

        fcfsWithArrival(processes);

        scanner.close();
    }

    public static void fcfsWithArrival(ArrayList<ProcessWithArrival> processes) {
        // Sort processes by arrival time
        processes.sort((p1, p2) -> Integer.compare(p1.arrivalTime, p2.arrivalTime));

        int currentTime = 0;
        for (ProcessWithArrival process : processes) {
            if (currentTime < process.arrivalTime) {
                currentTime = process.arrivalTime;
            }
            process.completionTime = currentTime + process.burstTime;
            process.turnaroundTime = process.completionTime - process.arrivalTime;
            process.waitingTime = process.turnaroundTime - process.burstTime;
            currentTime += process.burstTime;
        }

        printProcessDetails(processes);
    }

    public static void printProcessDetails(ArrayList<ProcessWithArrival> processes) {
        System.out.println("\nPID\tArrival Time\tBurst Time\tCompletion Time\tTurnaround Time\tWaiting Time");

        double totalTurnaroundTime = 0;
        double totalWaitingTime = 0;

        for (ProcessWithArrival process : processes) {
            totalTurnaroundTime += process.turnaroundTime;
            totalWaitingTime += process.waitingTime;
            System.out.println(process.pid + "\t" + process.arrivalTime + "\t\t" + process.burstTime + "\t\t" 
                + process.completionTime + "\t\t" + process.turnaroundTime + "\t\t" + process.waitingTime);
        }

        System.out.printf("\nAverage Turnaround Time: %.2f\n", (totalTurnaroundTime / processes.size()));
        System.out.printf("Average Waiting Time: %.2f\n", (totalWaitingTime / processes.size()));
    }
}
