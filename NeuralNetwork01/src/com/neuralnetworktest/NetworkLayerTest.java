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
		networkLayer.setLayerType(LayerType.H2);
		assertEquals(NetworkLayer.LayerType.H2, networkLayer.getLayerType());
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
	void testGetLayerOutputs() {
		
		double[] weights = new double[] {0.0};
		double[] outputs = new double[] {1.1, 2.2, 3.3};
		Neuron neuron1 = new Neuron(weights, 0.0, outputs[0]);
		Neuron neuron2 = new Neuron(weights, 0.0, outputs[1]);
		Neuron neuron3 = new Neuron(weights, 0.0, outputs[2]);
		Neuron[] neurons = new Neuron[] {neuron1, neuron2, neuron3};
		NetworkLayer  networkLayer = new NetworkLayer();
		networkLayer.setNeurons(neurons);
		assertEquals(outputs, networkLayer.getLayerOutputs());
		
		
	}

	@Test
	void testRunLayer() {
		fail("Not yet implemented");
	}

	@Test
	void testEquals() {
		fail("Not yet implemented");
	}

	@Test
	void testToString() {
		fail("Not yet implemented");
	}

}
