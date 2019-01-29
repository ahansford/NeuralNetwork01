package com.neuralnetworktest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.neuralnetwork.HillClimb;
import com.neuralnetwork.NetworkLayer;
import com.neuralnetwork.NeuralNetwork;

class NeuralNetworkTest {
	
	public static double[][][] TEST_TRAINING_SET = new double[][][] {{{0, 0}, {0}},
																     {{0, 1}, {1}},
																     {{1, 0}, {1}},
																     {{1, 1}, {0}}};

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
	void testGetNetworkLayersReturnsSpecifiedLayerCount() {
		NeuralNetwork neuralNetwork = new NeuralNetwork(5);
		assertEquals( 5, neuralNetwork.getNetworkLayers().length);
	}
	
	@Test
	void testNeuralNetworkLayerCountThreeOnSimpleCreate() {
		int inputsCount = 1; 
        int hiddenLayerCount = 1; 
        int hiddenLayerNeuronCount = 1;
        int outputsCount = 1;
		NeuralNetwork neuralNetwork = new NeuralNetwork(inputsCount,
														hiddenLayerCount,
														hiddenLayerNeuronCount,
														outputsCount );
		assertEquals(3,neuralNetwork.getNetworkLayerCount());
	}
		
	@Test
	void testGetNetworkLayersReturnsNonNullLayersOnSimpleCreate() {
		int inputsCount = 1; 
        int hiddenLayerCount = 1; 
        int hiddenLayerNeuronCount = 1;
        int outputsCount = 1;
		NeuralNetwork neuralNetwork = new NeuralNetwork(inputsCount,
														hiddenLayerCount,
														hiddenLayerNeuronCount,
														outputsCount );
		NetworkLayer[] networkLayers = neuralNetwork.getNetworkLayers();
		assertNotNull(networkLayers[0]);
		assertNotNull(networkLayers[1]);
		assertNotNull(networkLayers[2]);
	}
	
	@Test
	void testGetNetworkLayerCountReturnsCorrectValueOnComplexCreate() {
		int inputsCount = 7; 
        int hiddenLayerCount = 3; 
        int hiddenLayerNeuronCount = 17;
        int outputsCount = 17;
		NeuralNetwork neuralNetwork = new NeuralNetwork(inputsCount,
														hiddenLayerCount,
														hiddenLayerNeuronCount,
														outputsCount );
		assertEquals((1+3+1),neuralNetwork.getNetworkLayerCount());
	}
	
	
	@Test
	void testNeuralNetworkInputCountOneOnSimpleCreate() {
		int inputsCount = 1; 
        int hiddenLayerCount = 1; 
        int hiddenLayerNeuronCount = 1;
        int outputsCount = 1;
		NeuralNetwork neuralNetwork = new NeuralNetwork(inputsCount,
														hiddenLayerCount,
														hiddenLayerNeuronCount,
														outputsCount );
		assertEquals(1,neuralNetwork.getNetworkInputCount());
	}

	@Test
	void testGetNetworkOutputCountReturnsOutputNeuronCountOnCreate() {
		int inputsCount = 7; 
        int hiddenLayerCount = 3; 
        int hiddenLayerNeuronCount = 17;
        int outputsCount = 17;
		NeuralNetwork neuralNetwork = new NeuralNetwork(inputsCount,
														hiddenLayerCount,
														hiddenLayerNeuronCount,
														outputsCount );
		assertEquals(17,neuralNetwork.getNetworkOutputCount());
	}
	
	@Test
	void testNeuralNetworkOutputCountOneOnSimpleCreate() {
		int inputsCount = 1; 
        int hiddenLayerCount = 1; 
        int hiddenLayerNeuronCount = 1;
        int outputsCount = 1;
		NeuralNetwork neuralNetwork = new NeuralNetwork(inputsCount,
														hiddenLayerCount,
														hiddenLayerNeuronCount,
														outputsCount );
		assertEquals(1,neuralNetwork.getNetworkOutputCount());
	}

	@Test
	void testNeuralNetworkEqualsSelf() {
		NeuralNetwork neuralNetwork = new NeuralNetwork(1,1,1,1);
		assertTrue( neuralNetwork.equals(neuralNetwork) );
	}
	
	@Test
	void testNeuralNetworkDiffernetLayerCountsNotEqual() {
		NeuralNetwork neuralNetwork = new NeuralNetwork(1,1,1,1);
		NeuralNetwork otherNetwork =  new NeuralNetwork(1,2,1,1);
		assertFalse( neuralNetwork.equals(otherNetwork) );
		
	}
	
	@Test
	void testConfiguredNetworkCanBeCopiedOverUninitializedNetwork() {
		NeuralNetwork neuralNetwork = new NeuralNetwork(1,1,1,1);
		NeuralNetwork simpleNeuralNetwork; // uninitialized
		simpleNeuralNetwork = neuralNetwork.copyNeuralNetwork();
		assertTrue( neuralNetwork.equals(simpleNeuralNetwork) );
	}
	
