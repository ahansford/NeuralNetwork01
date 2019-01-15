package com.neuralnetwork;

public class Neuron {
	
	private double  LEARNING_RATE = 0.05;
	
	private double[] weights = new double[] {0.0};
	private double threshold = 0;
	private double output = 0;

	public Neuron() { 
		double[] weights = {Math.random() - 0.5};
		this.weights = weights;
		this.threshold = Math.random() - 0.5;
		this.output = 0.0;
	}
	
	public Neuron( double[] weights, double threshold, double output) {
		this.weights = weights; 			
		this.threshold = threshold;
		this.output = output;
		}
	
	public double[] getWeights() { return weights; }
	public void     setWeights(double[] weights) { this.weights = weights; }
	
	public double getThreshold() { return threshold; }
	public void   setThreshold(double threshold) { this.threshold = threshold; }
	
	public double getOutput() { return output; }
	public void   setOutput(double output) { this.output = output; }
	
	public Neuron getAdjustedNeuron() {
		double[] originalWeights = this.getWeights();
		double[] adjustedWeights = new double[originalWeights.length];
		for (int i = 0; i < adjustedWeights.length; i++) {
			adjustedWeights[i] = originalWeights[i] + (Math.random() - 0.5) * LEARNING_RATE; 
		}
		
		double originalThreshold = this.getThreshold();
		double adjustedThreshold = originalThreshold + (Math.random() - 0.5) * LEARNING_RATE;  
		
		Neuron adjustedNeuron = new Neuron(adjustedWeights, adjustedThreshold, 0);
		return adjustedNeuron;
	}
	
	public Neuron copyNeuron() {
		Neuron copiedNeuron = new Neuron(this.getWeights(), this.getThreshold(), this.getOutput());
		return copiedNeuron;
	}
	
	public void setNeuron(Neuron neuron) {
		this.weights = neuron.weights;
		this.threshold = neuron.threshold;
		this.output = neuron.output; 
	}
	
	public boolean equals(Neuron neuron) {
		if (neuron == null)                                         {return false; } // FAIL missing neuron
		if (neuron.getWeights().length == this.getWeights().length) {
			//weight arrays are equal length and can be compared
			for (int i = 0; i < neuron.getWeights().length; i++) {
				if (this.getWeights()[i] != neuron.getWeights()[i]) { return false; } // FAIL weights mismatch
			}
		} else                                                      { return false; } // unequal weights length
		if (this.getThreshold() != neuron.getThreshold())           { return false; } // FAIL threshold mismatch
			// Leaving the output test out, as this may not be needed 
			// if (this.getOutput() != neuron.getOutput())          { return false; }
		return true;
	}
	
	
	public static double applyActivationFunction(double weightedSum) {
		if (weightedSum < 0) weightedSum=0;
		//if(weightedSum > 1) weightedSum=1;
		return weightedSum;
	}
	
	public static double applyActivationFunction2(double weightedSum) {
		double output = 1.0 / (1 + Math.exp(-1.0 * weightedSum));
		return output;
	}
	
	public void runNeuron(double[] inputs) {
		if (inputs.length != this.weights.length) return; // input mismatch
		double weightedSum = -this.getThreshold();
		for (int i = 0; i < this.weights.length; i++) {
			weightedSum += inputs[i] * this.weights[i];
		}
		double adjustedSum = applyActivationFunction2(weightedSum);
		this.setOutput(adjustedSum);
	}
	
    @Override
    public String toString() { 
    	StringBuffer sB = new StringBuffer();
    	sB.append(String.format("Neuron {"));
    	for(int i=0; i < weights.length; i++) {
    		sB.append("w"  + i + ":"  + String.format("%.4f", weights[i]));
    	}
    	sB.append("threshold:" + String.format("%.4f|", threshold));
    	sB.append("output:" + String.format("%.4f|", output));
        return String.format("%s", sB); 
    }
}
