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
		Neuron copiedNeuron = Neuron.copyNeuron(neuron);
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
	void testEquals() {
		double[] weights = {1.1,2.2, 3.3};
		Neuron neuron = new Neuron(weights, 4, 5);
		Neuron copiedNeuron = Neuron.copyNeuron(neuron);
		assertTrue(neuron.equals(copiedNeuron));
	}

	@Test
	void testToString() {
		double[] weights = {1.1,2.2, 3.3};
		Neuron neuron = new Neuron(weights, 4, 5);
		assertEquals("Neuron {w0:1.1000|w1:2.2000|w2:3.3000|threshold:4.0000|output:5.0000", neuron.toString());
	}

}
