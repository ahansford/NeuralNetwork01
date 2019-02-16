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
//import com.neuralnetwork.Neuron;
import com.neuralnetwork.Neuron2;


class NetworkLayerTest {
	
	boolean printErrorMessages = true;
	boolean printMessages =  true;
	boolean printAllMessages =  true;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("*** NetworkLayerTest: START ***");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("*** NetworkLayerTest: END   ***");
	}

	@BeforeEach
	void setUp() throws Exception {
		//System.out.flush();
	}

	@AfterEach
	void tearDown() throws Exception {
		System.out.flush();
	}

	// *** Constructor functions work as expected *** //
	
	@Test
	void testNetworkLayerZeroOnCreate() {
		NetworkLayer  networkLayer = new NetworkLayer();
		assertEquals(NetworkLayer.LayerType.UNKNOWN, networkLayer.getLayerType());
			assertEquals(1, networkLayer.getNeurons()[0].getWeights().length);
			assertEquals(1, networkLayer.getNeurons()[0].getWeights()[0]); 
			assertEquals(0, networkLayer.getNeurons()[0].getThreshold());  
			assertEquals(0, networkLayer.getNeurons()[0].getOutput());
	}
	
	// *** Network accessor functions work as expected *** //
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
		Neuron2 neuron = new Neuron2(weights, 3.3, 4.4);
		Neuron2[] neurons = new Neuron2[] {neuron};
		networkLayer.setNeurons(neurons);
		assertTrue(networkLayer.getNeurons()[0].equals(neuron));
	}
	
	@Test
	void testAssigningMultiWeightNeuronsOnInputLayerTriggeresError() {
		if (printErrorMessages) System.out.println("testAssigningMultiWeightNeuronsOnInputLayerTriggeresError(): START");
		NetworkLayer  networkLayer = new NetworkLayer();
		networkLayer.setLayerType(LayerType.I);
		double[] weights = new double[] {1.1, 2,2};
		Neuron2 neuron = new Neuron2(weights, 3.3, 4.4);
		Neuron2[] neurons = new Neuron2[] {neuron};
		if (printErrorMessages) System.out.print("testAssigningMultiWeightNeuronsOnInputLayerTriggeresError(): triggering error -> ");
		networkLayer.setNeurons(neurons);
		assertTrue(networkLayer.getNeurons()[0].equals(neuron)); 
		if (printErrorMessages) System.out.println("testAssigningMultiWeightNeuronsOnInputLayerTriggeresError(): END \n");
	}
	
	@Test
	void testAssigningInputLayerTypeToMultiWeightNeuronsLayerTriggeresError() {
		if (printErrorMessages) System.out.println("testAssigningInputLayerTypeToMultiWeightNeuronsLayerTriggeresError: START");
		if (printErrorMessages) System.out.println("mmmm testAssigningInputLayerTypeToMultiWeightNeuronsLayerTriggeresError(): no error expected -> ");
		NetworkLayer  networkLayer = new NetworkLayer();
		double[] weights = new double[] {1.1, 2.2, 3.3};
		Neuron2 neuron = new Neuron2(weights, 0, 0);
		Neuron2[] neurons = new Neuron2[] {neuron};
		networkLayer.setNeurons(neurons);
		assertTrue(LayerType.I != networkLayer.getLayerType()); 
		if (printErrorMessages) System.out.print("mmmm testAssigningInputLayerTypeToMultiWeightNeuronsLayerTriggeresError(): triggering error -> ");
		networkLayer.setLayerType(LayerType.I);
		assertTrue(networkLayer.getNeurons()[0].equals(neuron)); 
		if (printErrorMessages) System.out.println("testAssigningInputLayerTypeToMultiWeightNeuronsLayerTriggeresError: END \n");
	}
	
	@Test
	void testCannotSetNullNeurons() {
		NetworkLayer  networkLayer = new NetworkLayer();
		double[] weights = new double[] {1.1, 2.2, 3.3};
		Neuron2 neuron = new Neuron2(weights, 0, 0);
		Neuron2[] neurons = new Neuron2[] {neuron};
		networkLayer.setNeurons(neurons);
		assertEquals(neurons, networkLayer.getNeurons());
		networkLayer.setNeurons(null);
		assertEquals(neurons, networkLayer.getNeurons());
	}
	
	
	
	// *** Neuron accessor functions at the NetworkLayer level work as expected *** //
	
	@Test
	void testSetAndGetNeuronAtIndexPassesCorrectValues() {
		NetworkLayer  networkLayer = new NetworkLayer(LayerType.H, 2, 3);
		double[] weights = new double[] {1, 2};
		Neuron2 neuronA = new Neuron2(weights, 3, 4);
		weights = new double[] {1, 2};
		Neuron2 neuronB = new Neuron2(weights, 5, 6);
		networkLayer.setNeuronAtIndex(neuronA, 0);
		assertTrue(neuronA.equals(networkLayer.getNeuronAtIndex(0)));
		networkLayer.setNeuronAtIndex(neuronB, 0);
		assertFalse(neuronA.equals(networkLayer.getNeuronAtIndex(0)));
		assertTrue(neuronB.equals(networkLayer.getNeuronAtIndex(0)));
	}
	
	@Test
	void testNeuronIndexRangeFailureForOutOfRangeReturnsFalse() {
		if (printErrorMessages) System.out.println("testNeuronIndexRangeFailureForOutOfRangeReturnsFalse: START");
		NetworkLayer  networkLayer = new NetworkLayer(LayerType.H, 2, 3);
		if (printErrorMessages) System.out.print("testNeuronIndexRangeFailureForOutOfRangeReturnsFalse(): triggering error -> ");
		assertTrue( networkLayer.neuronIndexRangeFailureFor(-1) );
		assertFalse( networkLayer.neuronIndexRangeFailureFor(0) );
		assertFalse( networkLayer.neuronIndexRangeFailureFor(1) );
		assertFalse( networkLayer.neuronIndexRangeFailureFor(2) );
		if (printErrorMessages) System.out.print("testNeuronIndexRangeFailureForOutOfRangeReturnsFalse(): triggering error -> ");
		assertTrue( networkLayer.neuronIndexRangeFailureFor(3) );
		if (printErrorMessages) System.out.println("testNeuronIndexRangeFailureForOutOfRangeReturnsFalse: END \n");
	}
	
	@Test
	void testCannotSetNeuronAtoutOfRangeIndex() {
		if (printErrorMessages) System.out.println("testCannotSetNeuronAtoutOfRangeIndex: START");
		NetworkLayer  networkLayer = new NetworkLayer(LayerType.H, 2, 2);
		double[] weights = new double[] {1, 2};
		Neuron2 neuronA = new Neuron2(weights, 3, 4);
		weights = new double[] {1, 2};
		Neuron2 neuronB = new Neuron2(weights, 5, 6);
		networkLayer.setNeuronAtIndex(neuronA, 0);
		networkLayer.setNeuronAtIndex(neuronB, 1);
		if (printErrorMessages) System.out.print("testCannotSetNeuronAtoutOfRangeIndex(): triggering error -> ");
		networkLayer.setNeuronAtIndex(neuronA, -1) ;
		assertEquals(3-2, 6-5);  // code delay
		assertEquals(3-2, 6-5);  // code delay
		if (printErrorMessages) System.out.print("testCannotSetNeuronAtoutOfRangeIndex(): triggering error -> ");
		networkLayer.setNeuronAtIndex(neuronB, 5) ;
		assertEquals(3-2, 6-5);  // code delay
		assertEquals(3-2, 6-5);  // code delay
		if (printErrorMessages) System.out.println("testCannotSetNeuronAtoutOfRangeIndex: END \n");
	}
	
	@Test
	void testCannotGetNeuronAtoutOfRangeIndex() {
		if (printErrorMessages) System.out.println("testSetAndGetNeuronAtIndexPassesCorrectValues: START");
		NetworkLayer  networkLayer = new NetworkLayer(LayerType.H, 2, 2);
		double[] weights = new double[] {1, 2};
		Neuron2 neuronA = new Neuron2(weights, 3, 4);
		weights = new double[] {1, 2};
		Neuron2 neuronB = new Neuron2(weights, 5, 6);
		networkLayer.setNeuronAtIndex(neuronA, 0);
		networkLayer.setNeuronAtIndex(neuronB, 1);
		if (printErrorMessages) System.out.print("testSetAndGetNeuronAtIndexPassesCorrectValues(): triggering error -> ");
		assertEquals(null, networkLayer.getNeuronAtIndex(5));
		if (printErrorMessages) System.out.print("testSetAndGetNeuronAtIndexPassesCorrectValues(): triggering error -> ");
		assertEquals(null, networkLayer.getNeuronAtIndex(-1));
		if (printErrorMessages) System.out.println("testSetAndGetNeuronAtIndexPassesCorrectValues: END \n");
	}
	
	@Test
	void testCannotSetNullNeuron() {
		if (printErrorMessages) System.out.println("testSetAndGetNeuronAtIndexPassesCorrectValues: START");
		NetworkLayer  networkLayer = new NetworkLayer(LayerType.H, 2, 2);
		double[] weights = new double[] {1, 2};
		Neuron2 neuronA = new Neuron2(weights, 3, 4);
		networkLayer.setNeuronAtIndex(neuronA, 0);
		assertEquals(neuronA, networkLayer.getNeuronAtIndex(0));
		networkLayer.setNeuronAtIndex(null, 0);
		assertEquals(neuronA, networkLayer.getNeuronAtIndex(0));
	}
	
	@Test
	void testGetNeuronCountInLayer() {
		double[] weights = new double[] {0.0};
		double[] outputs = new double[] {1.1, 2.2, 3.3};
		Neuron2 neuron1 = new Neuron2(weights, 0.0, outputs[0]);
		Neuron2 neuron2 = new Neuron2(weights, 0.0, outputs[1]);
		Neuron2 neuron3 = new Neuron2(weights, 0.0, outputs[2]);
		Neuron2 neuron4 = new Neuron2(weights, 0.0, outputs[2]);
		Neuron2[] neurons = new Neuron2[] {neuron1, neuron2, neuron3, neuron4};
		NetworkLayer  networkLayer = new NetworkLayer();
		networkLayer.setNeurons(neurons);
		assertEquals(neurons.length, networkLayer.getNeuronCountInLayer());
		
		NetworkLayer  networkLayer2 = new NetworkLayer();
		assertEquals(1, networkLayer2.getNeuronCountInLayer());
	}
	
	@Test
	void testGetInputCountToLayer() {
		if (printAllMessages) System.out.println("testGetInputCountToLayer(): START ");
		
		double[] weights = new double[] {0.0};
		double[] outputs = new double[] {1.1, 2.2, 3.3, 4.4, 5.5};
		Neuron2 neuron1 = new Neuron2(weights, 0.0, outputs[0]);
		Neuron2 neuron2 = new Neuron2(weights, 0.0, outputs[1]);
		Neuron2 neuron3 = new Neuron2(weights, 0.0, outputs[2]);
		Neuron2 neuron4 = new Neuron2(weights, 0.0, outputs[3]);
		Neuron2 neuron5 = new Neuron2(weights, 0.0, outputs[4]);
		Neuron2[] neurons = new Neuron2[] {neuron1, neuron2, neuron3, neuron4, neuron5};
		NetworkLayer  networkLayer = new NetworkLayer();
		networkLayer.setNeurons(neurons);
		
		networkLayer.setLayerType(LayerType.I);
		assertEquals(5, networkLayer.getInputCountIntoLayer());

		networkLayer.setLayerType(LayerType.H);
		assertEquals(1, networkLayer.getInputCountIntoLayer());
		
		networkLayer.setLayerType(LayerType.O);
		assertEquals(1, networkLayer.getInputCountIntoLayer());
		if (printAllMessages) System.out.println("testGetInputCountToLayer(): END \n");
	}

	
	@Test
	void testGetWeightedLayerOutputsReturnsCorrectValues() {
		double[] weights = new double[] {0.0};
		double[] outputs = new double[] {1.1, 2.2, 3.3};
		Neuron2 neuron1 = new Neuron2(weights, 0.0, outputs[0]);
		Neuron2 neuron2 = new Neuron2(weights, 0.0, outputs[1]);
		Neuron2 neuron3 = new Neuron2(weights, 0.0, outputs[2]);
		Neuron2[] neurons = new Neuron2[] {neuron1, neuron2, neuron3};
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
		Neuron2 neuron1 = new Neuron2(weights, 0.0, outputs[0]);
		Neuron2 neuron2 = new Neuron2(weights, 0.0, outputs[1]);
		Neuron2 neuron3 = new Neuron2(weights, 0.0, outputs[2]);
		Neuron2[] neurons = new Neuron2[] {neuron1, neuron2, neuron3};
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
		Neuron2 neuron1 = new Neuron2(weights, 3.0, outputs[0]);
		Neuron2 neuron2 = new Neuron2(weights, 3.0, outputs[1]);
		Neuron2 neuron3 = new Neuron2(weights, 3.0, outputs[2]);
		Neuron2[] neurons = new Neuron2[] {neuron1, neuron2, neuron3};
		Neuron2[] neurons2 = new Neuron2[] {neuron1.copyNeuron(), 
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
	void testSimpleRandonWeightsLayersAreEquals() {
		NetworkLayer  networkLayer = new NetworkLayer();		
		NetworkLayer  networkLayer2 = new NetworkLayer();
		assertTrue(networkLayer.equals(networkLayer2));
	}
	
	@Test
	void testEqualTestOfNullLayerReturnsFalse() {
		if (printAllMessages) System.out.println("testEqualTestOfNullLayerReturnsFalse(): START ");
		double[] weights = new double[] {1.1, 2.2, 3.3};
		double[] outputs = new double[] {4.4, 5.5, 6.6};
		Neuron2 neuron1 = new Neuron2(weights, 3.0, outputs[0]);
		Neuron2 neuron2 = new Neuron2(weights, 3.0, outputs[1]);
		Neuron2 neuron3 = new Neuron2(weights, 3.0, outputs[2]);
		Neuron2[] neurons = new Neuron2[] {neuron1, neuron2, neuron3};
		NetworkLayer  networkLayer = new NetworkLayer();
		networkLayer.setLayerType(LayerType.H);
		networkLayer.setNeurons(neurons);
		
		// Proper operation of equals() proves the new layer was modified
		NetworkLayer nullLayer =null;
		assertFalse(networkLayer.equals(nullLayer));
		if (printAllMessages) System.out.println("testEqualTestOfNullLayerReturnsFalser(): END \n ");
	}
	
	@Test
	void testCopiedLayerIsEqualToSelf() {
		if (printAllMessages) System.out.println("testCopiedLayerIsEqualToSelf(): START ");
		double[] weights = new double[] {1.1, 2.2, 3.3};
		double[] outputs = new double[] {4.4, 5.5, 6.6};
		Neuron2 neuron1 = new Neuron2(weights, 3.0, outputs[0]);
		Neuron2 neuron2 = new Neuron2(weights, 3.0, outputs[1]);
		Neuron2 neuron3 = new Neuron2(weights, 3.0, outputs[2]);
		Neuron2[] neurons = new Neuron2[] {neuron1, neuron2, neuron3};
		NetworkLayer  networkLayer = new NetworkLayer();
		networkLayer.setLayerType(LayerType.O);
		networkLayer.setNeurons(neurons);
		
		NetworkLayer copiedLayer2 = networkLayer.copyNetworkLayer();
		assertTrue(networkLayer.equals(copiedLayer2)); 
		assertTrue(networkLayer.equals(networkLayer.copyNetworkLayer())); 
		if (printAllMessages) System.out.println("testCopiedLayerIsEqualToSelf(): END \n ");
	}
	
	@Test
	void testWrongNeuronCountReturnsNotEqual() {
		double[] weights = new double[] {1.1, 2.2, 3.3};
		double[] outputs = new double[] {4.4, 5.5, 6.6};
		Neuron2 neuron1 = new Neuron2(weights, 3.0, outputs[0]);
		Neuron2 neuron2 = new Neuron2(weights, 3.0, outputs[1]);
		Neuron2 neuron3 = new Neuron2(weights, 3.0, outputs[2]);
		Neuron2[] neurons = new Neuron2[] {neuron1, neuron2, neuron3};
		NetworkLayer  networkLayer = new NetworkLayer();
		networkLayer.setLayerType(LayerType.O);
		networkLayer.setNeurons(neurons);
		
		neurons = new Neuron2[] {neuron1, neuron2};
		NetworkLayer  networkLayer2 = new NetworkLayer();
		networkLayer2.setLayerType(LayerType.O);
		networkLayer2.setNeurons(neurons);
		
		assertTrue( !networkLayer.equals(networkLayer2));  
	}
	
	@Test
	void testRunLayerReturnsExpectedActivatedValues() {
		if (printAllMessages) System.out.println("testRunLayerReturnsExpectedActivatedValues(): START ");
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
		Neuron2[] neurons = new Neuron2[2];
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
		if (printAllMessages) System.out.println("testRunLayerReturnsExpectedActivatedValues(): END \n ");
	}
	
	@Test
	void testRunningInputLayerReturnsExpectedValues() {
		if (printAllMessages) System.out.println("testRunningInputLayerReturnsExpectedValues(): START ");
		LayerType layerType = LayerType.I; 
		int neuronCount = 1;
		int inputCount = 1;
		NetworkLayer  networkLayer = getNewNetworkLayer(layerType, neuronCount, inputCount);
		double[] inputs = new double[] {7.5};
		networkLayer.runLayer(inputs);
		double[] outputs = networkLayer.getWeightedLayerOutputs();
		assertEquals(7.5, outputs[0]);
		if (printAllMessages) System.out.println("testRunningInputLayerReturnsExpectedValues(): END \n");
	}
	
	@Test
	void testRunningLayerDoesNotChangeWeightsOrThreshold() {
		if (printAllMessages) System.out.println("testRunningLayerDoesNotChangeWeightsOrThreshold(): START ");
		int numberInputs = 3;
        int numberNeurons= 3;
        double[] inputs = new double[] {2.0, 3.0, 4.0};
		double[] weights = new double[] {1.0};
		NetworkLayer  networkLayer = new NetworkLayer(LayerType.I,
				                                      numberInputs, 
				                                      numberNeurons);
		Neuron2[] neurons = networkLayer.getNeurons();
		neurons[0].setWeights(weights);
		neurons[1].setWeights(weights);
		neurons[2].setWeights(weights);
		networkLayer.setNeurons(neurons);
		NetworkLayer originalLayer = networkLayer.copyNetworkLayer();
		networkLayer.runLayer(inputs);
		assertTrue(networkLayer.equals(originalLayer));
		if (printAllMessages) System.out.println("testRunningLayerDoesNotChangeWeightsOrThreshold(): END \n");
	}
	
	@Test
	void testRunningWrongInputCountDoesNotChangeOutput() {
		if (printErrorMessages) System.out.println("testRunningWrongInputCountDoesNotChangeOutput(): START");
		int numberInputs = 2;
        int numberNeurons= 3;
        double[] inputs = new double[] {1, 1, 1};
		double[] weights = new double[] {2, 3, 4};
		NetworkLayer  networkLayer = new NetworkLayer(LayerType.H,
				                                      numberInputs, 
				                                      numberNeurons);
		Neuron2[] neurons = networkLayer.getNeurons();
		neurons[0].setWeights(weights);
		neurons[1].setWeights(weights);
		neurons[2].setWeights(weights);
		networkLayer.setNeurons(neurons);
		if (printErrorMessages) System.out.println("testRunningWrongInputCountDoesNotChangeOutput(): triggering error -> ");
		networkLayer.runLayer(inputs);
		//assertEquals(8.933, networkLayer.getWeightedLayerOutputs()[0]);
		if (printErrorMessages) System.out.println("testRunningWrongInputCountDoesNotChangeOutput(): END \n");
	}
	
	@Test
	void testRunLayerWithTooManyInputsDoesNotChangeOutput() {
		if (printAllMessages) System.out.print("testRunLayerWithTooManyInputsDoesNOtChangeOutput: START \n");
		int numberInputs = 3;
        int numberNeurons= 3;
        double[] inputs = new double[] {1, 1, 1, 1};
		double[] weights = new double[] {2, 3, 4};
		NetworkLayer  networkLayer = new NetworkLayer(LayerType.I,
				                                      numberInputs, 
				                                      numberNeurons);
		Neuron2[] neurons = networkLayer.getNeurons();
		neurons[0].setWeights(weights);
		neurons[1].setWeights(weights);
		neurons[2].setWeights(weights);
		networkLayer.setNeurons(neurons);
		networkLayer.runLayer(inputs);
		assertEquals(0.0, networkLayer.getWeightedLayerOutputs()[0]);
		if (printAllMessages) System.out.println("testRunLayerWithTooManyInputsDoesNOtChangeOutput: END \n");
	}
	
	@Test
	void testRunHiddenLayerWithTooManyInputsDoesNotChangeOutput() {
		if (printErrorMessages) System.out.println("CtestRunHiddenLayerWithTooManyInputsDoesNotChangeOutput: START");
		int numberInputs = 3;
        int numberNeurons= 3;
        double[] inputs = new double[] {1, 1, 1, 1};
		double[] weights = new double[] {2, 3, 4};
		NetworkLayer  networkLayer = new NetworkLayer(LayerType.H,
				                                      numberInputs, 
				                                      numberNeurons);
		Neuron2[] neurons = networkLayer.getNeurons();
		neurons[0].setWeights(weights);
		neurons[1].setWeights(weights);
		neurons[2].setWeights(weights);
		networkLayer.setNeurons(neurons);
		if (printErrorMessages) System.out.print("testRunHiddenLayerWithTooManyInputsDoesNotChangeOutput: triggering error -> ");
		
		networkLayer.runLayer(inputs);
		assertEquals(0.0, networkLayer.getWeightedLayerOutputs()[0]);
		if (printErrorMessages) System.out.println("CtestRunHiddenLayerWithTooManyInputsDoesNotChangeOutput: END \n");
	}
	
	
	@Test
	void testAdjustedLayerNeuronsAreNotEqualToSelf() {
		double[] weights = new double[] {1.1, 2.2, 3.3};
		double[] outputs = new double[] {4.4, 5.5, 6.6};
		Neuron2 neuron1 = new Neuron2(weights, 3.0, outputs[0]);
		Neuron2 neuron2 = new Neuron2(weights, 3.0, outputs[1]);
		Neuron2 neuron3 = new Neuron2(weights, 3.0, outputs[2]);
		Neuron2[] neurons = new Neuron2[] {neuron1, neuron2, neuron3};
		NetworkLayer  networkLayer = new NetworkLayer();
		networkLayer.setLayerType(LayerType.H);
		networkLayer.setNeurons(neurons);
		
		// Proper operation of equals() proves the new layer was modified
		NetworkLayer adjustedLayer = networkLayer.copyNetworkLayer();
		assertTrue(networkLayer.equals(adjustedLayer)); 
		
		adjustedLayer = networkLayer.adjustNetworkLayer();
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
		Neuron2 neuron1 = new Neuron2(weights, 3.0, outputs[0]);
		Neuron2 neuron2 = new Neuron2(weights, 3.0, outputs[1]);
		Neuron2 neuron3 = new Neuron2(weights, 3.0, outputs[2]);
		Neuron2[] neurons = new Neuron2[] {neuron1, neuron2, neuron3};
		Neuron2[] neurons2 = new Neuron2[] {neuron1.copyNeuron(), 
				                          neuron2.copyNeuron(), 
				                          neuron3.copyNeuron()};
		
		NetworkLayer  networkLayer = new NetworkLayer();
		networkLayer.setLayerType(LayerType.I);
		networkLayer.setNeurons(neurons);
		
		NetworkLayer  networkLayer2 = new NetworkLayer();
		assertFalse(networkLayer.equals(networkLayer2));
		networkLayer2.setLayerType(LayerType.O);
		networkLayer2.setNeurons(neurons2);
		if (printMessages) System.out.println("NetworkLayer toString: " + networkLayer.toString());		
	}
	
	NetworkLayer getNewNetworkLayer(LayerType layerType, int neuronCount, int inputCount) {
		if (printMessages) System.out.println("	getNewNetworkLayer() START");		
		double[] inputs = new double[inputCount];
        for (int i = 0; i < inputCount; i++) { inputs[i] = 0; }
        
        int weightCount = 0;
        if (layerType == LayerType.I)            { weightCount = 1; }	
        else if (layerType == LayerType.H)       { weightCount = inputCount; }
        else if (layerType == LayerType.O)       { weightCount = inputCount; }
        else if (layerType == LayerType.UNKNOWN) { weightCount = 1; }
        
        double[] weights = new double[weightCount];
        for (int i = 0; i < weightCount; i++) { weights[i] = 1.0; }
		
		NetworkLayer  networkLayer = new NetworkLayer(layerType,
													  inputCount, 
				                                      neuronCount);
		Neuron2[] neurons = new Neuron2[neuronCount];
		for (int i = 0; i < neuronCount; i++) { neurons[i] = new Neuron2(weights, 0, 0); }
		networkLayer.setNeurons(neurons);
		if (printMessages) System.out.println("	getNewNetworkLayer() END");	
		return networkLayer;
	}

}
