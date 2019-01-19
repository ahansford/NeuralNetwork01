package com.neuralnetwork;

import com.neuralnetwork.NetworkLayer.*;


public class NeuralNetwork {
	
	//  *** Members ***
	private NetworkLayer[] layers;
	protected boolean verboseFlag = false;
	
	
	//*** Access Methods ***	
	public NetworkLayer[] getNetworkLayers() { return this.layers; }
	
	public int getNetworkLayerCount() { 
		if (this.layers != null) { return this.layers.length; } // 
		else {return 0; }
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
	
	public void setNeuralNetworkTo(NeuralNetwork neuralNetwork) {this.layers = neuralNetwork.layers;}
	
	public void setVerboseFlag(boolean verboseFlag) { this.verboseFlag = verboseFlag; }
	
	
	//*** Constructor(s) ***
	public NeuralNetwork(int layerCount) {
		this.layers = new NetworkLayer[layerCount];
		for (int i = 0; i < layerCount; i++)
			this.layers[i]= new NetworkLayer();
	}
	
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
	

	// *** Methods ***	
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
		return copiedNeuralNetwork;
	}
	
	public void runNetwork(double[] inputs) {
		int layerCount = this.getNetworkLayerCount();
		NetworkLayer currentLayer;
		for (int index = 0; index < layerCount; index++) {
			currentLayer = this.getNetworkLayers()[index];
			if (currentLayer.getLayerType() == LayerType.I) {
				// This is the input layer
				//System.out.println( "NeuralNetwork input layer Just before RunningNetwork: " + index);
				currentLayer.runLayer(inputs);
				
			} else {
				// This is not the input layer
				// Use outputs from the prior layer to pass as inputs
				currentLayer.runLayer(this.getNetworkLayers()[index - 1].getActivatedLayerOutputs());
			}
			//System.out.println( "RunningNetwork: " + index + " "+ currentLayer.toString());
		}
		return;
	}
	
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
    	sB.append(" }");
        return String.format("%s", sB); 
    }
}
