package com.neuralnetwork;

//import com.neuralnetwork.NetworkLayer.LayerType;

/**
 * Implements a complete layer of perceptrons
 * 
 * Each layer holds an array of Neurons, as well as a LayerType indicator.
 * 
 * The copyNetworkLayer() generates an entirely new layer copy
 * 
 */ 
public class NetworkLayer {
	
	
	public enum LayerType {UNKNOWN, I, H, O}
	
	// *** Members  ***
	
	/**
	 * LayerType includes 'I'nput, 'H'idden, 'O'utput, and "UNKNOWN"
	 * A layer listed with an UNKNOWN LayerType should be considered an error
	 */ 
	LayerType layerType;
	
	/**
	 * A collection of neurons contained within the layer
	 */
	private Neuron2[] neurons;
	
	
	///////////////////////////////////////////////////////////////////////
    // *** True accessors to the combined threshold and weights array ***//
	
	public void setLayerType(LayerType layerType) {
		if ( layerType == LayerType.I )
			if ( this.getNeurons() != null ) 
				if (1 != this.getNeurons()[0].getWeightsCount() ) 
					System.err.println("UNTRAPPED ERROR: NetworkLayer.setLayerType(): ERROR assigning layer with multi-weight neurons as an input layer");
			
		this.layerType = layerType;
		}
	
	public LayerType getLayerType() {return this.layerType;}
	
	public void setNeurons(Neuron2[] neurons) {
		if ( layerType == LayerType.I ) {
			if ( this.getNeurons() != null ) {
				if ( 1 != neurons[0].getWeightsCount() ) {
					System.err.println("UNTRAPPED ERROR: NetworkLayer.setNeurons(): ERROR assigning multi-weight neurons to an input layer");
				}}}
		this.neurons = neurons;
	}
	
	public Neuron2[] getNeurons() {return this.neurons;}
	
	public void   setNeuronAtIndex(Neuron2 neuron, int index) {this.neurons[index] = neuron;}
	public Neuron2 getNeuronAtIndex(int index) {return this.neurons[index];}
	
	public int getInputCountIntoLayer() { 
		if (this.getLayerType() == LayerType.I) { return this.getNeuronCountInLayer(); } 
		else { return this.getNeuronAtIndex(0).getWeightsCount(); }
	}
	
	public int getGetNeuronThresholdAndWeightsCountForLayer() { 
		return this.getNeuronAtIndex(0).getThresholdAndWeightsCount(); 
	}
	
	public int getNeuronCountInLayer() { return this.getNeurons().length; }
	
	public double[] getActivatedLayerOutputs() {
		int neuronCount = this.getNeuronCountInLayer();
		double[] weightedOutputs = this.getWeightedLayerOutputs();
		double [] activatedOutputs = new double[neuronCount];
		for (int n = 0; n < neuronCount; n++) {
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
	
	//////////////////////////
	//*** Constructor(s) ***//
	public NetworkLayer() {
		this.setLayerType( LayerType.UNKNOWN );
		this.setNeurons( new Neuron2[1] );  // create a new Neuron array
			double[] weights = new double[] {1.0};
			double threshold = 0.0;
			double output = 0.0;
		this.setNeuronAtIndex( new Neuron2(weights, threshold, output), 0 );
	}
	
	/**
	 * Creates a new network layer.  If the LayerType is 'I'nput, then the 
	 * number of inputs per neuron is reset to one for a one-to-one arrangement.
	 * Other layer types allow the number of inputs to be defined separately
	 * from the number of neurons.
	 */
	public NetworkLayer(LayerType layerType, 
						int       numberInputs,
			            int       numberNeurons) {
		int inputCount = numberInputs;
		int neuronCount = numberNeurons;
		
		this.setLayerType( layerType );
		if (layerType == LayerType.I) {
			if (inputCount != neuronCount) {
				System.err.println("UNTRAPPED ERROR: NetworkLayer.NetworkLayer(): ERROR mismatched input/neuron count for input layer");
			}
			inputCount = 1;  // override the input count for the input layer
		}
		this.setNeurons( new Neuron2[neuronCount] );
			double[] weights = new double[inputCount];
			for (int n = 0; n < neuronCount; n++) {
				for (int i = 0; i < inputCount; i++) weights[i] = 1.0;
				double threshold = 0.0;
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
		int inputCountIntoLayer = this.getInputCountIntoLayer();
		double[] singleInput = new double[1];
		
		if (this.getLayerType() == LayerType.UNKNOWN) { // TODO: UNHANDLED ERROR
			System.err.println("ERROR: NetworkLayer.runNetwork() trying to run UNKNOWN layer type"); 
			return; 
		}
		
		if (inputCount != inputCountIntoLayer) {  // TODO: UNHANDLED ERROR
			System.err.println("ERROR NetworkLayer.runLayer(): inputs.length not matched to this.getInputCountIntoLayer: "+ inputCountIntoLayer + ", inputs: " + inputCount);
			System.err.println(this.toString());
			return;
		}  
		
		if (this.getLayerType() == LayerType.I) {  // Input layer processing needed
			for (int n = 0; n < numberNeurons; n++) {
				singleInput[0] = inputs[n];
				this.getNeurons()[n].runNeuron(singleInput);}
			}
		else {  // This is a hidden or output neuron
			for (int n = 0; n < numberNeurons; n++) {
				this.getNeurons()[n].runNeuron(inputs);
			}
		}
		return;
	}
	
    @Override
    public String toString() { 
    	StringBuffer sB = new StringBuffer();
    	sB.append(String.format("NetworkLayer {"));
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
    	sB.append("}");
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
    	//adjustedLayer.setLayerType(this.getLayerType());
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
