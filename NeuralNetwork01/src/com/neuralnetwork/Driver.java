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
		
	public static HillClimb hillClimbNetwork;
	public static NeuralNetwork neuralNetwork;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int inputsCount = TRAINING_SET[0][0].length; 
        int hiddenLayerCount = 1; 
        int hiddenLayerNeuronCount = 2;
        int outputsCount = 4;
        NeuralNetwork originalNetwork;
        //NeuralNetwork copyNetwork;
        
        neuralNetwork = new HillClimb(inputsCount,
									  hiddenLayerCount,
									  hiddenLayerNeuronCount,
									  outputsCount );
        originalNetwork = neuralNetwork.copyNeuralNetwork();
        ((HillClimb) neuralNetwork).runHillClimbAlgorithm(TRAINING_SET);
        printTrainingSetResults(neuralNetwork, TRAINING_SET);
        
        GradientDescent gradientNetwork = new GradientDescent(inputsCount,
				  											  hiddenLayerCount,
				  											  hiddenLayerNeuronCount,
				  											  outputsCount );
        // downcast needs instance of to prevent runtime exception
        if (originalNetwork instanceof GradientDescent) {
        	gradientNetwork = (GradientDescent) originalNetwork.copyNeuralNetwork() ;
        }	
        gradientNetwork.runGradiemtDescentAlgorithm(TRAINING_SET);
        printTrainingSetResults(gradientNetwork, TRAINING_SET);
        
	}
	
	
	
	public static void printTrainingSetResults(NeuralNetwork neuralNetwork, 
											   double[][][] trainingSet) {
		int trainingSetCount = trainingSet.length;
		printTrainingHeading(neuralNetwork, trainingSet);
	        for (int i = 0; i < trainingSetCount; i++) {
	        	printTrainingRow(neuralNetwork, trainingSet, i);
	        }
	        System.out.println("\n" + neuralNetwork.toString());  
	}
	
	public static void printTrainingHeading(NeuralNetwork neuralNetwork, 
			   								double[][][] trainingSet) {
		int inputsCount = TRAINING_SET[0][0].length; 
		int outputCount = TRAINING_SET[0][1].length; 
		//String divider = "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";
		String divider = "----------------------------------------------------------------------------------------------------------------";
		StringBuffer sB = new StringBuffer(divider + "\n NeuralNetwork Training Epoch: " + neuralNetwork.getEpoch());
		sB.append("; Error: " + neuralNetwork.getRMSerror() + "\n");
		sB.append(divider + "\n| ");
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
		sB.append("\n" + divider);
		System.out.println(sB);
	}
	
	public static void printTrainingRow(NeuralNetwork neuralNetwork, 
										double[][][] trainingSet, 
										int index) {
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
