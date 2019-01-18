package com.neuralnetwork;

public class HillClimb extends NeuralNetwork {
	
	public static int MAX_ITTERATIONS = 200;
	
	private boolean printFlag = false;
	private int     epoch = 0;
	private double  error = 0;
	
	public void    setPrintFlag (boolean flag) {this.printFlag = flag; }
	public boolean getPrintFlag() { return this.printFlag; }
	public int     getEpoch() { return this.epoch; }
	public double  getError() { return this.error; }
	
	// *** Constructor(s)
	public HillClimb(int layerCount) {
		super(layerCount);
		// TODO Auto-generated constructor stub
	}
	
	public HillClimb(int inputsCount, int hiddenLayerCount, int hiddenLayerNeuronCount, int outputsCount) {
		super(inputsCount, hiddenLayerCount, hiddenLayerNeuronCount, outputsCount);
		// TODO Auto-generated constructor stub
	}
	
	
	public void runHillClimb(double[][][] trainingSet) {
      
        NeuralNetwork adjustedNetwork = new NeuralNetwork(this.getNetworkLayerCount());
        double originalRMSerror = 0;
        double adjustedRMSerror = 0;
        int k =0;
        boolean runFlag = true;
        while (runFlag) {
        	adjustedNetwork = this.adjustAllWeightsThresholdsRandomly();
        	adjustedRMSerror = adjustedNetwork.calculateRMSerror(trainingSet);
        	originalRMSerror = this.calculateRMSerror(trainingSet);
        	if (printFlag) System.out.print("| " + originalRMSerror + " | " + adjustedRMSerror + " | ");
        	if (adjustedRMSerror < originalRMSerror) {
        		// The adjusted network performs better
        		this.setNetworkTo(adjustedNetwork); //reassign network
        		this.error = adjustedRMSerror;
        		this.epoch++;
        		if (printFlag) System.out.println("climb | ");
        		k =1;
        	} else {
        		// The original network performs better
        		if (printFlag) System.out.println("stay  | "+k);
        		k++;
        		if (k> MAX_ITTERATIONS) runFlag = false; // stop looping
        	}
		}
   }
	
	
	public NeuralNetwork adjustAllWeightsThresholdsRandomly() {
		NeuralNetwork adjustedNeuralNetwork = this.copyNeuralNetwork();
		NetworkLayer adjustementLayer = new NetworkLayer();
		int layerCount = this.getNetworkLayerCount();
		
		for (int l = 0; l < layerCount; l++) {
			adjustementLayer = this.getNetworkLayers()[l];
			adjustementLayer = adjustementLayer.adjustAllLayerWeightsThresholdsRandomly();
			adjustedNeuralNetwork.layers[l] = adjustementLayer;
		}
		return adjustedNeuralNetwork;
	}

}
