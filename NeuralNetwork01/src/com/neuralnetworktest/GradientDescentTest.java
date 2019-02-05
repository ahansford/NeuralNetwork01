package com.neuralnetworktest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.neuralnetwork.GradientDescent;

import com.neuralnetwork.Neuron2;

class GradientDescentTest {
	
	double[][][] GRADIENT_TRAINING_SET = new double[][][] {{{1}, {-1}}};

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		assertTrue(true);
	}
	
	@Test
	void testGradientDescentListsCorrectNeuronInSingleNeuronNetwork() {
		
		GradientDescent gradientNetwork = getNewThreeNeuronGradientNetwork();
		double[] input = new double[] {1.0};
		gradientNetwork.runNetwork(input);
		System.out.println(gradientNetwork.toString());
		
		//gradientNetwork.runGradiemtDescentAlgorithm(GRADIENT_TRAINING_SET);
		//int multiplier = gradientNetwork.getStepMultiplier(GRADIENT_TRAINING_SET, -0.2);
		
		//System.out.println(gradientNetwork.toString() + multiplier);
		
	}
	
	GradientDescent getNewThreeNeuronGradientNetwork() {
		int inputsCount = 1;
        int hiddenLayerCount = 1; 
        int hiddenLayerNeuronCount = 1;
        int outputsCount = 1;
		GradientDescent gradientNetwork = new GradientDescent(inputsCount, hiddenLayerCount, hiddenLayerNeuronCount, outputsCount);
		double[] weights = new double[] {1.0};
		Neuron2 neuron = new Neuron2(weights, 0, 0);
		gradientNetwork.getNetworkLayers()[0].setNeuronAtIndex(neuron.copyNeuron(), 0);
		gradientNetwork.getNetworkLayers()[1].setNeuronAtIndex(neuron.copyNeuron(), 0);
		gradientNetwork.getNetworkLayers()[2].setNeuronAtIndex(neuron.copyNeuron(), 0);
		return gradientNetwork;
	}

}
