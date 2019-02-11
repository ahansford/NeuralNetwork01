package com.neuralnetwork;

import com.neuralnetwork.NetworkLayer.*;


public class NeuralNetwork {
	
	//  *** Members *** //
	private NetworkLayer[] layers;
	protected boolean      verboseFlag = false;
	protected int          epoch = 0;
	protected double       RMSerror = 0.0;
	
	
	//*** Access Methods *** //	
	public NetworkLayer[] getNetworkLayers() { return this.layers; }
	
	public int getNetworkLayerCount() { 
		if (this.layers != null) { return this.layers.length; } // 
		else {return 0; }
	}
	
	
	//*** Second Level Access Methods *** //	
	
	public int getNetworkLayerNeuronCount(int layerIndex) {
		return this.getNetworkLayers()[layerIndex].getNeuronCountInLayer();
	}
	
	public int getNetworkLayerWeightCount(int layerIndex) {
		return this.getNetworkLayers()[layerIndex].getNeuronWeightsCountForLayer();
	}
	
	public int getNetworkLayerThresholdAndWeightsCountForLayer(int layerIndex) {
		return this.getNetworkLayers()[layerIndex].getNeuronThresholdAndWeightsCountForLayer();
	}
	
	public int getNetworkInputCount() {
		return this.getNetworkLayers()[0].getInputCountIntoLayer(); 
		}
	
	public double[] getNetworkOutputs() {
		return this.getNetworkLayers()[this.getNetworkLayerCount() - 1].getActivatedLayerOutputs();
	} 
	
	public int getNetworkOutputCount() {
		return this.getNetworkLayers()[this.getNetworkLayerCount() - 1].getNeuronCountInLayer();
	} 
	
	public void setNeuralNetworkTo(NeuralNetwork neuralNetwork) {
		this.layers = neuralNetwork.layers;
		this.verboseFlag = neuralNetwork.verboseFlag;
		this.epoch = neuralNetwork.epoch;
		this.RMSerror = neuralNetwork.RMSerror;
	}
	
	public void    setVerboseFlag(boolean verboseFlag) { this.verboseFlag = verboseFlag; }
	public boolean getVerboseFlag() { return this.verboseFlag; }
	
	public void   setRMSerror (double RMSerror) {this.RMSerror =  RMSerror; }
	public double getRMSerror() { return this.RMSerror; }
	
	public void setEpoch(int epoch) {this.epoch =  epoch; }
	public int  getEpoch() { return this.epoch; }
	
	
	//*** Constructor(s) ***
	/**
	 * Creates a simple three-layer network with IHO layers that each contain a 
	 * single neuron.
	 * 
	 */
	public NeuralNetwork() {  // Create a simple three layer network IHO
		int layerCount = 3;
		this.layers = new NetworkLayer[layerCount];
		
		NetworkLayer newLayer = new NetworkLayer();
		newLayer.setLayerType(LayerType.I);
		this.layers[0] = newLayer;
		
		newLayer = new NetworkLayer();
		newLayer.setLayerType(LayerType.H);
		this.layers[1] = newLayer;
		
		newLayer = new NetworkLayer();
		newLayer.setLayerType(LayerType.O);
		this.layers[2] = newLayer;
	}
	
	
	/**
	 * Creates a network with the specified number of layers.
	 * 
	 * Nominal layer count is 3, but it will generate a single layer 'I'nput layer,
	 * or an 'I'nput + 'O'utput layers 2-layer network.  Larger layer counts fill in 
	 * with 'H'idden layers.  
	 * 
	 * Note that input layers assume a single neuron with a single weight per input.
	 * 
	 */
	public NeuralNetwork(int layerCount) {
		this.layers = new NetworkLayer[layerCount];
		for (int i = 0; i < layerCount; i++) {
			NetworkLayer newLayer = new NetworkLayer();
			// The default layer type is hidden, the first layer dominates as input type
			// WARNING: The order of assignment is essential in this logic.
			newLayer.setLayerType(LayerType.H);
			if ( i == layerCount - 1) { newLayer.setLayerType(LayerType.O); }
			if ( i == 0 )             { newLayer.setLayerType(LayerType.I); }
			this.layers[i] = newLayer;
		}
	}
	
	/**
	 * Creates a network with the specified number of layers.  Provides options
	 * to specify network configuration parameters.
	 * 
	 */
	public NeuralNetwork(int inputsCount, 
			             int hiddenLayerCount, 
			             int hiddenLayerNeuronCount,
			             int outputsCount) {
		
		int layerCount = 1 + hiddenLayerCount + 1;
		this.layers = new NetworkLayer[layerCount];
		
		int index = 0;
		int numberInputs;
		int numberNeurons;
		NetworkLayer newLayer;
		
		
		// Create input layer
		numberInputs = 1;
		numberNeurons = inputsCount;
		newLayer = new NetworkLayer(LayerType.I, 
									numberInputs,
									numberNeurons);
		layers[index] = newLayer;
		index++;
		
		 // Create hidden layers
		numberInputs = inputsCount;
		numberNeurons = hiddenLayerNeuronCount;
		for(int i = 0; i < hiddenLayerCount; i++) {
			// if index is '1' then layer is directly after input neurons
		
			newLayer = new NetworkLayer(LayerType.H, 
									    numberInputs,
									    numberNeurons);
			layers[index] = newLayer;
			index++;
		}

		// Create output layer;
		numberInputs = hiddenLayerNeuronCount;
		numberNeurons = outputsCount;
		newLayer = new NetworkLayer(LayerType.O, 
									numberInputs,
									numberNeurons);
		layers[index] = newLayer;
	}
	

