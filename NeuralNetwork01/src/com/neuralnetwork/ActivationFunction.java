package com.neuralnetwork;

public abstract class ActivationFunction {
	
	public static double applyRectifiedLinear(double weightedInput) {
		return (weightedInput < 0 ? 0 : weightedInput);
	}
	
	public static double applyLogisticFunction(double weightedInput) {
		return (1/(1+Math.exp(-weightedInput)));
	}

}
