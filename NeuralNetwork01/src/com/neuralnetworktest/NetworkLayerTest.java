package com.neuralnetworktest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.neuralnetwork.NetworkLayer;
import com.neuralnetwork.NetworkLayer.LayerType;
import com.neuralnetwork.Neuron;


class NetworkLayerTest {
	
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
	void testNetworkLayerZeroOnCreate() {
		NetworkLayer  networkLayer = new NetworkLayer();
		assertEquals(NetworkLayer.LayerType.UNKNOWN, networkLayer.getLayerType());
			assertEquals(1, networkLayer.getNeurons()[0].getWeights().length);
			assertEquals(0, networkLayer.getNeurons()[0].getWeights()[0]);
			assertEquals(0, networkLayer.getNeurons()[0].getThreshold());
			assertEquals(0, networkLayer.getNeurons()[0].getOutput());
		assertEquals(null, networkLayer.getPriorLayer());
		assertEquals(null, networkLayer.getNextLayer());
	}
	
	@Test
	void testSetLayerTypeGetLayerTypeReturnSpecifiedValue() {
		NetworkLayer  networkLayer = new NetworkLayer();
		networkLayer.setLayerType(LayerType.H);
		assertEquals(NetworkLayer.LayerType.H, networkLayer.getLayerType());
	}

	@Test
	void testSetNeuronsGetNeuronsRerturnSpecifiedValues() {
		NetworkLayer  networkLayer = new NetworkLayer();
		double[] weights = new double[] {1.1, 2,2};
		Neuron neuron = new Neuron(weights, 3.3, 4.4);
		Neuron[] neurons = new Neuron[] {neuron};
		// assure that the original neuron is in place on networkLayer create
		assertFalse(networkLayer.getNeurons()[0].equals(neuron));
		networkLayer.setNeurons(neurons);
		// assure that new neuron is in place
		assertTrue(networkLayer.getNeurons()[0].equals(neuron));
	}

	@Test
	void testGetPriorLayor() {
		NetworkLayer  networkLayer = new NetworkLayer();
		assertNull(networkLayer.getPriorLayer());
	}
	
	@Test
	void testSetPriorLayor() {
		NetworkLayer  networkLayer = new NetworkLayer();
		NetworkLayer  priorNetworkLayer = new NetworkLayer();
		networkLayer.setPriorLayer(priorNetworkLayer);
		assertEquals(priorNetworkLayer, networkLayer.getPriorLayer());
	}

	@Test
	void testGetNextLayor() {
		NetworkLayer  networkLayer = new NetworkLayer();
		assertNull(networkLayer.getNextLayer());
	}

	@Test
	void testSetNextLayor() {
		NetworkLayer  networkLayer = new NetworkLayer();
		NetworkLayer  nextNetworkLayer = new NetworkLayer();
		networkLayer.setNextLayer(nextNetworkLayer);
		assertEquals(nextNetworkLayer, networkLayer.getNextLayer());
	}

	@Test
	void testGetLayerOutputsReturnsCorrectValues() {
		double[] weights = new double[] {0.0};
		double[] outputs = new double[] {1.1, 2.2, 3.3};
		Neuron neuron1 = new Neuron(weights, 0.0, outputs[0]);
		Neuron neuron2 = new Neuron(weights, 0.0, outputs[1]);
		Neuron neuron3 = new Neuron(weights, 0.0, outputs[2]);
		Neuron[] neurons = new Neuron[] {neuron1, neuron2, neuron3};
		NetworkLayer  networkLayer = new NetworkLayer();
		networkLayer.setNeurons(neurons);
		double[] testOutputs =networkLayer.getLayerOutputs();
		assertEquals(outputs.length, testOutputs.length);
		for(int i = 0; i < outputs.length; i++) {
			assertEquals(outputs[i], testOutputs[i]);
		}
	}

