package com.neuralnetwork;

public class NetworkLayer {
	
	public enum LayerType {UNKNOWN, I, H, O}
	
	// *** Members  ***
	LayerType layerType;
	private Neuron2[] neurons;
	
	
	// *** Access methods ***
	public void      setLayerType(LayerType layerType) {this.layerType = layerType;}
	public LayerType getLayerType() {return this.layerType;}
	
	public void     setNeurons(Neuron2[] neurons) {this.neurons = neurons;}
	public Neuron2[] getNeurons() {return this.neurons;}
	
	public void   setNeuronAtIndex(Neuron2 neuron, int index) {this.neurons[index] = neuron;}
	public Neuron2 getNeuronAtIndex(int index) {return this.neurons[index];}
	
	public int getInputCountIntoLayer() { 
		if (this.getLayerType() == LayerType.I) { return this.getNeuronCountInLayer(); } 
		else { return this.getNeuronAtIndex(0).getWeightsCount(); }
	}
	
	public int getNeuronCountInLayer() { return this.getNeurons().length; }
	
	public double[] getActivatedLayerOutputs() {
		int numberNeurons = this.getNeuronCountInLayer();
		double[] weightedOutputs = this.getWeightedLayerOutputs();
		double [] activatedOutputs = new double[numberNeurons];
		for (int n = 0; n < numberNeurons; n++) {
			activatedOutputs[n] = ActivationFunction.applyLogisticFunction(weightedOutputs[n]);
		}
		return activatedOutputs;
	}
	
	public double[] getWeightedLayerOutputs() {
		int numberNeurons = this.getNeuronCountInLayer();
		double[] weightedOutputs = new double[numberNeurons];
		for (int n = 0; n < numberNeurons; n++) {
			weightedOutputs[n] = this.getNeurons()[n].getOutput();
		}
		return weightedOutputs;
	}
	
	// *** Constructor(s) ***
	public NetworkLayer() {
		this.setLayerType( LayerType.UNKNOWN );
		this.setNeurons( new Neuron2[1] );  // create a new Neuron array
			double[] weights = new double[] {Math.random() - 0.5};
			double threshold = Math.random() - 0.5;
			double output = 0.0;
		this.setNeuronAtIndex( new Neuron2(weights, threshold, output), 0 );
	}
	
	public NetworkLayer(LayerType layerType, 
						int numberInputs,
			            int numberNeurons) {
		int inputCount = numberInputs;
		int neuronCount = numberNeurons;
		
		this.setLayerType( layerType );
		if (layerType == LayerType.I) inputCount = 1;  // override the input count for the input layer
		this.setNeurons( new Neuron2[neuronCount] );
			double[] weights = new double[inputCount];
			for (int n = 0; n < neuronCount; n++) {
				for (int i = 0; i < inputCount; i++) weights[i] = Math.random() - 0.5;
				double threshold = Math.random() - 0.5;
				double output = 0.0;
				this.setNeuronAtIndex( new Neuron2(weights, threshold, output), n );
			}
	}
	
	
	// *** Methods ***
	public boolean equals(NetworkLayer networkLayer) {
		if (networkLayer == null) return false; // missing network
		Neuron2[] neurons = this.getNeurons();
		Neuron2[] otherNeurons = networkLayer.getNeurons();
		
		if (this.getLayerType() != networkLayer.getLayerType()) {return false; }  // layers
		else if (neurons.length != otherNeurons.length)         {return false; }  // neuron count
		else { for (int i=0; i < neurons.length; i++) {
					if (!neurons[i].equals(otherNeurons[i]))    {return false; }  // neuron weights
				}
		}
		return true;   // SUCCESS no issues found 
	}
	
	public NetworkLayer copyNetworkLayer() {
		int originalNeuronCount = this.getNeuronCountInLayer();
		Neuron2[] originalNeurons = this.getNeurons();
		Neuron2[] copiedNeurons = new Neuron2[originalNeuronCount];
		NetworkLayer copiedLayer = new NetworkLayer();
		for (int i = 0; i < originalNeuronCount; i++) {
			copiedNeurons[i] = originalNeurons[i]; }
		copiedLayer.setNeurons(copiedNeurons);
		copiedLayer.setLayerType(this.getLayerType());
		return copiedLayer;
	}
	
