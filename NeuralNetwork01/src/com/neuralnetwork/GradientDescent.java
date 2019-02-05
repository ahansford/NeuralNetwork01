package com.neuralnetwork;

public class GradientDescent extends NeuralNetwork {

	public static int MAX_ITERATIONS = 2000;
	
	private int gradientLayerIndex  = 0;
	private int gradientNeuronIndex = 0;
	private int gradientWeightIndex = 0;
	
	private double initialStep = 0.05;
	private double holdingStep;
	
	//double originalRMSerror = 0;
	
	
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
			//Create a copy of this original network to manipulate looking for the greatest gradient
		 	NeuralNetwork adjustedNetwork = new NeuralNetwork(this.getNetworkLayerCount());
		 	
		 	// Create a holding network to hold the best intermediate values at they are found
		 	NeuralNetwork holdingNetwork = new GradientDescent(this.getNetworkLayerCount());
			
			// Original error is the "this" network error, the adjusted error is the current manipulated 
			// network error, holding error is the largest error found at a given stage through the algorithm
	        double originalRMSerror = this.calculateRMSerror(trainingSet);;
	        double adjustedRMSerror = originalRMSerror;
	        double holdingRMSerror  = originalRMSerror;

	        this.setEpoch(0);
	        int k = 0;
	        while (k < MAX_ITERATIONS) {
	        	this.setEpoch(this.getEpoch() + 1);
	        	System.out.println("\nNew Epoch: " + this.getEpoch() );
	        	
	        	// set all errors equal to the original network to start, improvements should be lower
	        	//adjustedRMSerror = originalRMSerror;
	        	//holdingRMSerror  = originalRMSerror;
	        	
	        	// Copy the original Network on each pass through
	        	adjustedNetwork.setNeuralNetworkTo(this);
	        	// Inject a random setting based on the Neuron2 learning rate
	        	adjustedNetwork.adjustNeuralNetwork();
	        	
	        	// loop through network layers, neurons and weights to find the largest gradient that reduces the error
	        	for(int l = 0; l < adjustedNetwork.getNetworkLayerCount(); l++) {
	        		for (int n = 0; n < adjustedNetwork.getNetworkLayerNeuronCount(l); n++ ) 	{
	        			for (int w = 0; w < adjustedNetwork.getNetworkLayerThresholdAndWeightsCountForLayer(l); w++) {
	        				System.out.print("Layer: " + l + "  Neuron: " + n + "  Weight: " + w);
	        				// adjust a single weight using the standard step size
	        				adjustedNetwork = adjustedNetwork.adjustNetworkNeuronWeight(l, n, w, this.initialStep);
	        				
	        				// calculate the new RMSerror
	        				adjustedRMSerror = adjustedNetwork.calculateRMSerror(trainingSet);
	        				
	        				// If adjusted error is larger than original, then try an inverted step
	        				if (adjustedRMSerror > originalRMSerror ) {
	        					// The original step increased the error, therefore invert step sign and re-check
	        					this.initialStep = -this.initialStep;
	        					adjustedNetwork = adjustedNetwork.adjustNetworkNeuronWeight(l, n, w, this.initialStep);
	        					adjustedRMSerror = adjustedNetwork.calculateRMSerror(trainingSet);
	        				}
	        				
	        				// If the inverted step direction still increases the error, then break out of this loop iteration
	        				if (adjustedRMSerror > originalRMSerror ) {
	        					// Neither step direction reduced the error ... break
	        					System.out.println(" Neither step direction improved at index : " +l+ " " + n + " "+ w );
	        					break;
	        				}
	        				
	        				// The adjusted error has smaller than the original error
	        				// Check to see if the adjusted error is smaller than the holding error
	        				
	        				System.out.println("Testing if adjustedRMSerror < holdingRMSerror  "+ adjustedRMSerror + " " + holdingRMSerror + " - "+(adjustedRMSerror < holdingRMSerror ? "new " : "keep")+" : "+ (holdingRMSerror-adjustedRMSerror) );
	        				if (adjustedRMSerror < holdingRMSerror) {
	        					//System.out.println("  SHIFT to new");
	        					System.out.println("   ... Shifting because the adjustedRMSerror < holdingRMSerror  "+ adjustedRMSerror + " " + holdingRMSerror);
		        				
	        					// this weight step is an improvement of the weights already tested
	        					// System.out.println("CurrentGradient: " + currentGradientError + ", HoldingGradient: " +  HoldingGradientError);
	        					holdingRMSerror = adjustedRMSerror;
	        					this.gradientLayerIndex  = l;
	        					this.gradientNeuronIndex = n;
	        					this.gradientWeightIndex = w;
	        					// holdingNetwork = adjustedNetwork;
	        					holdingStep = this.initialStep;
	        					holdingNetwork.setNeuralNetworkTo(adjustedNetwork.copyNeuralNetwork());
	        					System.out.println(" Found a stronger weight at index : " +l+ " " + n + " "+ w + " " +holdingRMSerror + " " +holdingRMSerror);
	        				} else {
	        					//System.out.println("  Keep old");
	        					
	        				}
	        
	        				
	        				// end of three loops inner code
	        			}
	        		}
	        	}
	        	
	        	// Pick the holding network since it holds the greatest improvement.
	        	System.out.println(" Selecting the weight at index : " +this.gradientLayerIndex+ " " + this.gradientNeuronIndex+ " "+ this.gradientWeightIndex + " Step: " + this.initialStep + " error: "+ holdingRMSerror);
	        	
	        	this.setNeuralNetworkTo(holdingNetwork);
	        	//this = holdingNetwork.copyNeuralNetwork();
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
