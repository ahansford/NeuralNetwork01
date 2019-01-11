package com.neuralnetwork;

public class NetworkLayer {
	public enum LayerType {I, H1, H2, O};
	
	LayerType layerType;
	private Neuron[] neurons;
	private NetworkLayer priorLayor;
	private NetworkLayer nextLayor;
	
	public void      setLayerType(LayerType layerType) {this.layerType = layerType;}
	public LayerType getLayerType() {return this.layerType;}
	
	public void     setNeurons(Neuron[] neurons) {this.neurons = neurons;}
	public Neuron[] getNeurons() {return this.neurons;}
	
	public void         setPriorLayor(NetworkLayer priorLayor) {this.priorLayor = priorLayor;}
	public NetworkLayer getPriorLayor() {return this.priorLayor;}
	
	public void         setNextLayor(NetworkLayer nextLayor) {this.nextLayor = nextLayor;}
	public NetworkLayer getNextLayor() {return this.nextLayor;}

}
