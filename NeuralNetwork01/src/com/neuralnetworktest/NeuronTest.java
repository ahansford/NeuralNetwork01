package com.neuralnetworktest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;



//import com.neuralnetwork.NetworkLayer;
import com.neuralnetwork.Neuron;

class NeuronTest {

	@Test
	void testOneNeuronWithOneWeightOnSimpleCreate() {
		Neuron neuron = new Neuron();
		//assertEquals(0, neuron.getWeights()[0]); // random
		assertEquals(1, neuron.getWeights().length);
		//assertEquals(0, neuron.getThreshold()); // random
		assertEquals(0, neuron.getOutput());
		//System.out.println("SimpleNeuronCreate: " + neuron.toString());
	}
	
	@Test
	void testAllNeuronValuesOnComplexCreateSetAsExpected() {
		double[] weights = {7.8};
		Neuron neuron = new Neuron(weights, 0.1, 0);
		assertEquals(7.8, neuron.getWeights()[0]);
		assertEquals(0.1, neuron.getThreshold());
		assertEquals(0, neuron.getOutput());
	}

	@Test
	void testNeuronComplexCreateWeightsSetAsExpected() {
		double[] weights = {25.7, 30.4};
		Neuron neuron = new Neuron(weights, 0, 0);
		assertEquals(25.7, neuron.getWeights()[0]);
		assertEquals(30.4, neuron.getWeights()[1]);
	}
	
	@Test
	void testNeuronComplexCreateThresholdSetAsExpected() {
		double[] weights = {0.0};
		Neuron neuron = new Neuron(weights, 58.3, 0);
		assertEquals(58.3, neuron.getThreshold());
	}
	
	@Test
	void testNeuronComplexCreateOutputSetAsExpected() {
		double[] weights = {0.0};
		Neuron neuron = new Neuron(weights, 0, -12.5);
		assertEquals(-12.5, neuron.getOutput());
	}
	
	@Disabled
	@Test
	void testAllNeuronValuesAsExpectedOnComplexCreateSet() {
		double[] weights = {7.8};
		Neuron neuron = new Neuron(weights, 0.1, 0);
		assertEquals(7.8, neuron.getWeights()[0]);
		assertEquals(0.1, neuron.getThreshold());
		assertEquals(0, neuron.getOutput());
	}
	
	@Test
	void testCopiedNeuronHasExpectedValues() {
		double[] weights = {1.1, 2.2, 3.3};
		Neuron neuron = new Neuron(weights, 4, 5);
		Neuron copiedNeuron = neuron.copyNeuron();
		assertEquals(1.1, copiedNeuron.getWeights()[0]);
		assertEquals(2.2, copiedNeuron.getWeights()[1]);
		assertEquals(3.3, copiedNeuron.getWeights()[2]);
		assertEquals(4,   copiedNeuron.getThreshold());
		assertEquals(5,   copiedNeuron.getOutput());
	}
	
	@Test
	void testNeuronCopyHasExpectedValues() {
		double[] weights = {1.1,2.2, 3.3};
		Neuron neuron = new Neuron(weights, 4, 5);
		assertEquals(1.1, neuron.copyNeuron().getWeights()[0]);
		assertEquals(2.2, neuron.copyNeuron().getWeights()[1]);
		assertEquals(3.3, neuron.copyNeuron().getWeights()[2]);
		assertEquals(4,   neuron.copyNeuron().getThreshold());
		assertEquals(5,   neuron.copyNeuron().getOutput());
	}

	@Test
	void testNeuronEqualsSelf() {
		double[] weights = {1.1,2.2, 3.3};
		Neuron neuron = new Neuron(weights, 4, 5);
		assertTrue(neuron.equals(neuron));
	}
	
	@Test
	void testNeuronNotEqualToNullNeuron() {
		double[] weights = {1.1,2.2, 3.3};
		Neuron neuron = new Neuron(weights, 4, 5);
		assertFalse(neuron.equals((Neuron)null));
	}
	
	@Test
	void testUnequalNeuronsNotEqual() {
		double[] weights = {1.1,2.2, 3.3};
		Neuron neuron = new Neuron(weights, 4, 5);
		Neuron neuron2 = new Neuron();
		assertFalse(neuron.equals(neuron2));
	}
	
	@Test
	void testWrongThresholdsNotEqual() {
		double[] weights = {1.1, 2.2, 3.3};
		double threshold = 5;
		Neuron neuron = new Neuron(weights, threshold, 0);
		Neuron copiedNeuron = neuron.copyNeuron();
		copiedNeuron.setThreshold(threshold + 1);
		assertFalse(neuron.equals(copiedNeuron));
	}
	
