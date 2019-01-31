package com.neuralnetwork;

public class GradientDescent extends NeuralNetwork {

	public static int MAX_ITERATIONS = 2000;
	
	private int gradientLayerIndex  = 0;
	private int gradientNeuronIndex = 0;
	private int gradientWeightIndex = 0; 
	private double holdingStep;
	
	private double initialStep = 0.05;
	GradientDescent holdingNetwork = null;
	
	double originalRMSerror = 0;
	
	
	public GradientDescent(int layerCount) {
		super(layerCount);
		// TODO Auto-generated constructor stub
	}
	
	public GradientDescent(int inputsCount, 
	           		       int hiddenLayerCount, 
	           		       int hiddenLayerNeuronCount,
	           		       int outputsCount) {
		super(inputsCount, hiddenLayerCount, hiddenLayerNeuronCount, outputsCount);
	}
	
	 public void runGradiemtDescentAlgorithm(double[][][] trainingSet) {
			NeuralNetwork adjustedNetwork = new NeuralNetwork(this.getNetworkLayerCount());
			holdingNetwork = new GradientDescent(this.getNetworkLayerCount());
	        //double originalRMSerror = 0;
	        double adjustedRMSerror = 0;
	        //double currentGradientError = 0;
	        //double adjustedGradientError = 0;
	        //double holdingStep = 0;
	        double deltaCostMagnitude = 0;
	        double HoldingCostMagnitude = 0;
	        
	        this.setEpoch(0);
	        int k = 0;
	        //boolean runFlag = true;
	        while (k < MAX_ITERATIONS) {
	        	this.setEpoch(this.getEpoch() + 1);
	        	this.originalRMSerror = this.calculateRMSerror(trainingSet);
	        	adjustedNetwork.setNeuralNetworkTo(this);
	        	// any improvement with certainly be smaller in magnitude than the original error
	        	HoldingCostMagnitude = 0;
	        	System.out.println("\nNew Epoch: " + k );
	        	// find the largest gradient
	        	for(int l = 0; l < adjustedNetwork.getNetworkLayerCount(); l++) {
	        		for (int n = 0; n < adjustedNetwork.getNetworkLayerNeuronCount(l); n++ ) 	{
	        			for (int w = 0; w < adjustedNetwork.getNetworkLayerWeightCount(l); w++) {
	        				
	        				
	        				// adjust a single weight using the standard step size
	        				adjustedNetwork = adjustedNetwork.adjustNetworkNeuronWeight(l, n, w, this.initialStep);
	        				
	        				// calculate the new RMSerror
	        				adjustedRMSerror = adjustedNetwork.calculateRMSerror(trainingSet);
	        				
	        				// calculate the difference between the adjusted weight error and the original error
	        				double deltaCost = adjustedRMSerror - this.originalRMSerror;
	        				
	        				// if the deltaCost error is positive, then invert the step sign
	        				if ( deltaCost >= 0 ) {
	        					//  Step direction increased the cost function, opposite of our goal
	        					//  ... invert the step
	        					this.initialStep = -this.initialStep;
	        					//Reverse the original weight adjustment
	        					adjustedNetwork = adjustedNetwork.adjustNetworkNeuronWeight(l, n, w, this.initialStep);
	        					// make a step in the descending direction
	        					adjustedNetwork = adjustedNetwork.adjustNetworkNeuronWeight(l, n, w, this.initialStep);
	        					// calculate the new RMSerror
		        				adjustedRMSerror = adjustedNetwork.calculateRMSerror(trainingSet);
		        				// calculate the difference between the adjusted weight error and the original error
		        				deltaCost = adjustedRMSerror - originalRMSerror;
	        				}
	        				
	        				//System.out.println("DeltaCost should be negative here: " + deltaCost + ", k="+k+" "+l+ " " + n + " "+ w);
	        				
	        				deltaCostMagnitude = Math.abs(deltaCost);
	        				System.out.println("Shift if deltaCostMagnitude > HoldingCostMagnitude"+ deltaCostMagnitude + " " + HoldingCostMagnitude);
	        				if (deltaCostMagnitude > HoldingCostMagnitude) {
	        					// this weight is an improvement of the weights already tested
	        					// System.out.println("CurrentGradient: " + currentGradientError + ", HoldingGradient: " +  HoldingGradientError);
	        					HoldingCostMagnitude = deltaCostMagnitude;
	        					this.gradientLayerIndex  = l;
	        					this.gradientNeuronIndex = n;
	        					this.gradientWeightIndex = w;
	        					// holdingNetwork = adjustedNetwork;
	        					holdingStep = this.initialStep;
	        					System.out.println(" Found a stronger weight at index : " +l+ " " + n + " "+ w + " " +deltaCost + " " +deltaCostMagnitude);
	        				}
	        				
	        				// end of three loops 
	        			}
	        		}
	        	}
	        	System.out.println(" Selecting the weight at index : " +this.gradientLayerIndex+ " " + this.gradientNeuronIndex+ " "+ this.gradientWeightIndex + " " + HoldingCostMagnitude);
	        	
	        	HoldingCostMagnitude=0;
	        	
	        	
	        	// determine max useful step down the gradient
	        	holdingNetwork.setNeuralNetworkTo(adjustedNetwork);
	        	int stepMultiplier = ((GradientDescent) holdingNetwork).getStepMultiplier(trainingSet, this.holdingStep);
	        	System.out.println("index : " + k + "  "+gradientLayerIndex+ " " + gradientNeuronIndex + " "+ gradientWeightIndex + " " + HoldingCostMagnitude);
	        	System.out.println(",  stepMultiplier : " +stepMultiplier);
	        	adjustedNetwork = this.adjustNetworkNeuronWeight(gradientLayerIndex, 
	        												     gradientNeuronIndex, 
	        													 gradientWeightIndex, 
	        													 this.holdingStep * stepMultiplier);
	        
	        this.setNeuralNetworkTo(adjustedNetwork);
	        k++;
	        
	        }
	 }
	        
	public int getStepMultiplier(double[][][] trainingSet, double step) {
		//NeuralNetwork holdingNetwork = null;
	    //boolean breakFlag;
	    double holdingError = this.calculateRMSerror(trainingSet);
	    NeuralNetwork adjustedNetwork;
	    int i = 2;
	    double adjustedRMSerror;
	    boolean descentFlag =  true;
	    do {
	    	adjustedNetwork = this.adjustNetworkNeuronWeight(this.gradientLayerIndex, 
					 										this.gradientNeuronIndex, 
					 										this.gradientWeightIndex, 
					 										this.holdingStep );
	    	adjustedRMSerror = adjustedNetwork.calculateRMSerror(trainingSet);
	    	//adjustedRMSerror = adjustedNetwork.calculateRMSerror(trainingSet);
	    	if ( adjustedRMSerror < holdingError) {
	    		// the last step multiplier was an improvement
	    		System.out.println(" the last step multiplier was an improvement, i: " + i);
	    		holdingError = adjustedRMSerror;
	    		i++;
	    	} else { 
	    		descentFlag =  false; 
	    		// back up one step
	    		adjustedNetwork = this.adjustNetworkNeuronWeight(this.gradientLayerIndex, 
							this.gradientNeuronIndex, 
							this.gradientWeightIndex, 
							this.holdingStep );
	    		i--;
	    		System.out.println(" back up one step, i: " + i);
	    	}
	    } while (descentFlag);
	    System.out.println(" RETURNING, i: " + i);
	return i-1;  
	}


}
