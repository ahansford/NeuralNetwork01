package com.neuralnetwork;

import com.neuralnetwork.NetworkLayer.*;

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
	
	public NetworkLayer[] getNetworkLayers() { return this.layers; }
	
	public int getNetworkLayerCount() { return this.layers.length; }
	
	
	public int getNetworkInputCount() {
		return this.layers[0].getInputCountIntoLayer(); 
		}
	
	public void runNetwork(double[] inputs) {
		/*
		 * if (inputs.length != this.layers[0].getInputCountIntoLayer()) {
		 
			System.out.print("InputsToLayer: " + this.layers[0].getInputCountIntoLayer());
			System.out.println("ERROR: inputs into network mishmatch");
			for (int x =0; x < inputs.length;x++) System.out.print(" in:" + x + " " + inputs[x]);
		}*/
		int layerCount = this.layers.length;
		NetworkLayer currentLayer;
		for (int index = 0; index < layerCount; index++) {
			currentLayer = this.layers[index];
			if (currentLayer.getLayerType() == LayerType.I) {
				// This is the input layer
				//System.out.println( "NeuralNetwork input layer Just before RunningNetwork: " + index);
				currentLayer.runLayer(inputs);
				
			} else {
				// This is not the input layer
				// Use outputs from the prior layer to pass as inputs
				//System.out.println( "NeuralNetwork h or o layer Just before RunningNetwork: " + index);
				currentLayer.runLayer(this.layers[index - 1].getLayerOutputs());
			}
			//System.out.println( "RunningNetwork: " + index + " "+ currentLayer.toString());
		}
		return;
	}

	public double[] getNetworkOutputs() {
		return this.layers[this.layers.length - 1].getLayerOutputs();
	}
	
    @Override
    public String toString() { 
    	StringBuffer sB = new StringBuffer();
    	sB.append("{ Network");
    	int numberLayers = this.getNetworkLayerCount();
    	for(int i=0; i < numberLayers; i++) {
    		sB.append("; ");
    		sB.append(this.getNetworkLayers()[i].toString());
    	}
    	sB.append(" }");
        return String.format("%s", sB); 
    }
}
