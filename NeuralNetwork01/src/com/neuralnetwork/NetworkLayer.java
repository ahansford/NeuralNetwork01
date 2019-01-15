package com.neuralnetwork;

public class NetworkLayer {
	public enum LayerType {UNKNOWN, I, H, O}
	
	LayerType layerType;
	private Neuron[] neurons;
	
	public NetworkLayer() {
		this.layerType = LayerType.UNKNOWN;
		this.neurons = new Neuron[1];
			double[] weights = new double[] {Math.random() - 0.5};
			double threshold = Math.random() - 0.5;
			double output = 0.0;
			neurons[0] = new Neuron(weights, threshold, output);
	}
	
	public NetworkLayer(LayerType layerType, 
						int numberInputs,
			            int numberNeurons) {
		int inputCount = numberInputs;
		int neuronCount = numberNeurons;
		this.layerType = layerType;
		if (layerType == LayerType.I) inputCount = 1;
		this.neurons = new Neuron[neuronCount];
			double[] weights = new double[inputCount];
			for (int n = 0; n < neuronCount; n++) {
				for (int i = 0; i < inputCount; i++) weights[i]=Math.random() - 0.5;
				double threshold = Math.random() - 0.5;
				double output = 0.0;
				neurons[n] = new Neuron(weights, threshold, output);
			}
	}
	
	public void      setLayerType(LayerType layerType) {this.layerType = layerType;}
	public LayerType getLayerType() {return this.layerType;}
	
	public void     setNeurons(Neuron[] neurons) {this.neurons = neurons;}
	public Neuron[] getNeurons() {return this.neurons;}
	
	public int getNeuronCountInLayer() { 
		return this.getNeurons().length; 
	}
	
	public int getInputCountIntoLayer() { 
		if (this.getLayerType() == LayerType.I) {
			return this.getNeuronCountInLayer(); 
		} else {
			return this.getNeurons()[0].getWeights().length; 
		}
	}
	
	public double[] getLayerOutputs() {
		int numberNeurons = this.getNeurons().length;
		double[] outputs = new double[numberNeurons];
		for (int i = 0; i < numberNeurons; i++) {
			outputs[i] = neurons[i].getOutput();
		}
		return outputs;
	}
	
	public void runLayer(double[] inputs) {
		//if (this.getInputCountIntoLayer() != inputs.length) { return; }
		//System.out.println("RunningLayer: " + this.toString());
		int numberNeurons = this.getNeuronCountInLayer();
		for (int n = 0; n < numberNeurons; n++) {
			//System.out.println("Layer: " + n);
			if (this.layerType == LayerType.I) {
				// This is an input class neuron
				if (this.getNeuronCountInLayer() != inputs.length) { 
					//System.out.println("ERROR: input layer neuron count and inputs[] mismatch");
					return; 
					}
				this.neurons[n].setOutput(inputs[n]);
			} else {
				// This is a hidden or output neuron
				this.neurons[n].runNeuron(inputs);
			}
		}
		//System.out.println("RunningLayer: " + this.toString());
		return;
	}
	
	
	public boolean equals(NetworkLayer networkLayer) {
		if (networkLayer == null) return false; // missing network
		Neuron[] neurons = this.getNeurons();
		Neuron[] otherNeurons = networkLayer.getNeurons();
		
		if (this.layerType != networkLayer.getLayerType())   {return false; }  // layers
		else if (neurons.length != otherNeurons.length)      {return false; }  // neuron count
		else { for (int i=0; i < neurons.length; i++) {
					if (!neurons[i].equals(otherNeurons[i])) {return false; }  // neuron weights
				}
		}
		return true;   // SUCCESS no issues found 
	}

	public NetworkLayer copyNetworkLayer() {
		int originalNeuronCount = this.getNeuronCountInLayer();
		Neuron[] originalNeurons = this.getNeurons();
		Neuron[] copiedNeurons = new Neuron[originalNeuronCount];
		NetworkLayer copiedLayer = new NetworkLayer();
		for (int i = 0; i < originalNeuronCount; i++) {
			copiedNeurons[i] = originalNeurons[i]; }
		copiedLayer.setNeurons(copiedNeurons);
		copiedLayer.setLayerType(this.getLayerType());
		return copiedLayer;
	}
	
	public NetworkLayer adjustNetworkLayer() {
		NetworkLayer adjustedLayer = this.copyNetworkLayer();
		Neuron[] originalNeurons = this.getNeurons();
		int neuronCount = this.getNeuronCountInLayer();
		Neuron[] adjustedNeurons = new Neuron[neuronCount]; 

		for (int i = 0; i < neuronCount; i++) {
			adjustedNeurons[i] = originalNeurons[i].getAdjustedNeuron(); 
		}
		adjustedLayer.setNeurons(adjustedNeurons);
		adjustedLayer.setLayerType(this.getLayerType());
		return adjustedLayer;
	}
	
    @Override
    public String toString() { 
    	StringBuffer sB = new StringBuffer();
    	sB.append(String.format("\n NetworkLayer {"));
    	sB.append("  LayerType:" + String.format("%s", layerType));
    	int numberNeurons = this.getNeuronCountInLayer();
    	//System.out.println("Layer:toString:neuronCountneuronCount before loop: " + numberNeurons + " " + this.getNeurons().length);
    	Neuron neuron;
    	for(int i = 0; i < numberNeurons; i++) {
    		sB.append("; ");
    		//System.out.println("Layer:toString:neuronCount: " + this.getNeurons().length + " index: " + i);
    		if (true) { neuron = this.getNeurons()[i];}
    		//System.out.println("neuron: " + i);
    		sB.append("\n     " + neuron.toString());
    	}
    	sB.append(" }");
        return String.format("%s", sB); 
    }
    
}
