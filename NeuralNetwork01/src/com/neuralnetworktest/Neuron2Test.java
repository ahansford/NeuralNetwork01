package com.neuralnetworktest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.neuralnetwork.Neuron2;

class Neuron2Test {

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
	
	@Disabled
	@Test
	void testDisabled() {}
	
	// *** Simple constructor working as expected *** //
	@Test
	void testOneNeuronWithOneWeightOnSimpleCreate() {
		Neuron2 neuron = new Neuron2();
		assertEquals(1, neuron.getWeightsCount());
        assertEquals(1.0, neuron.getWeights()[0]);
		assertEquals(0, neuron.getThreshold());
		assertEquals(0, neuron.getOutput());
		//System.out.println("\n testOneNeuronWithOneWeightOnSimpleCreate(): SimpleNeuronCreate: " + neuron.toString() + "\n");
	}
	
	
	
	// *** Complex constructor working as expected *** //
	@Test
	void testNeuronComplexCreateWeightsSetAsExpected() {
		double[] weights = {25.7, 30.4};
		Neuron2 neuron = new Neuron2(weights, 0, 0);
		assertEquals(25.7, neuron.getWeights()[0]);
		assertEquals(30.4, neuron.getWeights()[1]);
	}
	
	@Test
	void testNeuronComplexCreateThresholdSetAsExpected() {
		double[] weights = {0.0};
		Neuron2 neuron = new Neuron2(weights, 58.3, 0);
		assertEquals(58.3, neuron.getThreshold());
	}
	
	@Test
	void testNeuronComplexCreateOutputSetAsExpected() {
		double[] weights = {0.0};
		Neuron2 neuron = new Neuron2(weights, 0, -12.5);
		assertEquals(-12.5, neuron.getOutput());
	}
	
	@Test
	void testAllNeuronValuesAsExpectedOnComplexCreateSet() {
		double[] weights = {7.8};
		Neuron2 neuron = new Neuron2(weights, 0.1, 0);
		assertEquals(7.8, neuron.getWeights()[0]);
		assertEquals(0.1, neuron.getThreshold());
		assertEquals(0, neuron.getOutput());
	}
	
	@Test
	void testAllNeuronValuesOnComplexCreateSetAsExpected() {
		double[] weights = {7.8};
		Neuron2 neuron = new Neuron2(weights, 0.1, 0);
		assertEquals(7.8, neuron.getWeights()[0]);
		assertEquals(0.1, neuron.getThreshold());
		assertEquals(0, neuron.getOutput());
	}
	
	
	// *** Local function to create a new Neuron working as expected *** //
	
		@Test
		void testGetStandardNeuronReturnsExpectedValues() {
			int weightCount = 1;
			Neuron2 neuron = getStandardNeuron2(weightCount);
			assertEquals(1, neuron.getWeights()[0]); 
			assertEquals(1, neuron.getWeightsCount());
			assertEquals(0, neuron.getThreshold()); 
			assertEquals(0, neuron.getOutput());
			//System.out.println("\n testGetStandardNeuronReturnsExpectedValues(): getStandardNeuron2: " + neuron.toString() + "\n");
		}
		
		@Test
		void testGetStandardNeuronHandlesMultipleWeights() {
			int weightCount = 4;
			Neuron2 neuron = getStandardNeuron2(weightCount);
			assertEquals(1, neuron.getWeights()[0]); 
			assertEquals(1, neuron.getWeights()[1]);
			assertEquals(1, neuron.getWeights()[2]);
			assertEquals(1, neuron.getWeights()[3]);
			//System.out.println("\n testGetStandardNeuronReturnsExpectedValues(): getStandardNeuron2: with " +weightCount+ " weights -> " + neuron.toString() + "\n");
		}
		
	// *** Copy will make a complete replica of the original Neuron *** //
	@Test
	void testCopiedNeuronHasExpectedValues() {
		double[] weights = {1.1, 2.2, 3.3};
		Neuron2 neuron = new Neuron2(weights, 4, 5);
		Neuron2 copiedNeuron = neuron.copyNeuron();
		assertEquals(1.1, copiedNeuron.getWeights()[0]);
		assertEquals(2.2, copiedNeuron.getWeights()[1]);
		assertEquals(3.3, copiedNeuron.getWeights()[2]);
		assertEquals(4,   copiedNeuron.getThreshold());
		assertEquals(5,   copiedNeuron.getOutput());
	}
	
