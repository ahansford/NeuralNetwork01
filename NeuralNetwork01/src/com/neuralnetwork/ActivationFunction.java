package com.neuralnetwork;

public class ActivationFunction {
	
	public static double applyLogisticFunction(double weightedSum) {
		return 1.0 / (1 + Math.exp(-1.0 * weightedSum));
	}

}
