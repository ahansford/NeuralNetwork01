package com.neuralnetwork;

public class Neuron {
	
	private double  LEARNING_RATE = 0.1;
	
	private double[] weights = new double[] {0.0};
	private double threshold = 0;
	private double output = 0;

	public double[] getWeights() { return weights; }
	public void     setWeights(double[] weights) { this.weights = weights; }
	
	public int getWeightCount() { return this.getWeights().length; }
	
	public double getThreshold() { return threshold; }
	public void   setThreshold(double threshold) { this.threshold = threshold; }
	
	public double getOutput() { return output; }
	public void   setOutput(double output) { this.output = output; }
	
	
	public Neuron() { 
		double[] weights = { (Math.random() - 0.5) };
		this.setWeights( weights );
		this.setThreshold( Math.random() - 0.5 );
		this.setOutput( 0.0 );
	}
	
	public Neuron( double[] weights, double threshold, double output) {
		this.setWeights( weights );
		this.setThreshold( threshold );
		this.setOutput( output );
		}
	
	
	public boolean equals(Neuron neuron) {
		if (neuron == null)                                     {return false;} // FAIL missing neuron
		if (neuron.getWeightCount() != this.getWeightCount())   {return false;} // FAIL mismatched weight count	
		for (int i = 0; i < this.getWeightCount(); i++) {
			if (this.getWeights()[i] != neuron.getWeights()[i]) {return false;} // FAIL weight doesn't match
		}
		if (this.getThreshold() != neuron.getThreshold())       {return false;} // FAIL threshold doesn't match
			// Leaving the output test out, as this may not be needed 
			// if (this.getOutput() != neuron.getOutput())          {return false;}
		return true;  // SUCCESS no differences found
	}
		
	public Neuron copyNeuron() {
		Neuron copiedNeuron = new Neuron(this.getWeights(), this.getThreshold(), this.getOutput());
		return copiedNeuron;
	}
	
	public Neuron getAdjustedNeuron() {
		int weightCount = this.getWeightCount();
		double[] originalWeights = this.getWeights();
		double[] adjustedWeights = new double[weightCount];
		for (int i = 0; i < weightCount; i++) {
			adjustedWeights[i] = originalWeights[i] + (Math.random() - 0.5) * LEARNING_RATE; 
		}
		double adjustedThreshold = this.getThreshold() + (Math.random() - 0.5) * LEARNING_RATE;  
		return new Neuron(adjustedWeights, adjustedThreshold, 0);
	}
	
	/**/
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
		int weightCount = this.getWeightCount();
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
    	for(int i=0; i < weights.length; i++) {
    		sB.append("w"  + i + ":"  + String.format("%.4f", weights[i]));
    	}
    	sB.append("threshold:" + String.format("%.4f|", threshold));
    	sB.append("output:" + String.format("%.4f|", output));
        sB.append( String.format("}") );
        return String.format("%s", sB); 
    }
}