	@Test
	void testCopiedNeuronIsNotOriginal() {
		double[] weights = {1.1,2.2, 3.3};
		Neuron2 neuron = new Neuron2(weights, 4, 5);
		Neuron2 copiedNeuron = neuron.copyNeuron();
		copiedNeuron.setThreshold(14);
		assertEquals(4, neuron.getThreshold());
		copiedNeuron.getWeights()[0] = 400;
		assertFalse(neuron.equals(copiedNeuron));
	}

	
	// *** Equal properly matches weights and thresholds *** //
	
	@Test
	void testNeuronEqualsSelf() {
		double[] weights = {1.1,2.2, 3.3};
		Neuron2 neuron = new Neuron2(weights, 4, 5);
		assertTrue(neuron.equals(neuron));
	}
		
	@Test
	void testNeuronNotEqualToNullNeuron() {
		double[] weights = {1.1,2.2, 3.3};
		Neuron2 neuron = new Neuron2(weights, 4, 5);
		assertFalse(neuron.equals((Neuron2)null));
	}
	
	@Test
	void testWrongThresholdsNotEqual() {
		double[] weights = {1.1, 2.2, 3.3};
		double threshold = 5;
		Neuron2 neuron = new Neuron2(weights, threshold, 0);
		Neuron2 copiedNeuron = neuron.copyNeuron();
		copiedNeuron.setThreshold(threshold + 1);
		assertFalse(neuron.equals(copiedNeuron));
	}
	
	@Test
	void testDifferingOutputsStillListAsEqual() {
		// Left out as it is not clear outputs should be tested in equal
		Neuron2 neuron = getStandardNeuron2(1);
		Neuron2 neuron2 = getStandardNeuron2(1);
		double[] inputs = {1.0};
		neuron2.runNeuron(inputs);
		assertTrue(neuron.getOutput() != neuron2.getOutput());
		assertTrue(neuron.equals(neuron2));
	} 
	
	@Test
	void testWrongInputLenght1NotEqual() {
		Neuron2 neuron = getStandardNeuron2(1);
		Neuron2 neuron2 = getStandardNeuron2(2);
		assertFalse(neuron.equals(neuron2));
	}
	
	@Test
	void testWrongWeightValuesNotEqual() {
		double[] weights1 = {1.1, 2.2, 3.3};
		double[] weights2 = {4.4, 5.5, 6.6};
		Neuron2 neuron = new Neuron2(weights1, 0, 0);
		Neuron2 neuron2 = new Neuron2(weights2, 0, 0);
		assertFalse(neuron.equals(neuron2));
	}

	@Test
	void testCopiedNeuronIsEqual() {
		double[] weights = {1.1,2.2, 3.3};
		Neuron2 neuron = new Neuron2(weights, 4, 5);
		Neuron2 copiedNeuron = neuron.copyNeuron();
		assertTrue(neuron.equals(copiedNeuron));
	}


	// *** Adjust Neuron makes adjustments as expected *** //
	
	@Test
	void testAdjustedTsAndWsDoesNotEqualOriginal() {
		int weightCount = 3;
		Neuron2 neuron = getStandardNeuron2(weightCount);
		Neuron2 adjustedNeuron = neuron.getThresholdAndWeightsAdjustedNeuron();
		assertFalse(neuron.equals(adjustedNeuron));
	}
	
	
	// *** Adjust Neuron weight at INDEX makes adjustments as expected *** //
	
	@Test
	void testAdjustedNeuronWeightAtIndexNotEqualOriginal() {
		double[] weights = {1.0, 1.0, 1.0};
		Neuron2 neuron = new Neuron2(weights, 1, 0);
		//System.out.println("Neuron" + neuron.toString());
		Neuron2 adjustedNeuron = neuron.getNeuronWithAdjustedWeightAtIndex(0, 0.1);
		assertFalse(neuron.equals(adjustedNeuron));
	}
	
	@Test
	void testGetNeuronWithAdjustedWeightAtIndexReturnsStepIncrement() {
		Neuron2 neuron = getStandardNeuron2(1);
		double step = 0.2;
		int weightIndex = 0;
		Neuron2 adjustedNeuron = neuron.getNeuronWithAdjustedWeightAtIndex(weightIndex, step);
		assertEquals(1.2, adjustedNeuron.getWeights()[weightIndex]);
	}
	
