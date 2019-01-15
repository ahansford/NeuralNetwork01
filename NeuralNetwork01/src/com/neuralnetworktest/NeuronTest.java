package com.neuralnetworktest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

//import com.neuralnetwork.NetworkLayer;
import com.neuralnetwork.Neuron;

class NeuronTest {

	@Test
	void testNeuronZeroOnSimpleCreate() {
		Neuron neuron = new Neuron();
		//assertEquals(0, neuron.getWeights()[0]); // random
		assertEquals(1, neuron.getWeights().length);
		//assertEquals(0, neuron.getThreshold()); // random
		assertEquals(0, neuron.getOutput());
		//System.out.println("SimpleNeuronCreate: " + neuron.toString());
	}
	
	@Test
	void testNeuronZeroOnComplexCreateSetAsExpected() {
		double[] weights = {7.8};
		Neuron neuron = new Neuron(weights, 0.1, 0);
		//System.out.println("WithWeightsNeuronCreate: " + neuron.toString());
		assertEquals(7.8, neuron.getWeights()[0]);
		assertEquals(0.1, neuron.getThreshold());
		assertEquals(0, neuron.getOutput());
	}
	
	@Test
	void testNeuronComplexCreateWeightsSetAsExpected() {
		double[] weights = {25.7};
		Neuron neuron = new Neuron(weights, 0, 0);
		assertEquals(25.7, neuron.getWeights()[0]);
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
	
	@Test
	void testCopy() {
		double[] weights = {1.1,2.2, 3.3};
		Neuron neuron = new Neuron(weights, 4, 5);
		Neuron copiedNeuron = neuron.copyNeuron();
		assertEquals(1.1, copiedNeuron.getWeights()[0]);
		assertEquals(2.2, copiedNeuron.getWeights()[1]);
		assertEquals(3.3, copiedNeuron.getWeights()[2]);
		assertEquals(4,   copiedNeuron.getThreshold());
		assertEquals(5,   copiedNeuron.getOutput());
	}
	
	@Test
	void testSetNeuron() {
		double[] weights = {1.1, 2.2, 3.3};
		Neuron neuron = new Neuron(weights, 4, 5);
		Neuron myNeuron = new Neuron();
		myNeuron.setNeuron(neuron);
		//System.out.println("SetNeuronValues: " + neuron.toString());
		assertEquals(1.1, myNeuron.getWeights()[0]);
		assertEquals(2.2, myNeuron.getWeights()[1]);
		assertEquals(3.3, myNeuron.getWeights()[2]);
		assertEquals(4,   myNeuron.getThreshold());
		assertEquals(5,   myNeuron.getOutput());
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
		Neuron neuron2 = null;
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
	void testUnequalNeuronsNotEqual() {
		double[] weights = {1.1,2.2, 3.3};
		Neuron neuron = new Neuron(weights, 4, 5);
		Neuron neuron2 = new Neuron();
		assertFalse(neuron.equals(neuron2));
	}
	
	@Test
	void testWrongThresholdsNotEqual() {
		double[] weights = {1.1, 2.2, 3.3};
		Neuron neuron = new Neuron(weights, 1, 0);
		Neuron neuron2 = new Neuron(weights, 2, 0);
		assertFalse(neuron.equals(neuron2));
	}
	
	@Test
	void testWrongOutputNotEqual() {
		/* Left out as it is not clear outputs should be tested in equal
		double[] weights = {1.1, 2.2, 3.3};
		Neuron neuron = new Neuron(weights, 0, 0);
		Neuron neuron2 = new Neuron(weights, 0, 2);
		assertFalse(neuron.equals(neuron2));*/
	}
	
	@Test
	void testWrongInputLenght1NotEqual() {
		double[] weights = {1.1, 2.2, 3.3};
		double[] weights2 = {1.1, 2.2};
		Neuron neuron = new Neuron(weights, 0, 0);
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
		double[] weights = {1.1, 2.2, 3.3};
		double[] weights2 = {4.4, 5.5, 6.6};
		Neuron neuron = new Neuron(weights, 0, 0);
		Neuron neuron2 = new Neuron(weights2, 0, 0);
		assertFalse(neuron.equals(neuron2));
	}

	@Test
	void testAdjustedNeuronDoesNotEqualOriginal() {
		double[] weights = {1.0, 1.0, 1.0};
		Neuron neuron = new Neuron(weights, 1, 0);
		Neuron adjustedNeuron = neuron.getAdjustedNeuron();
		assertFalse(neuron.equals(adjustedNeuron));
		//System.out.println("testAdjustedNeuronDoesNotEqualOriginal: Original " + neuron.toString());
		//System.out.println("testAdjustedNeuronDoesNotEqualOriginal: Adjusted " + adjustedNeuron.toString());
		adjustedNeuron = adjustedNeuron.getAdjustedNeuron();
		adjustedNeuron = adjustedNeuron.getAdjustedNeuron();
		adjustedNeuron = adjustedNeuron.getAdjustedNeuron();
		adjustedNeuron = adjustedNeuron.getAdjustedNeuron();
		adjustedNeuron = adjustedNeuron.getAdjustedNeuron();
		adjustedNeuron = adjustedNeuron.getAdjustedNeuron();
		adjustedNeuron = adjustedNeuron.getAdjustedNeuron();
		adjustedNeuron = adjustedNeuron.getAdjustedNeuron();
		//System.out.println("testAdjustedNeuronDoesNotEqualOriginal: Adjusted " + adjustedNeuron.toString());
	}
	
	
	@Test
	void testToString() {
		//double[] weights = {1.1,2.2, 3.3};
		//Neuron neuron = new Neuron(weights, 4, 5);
		//System.out.println(neuron.toString());
		//assertEquals("Neuron {w0:1.1000w1:2.2000w2:3.3000threshold:4.0000|output:5.0000|", neuron.toString());
	}
	
	@Test
	void testRunNeuron() {
		double[] inputs = new double[] {1, 1, 1};
		double[] weights = new double[] {2, 3, 4};
		Neuron neuron = new Neuron(weights, 1, 0);
		neuron.runNeuron(inputs);
		//expectedOutput = 1*2 + 1*3 + 1*4 = 9
		//assertEquals(9, neuron.getOutput()); // see the activation function
		//System.out.println("AfterRunNeuron: " + neuron.toString());
	}

}
