import java.util.ArrayList;
import java.util.Scanner;

class ProcessWithoutArrival {
    int pid;
    int burstTime;
    int completionTime;
    int turnaroundTime;
    int waitingTime;

    public ProcessWithoutArrival(int pid, int burstTime) {
        this.pid = pid;
        this.burstTime = burstTime;
    }
}

public class FCFSWithoutArrival {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = scanner.nextInt();
        ArrayList<ProcessWithoutArrival> processes = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter burst time for process " + (i + 1) + ": ");
            int burstTime = scanner.nextInt();
            processes.add(new ProcessWithoutArrival(i + 1, burstTime));
        }

        fcfsWithoutArrival(processes);

        scanner.close();
    }

    public static void fcfsWithoutArrival(ArrayList<ProcessWithoutArrival> processes) {
        int currentTime = 0;

        for (ProcessWithoutArrival process : processes) {
            process.completionTime = currentTime + process.burstTime;
            process.turnaroundTime = process.completionTime;
            process.waitingTime = process.turnaroundTime - process.burstTime;
            currentTime += process.burstTime;
        }

        printProcessDetails(processes);
    }

    public static void printProcessDetails(ArrayList<ProcessWithoutArrival> processes) {
        System.out.println("\nPID\tBurst Time\tCompletion Time\tTurnaround Time\tWaiting Time");

        double totalTurnaroundTime = 0;
        double totalWaitingTime = 0;

        for (ProcessWithoutArrival process : processes) {
            totalTurnaroundTime += process.turnaroundTime;
            totalWaitingTime += process.waitingTime;
            System.out.println(process.pid + "\t" + process.burstTime + "\t\t" 
                + process.completionTime + "\t\t" + process.turnaroundTime + "\t\t" + process.waitingTime);
        }

        System.out.printf("\nAverage Turnaround Time: %.2f\n", (totalTurnaroundTime / processes.size()));
        System.out.printf("Average Waiting Time: %.2f\n", (totalWaitingTime / processes.size()));
    }
}