	@Test
	void testGetNeuronWithAdjustedWeightAtIndexSecondWeightReturnsStepIncrement() {
		Neuron2 neuron = getStandardNeuron2(2);
		double step = 0.3;
		int weightIndex = 1;
		Neuron2 adjustedNeuron = neuron.getNeuronWithAdjustedWeightAtIndex(weightIndex, step);
		assertEquals((1.0 + step), adjustedNeuron.getWeights()[weightIndex]);
	}
	
	@Test
	void testGetNeuronWithAdjustedWeightAtIndexReturnsSelfOnOutOfRangeIndex() {
		Neuron2 neuron = getStandardNeuron2(2);
		double step = 0.3;
		int weightIndex = -3;
		Neuron2 adjustedNeuron = neuron.getNeuronWithAdjustedWeightAtIndex(weightIndex, step);
		assertTrue(adjustedNeuron.equals(neuron));
		weightIndex = 100;
		adjustedNeuron = neuron.getNeuronWithAdjustedWeightAtIndex(weightIndex, step);
		assertTrue(adjustedNeuron.equals(neuron));
	}
	
	
	// *** Automatic reseting get Index point works as expected *** //
	
	@Test
	void testGetThresholdAndWeightsIndexReturnsZeroOnFirstCall() {
		Neuron2 neuron = getStandardNeuron2(2);
		assertEquals(0, neuron.getThresholdAndWeightsIndex());
	}
	
	@Test
	void testGetThresholdAndWeightsIndexIncrements() {
		Neuron2 neuron = getStandardNeuron2(2);
		assertEquals(0, neuron.getThresholdAndWeightsIndex());
		assertEquals(1, neuron.getThresholdAndWeightsIndex());
		assertEquals(2, neuron.getThresholdAndWeightsIndex());
		assertEquals(0, neuron.getThresholdAndWeightsIndex());
		assertEquals(1, neuron.getThresholdAndWeightsIndex());
	}
	
	@Test
	void testResetThresholdAndWeightsIndexReturnsZeroOnFirstCall() {
		Neuron2 neuron = getStandardNeuron2(5);
		neuron.getThresholdAndWeightsIndex();
		neuron.getThresholdAndWeightsIndex();
		neuron.resetThresholdAndWeightsIndex();
		assertEquals(0, neuron.getThresholdAndWeightsIndex());
	}
	

	// *** RunNeuron works as expected *** //
	
	@Test
	void testRunNeuron() {
		double[] inputs = new double[] {1, 1, 1};
		double[] weights = new double[] {2, 3, 4};
		double thresholdValue = 0;
		double expectedOutputValue = 2 + 3 + 4 - thresholdValue;
		Neuron2 neuron = new Neuron2(weights, thresholdValue, 0);
		neuron.runNeuron(inputs);
		assertEquals( expectedOutputValue, neuron.getOutput() ); 
	}
	
	@Test
	void testRunSingleInputNeuronReturnsOutputOfOne() {
		double[] inputs = new double[] {1};
		Neuron2 neuron = getStandardNeuron2(1);
		neuron.runNeuron(inputs);
		assertEquals( 1.0, neuron.getOutput() ); // careful about comparing floats
	}
	
	@Test
	void testThreeNeuronRunStandardNeuronReturnsOutputOfThree() {
		int weightsCount = 3;
		double[] inputs = new double[] {1, 1, 1};
		Neuron2 neuron = getStandardNeuron2(weightsCount);
		neuron.runNeuron(inputs);
		assertEquals( 3.0, neuron.getOutput() ); // careful about comparing floats
	}
	
	@Test
	void testRunNeuronUnchangedOnTooFewInputCount() {
		double[] inputs = new double[] {1, 1};
		double[] weights = new double[] {2, 3, 4};
		Neuron2 neuron = new Neuron2(weights, 1, 0);
		System.out.print("testRunNeuronUnchangedOnTooFewInputCount(): triggering error -> ");
		neuron.runNeuron(inputs);
		assertEquals(0,  neuron.getOutput());
	}
	
	@Test
	void testRunNeuronUnchangedOnTooManyInputCount() {
		double[] inputs = new double[] {1, 1, 1, 3};
		double[] weights = new double[] {2, 3, 4};
		Neuron2 neuron = new Neuron2(weights, 1, 0);
		System.out.print("testRunNeuronUnchangedOnTooManyInputCount(): triggering error -> ");
		neuron.runNeuron(inputs);
		assertEquals(0,  neuron.getOutput());
	}
	
