package com.neuralnetwork;

public class HillClimb extends NeuralNetwork {
	
	public static int MAX_ITTERATIONS = 200;
	
	public HillClimb(int layerCount) {
		super(layerCount);
		// TODO Auto-generated constructor stub
	}

	
    public void adjustNetwork(double[][][] trainingSet) {
		int trainingSetCount = trainingSet.length;
		
        NeuralNetwork adjustedNetwork = new NeuralNetwork(this.getNetworkLayerCount());
        double originalRMSerror = 0;
        double adjustedRMSerror = 0;
        int k =0;
        boolean runFlag = true;
        while (runFlag) {
        	adjustedNetwork = this.copyNeuralNetwork().adjustNeuralNetwork();
        	adjustedRMSerror = adjustedNetwork.calculateRMSerror(trainingSet);
        	originalRMSerror = this.calculateRMSerror(trainingSet);
        	if (this.verboseFlag) { System.out.print("| " + originalRMSerror + " | " + adjustedRMSerror + " | "); }
        	if (adjustedRMSerror < originalRMSerror) {
        		// adjusted is better
        		this.setNeuralNetworkTo(adjustedNetwork); //reassign network
        		if (this.verboseFlag) { System.out.println("climb | "); }
        		k =1;
        	} else {
        		if (this.verboseFlag) { System.out.println("stay  | "+k); }
        		k++;
        		if (k> MAX_ITTERATIONS) runFlag = false; // stop looping
        	}
        	for (int i = 0; i < trainingSetCount; i++) {
				this.runNetwork(trainingSet[i][0]);
				if (this.verboseFlag) { Driver.printTrainingRow(trainingSet, i);}
			}
        	if (this.verboseFlag) { System.out.println("\n"); }
        }
	}

}