	@Test
	void testConfiguredNetworkCanBeCopiedOverSimpleNetwork() {
		NeuralNetwork neuralNetwork = new NeuralNetwork(1,1,1,1);
		NeuralNetwork simpleNeuralNetwork = new NeuralNetwork(1);
		simpleNeuralNetwork = neuralNetwork.copyNeuralNetwork();
		assertTrue( neuralNetwork.equals(simpleNeuralNetwork) );
	}
	
	@Test
	void testCopiedNeuralNetworkEqualsSelf() {
		NeuralNetwork neuralNetwork = new NeuralNetwork(1,1,1,1);
		NeuralNetwork copiedNetwork = new NeuralNetwork(1);
		copiedNetwork = neuralNetwork.copyNeuralNetwork();
		assertTrue( neuralNetwork.equals(copiedNetwork) );
		assertTrue( copiedNetwork.equals(neuralNetwork) );
		
		
		NeuralNetwork uninitializedNetwork = new NeuralNetwork(1);
		uninitializedNetwork = copiedNetwork.copyNeuralNetwork();
		assertTrue( copiedNetwork.equals(uninitializedNetwork) );
	}
	
	@Test
	void testTwoNetworksWithDifferentOutputCountsNotEqual() {
		NeuralNetwork neuralNetwork = new NeuralNetwork(1,1,1,1);
		NeuralNetwork copiedNetwork = new NeuralNetwork(1,1,1,3);
		assertFalse( neuralNetwork.equals(copiedNetwork) );
		assertFalse( copiedNetwork.equals(neuralNetwork) );
	}
	
	@Test
	void testCanCopyMinimallyInitializedNetwork() {
		NeuralNetwork neuralNetwork = new NeuralNetwork(1);
		NeuralNetwork copiedNetwork = neuralNetwork.copyNeuralNetwork();
		assertTrue(copiedNetwork.equals(neuralNetwork));
	}
	
	@Test
	void testAdjustedNeuralNetworkDoesAffectOriginal() {
		NeuralNetwork neuralNetwork = new NeuralNetwork(1,1,1,1);
		NeuralNetwork originalNetwork = neuralNetwork.copyNeuralNetwork();
		NeuralNetwork adjustedNetwork = ((HillClimb) neuralNetwork).adjustAllWeightsThresholdsRandomly();
		assertFalse( neuralNetwork.equals(adjustedNetwork) );
		assertTrue(originalNetwork.equals(neuralNetwork));;
	}

	@Test
	void testRunNetwork() {
		int inputsCount = 2; 
        int hiddenLayerCount = 1; 
        int hiddenLayerNeuronCount = 2;
        int outputsCount = 1;
		NeuralNetwork neuralNetwork = new NeuralNetwork(inputsCount,
														hiddenLayerCount,
														hiddenLayerNeuronCount,
														outputsCount );
		
		double[] inputs = new double[] {2, 3};
		neuralNetwork.runNetwork(inputs);
	}

	@Test
	void testRMSValueCalculatedCorrectly() {
		int inputsCount = 2; 
        int hiddenLayerCount = 1; 
        int hiddenLayerNeuronCount = 1;
        int outputsCount = 1;
		NeuralNetwork neuralNetwork = new NeuralNetwork(inputsCount,
														hiddenLayerCount,
														hiddenLayerNeuronCount,
														outputsCount );
		int trainingSetLength = TEST_TRAINING_SET.length;
		double[] inputs = new double[TEST_TRAINING_SET[0][0].length];
		inputs = TEST_TRAINING_SET[1][0];
		double result = 0;
		double runSquaresTotal = 0;
		
		neuralNetwork.runNetwork(inputs);
		for(int i = 0; i < trainingSetLength; i++) {
			neuralNetwork.runNetwork(inputs);
			result = neuralNetwork.getNetworkOutputs()[0];
			runSquaresTotal += (result - TEST_TRAINING_SET[i][1][0]) * (result - TEST_TRAINING_SET[i][1][0]);
		}
		double goalRMSerror = Math.sqrt(runSquaresTotal/trainingSetLength);
		assertTrue(goalRMSerror-neuralNetwork.calculateRMSerror(TEST_TRAINING_SET) < 0.001);
	}
	
	//@Disabled
	@Test
	void testNeuralNetworkToString() {
		int inputsCount = 2; 
        int hiddenLayerCount = 1; 
        int hiddenLayerNeuronCount = 1;
        int outputsCount = 4;
		NeuralNetwork neuralNetwork = new NeuralNetwork(inputsCount,
														hiddenLayerCount,
														hiddenLayerNeuronCount,
														outputsCount );
		//int trainingSetLength = TEST_TRAINING_SET.length;
		double[] inputs = new double[TEST_TRAINING_SET[0][0].length];
		inputs = TEST_TRAINING_SET[1][0];
		neuralNetwork.runNetwork(inputs);
		System.out.println(neuralNetwork.toString());
	}

}