	@Test
	void testRunLayer() {
		int numberInputs = 3;
        int numberNeurons= 2;
        NetworkLayer priorLayer = null;
        NetworkLayer nextLayer = null;
        double[] inputs = new double[] {1, 1, 1};
		double[] weights0 = new double[] {2, 3, 4};
		double[] weights1 = new double[] {5, 6, 7};
		double[] outputs = new double[numberNeurons];
		double[] expectedOutputs = new double[] {9,18};
		
		
		NetworkLayer  networkLayer = new NetworkLayer(LayerType.H,
				                                      numberInputs, 
				                                      numberNeurons, 
				                                      priorLayer, 
				                                      nextLayer);
		Neuron[] neurons = new Neuron[2];
		neurons = networkLayer.getNeurons();
		neurons[0].setWeights(weights0);
		neurons[1].setWeights(weights1);
		networkLayer.setNeurons(neurons);
		networkLayer.runLayer(inputs);
		outputs = networkLayer.getLayerOutputs();
		assertEquals(outputs[0], expectedOutputs[0]);
		assertEquals(outputs[1], expectedOutputs[1]);
	}
	
	@Test
	void testNetworkLayerIsEqualToSelf() {
		NetworkLayer  networkLayer = new NetworkLayer();
		assertTrue(networkLayer.equals(networkLayer));
	}

	@Test
	void testTwoNetworkLayersAreEquals() {
		double[] weights = new double[] {1.1, 2.2, 3.3};
		double[] outputs = new double[] {4.4, 5.5, 6.6};
		Neuron neuron1 = new Neuron(weights, 3.0, outputs[0]);
		Neuron neuron2 = new Neuron(weights, 3.0, outputs[1]);
		Neuron neuron3 = new Neuron(weights, 3.0, outputs[2]);
		Neuron[] neurons = new Neuron[] {neuron1, neuron2, neuron3};
		Neuron[] neurons2 = new Neuron[] {neuron1.copyNeuron(), neuron2.copyNeuron(), neuron2.copyNeuron()};
		NetworkLayer priorLayer = new NetworkLayer();
		NetworkLayer nextLayer = new NetworkLayer();
		
		NetworkLayer  networkLayer = new NetworkLayer();
		networkLayer.setLayerType(LayerType.H);
		networkLayer.setNeurons(neurons);
		networkLayer.setPriorLayer(priorLayer);
		networkLayer.setNextLayer(nextLayer);
		
		NetworkLayer  networkLayer2 = new NetworkLayer();
		assertFalse(networkLayer.equals(networkLayer2));
		networkLayer2.setLayerType(LayerType.H);
		networkLayer2.setNeurons(neurons2);
		networkLayer2.setPriorLayer(priorLayer);
		networkLayer2.setNextLayer(nextLayer);
		assertTrue(networkLayer.equals(networkLayer2));
	}
	
	@Test
	void testWrongLayerTypeNotAreEquals() {
		NetworkLayer  networkLayer = new NetworkLayer();		
		NetworkLayer  networkLayer2 = new NetworkLayer();
		networkLayer2.setLayerType(LayerType.H);
		assertFalse(networkLayer.equals(networkLayer2));
	}
	
	@Test
	void testWrongPriorLayersNotAreEquals() {
		NetworkLayer  networkLayer = new NetworkLayer();		
		NetworkLayer  networkLayer2 = new NetworkLayer();
		networkLayer2.setPriorLayer(networkLayer2);
		assertFalse(networkLayer.equals(networkLayer2));
	}
	
	@Test
	void testWrongNextLayersNotAreEquals() {
		NetworkLayer  networkLayer = new NetworkLayer();		
		NetworkLayer  networkLayer2 = new NetworkLayer();
		networkLayer2.setNextLayer(networkLayer2);
		assertFalse(networkLayer.equals(networkLayer2));
	}

	@Test
	void testToString() {
		// NetworkLayer  networkLayer = new NetworkLayer();
		//assertEquals("", networkLayer.toString());
	}

}