	// *** Methods ***	// 
	
	public boolean equals(NeuralNetwork otherNetwork) {
		int layerCount = this.getNetworkLayerCount(); 
		int otherLayerCount = otherNetwork.getNetworkLayerCount();
		if (otherLayerCount != layerCount) return false; // size mismatch
		for (int i = 0; i< layerCount; i++) {
			if (this.getNetworkLayers()[i]==null)         return false;  //empty layer
			if (otherNetwork.getNetworkLayers()[i]==null) return false;  //empty layer
			if( !this.getNetworkLayers()[i].equals(otherNetwork.getNetworkLayers()[i]) ) {
				return false;
			}
		}
		return true;  // SUCCESS no defect found
	}
	
	public NeuralNetwork copyNeuralNetwork() {
		int layerCount = this.getNetworkLayerCount();
		NeuralNetwork copiedNeuralNetwork = new NeuralNetwork(layerCount);
		
		for (int index = 0; index < layerCount; index++) {
			if (this.getNetworkLayers()[index] == null) return null;
			if (copiedNeuralNetwork.getNetworkLayers()[index] == null) return null;
			copiedNeuralNetwork.layers[index] = this.getNetworkLayers()[index].copyNetworkLayer();
		}
		copiedNeuralNetwork.setVerboseFlag(this.getVerboseFlag());
		copiedNeuralNetwork.setEpoch(this.getEpoch());
		copiedNeuralNetwork.setRMSerror(this.getRMSerror());
		return copiedNeuralNetwork;
	}
	
	public void runNetwork(double[] inputs) {
		int layerCount = this.getNetworkLayerCount();
		NetworkLayer currentLayer;
		
		for (int index = 0; index < layerCount; index++) {
			currentLayer = this.getNetworkLayers()[index];
			if (currentLayer.getLayerType() == LayerType.UNKNOWN) { 
				System.err.println("ERROR: NeuralNetwork.runNetwork() trying to run UNKNOWN layer type"); 
				return;
			}
			if (currentLayer.getLayerType() == LayerType.I) {
				// This is the input layer
				//System.out.println( "NeuralNetwork input layer Just before RunningNetwork: " + index);
				currentLayer.runLayer(inputs);
				
			} else {
				// This is not the input layer
				// Use outputs from the prior layer to pass as inputs
				// System.out.println("Getting prior layer outputs for layer index: " + index);
				NetworkLayer priorLayer = this.getNetworkLayers()[index - 1];
				currentLayer.runLayer(priorLayer.getActivatedLayerOutputs());
			}
			//System.out.println( "RunningNetwork: " + index + " "+ currentLayer.toString());
		}
		return;
	}
	
	public double calculateMeanSqrError(double[][][] trainingData) {
		double runningTotals = 0;
		double result;
		for ( int i = 0; i < trainingData.length; i++) {            // cycle through the training sets
			for (int j =0; j < this.getNetworkOutputCount(); j++) { // cycle through the network outputs
				this.runNetwork(trainingData[i][0]);
				result = this.getNetworkOutputs()[j];
				runningTotals += (trainingData[i][1][j] - result) * (trainingData[i][1][j] - result);
			}
		}
		double meanSqrError = runningTotals / (trainingData.length*this.getNetworkOutputCount()); 
		return meanSqrError;
	} 
	
	public double calculateRMSerror(double[][][] trainingData) {
		double RMSerror = Math.sqrt(calculateMeanSqrError(trainingData));
		this.setRMSerror(RMSerror);
		return RMSerror;
	}
	
    @Override
    public String toString() { 
    	StringBuffer sB = new StringBuffer();
    	sB.append("Network:{");
    	int numberLayers = this.getNetworkLayerCount();
    	for(int i=0; i < numberLayers; i++) {
    		sB.append(" ");
    		sB.append(this.getNetworkLayers()[i].toString());
    	}
    	sB.append("\n  }");
        return String.format("%s", sB); 
    }




	//////////////////////////////////////////////
	//*** PRIVATE Hill Climb Support Methods ***//
	
	public NeuralNetwork adjustNeuralNetwork() {
		NeuralNetwork adjustedNeuralNetwork = this.copyNeuralNetwork();
		
		int layerCount = this.getNetworkLayerCount();
		NetworkLayer adjustementLayer = new NetworkLayer();
		for (int index = 0; index < layerCount; index++) {
			adjustementLayer = this.getNetworkLayers()[index];
			adjustementLayer = adjustementLayer.adjustNetworkLayer();
			adjustedNeuralNetwork.layers[index] = adjustementLayer;
		}
		return adjustedNeuralNetwork;
	}
	
	public NeuralNetwork adjustNetworkNeuronWeight(int layerIndex, int neuronIndex, int weightIndex, double step) {
		NeuralNetwork adjustedNetwork = this.copyNeuralNetwork();
		NetworkLayer[] adjustedLayers = adjustedNetwork.getNetworkLayers();
		//adjustedLayers[layerIndex] = adjustedLayers[layerIndex].adjustLayerNeuronWeight(neuronIndex, weightIndex, step);
		adjustedLayers[layerIndex] = adjustedLayers[layerIndex].adjustLayerNeuronThresholdAndWeights(neuronIndex, weightIndex, step);
		adjustedNetwork.layers = adjustedLayers;
		//System.out.println("Adjusting layer number: " + layerIndex + ",  error: " + this.calculateRMSerror(Driver.TRAINING_SET));
		return adjustedNetwork;
	}

}
