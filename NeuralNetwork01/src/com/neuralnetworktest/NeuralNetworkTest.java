package com.neuralnetworktest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
	void testNeuralNetworkOutputCountOneOnSimpleCreate() {
		int inputsCount = 1; 
        int hiddenLayerCount = 1; 
        int hiddenLayerNeuronCount = 1;
        int outputsCount = 1;
		NeuralNetwork neuralNetwork = new NeuralNetwork(inputsCount,
														hiddenLayerCount,
														hiddenLayerNeuronCount,
														outputsCount );
		assertEquals(1,neuralNetwork.getNetworkOutputs().length);
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
	void testGetNetworkLayers() {
		NeuralNetwork neuralNetwork = new NeuralNetwork(1,1,1,1);
		NetworkLayer[] networkLayers = neuralNetwork.getNetworkLayers();
		// show that at lease one neuron is present
		assertTrue( 0 < networkLayers[0].getNeurons().length);
	}
	
	@Test
	void testNeuralNetworkEqualsSelf() {
		NeuralNetwork neuralNetwork = new NeuralNetwork(1,1,1,1);
		assertTrue( neuralNetwork.equals(neuralNetwork) );
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
		
		neuralNetwork = neuralNetwork.adjustNeuralNetwork();
		assertFalse( neuralNetwork.equals(simpleNeuralNetwork) );
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
	void testCanCopyMinimallyInitializedNetwork() {
		NeuralNetwork neuralNetwork = new NeuralNetwork(1);
		NeuralNetwork copiedNetwork = neuralNetwork.copyNeuralNetwork();
		assertTrue(copiedNetwork.equals(neuralNetwork));
	}
	
	@Test
	void testAdjustedNeuralNetworkDoesAffectOriginal() {
		NeuralNetwork neuralNetwork = new NeuralNetwork(1,1,1,1);
		NeuralNetwork originalNetwork = neuralNetwork.copyNeuralNetwork();
		NeuralNetwork adjustedNetwork = neuralNetwork.adjustNeuralNetwork();
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
		
		//System.out.println(neuralNetwork.getNetworkLayers()[0]);
		double[] inputs = new double[] {2, 3};
		//System.out.println("Before runNetoworkTest" + neuralNetwork.toString());
		neuralNetwork.runNetwork(inputs);
		//System.out.println("After runNetoworkTest" + neuralNetwork.toString());
		//assertEquals(1, neuralNetwork.getNetworkOutputs()[0]);
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

}
