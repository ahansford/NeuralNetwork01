package com.neuralnetwork;

public class NetworkLayer {
	public enum LayerType {UNKNOWN, I, H, O}
	
	LayerType layerType;
	private Neuron[] neurons;
	private NetworkLayer priorLayer;
	private NetworkLayer nextLayer;
	
	public NetworkLayer() {
		this.layerType = LayerType.UNKNOWN;
		this.neurons = new Neuron[1];
			double[] weights = new double[] {0.0};
			double threshold = 0.0;
			double output = 0.0;
			neurons[0] = new Neuron(weights, threshold, output);
			
		this.priorLayer = null;
		this.nextLayer  = null;
		
	}
	
	public NetworkLayer(LayerType layerType, 
						int numberInputs,
			            int numberNeurons, 
			            NetworkLayer priorLayer,
			            NetworkLayer nextLayer) {
		
		this.layerType = layerType;
		this.neurons = new Neuron[numberNeurons];
			double[] weights = new double[numberInputs];
			for (int i = 0; i < numberInputs; i++) weights[i]=0.0;
			double threshold = 0.0;
			double output = 0.0;
			for (int n = 0; n < numberNeurons; n++) {
				neurons[n] = new Neuron(weights, threshold, output);
			}
		this.priorLayer = priorLayer;
		this.nextLayer  = nextLayer;
	}
	
	public void      setLayerType(LayerType layerType) {this.layerType = layerType;}
	public LayerType getLayerType() {return this.layerType;}
	
	public void     setNeurons(Neuron[] neurons) {this.neurons = neurons;}
	public Neuron[] getNeurons() {return this.neurons;}
	
	public void         setPriorLayer(NetworkLayer priorLayer) {this.priorLayer = priorLayer;}
	public NetworkLayer getPriorLayer() {return this.priorLayer;}
	
	public void         setNextLayer(NetworkLayer nextLayer) {this.nextLayer = nextLayer;}
	public NetworkLayer getNextLayer() {return this.nextLayer;}
	
	public int getNeuronCountInLayer() { 
		return this.getNeurons().length; 
		}
	
	public int getInputCountIntoLayer() { 
		return this.getNeurons()[0].getWeights().length; 
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
		int numberInputs  = inputs.length;
		int numberNeurons = this.getNeurons().length;
		double weightedSum;
		int maxMultiplyCount;
		int neuronWeightCount;
		for (int n = 0; n < numberNeurons; n++) {
			neuronWeightCount=neurons[n].getWeights().length;
			weightedSum = 0.0;
			maxMultiplyCount = (numberInputs > neuronWeightCount ? neuronWeightCount : numberInputs);  // minimum
			for (int i = 0; i < maxMultiplyCount; i++) {
				weightedSum += neurons[n].getWeights()[i] * inputs[i];
			}
			weightedSum = Neuron.applyActivationFunction(weightedSum);
			neurons[n].setOutput(weightedSum);
		}
		return;
	}
	
	public boolean equals(NetworkLayer networkLayer) {
		boolean equalsFlag = true;
		Neuron[] neurons = this.getNeurons();
		Neuron[] otherNeurons = networkLayer.getNeurons();
		
		if (this.layerType != networkLayer.getLayerType())   { equalsFlag = false; }
		if (neurons.length != otherNeurons.length)           { equalsFlag = false; }
		else { for (int i=0; i < neurons.length; i++) {
					if (!neurons[i].equals(otherNeurons[i]))  { equalsFlag = false; }
				}
		}
		if (this.priorLayer != networkLayer.getPriorLayer()) { equalsFlag = false; }
		if (this.nextLayer != networkLayer.getNextLayer())   { equalsFlag = false; }
		return equalsFlag;
	}

}
