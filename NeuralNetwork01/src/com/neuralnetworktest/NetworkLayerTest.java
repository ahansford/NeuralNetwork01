package com.neuralnetworktest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
			//assertEquals(0, networkLayer.getNeurons()[0].getThreshold());  // threshold is random
			assertEquals(0, networkLayer.getNeurons()[0].getOutput());
	}
	
	@Test
	void testSetLayerTypeGetLayerTypeReturnSpecifiedValue() {
		NetworkLayer  networkLayer = new NetworkLayer();
		networkLayer.setLayerType(LayerType.H);
		assertEquals(NetworkLayer.LayerType.H, networkLayer.getLayerType());
	}

	@Test
	void testSetNeuronsGetNeuronsReturnSpecifiedValues() {
		NetworkLayer  networkLayer = new NetworkLayer();
		double[] weights = new double[] {1.1, 2,2};
		Neuron neuron = new Neuron(weights, 3.3, 4.4);
		Neuron[] neurons = new Neuron[] {neuron};
		networkLayer.setNeurons(neurons);
		assertTrue(networkLayer.getNeurons()[0].equals(neuron));
	}
	
	@Test
	void testSetAndGetNeuronAtIndexPassesCorrectValues() {
		NetworkLayer  networkLayer = new NetworkLayer(LayerType.H, 2,3);
		double[] weights = new double[] {1, 2};
		Neuron neuronA = new Neuron(weights, 3, 4);
		weights = new double[] {1, 2};
		Neuron neuronB = new Neuron(weights, 5, 6);
		networkLayer.setNeuronAtIndex(neuronA, 0);
		assertTrue(neuronA.equals(networkLayer.getNeuronAtIndex(0)));
		networkLayer.setNeuronAtIndex(neuronB, 0);
		assertFalse(neuronA.equals(networkLayer.getNeuronAtIndex(0)));
		assertTrue(neuronB.equals(networkLayer.getNeuronAtIndex(0)));
	}
	
	@Test
	void testGetNeuronCountInLayer() {
		double[] weights = new double[] {0.0};
		double[] outputs = new double[] {1.1, 2.2, 3.3};
		Neuron neuron1 = new Neuron(weights, 0.0, outputs[0]);
		Neuron neuron2 = new Neuron(weights, 0.0, outputs[1]);
		Neuron neuron3 = new Neuron(weights, 0.0, outputs[2]);
		Neuron neuron4 = new Neuron(weights, 0.0, outputs[2]);
		Neuron[] neurons = new Neuron[] {neuron1, neuron2, neuron3, neuron4};
		NetworkLayer  networkLayer = new NetworkLayer();
		networkLayer.setNeurons(neurons);
		assertEquals(neurons.length, networkLayer.getNeuronCountInLayer());
		
		NetworkLayer  networkLayer2 = new NetworkLayer();
		assertEquals(1, networkLayer2.getNeuronCountInLayer());
	}
	
	@Test
	void testGetInputCountToLayer() {
		double[] weights = new double[] {0.0};
		double[] outputs = new double[] {1.1, 2.2, 3.3, 4.4, 5.5};
		Neuron neuron1 = new Neuron(weights, 0.0, outputs[0]);
		Neuron neuron2 = new Neuron(weights, 0.0, outputs[1]);
		Neuron neuron3 = new Neuron(weights, 0.0, outputs[2]);
		Neuron neuron4 = new Neuron(weights, 0.0, outputs[3]);
		Neuron neuron5 = new Neuron(weights, 0.0, outputs[4]);
		Neuron[] neurons = new Neuron[] {neuron1, neuron2, neuron3, neuron4, neuron5};
		NetworkLayer  networkLayer = new NetworkLayer();
		networkLayer.setNeurons(neurons);
		
		networkLayer.setLayerType(LayerType.I);
		assertEquals(5, networkLayer.getInputCountIntoLayer());

		networkLayer.setLayerType(LayerType.H);
		assertEquals(1, networkLayer.getInputCountIntoLayer());
		
		networkLayer.setLayerType(LayerType.O);
		assertEquals(1, networkLayer.getInputCountIntoLayer());
	}
	
	@Test
	void testGetWeightedLayerOutputsReturnsCorrectValues() {
		double[] weights = new double[] {0.0};
		double[] outputs = new double[] {1.1, 2.2, 3.3};
		Neuron neuron1 = new Neuron(weights, 0.0, outputs[0]);
		Neuron neuron2 = new Neuron(weights, 0.0, outputs[1]);
		Neuron neuron3 = new Neuron(weights, 0.0, outputs[2]);
		Neuron[] neurons = new Neuron[] {neuron1, neuron2, neuron3};
		NetworkLayer  networkLayer = new NetworkLayer();
		networkLayer.setNeurons(neurons);
		double[] testOutputs =networkLayer.getWeightedLayerOutputs();
		assertEquals(outputs.length, testOutputs.length);
		for(int i = 0; i < outputs.length; i++) {
			assertEquals(outputs[i], testOutputs[i]);
		}
	}
	
	@Test
	void testGetActivatedLayerOutputsReturnsCorrectValues() {
		double[] weights = new double[] {0.0};
		double[] outputs = new double[] {1.1, 2.2, 3.3};
		Neuron neuron1 = new Neuron(weights, 0.0, outputs[0]);
		Neuron neuron2 = new Neuron(weights, 0.0, outputs[1]);
		Neuron neuron3 = new Neuron(weights, 0.0, outputs[2]);
		Neuron[] neurons = new Neuron[] {neuron1, neuron2, neuron3};
		NetworkLayer  networkLayer = new NetworkLayer();
		networkLayer.setNeurons(neurons);
		double[] testOutputs =networkLayer.getActivatedLayerOutputs();
		assertTrue((testOutputs[0] - 0.750)< 0.001);
		assertTrue((testOutputs[1] - 0.900)< 0.001);
		assertTrue((testOutputs[2] - 0.964)< 0.001);
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
		networkLayer.setLayerType(LayerType.UNKNOWN);
		networkLayer2.setLayerType(LayerType.H);
		assertFalse(networkLayer.equals(networkLayer2));
	}
	
	@Test
	void testSimpleRandonWeightsLayersNotAreEquals() {
		NetworkLayer  networkLayer = new NetworkLayer();		
		NetworkLayer  networkLayer2 = new NetworkLayer();
		assertFalse(networkLayer.equals(networkLayer2));
	}
	
	@Test
	void testEqualTestOfNullLayerReturnsFalse() {
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
		NetworkLayer nullLayer =null;
		assertFalse(networkLayer.equals(nullLayer));
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
	void testWrongNeuronCountReturnsNotEqual() {
		double[] weights = new double[] {1.1, 2.2, 3.3};
		double[] outputs = new double[] {4.4, 5.5, 6.6};
		Neuron neuron1 = new Neuron(weights, 3.0, outputs[0]);
		Neuron neuron2 = new Neuron(weights, 3.0, outputs[1]);
		Neuron neuron3 = new Neuron(weights, 3.0, outputs[2]);
		Neuron[] neurons = new Neuron[] {neuron1, neuron2, neuron3};
		NetworkLayer  networkLayer = new NetworkLayer();
		networkLayer.setLayerType(LayerType.I);
		networkLayer.setNeurons(neurons);
		
		neurons = new Neuron[] {neuron1, neuron2};
		NetworkLayer  networkLayer2 = new NetworkLayer();
		networkLayer2.setLayerType(LayerType.I);
		networkLayer2.setNeurons(neurons);
		
		assertTrue( !networkLayer.equals(networkLayer2));  
	}
	
	@Test
	void testRunLayerReturnsExpectedActivatedValues() {
		int numberInputs = 3;
        int numberNeurons= 2;
        double[] inputs = new double[] {1, 1, 1};
		double[] weights0 = new double[] {2, 3, 4};
		double[] weights1 = new double[] {5, 6, 7};
		double[] outputs = new double[numberNeurons];
		//double[] expectedWeightedOutputs = new double[] {9,18};
		double[] expectedActivatedOutputs = new double[] {0.99981,0.99999};
		NetworkLayer  networkLayer = new NetworkLayer(LayerType.H,
				                                      numberInputs, 
				                                      numberNeurons);
		Neuron[] neurons = new Neuron[2];
		neurons = networkLayer.getNeurons();
		neurons[0].setWeights(weights0);
		neurons[1].setWeights(weights1);
		networkLayer.setNeurons(neurons);
		networkLayer.runLayer(inputs);
		outputs = networkLayer.getActivatedLayerOutputs();
		//assertEquals(expectedOutputs[0], outputs[0]);
		//assertEquals(expectedActivatedOutputs[1],  outputs[1]);
		assertTrue((expectedActivatedOutputs[0] - outputs[0]) < 0.1);
		assertTrue((expectedActivatedOutputs[1] - outputs[1]) < 0.1);
	}
	
	@Test
	void testRunningLayerDoesNotChangeWeightsOrThreshold() {
		int numberInputs = 3;
        int numberNeurons= 3;
        double[] inputs = new double[] {1, 1, 1};
		double[] weights = new double[] {2, 3, 4};
		NetworkLayer  networkLayer = new NetworkLayer(LayerType.I,
				                                      numberInputs, 
				                                      numberNeurons);
		Neuron[] neurons = networkLayer.getNeurons();
		neurons[0].setWeights(weights);
		neurons[1].setWeights(weights);
		neurons[2].setWeights(weights);
		networkLayer.setNeurons(neurons);
		NetworkLayer originalLayer = networkLayer.copyNetworkLayer();
		networkLayer.runLayer(inputs);
		assertTrue(networkLayer.equals(originalLayer));
	}
	
	@Test
	void testRunningWrongInputCountDoesNotChangeOutput() {
		int numberInputs = 2;
        int numberNeurons= 3;
        double[] inputs = new double[] {1, 1, 1};
		double[] weights = new double[] {2, 3, 4};
		NetworkLayer  networkLayer = new NetworkLayer(LayerType.I,
				                                      numberInputs, 
				                                      numberNeurons);
		Neuron[] neurons = networkLayer.getNeurons();
		neurons[0].setWeights(weights);
		neurons[1].setWeights(weights);
		neurons[2].setWeights(weights);
		networkLayer.setNeurons(neurons);

		networkLayer.runLayer(inputs);
		assertEquals(0.0, networkLayer.getWeightedLayerOutputs()[0]);
	}
	
	@Test
	void testRunLayerWithTooManyInputsDoesNOtChangeOutput() {
		int numberInputs = 3;
        int numberNeurons= 3;
        double[] inputs = new double[] {1, 1, 1, 1};
		double[] weights = new double[] {2, 3, 4};
		NetworkLayer  networkLayer = new NetworkLayer(LayerType.I,
				                                      numberInputs, 
				                                      numberNeurons);
		Neuron[] neurons = networkLayer.getNeurons();
		neurons[0].setWeights(weights);
		neurons[1].setWeights(weights);
		neurons[2].setWeights(weights);
		networkLayer.setNeurons(neurons);
		
		networkLayer.runLayer(inputs);
		assertEquals(0.0, networkLayer.getWeightedLayerOutputs()[0]);
	}
	
	@Test
	void testRunHiddenLayerWithTooManyInputsDoesNOtChangeOutput() {
		int numberInputs = 3;
        int numberNeurons= 3;
        double[] inputs = new double[] {1, 1, 1, 1};
		double[] weights = new double[] {2, 3, 4};
		NetworkLayer  networkLayer = new NetworkLayer(LayerType.H,
				                                      numberInputs, 
				                                      numberNeurons);
		Neuron[] neurons = networkLayer.getNeurons();
		neurons[0].setWeights(weights);
		neurons[1].setWeights(weights);
		neurons[2].setWeights(weights);
		networkLayer.setNeurons(neurons);
		
		networkLayer.runLayer(inputs);
		assertEquals(0.0, networkLayer.getWeightedLayerOutputs()[0]);
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
		NetworkLayer adjustedLayer = networkLayer.copyNetworkLayer();
		assertTrue(networkLayer.equals(adjustedLayer)); 
		
		adjustedLayer = networkLayer.adjustAllLayerWeightsThresholdsRandomly();
		assertFalse(networkLayer.equals(adjustedLayer)); 

		// Demonstrates partial matching assurance; dependent on neuronAdjust testing
		assertEquals(networkLayer.getLayerType(),          adjustedLayer.getLayerType());
		assertEquals(networkLayer.getNeuronCountInLayer(), adjustedLayer.getNeuronCountInLayer());
	}
	
	@Disabled
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
		networkLayer2.setLayerType(LayerType.O);
		networkLayer2.setNeurons(neurons2);
		System.out.println("NetworkLayer toString: " + networkLayer.toString());		
	}

}