	@Disabled  // TODO:  REMOVE?
	@Test
	void testWrongOutputNotEqual() {
		// Left out as it is not clear outputs should be tested in equal
		double[] weights = {1.1, 2.2, 3.3};
		double output = 7;
		Neuron neuron = new Neuron(weights, 0, output);
		Neuron neuron2 = new Neuron(weights, 0, output+1);
		assertFalse(neuron.equals(neuron2));
	} 
	
	@Test
	void testWrongInputLenght1NotEqual() {
		double[] weights1 = {1.1};
		double[] weights2 = {1.1, 2.2};
		Neuron neuron = new Neuron(weights1, 0, 0);
		Neuron neuron2 = new Neuron(weights2, 0, 0);
		assertFalse(neuron.equals(neuron2));
	}
	
	@Test
	void testWrongInputLenght2NotEqual() {
		double[] weights = {1.1, 2.2};
		double[] weights2 = {1.1, 2.2, 3.3};
		Neuron neuron = new Neuron(weights, 0, 0);
		Neuron neuron2 = new Neuron(weights2, 0, 0);
		assertFalse(neuron.equals(neuron2));
	}
	
	@Test
	void testWrongWeightValuesNotEqual() {
		double[] weights1 = {1.1, 2.2, 3.3};
		double[] weights2 = {4.4, 5.5, 6.6};
		Neuron neuron = new Neuron(weights1, 0, 0);
		Neuron neuron2 = new Neuron(weights2, 0, 0);
		assertFalse(neuron.equals(neuron2));
	}

	@Test
	void testCopiedNeuronIsEqual() {
		double[] weights = {1.1,2.2, 3.3};
		Neuron neuron = new Neuron(weights, 4, 5);
		Neuron copiedNeuron = neuron.copyNeuron();
		assertTrue(neuron.equals(copiedNeuron));
	}
	
	@Test
	void testAdjustedNeuronDoesNotEqualOriginal() {
		double[] weights = {1.0, 1.0, 1.0};
		Neuron neuron = new Neuron(weights, 1, 0);
		Neuron adjustedNeuron = neuron.getAdjustedNeuron();
		assertFalse(neuron.equals(adjustedNeuron));
	}
	
	
	// TODO: remove to the layer level
	@Test
	void testAdjustedNeuron2ReturnsZeroForNegativeNumbers() {
		double inputValue = -1.5;
		assertEquals(0.0, Neuron.applyActivationFunction(inputValue));
	}
	
	// TODO: remove to the layer level
	@Test
	void testAdjustedNeuron2ReturnsPointFiveForPointFive() {
		double inputValue = 0.5;
		assertEquals(0.5, Neuron.applyActivationFunction(inputValue));
	}
	
	// TODO: remove to the layer level
	@Test
	void testAdjustedNeuron2ReturnsSevenForPointSeven() {
		double inputValue = 7;
		assertEquals(7, Neuron.applyActivationFunction(inputValue));
	}
	
	@Test
	void testRunNeuron() {
		double[] inputs = new double[] {1, 1, 1};
		double[] weights = new double[] {2, 3, 4};
		Neuron neuron = new Neuron(weights, 1, 0);
		neuron.runNeuron(inputs);
		assertEquals( 8, neuron.getOutput() ); // see the activation function
	}
	
	@Test
	void testRunNeuronUnchangedOnTooFewInputCount() {
		double[] inputs = new double[] {1, 1};
		double[] weights = new double[] {2, 3, 4};
		Neuron neuron = new Neuron(weights, 1, 0);
		neuron.runNeuron(inputs);
		assertEquals(0,  neuron.getOutput());
	}
	
	@Test
	void testRunNeuronUnchangedOnTooManyInputCount() {
		double[] inputs = new double[] {1, 1, 1, 3};
		double[] weights = new double[] {2, 3, 4};
		Neuron neuron = new Neuron(weights, 1, 0);
		neuron.runNeuron(inputs);
		assertEquals(0,  neuron.getOutput());
	}

	//@Disabled
	@Test
	void testToString() {
		double[] weights = {1.1,2.2, 3.3};
		Neuron neuron = new Neuron(weights, 4, 5);
		//System.out.println(neuron.toString());
		assertEquals("Neuron {|w0:1.1000w1:2.2000w2:3.3000threshold:4.0000|output:5.0000|}", neuron.toString());
	}
}