	// *** Activation functions work as expected *** //
	// TODO: remove to the layer level
	@Test
	void testAdjustedNeuron2ReturnsZeroForNegativeNumbers() {
		double inputValue = -1.5;
		assertEquals(0.0, Neuron2.applyActivationFunction(inputValue));
	}
	
	// TODO: remove to the layer level
	@Test
	void testAdjustedNeuron2ReturnsPointFiveForPointFive() {
		double inputValue = 0.5;
		assertEquals(0.5, Neuron2.applyActivationFunction(inputValue));
	}
	
	// TODO: remove to the layer level
	@Test
	void testAdjustedNeuron2ReturnsSevenForPointSeven() {
		double inputValue = 7;
		assertEquals(7, Neuron2.applyActivationFunction(inputValue));
	}
	
	@Test
	void testApplyActivationFunction2ReturnsCorrectValue() {
		double inputValue = 0;
		assertEquals(0.5, Neuron2.applyActivationSigmoid(inputValue));
	}
	
	@Test
	void testApplyActivationFunction2ReturnsCorrectValueOnLargePositive() {
		double inputValue = 100;
		assertTrue(0.98 < Neuron2.applyActivationSigmoid(inputValue));
	}
	
	@Test
	void testApplyActivationFunction2ReturnsCorrectValueOnLargeNegative() {
		double inputValue = -100;
		assertTrue(0.02 > Neuron2.applyActivationSigmoid(inputValue));
	}
	
	
	@Test
	void testCheckGradientWeightValues() {
		double[][][] inputs = new double[][][] {{{2.5, 0.5}, {1}}};
		
		double step = 10;
		int weightsCount = 2;
		Neuron2 neuron = getStandardNeuron2(weightsCount);
		neuron.runNeuron(inputs[0][0]);
		neuron.setOutput(Neuron2.applyActivationFunction(neuron.getOutput()));
		double orginalError = neuron.getOutput() - inputs[0][1][0];
		double holdingDelta = 0;
		int holdingIndex = 0;
		
		double adjustedError = 0;
		Neuron2 adjustedNeuron = neuron.copyNeuron();
		int thresholdAndWeightsCount = adjustedNeuron.getThresholdAndWeightsCount();
				//System.out.println("neuron.getThresholdAndWeightsCount(): " + neuron.getThresholdAndWeightsCount());
		double[] gradientOutputs = new double[thresholdAndWeightsCount];
		for ( int i = 0; i < thresholdAndWeightsCount; i++) {
			adjustedNeuron = neuron.getNeuronWithAdjustedThresholdAndWeightsAtIndex(i, step);
			adjustedNeuron.runNeuron(inputs[0][0]);
			adjustedNeuron.setOutput(Neuron2.applyActivationFunction(adjustedNeuron.getOutput()));
			adjustedError = adjustedNeuron.getOutput() - inputs[0][1][0];
			gradientOutputs[i] = adjustedError;
			double currentDeltaMagnitude = Math.abs(adjustedError - orginalError);
			if (currentDeltaMagnitude > holdingDelta) {
				// Found a larger error at this index
				holdingDelta = currentDeltaMagnitude;
				holdingIndex = i; 
					//System.out.println("....Found a larger error at index: " + holdingIndex + ", *** Error: " + adjustedError);
			}
				
			//System.out.println("gradientOutputs[" + i + "]: " + gradientOutputs[i]);
			//System.out.println(neuron.toString());
			//System.out.println(adjustedNeuron.toString());
			
		}
		
		//assertEquals(0,  neuron.getOutput());
	}
	

	//@Disabled
	@Test
	void testToString() {
		double[] weights = {1.1,2.2, 3.3};
		Neuron2 neuron = new Neuron2(weights, 4, 5);
		//System.out.println(neuron.toString());
		assertEquals("Neuron {| w0:1.1000 w1:2.2000 w2:3.3000 threshold:4.0000 | output:5.0000 |}", neuron.toString());
	}
	
	Neuron2 getStandardNeuron2(int weightCount) {
		double[] weights = new double[weightCount];
		// TODO System.out.println("weightCount: " +weightCount + ", weights.length: " +weights.length);
		for (int i = 0; i < weightCount; i++) { 
			weights[i] = 1.0; 
			// TODO System.out.println("Setting weights[" + i +"]: " +weights[i] );
			
			}
		double threshold = 0.0;
		Neuron2 neuron = new Neuron2(weights, threshold, 0);
		return neuron;
	}
	

}
