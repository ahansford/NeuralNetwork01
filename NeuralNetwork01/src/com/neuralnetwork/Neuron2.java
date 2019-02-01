package com.neuralnetwork;


/**
 * Implements a perceptron model named Neuron2 
 * 
 * Modeled externally as a series of inputs to be multiplied by
 * their respective weighting factors.  The sum of these are then compared 
 * to a threshold to determine the perceptron output state.  The reported
 * output is typically passed through a compression function to limit the 
 * output range between 0 and 1.
 * 
 * Internal processing is able to determine the weight or threshold that 
 * results in the highest gradient change of the output error against a 
 * targeted goal output.  Adjusting weights or the threshold to minimize 
 * errors to this target is the primary purpose of network training.
 * 
 * Externally presented as a set of weights, a threshold and an output.
 * 
 */ 
public class Neuron2 {
	
	// TODO:  used as part of the Hill Climb algorithm.  This value should be
	// passed into the getNeuronWithAdjustedWeightAtIndex() so that tuning 
	// parameters can be explored.
	private double  LEARNING_RATE = 0.1;
	
	private double[] thresholdAndWeights = new double[] {0.0};
	private double output = 0.0;
	private int    index  = -1;  // resolves to index of 0 on first call

	
	///////////////////////////////////////////////////////////////////////
    // *** True accessors to the combined threshold and weights array ***//
	public int getThresholdAndWeightsCount() { return this.thresholdAndWeights.length; }
	
	public double[] getThresholdAndWeights() { return this.thresholdAndWeights; }
	
	public void setThresholdAndWeights(double[] thresholdAndWeights) 
		{ this.thresholdAndWeights = thresholdAndWeights; }
	
	public int getThresholdAndWeightsIndex() {
		if (this.index >= this.getThresholdAndWeightsCount() - 1) { 
			this.index = 0;
		} else { 
			this.index++; 
		}
		if (this.index < 0 ) this.index = 0;
		return this.index;
	}
	
	public void resetThresholdAndWeightsIndex() { this.index = -1; }
	
	
	//////////////////////////////////////////////////////////////////////////////
	// *** PUBLIC interface accessors to an array of weights and a threshold ***//
	
	/**
	 * Returns the count of weights/inputs supported in this neuron.
	 */
	public int getWeightsCount() { return (this.thresholdAndWeights.length - 1); }
	
	/**
	 * Returns an array of weights in this neuron.  Modifications to the returned array
	 * do not affect the original neuron.  If changes to the original neuron weights
	 * are desired, see getWeights().
	 */
	public double[] getWeights() { 
		int weightsCount = this.getWeightsCount();
		double[] weights = new double[weightsCount];
		for (int i =0; i < weightsCount; i++) { weights[i] = thresholdAndWeights[i + 1]; }
		return weights; 
	} 
	
	/**
	 * Sets the weights in this neuron. New weight count can be the same, larger
	 * or smaller than the original weight count.
	 */
	public void setWeights(double[] weights) { 
		int weightsCount = weights.length;
		double[] newThresholdAndWeights = new double[weightsCount+1];
		newThresholdAndWeights[0] = this.getThreshold();
		for (int i = 0; i < weightsCount; i++) { newThresholdAndWeights[i+1] = weights[i]; }
		this.setThresholdAndWeights(newThresholdAndWeights); 
	}
	
	/**
	 * Returns or sets the current threshold value.  Raw outputs above the threshold are
	 * deemed as the neuron firing.  Outputs below the threshold value are deemed
	 * as the neuron not firing.  The raw output value can be placed through a
	 * compressor function to narrow the reported output range between 0 and 1 for 
	 * example.
	 */
	public double getThreshold() { return this.thresholdAndWeights[0]; }
	public void   setThreshold(double threshold) { this.thresholdAndWeights[0] = threshold; }
	
	/**
	 * Returns or sets the output value.  Raw outputs above the threshold are
	 * deemed as the neuron firing.  Outputs below the threshold value are deemed
	 * as the neuron not firing.  The raw output value can be placed through a
	 * compressor function to narrow the reported output range between 0 and 1 for 
	 * example.
	 */
	public double getOutput() { return output; }
	public void   setOutput(double output) { this.output = output; }
	
	//////////////////////////
	//*** Constructor(s) ***//
	
	/**
	 * Simple Constructor creates a neuron with a single input and weight.  The weight value 
	 * and threshold values are assigned a random value between -0.5 and +0.5.
	 */
	public Neuron2() { 
		double[] weights = { (Math.random() - 0.5) };
		this.setWeights( weights );
		this.setThreshold( Math.random() - 0.5 );
		this.setOutput( 0.0 );
	}
	
	/**
	 * Complex Constructor creates a neuron with the number of weights contained 
	 * in the weights[] input.  The weights[] array values should be initialized 
	 * prior to calling the constructor.  The threshold value is assigned a 
	 * the specified value.  The output is set to the specified value; a value 
	 * of 0 is acceptable.
	 */
	public Neuron2( double[] weights, double threshold, double output) {
		double[] thresholdAndWeights = new double[ weights.length +1 ];
		thresholdAndWeights[0] = threshold;  // w0 is the threshold position
		// TODO System.out.println("thresholdAndWeights.length: " +thresholdAndWeights.length + ", weights.length: " +weights.length);
		
		for (int i = 0; i < weights.length; i++) { thresholdAndWeights[i+1] = weights[i]; } // weights are added after the threshold
		this.setThresholdAndWeights( thresholdAndWeights );
		this.setOutput( output );
		}
	
	
	//////////////////////////////////////////
	//*** Public General Support Methods ***//
	
