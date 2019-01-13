package com.neuralnetwork;

import com.neuralnetwork.NetworkLayer.LayerType;

public class NeuralNetwork {
	
	
	private NetworkLayer[] layers;
	
	public NeuralNetwork(int inputsCount, 
			             int hiddenLayerCount, 
			             int hiddenLayerNeuronCount,
			             int outputsCount) {
		
		int layerCount = 1 + hiddenLayerCount + 1;
		layers = new NetworkLayer[layerCount];
		
		int index = 0;
		int numberInputs;
		int numberNeurons;
		NetworkLayer newLayer;
		
		
		// Create input layer
		numberInputs = 1;
		numberNeurons = inputsCount;
		newLayer = new NetworkLayer(LayerType.I, 
									numberInputs,
									numberNeurons, 
									null,
									null);
		layers[index++] = newLayer;
		
		
		 // Create hidden layers
		numberInputs = hiddenLayerNeuronCount;
		numberNeurons = outputsCount;
		for(int i = 0; i < hiddenLayerCount; i++) {
			// if index is '1' then layer is directly after input neurons
			if (index == 1) { numberInputs = inputsCount; }
		
			newLayer = new NetworkLayer(LayerType.H, 
									    numberInputs,
									    numberNeurons, 
									    null,
									    null);
			layers[index++] = newLayer;
		}

		// Create output layer;
		numberInputs = hiddenLayerNeuronCount;
		numberNeurons = outputsCount;
		newLayer = new NetworkLayer(LayerType.O, 
									numberInputs,
									numberNeurons, 
									null,
									null);
		layers[index] = newLayer;
	}
	
	NetworkLayer[] getNetworkLayers() { return this.layers; }
	
	public int getNetworkLayerCount() { return this.layers.length; }
	
	
	public int getNetworkInputCount() {
		return this.layers[0].getInputCountIntoLayer(); 
		}
	
	public void runNetwork(double[] inputs) {
		int layerCount = this.layers.length;
		int inputCount;
		for (int i=0; i < layerCount; i++) {
			inputCount = this.layers[i].getInputCountIntoLayer();
			double[] inputSet = new double[inputCount];
			if (i == 0) { inputSet = inputs; }
			else { inputSet = layers[i-1].getLayerOutputs(); } //TODO: put into a for loop
			this.layers[i].runLayer(inputSet);
		}
		return;
	}

	public double[] getNetworkOutputs() {
		return this.layers[this.layers.length - 1].getLayerOutputs();
	}
}
