package com.neuralnetwork;

//import java.util.stream.IntStream;

//import com.za.neuralnetwork.Neuron;

public class Neuron {
	
	private double  LEARNING_RATE = 1;
	
	private double[] weights = new double[] {0.0,0.0};
	private double threshold = 0;
	private double output = 0;
	//private NeuralNetwork.LayerType layerType;

	 /*
	public Neuron(NeuralNetwork.LayerType layerType) { 
		this.layerType = layerType; 
		}
	
	public Neuron( double threshold, double[] weights, NeuralNetwork.LayerType layerType) {
		this.threshold = threshold;
		this.weights = weights;
		this.layerType = layerType;
	}*/
	
	public double[] getWeights() { return weights; }
	public void     setWeights(double[] weights) { this.weights = weights; }
	
	public double getThreshold() { return threshold; }
	public void   setThreshold(double threshold) { this.threshold = threshold; }
	
	public double getOutput() { return output; }
	public void   setOutput(double output) { this.output = output; }
	
	public Neuron getAdjustedNeuron(Neuron neuron) {
		neuron = new Neuron();
		//IntStream.range(0, weights.length).forEach(x -> neuron.weights[x] += (Math.random() - 0.5) * LEARNING_RATE); 
		neuron.threshold += (Math.random() - 0.5) * LEARNING_RATE;
		return neuron;
	}
	
	public void setNeuron(Neuron neuron) {
		
		this.weights = neuron.weights;
		this.threshold = neuron.threshold;
		this.output = neuron.output; 
	}
	
	public double applyActivationFunction(double weightedSum) {
		if (weightedSum < 0) weightedSum=0;
		//if(weightedSum > 1) weightedSum=1;
		return weightedSum;
	}
	
	public double applyActivationFunction2(double weightedSum) {
		output = 1.0 / (1 + Math.exp(-1.0 * weightedSum));
		return output;
	}
	
    @Override
    public String toString() { 
    	StringBuffer sB = new StringBuffer();
    	sB.append(String.format("Neuron {"));
    	for(int i=0; i < weights.length; i++) {
    		sB.append("w"  + i + ":"  + String.format("%.4f|", weights[i]));
    		//string = String.format(string + "w" + String.format("%.i", i) + ":" + String.format("%.f2", weights[i]) + ", ");
    	}
    	sB.append("threshold:" + String.format("%.4f|", threshold));
    	sB.append("output:" + String.format("%.4f|", output));
    	sB.append("LayerType:");
    	/*switch (layerType) {
    	case I:  {sB.append("Input|"); break;}
    	case H:  {sB.append("Hidden|"); break;}
    	case O:  {sB.append("Output|"); break;}
    	
    	}*/
    	
        return String.format("%s", sB); 
    }
}

/*
package com.za.neuralnetwork;

import java.util.stream.IntStream;

public class Neuron {
	private double  LEARNING_RATE = 1;
	
	private double[] weights = new double[] {0.0,0.0};
	private double threshold = 0;
	private double output = 0;
	private NeuralNetwork.LayerType layerType;

	
	public Neuron(NeuralNetwork.LayerType layerType) { 
		this.layerType = layerType; 
		}
	
	public Neuron( double threshold, double[] weights, NeuralNetwork.LayerType layerType) {
		this.threshold = threshold;
		this.weights = weights;
		this.layerType = layerType;
	}
	
	public double[] getWeights() { return weights; }
	public void     setWeights(double[] weights) { this.weights = weights; }
	
	public double getThreshold() { return threshold; }
	public void   setThreshold(double threshold) { this.threshold = threshold; }
	
	public double getOutput() { return output; }
	public void   setOutput(double output) { this.output = output; }
	
	public NeuralNetwork.LayerType getLayerType() { return layerType; }
	
	public double applyActivationFunction(double weightedSum) {
		if (weightedSum < 0) weightedSum=0;
		//if(weightedSum > 1) weightedSum=1;
		return weightedSum;
	}
	
	public double applyActivationFunction2(double weightedSum) {
		output = 1.0 / (1 + Math.exp(-1.0 * weightedSum));
		return output;
	}
	
	public Neuron adjust() {
		Neuron neuron = new Neuron(layerType);
		IntStream.range(0, weights.length).forEach(x -> neuron.weights[x] += (Math.random() - 0.5) * LEARNING_RATE); 
		neuron.threshold += (Math.random() - 0.5) * LEARNING_RATE;
		return neuron;
	}
	
    @Override
    public String toString() { 
    	StringBuffer sB = new StringBuffer();
    	sB.append(String.format("Neuron {"));
    	for(int i=0; i < weights.length; i++) {
    		sB.append("w"  + i + ":"  + String.format("%.4f|", weights[i]));
    		//string = String.format(string + "w" + String.format("%.i", i) + ":" + String.format("%.f2", weights[i]) + ", ");
    	}
    	sB.append("threshold:" + String.format("%.4f|", threshold));
    	sB.append("output:" + String.format("%.4f|", output));
    	sB.append("LayerType:");
    	switch (layerType) {
    	case I:  {sB.append("Input|"); break;}
    	case H:  {sB.append("Hidden|"); break;}
    	case O:  {sB.append("Output|"); break;}
    	
    	}
    	
        return String.format("%s", sB); 
    }
}
*/