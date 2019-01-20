package com.neuralnetwork;

public class HillClimb extends NeuralNetwork {
	
	public static int MAX_ITTERATIONS = 200;
	
	public HillClimb(int layerCount) {
		super(layerCount);
		// TODO Auto-generated constructor stub
	}
	
	public HillClimb(int inputsCount, 
	           		 int hiddenLayerCount, 
	           		 int hiddenLayerNeuronCount,
	           		 int outputsCount) {
		super(inputsCount, hiddenLayerCount, hiddenLayerNeuronCount, outputsCount);
	}

	
    public void runHillClimbAlgorithm(double[][][] trainingSet) {
		NeuralNetwork adjustedNetwork = new NeuralNetwork(this.getNetworkLayerCount());
        double originalRMSerror = 0;
        double adjustedRMSerror = 0;
        this.setEpoch(0);
        int k = 0;
        boolean runFlag = true;
        while (runFlag) {
        	adjustedNetwork = this.copyNeuralNetwork().adjustNeuralNetwork();
        	adjustedRMSerror = adjustedNetwork.calculateRMSerror(trainingSet);
        	originalRMSerror = this.calculateRMSerror(trainingSet);
        	if (this.getVerboseFlag()) { System.out.print("| " + originalRMSerror + " | " + adjustedRMSerror + " | "); }
        	if (adjustedRMSerror < originalRMSerror) {
        		// adjusted is better
        		this.setNeuralNetworkTo(adjustedNetwork); //reassign network
        		if (this.getVerboseFlag()) { System.out.println("CLIMB | "); }
        		k = 1;
        		this.setEpoch(this.getEpoch() + 1);
        		this.setRMSerror(adjustedRMSerror);
        	} else {
        		if (this.getVerboseFlag()) { System.out.println("stay  | " + k); }
        		k++;
        		if (k > MAX_ITTERATIONS) runFlag = false; // stop looping
        	}
        }
	}

}
