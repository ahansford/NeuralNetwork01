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


}