	public void runLayer(double[] inputs) {
		int inputCount = inputs.length;
		int numberNeurons = this.getNeuronCountInLayer();
		double[] singleInput = new double[1];
		if (this.getLayerType() == LayerType.I) { 
			// Input layer processing needed
			if (inputCount != this.getNeuronCountInLayer()) {
				System.out.println("ERROR NetworkLayer.runLayer(): Inputs.length not matched to INPUT layer neuron count");
				return;}  // UNHANDLED ERROR
			for (int n = 0; n < numberNeurons; n++) {
				singleInput[0] = inputs[n];
				this.getNeurons()[n].runNeuron(singleInput);}
			}
		else {
			// This is a hidden or output neuron
			if (inputCount != this.getInputCountIntoLayer()) { 
				System.out.println("ERROR NetworkLayer.runLayer(): Inputs.length not matched to required input count to layer"); 
				return; } // input mismatch UNHANDLED ERROR
			for (int n = 0; n < numberNeurons; n++) {
				this.getNeurons()[n].runNeuron(inputs);
				}
		}
		return;
	}
	
    @Override
    public String toString() { 
    	StringBuffer sB = new StringBuffer();
    	sB.append(String.format("\n NetworkLayer {"));
    	sB.append("  LayerType:" + String.format("%s", this.getLayerType()));
    	int numberNeurons = this.getNeuronCountInLayer();
    	//System.out.println("Layer:toString:neuronCountneuronCount before loop: " + numberNeurons + " " + this.getNeurons().length);
    	Neuron2 neuron;
    	for(int i = 0; i < numberNeurons; i++) {
    		sB.append(",");
    		//System.out.println("Layer:toString:neuronCount: " + this.getNeurons().length + " index: " + i);
    		if (true) { neuron = this.getNeurons()[i];}
    		//System.out.println("neuron: " + i);
    		sB.append("\n     " + neuron.toString());
    	}
    	sB.append("}\n");
        return String.format("%s", sB); 
    }
	
    
	//////////////////////////////////////
	//*** Hill Climb Support Methods ***//
    
	public NetworkLayer adjustNetworkLayer() {
		NetworkLayer adjustedLayer = this.copyNetworkLayer();
		Neuron2[] originalNeurons = this.getNeurons();
		int neuronCount = this.getNeuronCountInLayer();
		Neuron2[] adjustedNeurons = new Neuron2[neuronCount]; 

		for (int i = 0; i < neuronCount; i++) {
			adjustedNeurons[i] = originalNeurons[i].getThresholdAndWeightsAdjustedNeuron(); 
		}
		adjustedLayer.setNeurons(adjustedNeurons);
		adjustedLayer.setLayerType(this.getLayerType());
		return adjustedLayer;
	}

	
	////////////////////////////////////////////////////
	//*** PRIVATE Gradient Descent Support Methods ***//
    
	/**
	 * Returns a new NetworkLayer with the the specified neuron 
	 * weight/threshold adjusted by the step amount.  There is no need
	 * to make a separate call to adjust the threshold.  The threshold 
	 * is managed within the index range as well as the weights.
	 */
    public NetworkLayer adjustLayerNeuronThresholdAndWeights(int neuronIndex, int thresholdAndWeightsIndex, double step) {
    	NetworkLayer adjustedLayer = this.copyNetworkLayer();
    	Neuron2 adjustedNeuron = adjustedLayer.getNeuronAtIndex(neuronIndex).getNeuronWithAdjustedThresholdAndWeightsAtIndex(thresholdAndWeightsIndex, step);
    	adjustedLayer.setNeuronAtIndex(adjustedNeuron, neuronIndex);
    	//System.out.println("Adjusting Neuron number: " + neuronIndex);
		return adjustedLayer;
	}

	/**
	 * DEPRECATED
	 * Returns a new NetworkLayer with the the specified neuron weight
	 * adjusted by the step amount.  used in conjunction with a specific
	 * call to adjust the threshold separate from the weights.  The weights
	 * or the threshold could be the larger affect. Both need to be checked.
	 */
    public NetworkLayer adjustLayerNeuronWeight(int neuronIndex, int weightIndex, double step) {
    	NetworkLayer adjustedLayer = this.copyNetworkLayer();
    	Neuron2 adjustedNeuron = adjustedLayer.getNeuronAtIndex(neuronIndex).getNeuronWithAdjustedWeightAtIndex(weightIndex, step);
    	adjustedLayer.setNeuronAtIndex(adjustedNeuron, neuronIndex);
    	//System.out.println("Adjusting Neuron number: " + neuronIndex);
		return adjustedLayer;
	}
    
}
