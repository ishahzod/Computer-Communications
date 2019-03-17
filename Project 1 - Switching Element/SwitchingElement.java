import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Random;
import java.util.Scanner;

public class SwitchingElement {
	public static void main(String[] args) throws IOException {

		Random r = new Random(System.currentTimeMillis());
		Scanner s = new Scanner(System.in);

		System.out.print("Enter the number of time slots: ");
		int timeSlots = Integer.parseInt(s.nextLine());
		System.out.print("Enter file save name: ");
		String fileName = s.nextLine();
		BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
		//adding the header 
		bw.append("p");
		bw.append(",");
		bw.append("Avg Outputs");
		bw.append(",");
		bw.append("Avg Dropped");

		int[] outputs = new int[timeSlots]; //an array to store outputs
		double sumPass = 0, sumDrop = 0;
		double avgOutputs[] = new double[timeSlots]; //an array to store the average number of outputs;
		double avgDropped[] = new double[timeSlots]; //an array to store the avg dropped packets

		for (double p = 0.05; p < 1.0; p += 0.05) { //for each probability p
			int j = 0;
			for (int i = 0; i < timeSlots; i++) {  //for each timeSlot at a given probability
				int genPacket = r.nextInt(10) + 1; //generate a random packet [1-10] inputs
				outputs[i] = BernoulliProcess(p, genPacket); //Run the bernoulli process for that p value
				sumPass += outputs[i];                //calculate the total passes 
				sumDrop += (genPacket - outputs[i]);  //calculate the total drops

			}
			//Calculate the average dropped / passed packets
			avgOutputs[j] = sumPass / timeSlots;
			avgDropped[j] = sumDrop / timeSlots;
			System.out.println("Avg Output: " + avgOutputs[j]);
			System.out.println("Avg Dropped: " + avgDropped[j]);
			saveToFile(bw, avgOutputs, avgDropped, p, j, fileName);
			j++;
			sumPass = 0;
			sumDrop = 0;
		}
		bw.close();
		s.close();
	}

	public static int BernoulliProcess(double p, int genPacket) {
		int succeed = 0, passed = 0, dropped = 0;

		for (int i = 0; i < genPacket; i++) { //for each generated packet at a given timeslot
			if (uniform() < p) //randomly generates a probability, compares to p, and passes the packet if true
				succeed++;
		}

		if (succeed > 3)  //if the number of packets succeeded is 3 or greater
			passed = 3;    //output gets 3 (max # of outputs)
		else               //otherwise
			passed = succeed;   //outputs gets 0, 1, 2

		dropped = genPacket - passed;

		System.out.format("\nProbability: %.2f \n", p);
		System.out.println("Generated Packets: " + genPacket);
		System.out.println("Passed: " + passed);
		System.out.println("Dropped: " + dropped + "\n");

		return passed;
	}

	//save to a specifed file based on extension (e.g. .csv , .xls, etc)
	public static void saveToFile(BufferedWriter bw, double avgOutputs[], double avgDropped[], double p, int j,
			String fileName) throws IOException {

		try {

			for (int i = 0; i <= j; i++) {
				bw.append("\n");
				bw.append(String.valueOf(p));
				bw.append(",");
				bw.append(String.valueOf(avgOutputs[i]));
				bw.append(",");
				bw.append(String.valueOf(avgDropped[i]));

			}
			bw.flush();
			//System.out.println("Save successful!\n");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 *Generates a random real number uniformly in [0, 1).
	 */
	public static double uniform() {
		Random r = new Random();
		return r.nextDouble();
	}
}
