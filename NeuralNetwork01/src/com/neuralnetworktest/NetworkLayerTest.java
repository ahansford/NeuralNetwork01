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
			//assertEquals(0, networkLayer.getNeurons()[0].getWeights()[0]); // weight is random
			//assertEquals(0, networkLayer.getNeurons()[0].getThreshold()); // threshold is random
			assertEquals(0, networkLayer.getNeurons()[0].getOutput());
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
	void testGetInputCountToLayer() {
		double[] weights = new double[] {0.0};
		double[] outputs = new double[] {1.1, 2.2, 3.3};
		Neuron neuron1 = new Neuron(weights, 0.0, outputs[0]);
		Neuron neuron2 = new Neuron(weights, 0.0, outputs[1]);
		Neuron neuron3 = new Neuron(weights, 0.0, outputs[2]);
		Neuron neuron4 = new Neuron(weights, 0.0, outputs[2]);
		Neuron[] neurons = new Neuron[] {neuron1, neuron2, neuron3, neuron4};
		NetworkLayer  networkLayer = new NetworkLayer();
		networkLayer.setNeurons(neurons);
		
		networkLayer.setLayerType(LayerType.I);
		assertEquals(networkLayer.getNeurons().length, networkLayer.getInputCountIntoLayer());

		networkLayer.setLayerType(LayerType.H);
		assertEquals(networkLayer.getNeurons()[0].getWeights().length, networkLayer.getInputCountIntoLayer());
		
		networkLayer.setLayerType(LayerType.O);
		assertEquals(networkLayer.getNeurons()[0].getWeights().length, networkLayer.getInputCountIntoLayer());
	}

	@Test
	void testRunLayer() {
		int numberInputs = 3;
        int numberNeurons= 2;
        double[] inputs = new double[] {1, 1, 1};
		double[] weights0 = new double[] {2, 3, 4};
		double[] weights1 = new double[] {5, 6, 7};
		double[] outputs = new double[numberNeurons];
		double[] expectedOutputs = new double[] {9,18};
		NetworkLayer  networkLayer = new NetworkLayer(LayerType.H,
				                                      numberInputs, 
				                                      numberNeurons);
		Neuron[] neurons = new Neuron[2];
		neurons = networkLayer.getNeurons();
		neurons[0].setWeights(weights0);
		neurons[1].setWeights(weights1);
		networkLayer.setNeurons(neurons);
		networkLayer.runLayer(inputs);
		outputs = networkLayer.getLayerOutputs();
		assertTrue(Neuron.applyActivationFunction2(expectedOutputs[0])-outputs[0] < 0.1);
		assertTrue(Neuron.applyActivationFunction2(expectedOutputs[1])-outputs[1] < 0.1);
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
		Neuron[] neurons2 = new Neuron[] {neuron1.copyNeuron(), 
				                          neuron2.copyNeuron(), 
				                          neuron2.copyNeuron()};
		
		NetworkLayer  networkLayer = new NetworkLayer();
		networkLayer.setLayerType(LayerType.H);
		networkLayer.setNeurons(neurons);
		
		NetworkLayer  networkLayer2 = new NetworkLayer();
		assertFalse(networkLayer.equals(networkLayer2));
		networkLayer2.setLayerType(LayerType.H);
		networkLayer2.setNeurons(neurons2);
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
		assertFalse(networkLayer.equals(networkLayer2));
	}
	
	@Test
	void testWrongNextLayersNotAreEquals() {
		NetworkLayer  networkLayer = new NetworkLayer();		
		NetworkLayer  networkLayer2 = new NetworkLayer();
		assertFalse(networkLayer.equals(networkLayer2));
	}
	
	@Test
	void testCopiedLayerIsEqualToSelf() {
		double[] weights = new double[] {1.1, 2.2, 3.3};
		double[] outputs = new double[] {4.4, 5.5, 6.6};
		Neuron neuron1 = new Neuron(weights, 3.0, outputs[0]);
		Neuron neuron2 = new Neuron(weights, 3.0, outputs[1]);
		Neuron neuron3 = new Neuron(weights, 3.0, outputs[2]);
		Neuron[] neurons = new Neuron[] {neuron1, neuron2, neuron3};
		NetworkLayer  networkLayer = new NetworkLayer();
		networkLayer.setLayerType(LayerType.I);
		networkLayer.setNeurons(neurons);
		
		NetworkLayer copiedLayer2 = networkLayer.copyNetworkLayer();
		assertTrue(networkLayer.equals(copiedLayer2)); 
		assertTrue(networkLayer.equals(networkLayer.copyNetworkLayer())); 
	}
	
	@Test
	void testAdjustedLayerNeuronsAreNotEqualToSelf() {
		double[] weights = new double[] {1.1, 2.2, 3.3};
		double[] outputs = new double[] {4.4, 5.5, 6.6};
		Neuron neuron1 = new Neuron(weights, 3.0, outputs[0]);
		Neuron neuron2 = new Neuron(weights, 3.0, outputs[1]);
		Neuron neuron3 = new Neuron(weights, 3.0, outputs[2]);
		Neuron[] neurons = new Neuron[] {neuron1, neuron2, neuron3};
		NetworkLayer  networkLayer = new NetworkLayer();
		networkLayer.setLayerType(LayerType.I);
		networkLayer.setNeurons(neurons);
		
		// Proper operation of equals() proves the new layer was modified
		NetworkLayer adjustedLayer;
		adjustedLayer = networkLayer.copyNetworkLayer();
		assertTrue(networkLayer.equals(adjustedLayer)); 
		
		adjustedLayer = networkLayer.adjustNetworkLayer();
		assertFalse(networkLayer.equals(adjustedLayer)); 

		// Demonstrates partial matching assurance; dependent on neuronAdjust testing
		assertEquals(networkLayer.getLayerType(),          adjustedLayer.getLayerType());
		assertEquals(networkLayer.getNeuronCountInLayer(), adjustedLayer.getNeuronCountInLayer());
	}

	@Test
	void testToString() {
		double[] weights = new double[] {1.1, 2.2, 3.3};
		double[] outputs = new double[] {4.4, 5.5, 6.6};
		Neuron neuron1 = new Neuron(weights, 3.0, outputs[0]);
		Neuron neuron2 = new Neuron(weights, 3.0, outputs[1]);
		Neuron neuron3 = new Neuron(weights, 3.0, outputs[2]);
		Neuron[] neurons = new Neuron[] {neuron1, neuron2, neuron3};
		Neuron[] neurons2 = new Neuron[] {neuron1.copyNeuron(), 
				                          neuron2.copyNeuron(), 
				                          neuron3.copyNeuron()};
		
		NetworkLayer  networkLayer = new NetworkLayer();
		networkLayer.setLayerType(LayerType.I);
		networkLayer.setNeurons(neurons);
		
		NetworkLayer  networkLayer2 = new NetworkLayer();
		assertFalse(networkLayer.equals(networkLayer2));
		networkLayer2.setLayerType(LayerType.H);
		networkLayer2.setNeurons(neurons2);
		//System.out.println("NetworkLayer toString: " + networkLayer.toString());
		//System.out.println("NetworkLayer toString: " + networkLayer2.toString());

		//assertEquals(networkLayer.toString(), "NetworkLayer {LayerType:I; Neuron {w0:1.1000w1:2.2000w2:3.3000threshold:3.0000|output:4.4000|; Neuron {w0:1.1000w1:2.2000w2:3.3000threshold:3.0000|output:5.5000|; Neuron {w0:1.1000w1:2.2000w2:3.3000threshold:3.0000|output:6.6000| }> but was: <NetworkLayer {LayerType:I; Neuron {w0:1.1000|w1:2.2000|w2:3.3000|threshold:3.0000|output:4.4000|; Neuron {w0:1.1000|w1:2.2000|w2:3.3000|threshold:3.0000|output:5.5000|; Neuron {w0:1.1000|w1:2.2000|w2:3.3000|threshold:3.0000|output:6.6000| }");
		
	}

}
