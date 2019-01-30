package com.neuralnetwork;

public class Neuron2 {
	
	private double  LEARNING_RATE = 0.1;
	
	private double[] thresholdAndWeights = new double[] {0.0};
	private double output = 0.0;
	private int    index  = -1;  // resolves to index of 0 on first call

	public double[] getWeights() { 
		int weightsCount = this.getWeightsCount();
		double[] weights = new double[weightsCount];
		for (int i =0; i < weightsCount; i++) { weights[i] = thresholdAndWeights[i + 1]; }
		return weights; 
	} 
	
	public void setWeights(double[] weights) { 
		int weightsCount = weights.length;
		double[] newThresholdAndWeights = new double[weightsCount+1];
		newThresholdAndWeights[0] = this.getThreshold();
		for (int i = 0; i < weightsCount; i++) { newThresholdAndWeights[i+1] = weights[i]; }
		this.setThresholdAndWeights(newThresholdAndWeights); 
	}
	
	public double[] getThresholdAndWeights() { return this.thresholdAndWeights; }
	public void     setThresholdAndWeights(double[] thresholdAndWeights) { this.thresholdAndWeights = thresholdAndWeights; }
	
	public int getWeightsCount() { return (this.thresholdAndWeights.length - 1); }
	public int getThresholdAndWeightsCount() { return this.thresholdAndWeights.length;   }
	
	public double getThreshold() { return this.thresholdAndWeights[0]; }
	public void   setThreshold(double threshold) { this.thresholdAndWeights[0] = threshold; }
	
	public int getThresholdAndWeightsIndex() {
		if (this.index >= this.getThresholdAndWeightsCount() - 1) { this.index = 0;} else {this.index++;}
		if (this.index < 0 ) this.index = 0;
		return this.index;
	}
	public void resetThresholdAndWeightsIndex() { this.index = -1; }
	
	public double getOutput() { return output; }
	public void   setOutput(double output) { this.output = output; }
	
	//*** Constructor(s) ***//
	public Neuron2() { 
		double[] weights = { (Math.random() - 0.5) };
		this.setWeights( weights );
		this.setThreshold( Math.random() - 0.5 );
		this.setOutput( 0.0 );
	}
	
	public Neuron2( double[] weights, double threshold, double output) {
		double[] thresholdAndWeights = new double[ weights.length +1 ];
		thresholdAndWeights[0] = threshold;  // w0 is the threshold position
		// TODO System.out.println("thresholdAndWeights.length: " +thresholdAndWeights.length + ", weights.length: " +weights.length);
		
		for (int i = 0; i < weights.length; i++) { thresholdAndWeights[i+1] = weights[i]; } // weights are added after the threshold
		this.setThresholdAndWeights( thresholdAndWeights );
		this.setOutput( output );
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
		
	public Neuron2 copyNeuron () {
		double[] weights = new double[this.getWeightsCount()];
		for (int w = 0; w < this.getWeightsCount(); w++) { weights[w] = this.getWeights()[w]; }
		Neuron2 copiedNeuron = new Neuron2(weights, this.getThreshold(), this.getOutput());
		return copiedNeuron;
	}
	
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
	
	public static double applyActivationFunction(double weightedSum) {
		return (weightedSum < 0 ? 0: weightedSum);
	}
	
	public static double applyActivationFunction2(double weightedSum) {
		return 1.0 / (1 + Math.exp(-1.0 * weightedSum));
	}
	
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
}
