package com.neuralnetwork;

public class NetworkLayer {
	public enum LayerType {I, H1, H2, O}
	
	LayerType layerType;
	private Neuron[] neurons;
	private NetworkLayer priorLayer;
	private NetworkLayer nextLayer;
	
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
	
	public void         setPriorLayor(NetworkLayer priorLayor) {this.priorLayer = priorLayor;}
	public NetworkLayer getPriorLayor() {return this.priorLayer;}
	
	public void         setNextLayor(NetworkLayer nextLayor) {this.nextLayer = nextLayor;}
	public NetworkLayer getNextLayor() {return this.nextLayer;}
	
	public int getNeuronCountInLayer(NetworkLayer networkLayer) { 
		return this.getNeurons().length; 
		}
	
	public int getInputCountIntoLayer(NetworkLayer networkLayer) { 
		return this.getNeurons()[0].getWeights().length; 
		}
	
	public double[] getLayerOutputs(NetworkLayer networkLayer) {
		int numberNeurons = networkLayer.getNeurons().length;
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

}