	/**
	 * Run processes the specified input[] array multiplying each input by its 
	 * matching weight.  The sum of these results is compared to the threshold
	 * level to determine if the Neuron has fired.  The raw output is accessed 
	 * through getOutput(), and it has not been put through the compressor
	 * function.  To apply the compressor function, call ...
	 *  .... setOutput(applyActivationFunction(getOutput()));
	 */
	public void runNeuron(double[] inputs) {
		// GOAL:  Sum all neuron inputs[i]*weights[i], then subtract the threshold
		// OUTPUT a positive number if weightedSum is less than the threshold
		// ... if weightedSum-threshold is negative, the neuron did not fire
		int weightCount = this.getWeightsCount();
		if (inputs.length != weightCount) return; // input mismatch  UNREPORTED ERROR 
		double[] weights = this.getWeights();
		double weightedSum = -this.getThreshold();  // preemptively subtract the current threshold
		for (int i = 0; i < weightCount; i++) {
			weightedSum += inputs[i] * weights[i];
		}
		//weightedSum = applyActivationFunction2(weightedSum);
		this.setOutput(weightedSum);
	}
	
	public static double applyActivationFunction(double weightedSum) {
		return (weightedSum < 0 ? 0: weightedSum);
	}
	
	public static double applyActivationFunction2(double weightedSum) {
		return 1.0 / (1 + Math.exp(-1.0 * weightedSum));
	}
	
	public Neuron2 copyNeuron () {
		double[] weights = new double[this.getWeightsCount()];
		for (int w = 0; w < this.getWeightsCount(); w++) { weights[w] = this.getWeights()[w]; }
		Neuron2 copiedNeuron = new Neuron2(weights, this.getThreshold(), this.getOutput());
		return copiedNeuron;
	}
	
	public boolean equals(Neuron2 neuron) {
		if (neuron == null)                                                             {return false;} // FAIL missing neuron
		if (neuron.getThresholdAndWeightsCount() != this.getThresholdAndWeightsCount()) {return false;} // FAIL mismatched weight count	
		for (int i = 0; i < this.getThresholdAndWeightsCount(); i++) {
			if (this.getThresholdAndWeights()[i] != neuron.getThresholdAndWeights()[i]) {return false;} // FAIL threshold and weights doesn't match
		}
			// Leaving the output test out, as this may not be needed 
			// if (this.getOutput() != neuron.getOutput()) {return false;}
		return true;  // SUCCESS no differences found
	}
	
    @Override
    public String toString() { 
    	StringBuffer sB = new StringBuffer();
    	sB.append(String.format("Neuron {|"));
    	for(int i=0; i < getWeightsCount(); i++) {
    		sB.append(" w"  + i + ":"  + String.format("%.4f", this.getWeights()[i]));
    	}
    	sB.append(" threshold:" + String.format("%.4f |", this.getThreshold()));
    	sB.append(" output:" + String.format("%.4f |", this.getOutput()));
        sB.append( String.format("}") );
        return String.format("%s", sB); 
    }
    
    
	/////////////////////////////////////////////
	//*** Hill Climb Support Methods ***//
	
	/**
	 * Returns a new Neuron2 with modifications to all weights and the threshold.
	 * This method is typically used at the core of the Hill Climb training algorithm
	 * to search out improved configurations.  The LEARNING_RATE is roughly a step size 
	 * that can be used to tune the efficiency of the Hill Climb algorithm.
	 */
	public Neuron2 getThresholdAndWeightsAdjustedNeuron() {
		int weightsCount = this.getWeightsCount();
		double[] originalWeights = this.getWeights();
		double[] adjustedWeights = new double[weightsCount];
		for (int i = 0; i < weightsCount; i++) {
			adjustedWeights[i] = originalWeights[i] + (Math.random() - 0.5) * LEARNING_RATE; 
		}
		double adjustedThreshold = this.getThreshold() + (Math.random() - 0.5) * LEARNING_RATE;  
		return new Neuron2(adjustedWeights, adjustedThreshold, 0);
	}
	
	
	////////////////////////////////////////////////////
	//*** PRIVATE Gradient Descent Support Methods ***//
	
	
	// TODO is this truly needed once the code shifts to provide the 
	// largest gradient descent option.
	public Neuron2 getNeuronWithAdjustedWeightAtIndex(int weightIndex, double step) {
		if ((weightIndex) > this.getWeightsCount() ) return this;  // ERROR weight index
		if ((weightIndex) < 0 ) return this;                       // ERROR weight index
		Neuron2  adjustedNeuron= this.copyNeuron();
		double[] adjustedWeights = adjustedNeuron.getWeights();
		adjustedWeights[weightIndex] += step; 
		adjustedNeuron.setWeights(adjustedWeights);
		return adjustedNeuron;
	}
	
	public Neuron2 getNeuronWithAdjustedThresholdAndWeightsAtIndex(int weightIndex, double step) {
		if ((weightIndex) > this.getWeightsCount() ) return this;  // ERROR weight index
		if ((weightIndex) < 0 ) return this;                       // ERROR weight index
		Neuron2  adjustedNeuron= this.copyNeuron();
		double[] adjustedThresholdAndWeights = adjustedNeuron.getThresholdAndWeights();
		adjustedThresholdAndWeights[weightIndex] += step; 
		adjustedNeuron.setThresholdAndWeights(adjustedThresholdAndWeights);
		return adjustedNeuron;
	}
	
	
}
