package com.neuralnetworktest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.neuralnetwork.Neuron;

class NeuronTest {

	@Test
	void testNeuronZeroOnCreate() {
		Neuron neuron = new Neuron();
		assertEquals(0, neuron.getWeights()[0]);
		assertEquals(0, neuron.getThreshold());
		assertEquals(0, neuron.getOutput());
	}
	
	@Test
	void testNeuronZeroOnComplexCreateSetAsExpected() {
		double[] weights = {0.0};
		Neuron neuron = new Neuron(weights, 0, 0);
		assertEquals(0, neuron.getWeights()[0]);
		assertEquals(0, neuron.getThreshold());
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
		double[] weights = {1.1,2.2, 3.3};
		Neuron neuron = new Neuron(weights, 4, 5);
		Neuron myNeuron = new Neuron();
		myNeuron.setNeuron(neuron);
		assertEquals(1.1, myNeuron.getWeights()[0]);
		assertEquals(2.2, myNeuron.getWeights()[1]);
		assertEquals(3.3, myNeuron.getWeights()[2]);
		assertEquals(4,   myNeuron.getThreshold());
		assertEquals(5,   myNeuron.getOutput());
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
	void testToString() {
		double[] weights = {1.1,2.2, 3.3};
		Neuron neuron = new Neuron(weights, 4, 5);
		assertEquals("Neuron {w0:1.1000|w1:2.2000|w2:3.3000|threshold:4.0000|output:5.0000|", neuron.toString());
	}

}
