/**
 * 
 */
package com.neuralnetwork;

/**
 * @author RedSpectre
 *
 */
public class Driver {
	
	public static double[][][] TRAINING_SET = new double[][][] {{{0, 0}, {1, 0, 0, 1}},
															    {{0, 1}, {1, 0, 1, 0}},
															    {{1, 0}, {1, 0, 1, 0}},
															    {{1, 1}, {0, 1, 0, 0}}};
		
	public static NeuralNetwork neuralNetwork;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Create a network
		int inputsCount = TRAINING_SET[0][0].length; 
        int hiddenLayerCount = 1; 
        int hiddenLayerNeuronCount = 2;
        int outputsCount = 4;
        neuralNetwork = new HillClimb(inputsCount,
									  hiddenLayerCount,
									  hiddenLayerNeuronCount,
									  outputsCount );
		
        ((HillClimb)neuralNetwork).setPrintFlag(false);
        ((HillClimb)neuralNetwork).runHillClimb(TRAINING_SET);
        System.out.println("Epoch: " + ((HillClimb)neuralNetwork).getEpoch() + ",   Error: " + ((HillClimb)neuralNetwork).getError() );
        printTrainingSetResult(TRAINING_SET);
        
        System.exit(0);
	}
	
	
	
	// *** Support and Printing methods
	public static void printTrainingSetResult (double[][][] trainingSet) {
		int trainingSetCount = TRAINING_SET.length;
        printTrainingHeading(TRAINING_SET);
        for (int i = 0; i < trainingSetCount; i++) {
        	printTrainingRow(TRAINING_SET, i);
        }
        System.out.println("\n" + neuralNetwork.toString());
	}
	
	public static void printTrainingHeading(double[][][] trainingSet) {
		int inputsCount = TRAINING_SET[0][0].length; 
		int outputCount = TRAINING_SET[0][1].length; 
		
		StringBuffer sB = new StringBuffer("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
		sB.append("| ");
		for (int i = 0; i < inputsCount; i++) {
			sB.append("w" + i + " |  ");
		}
		for (int i = 0; i < outputCount; i++) {
			sB.append("o" + i + " |  ");
		}
		for (int i = 0; i < neuralNetwork.getNetworkOutputs().length; i++) {
			sB.append("     O" + i + "      |  ");
		}
		sB.append("RMS" +  "      |  ");
		System.out.println(sB);
	}
	
	public static void printTrainingRow(double[][][] trainingSet, int index) {
		int inputsCount = trainingSet[0][0].length; 
		int outputCount = trainingSet[0][1].length; 
		// print values
		StringBuffer sB = new StringBuffer("| ");
		for (int i = 0; i < inputsCount; i++) {
			sB.append(String.format("%.0f", trainingSet[index][0][i]) + "  |  ");
		}
		for (int i = 0; i < outputCount; i++) {
			sB.append(String.format("%.0f", trainingSet[index][1][i]) + "  |  ");
		}
		for (int i = 0; i < outputCount; i++) {
			neuralNetwork.runNetwork(TRAINING_SET[index][0]);
			sB.append(String.format("%.9f", neuralNetwork.getNetworkOutputs()[i]) + "  |  ");
		}
		sB.append(String.format("%.5f", neuralNetwork.calculateRMSerror(TRAINING_SET)) + "  |  ");
		System.out.println(sB);
	}

}
