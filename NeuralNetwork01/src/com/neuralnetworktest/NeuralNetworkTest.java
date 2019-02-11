package com.neuralnetworktest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.neuralnetwork.GradientDescent;
import com.neuralnetwork.NetworkLayer;
import com.neuralnetwork.NetworkLayer.LayerType;
import com.neuralnetwork.NeuralNetwork;
import com.neuralnetwork.Neuron2;

class NeuralNetworkTest {
	
	boolean printErrorMessages = true;
	boolean printMessages =  true;
	boolean printAllMessages =  true;
	
	public static double[][][] TEST_TRAINING_SET = new double[][][] {{{0, 0}, {0}},
																     {{0, 1}, {1}},
																     {{1, 0}, {1}},
																     {{1, 1}, {0}}};

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("*** NeuralNetworkTest: START ***");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("*** NeuralNetworkTest: END   ***");
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	

	// *** Constructor functions work as expected *** //
	
	@Test
	void testSimpleCreateReturnsLayerCountThree() {
		NeuralNetwork neuralNetwork = new NeuralNetwork();
		assertEquals( 3, neuralNetwork.getNetworkLayerCount());
	}
	
	@Test
	void testInputLayerOnSimpleCreate() {
		NeuralNetwork neuralNetwork = new NeuralNetwork();
		assertEquals(LayerType.I, neuralNetwork.getNetworkLayers()[0].getLayerType());
	}
	
	@Test
	void testOneLayerOnSinglelayerCreate() {
		NeuralNetwork neuralNetwork = new NeuralNetwork(1);
		assertEquals(1, neuralNetwork.getNetworkLayerCount());
	}
	
	@Test
	void testInputLayerOnSinglelayerCreate() {
		NeuralNetwork neuralNetwork = new NeuralNetwork(1);
		assertEquals(LayerType.I, neuralNetwork.getNetworkLayers()[0].getLayerType());
	}
	
	@Test
	void testTwoLayersOnSinglelayerCreate() {
		NeuralNetwork neuralNetwork = new NeuralNetwork(2);
		assertEquals(2, neuralNetwork.getNetworkLayerCount());
	}
		
	@Test
	void testIO_On2layerCreate() {
		NeuralNetwork neuralNetwork = new NeuralNetwork(2);
		neuralNetwork.getNetworkLayers()[0].getLayerType();
		assertEquals(LayerType.I, neuralNetwork.getNetworkLayers()[0].getLayerType());
		assertEquals(LayerType.O, neuralNetwork.getNetworkLayers()[1].getLayerType());
	}
	
	@Test
	void testThreeLayersOn3layerCreate() {
		NeuralNetwork neuralNetwork = new NeuralNetwork(3);
		assertEquals(3, neuralNetwork.getNetworkLayerCount());
	}
	
	@Test
	void testIHO_OnSimple3layerCreate() {
		NeuralNetwork neuralNetwork = new NeuralNetwork(3);
		assertEquals(LayerType.I, neuralNetwork.getNetworkLayers()[0].getLayerType());
		assertEquals(LayerType.H, neuralNetwork.getNetworkLayers()[1].getLayerType());
		assertEquals(LayerType.O, neuralNetwork.getNetworkLayers()[2].getLayerType());
	}
	
	@Test
	void testGetNetworkLayersReturnsSpecifiedLayerCount() {
		NeuralNetwork neuralNetwork = new NeuralNetwork(5);
		assertEquals( 5, neuralNetwork.getNetworkLayers().length);
	}
	
	@Test
	void testLayerTypeSequenceAsExpectedOnSpecifiedLayerCount() {
		NeuralNetwork neuralNetwork = new NeuralNetwork(5);
		assertEquals(LayerType.I, neuralNetwork.getNetworkLayers()[0].getLayerType());
		assertEquals(LayerType.H, neuralNetwork.getNetworkLayers()[1].getLayerType());
		assertEquals(LayerType.H, neuralNetwork.getNetworkLayers()[2].getLayerType());
		assertEquals(LayerType.H, neuralNetwork.getNetworkLayers()[3].getLayerType());
		assertEquals(LayerType.O, neuralNetwork.getNetworkLayers()[4].getLayerType());
	}
	
	@Test
	void testNeuralNetworkLayerCountThreeOnComplexCreate() {
		int inputsCount = 1; 
        int hiddenLayerCount = 1; 
        int hiddenLayerNeuronCount = 1;
        int outputsCount = 1;
		NeuralNetwork neuralNetwork = new NeuralNetwork(inputsCount,
														hiddenLayerCount,
														hiddenLayerNeuronCount,
														outputsCount );
		assertEquals(3,neuralNetwork.getNetworkLayerCount());
	}
		
	@Test
	void testGetNetworkLayersReturnsNonNullLayersOnComplexCreate() {
		int inputsCount = 1; 
        int hiddenLayerCount = 1; 
        int hiddenLayerNeuronCount = 1;
        int outputsCount = 1;
		NeuralNetwork neuralNetwork = new NeuralNetwork(inputsCount,
														hiddenLayerCount,
														hiddenLayerNeuronCount,
														outputsCount );
		NetworkLayer[] networkLayers = neuralNetwork.getNetworkLayers();
		assertNotNull(networkLayers[0]);
		assertNotNull(networkLayers[1]);
		assertNotNull(networkLayers[2]);
	}
	
	// *** Network accessor functions work as expected *** //
	
	
	@Test
	void testgetNetworkLayerNeuronCountReturnsCorrectNeuronCount() {
		int inputsCount = 2; 
        int hiddenLayerCount = 1; 
        int hiddenLayerNeuronCount = 6;
        int outputsCount = 7;
		NeuralNetwork neuralNetwork = new NeuralNetwork(inputsCount,
														hiddenLayerCount,
														hiddenLayerNeuronCount,
														outputsCount );
		assertEquals(inputsCount,            neuralNetwork.getNetworkLayerNeuronCount(0));
		assertEquals(hiddenLayerNeuronCount, neuralNetwork.getNetworkLayerNeuronCount(1));
		assertEquals(outputsCount,           neuralNetwork.getNetworkLayerNeuronCount(2));
	}
	
	@Test
	void testgetNetworkLayerNeuronCountReturnsOneOneSimpleCreate() {
		NeuralNetwork neuralNetwork = new NeuralNetwork();
		assertEquals(1, neuralNetwork.getNetworkLayerNeuronCount(0));
		assertEquals(1, neuralNetwork.getNetworkLayerNeuronCount(1));
		assertEquals(1, neuralNetwork.getNetworkLayerNeuronCount(2));
	}
	
	@Test
	void testNeuralNetworkInputCountOneOnComplexCreate() {
		int inputsCount = 40; 
        int hiddenLayerCount = 1; 
        int hiddenLayerNeuronCount = 120;
        int outputsCount = 37;
		NeuralNetwork neuralNetwork = new NeuralNetwork(inputsCount,
														hiddenLayerCount,
														hiddenLayerNeuronCount,
														outputsCount );
		assertEquals(40,neuralNetwork.getNetworkInputCount());
	}
	
	@Test
	void testNeuralNetworkInputCountOneOnSimpleCreate() {
		int inputsCount = 1; 
        int hiddenLayerCount = 1; 
        int hiddenLayerNeuronCount = 1;
        int outputsCount = 1;
		NeuralNetwork neuralNetwork = new NeuralNetwork(inputsCount,
														hiddenLayerCount,
														hiddenLayerNeuronCount,
														outputsCount );
		assertEquals(1,neuralNetwork.getNetworkInputCount());
	}

	@Test
	void testGetNetworkOutputCountReturnsOutputNeuronCountOnCreate() {
		int inputsCount = 7; 
        int hiddenLayerCount = 3; 
        int hiddenLayerNeuronCount = 17;
        int outputsCount = 17;
		NeuralNetwork neuralNetwork = new NeuralNetwork(inputsCount,
														hiddenLayerCount,
														hiddenLayerNeuronCount,
														outputsCount );
		assertEquals(17,neuralNetwork.getNetworkOutputCount());
	}
	
	@Test
	void testNeuralNetworkOutputCountOneOnSimpleCreate() {
		//if (printAllMessages) System.out.println("testNeuralNetworkOutputCountOneOnSimpleCreate(): START ");
		int inputsCount = 1; 
        int hiddenLayerCount = 1; 
        int hiddenLayerNeuronCount = 1;
        int outputsCount = 1;
		NeuralNetwork neuralNetwork = new NeuralNetwork(inputsCount,
														hiddenLayerCount,
														hiddenLayerNeuronCount,
														outputsCount );
		//System.out.print("testNeuralNetworkOutputCountOneOnSimpleCreate(): triggering error -> ");
		assertEquals(1,neuralNetwork.getNetworkOutputCount());
		//if (printAllMessages) System.out.println("testNeuralNetworkOutputCountOneOnSimpleCreate(): END \n ");
	}
	
	@Test
	void testGetNetworkOutputRetutnsCorrectLength() {
		int inputsCount = 7; 
        int hiddenLayerCount = 3; 
        int hiddenLayerNeuronCount = 17;
        int outputsCount = 17;
		NeuralNetwork neuralNetwork = new NeuralNetwork(inputsCount,
														hiddenLayerCount,
														hiddenLayerNeuronCount,
														outputsCount );
		assertEquals(outputsCount,neuralNetwork.getNetworkOutputs().length);
	}
	
	@Test
	void testNeuronCountOneOnCreate() {
		NeuralNetwork neuralNetwork = new NeuralNetwork(3);
		assertEquals(1, neuralNetwork.getNetworkLayerNeuronCount(0));
		assertEquals(1, neuralNetwork.getNetworkLayerNeuronCount(1));
		assertEquals(1, neuralNetwork.getNetworkLayerNeuronCount(2));
	}

	@Test
	void testNeuronCountAsExpectedOnComplexCreate() {
		int inputsCount = 4; 
        int hiddenLayerCount = 1; 
        int hiddenLayerNeuronCount = 6;
        int outputsCount = 10;
		NeuralNetwork neuralNetwork = new NeuralNetwork(inputsCount,
														hiddenLayerCount,
														hiddenLayerNeuronCount,
														outputsCount );
		assertEquals(4, neuralNetwork.getNetworkLayerNeuronCount(0));
		assertEquals(6, neuralNetwork.getNetworkLayerNeuronCount(1));
		assertEquals(10, neuralNetwork.getNetworkLayerNeuronCount(2));
	}
	
	@Test
	void testNeuronWeightCountOneOnCreate() {
		NeuralNetwork neuralNetwork = new NeuralNetwork(3);
		assertEquals(1, neuralNetwork.getNetworkLayerWeightCount(0));
		assertEquals(1, neuralNetwork.getNetworkLayerWeightCount(1));
		assertEquals(1, neuralNetwork.getNetworkLayerWeightCount(2));
	}

	@Test
	void testNeuronWeightCountAsExpectedOnComplexCreate() {
		int inputsCount = 4; 
        int hiddenLayerCount = 1; 
        int hiddenLayerNeuronCount = 6;
        int outputsCount = 10;
		NeuralNetwork neuralNetwork = new NeuralNetwork(inputsCount,
														hiddenLayerCount,
														hiddenLayerNeuronCount,
														outputsCount );
		assertEquals(1, neuralNetwork.getNetworkLayerWeightCount(0));
		assertEquals(4, neuralNetwork.getNetworkLayerWeightCount(1));
		assertEquals(6, neuralNetwork.getNetworkLayerWeightCount(2));
	}	
	
	@Test
	void testNeuronThresholdAndWeightCountAsExpectedOnComplexCreate() {
		int inputsCount = 4; 
        int hiddenLayerCount = 1; 
        int hiddenLayerNeuronCount = 6;
        int outputsCount = 10;
		NeuralNetwork neuralNetwork = new NeuralNetwork(inputsCount,
														hiddenLayerCount,
														hiddenLayerNeuronCount,
														outputsCount );
		assertEquals(1+1, neuralNetwork.getNetworkLayerThresholdAndWeightsCountForLayer(0));
		assertEquals(4+1, neuralNetwork.getNetworkLayerThresholdAndWeightsCountForLayer(1));
		assertEquals(6+1, neuralNetwork.getNetworkLayerThresholdAndWeightsCountForLayer(2));
	}
	
	
	@Test
	void testGetVerboseFlagReturnsFalseLayersOnSimpleCreate() {
		int inputsCount = 1; 
        int hiddenLayerCount = 1; 
        int hiddenLayerNeuronCount = 1;
        int outputsCount = 1;
		NeuralNetwork neuralNetwork = new NeuralNetwork(inputsCount,
														hiddenLayerCount,
														hiddenLayerNeuronCount,
														outputsCount );
		assertEquals(false, neuralNetwork.getVerboseFlag());
	}
	
	@Test
	void testSetVerboseFlagControlsFlag() {
		NeuralNetwork neuralNetwork = new NeuralNetwork();
		neuralNetwork.setVerboseFlag(true);
		assertEquals(true, neuralNetwork.getVerboseFlag());
	}
	
	@Test
	void testSetEpoch() {
		NeuralNetwork neuralNetwork = new NeuralNetwork();
		neuralNetwork.setEpoch(39);
		assertEquals(39, neuralNetwork.getEpoch());
	}
	
	@Test
	void testGetEpochReturnsZeroLayersOnSimpleCreate() {
		int inputsCount = 1; 
        int hiddenLayerCount = 1; 
        int hiddenLayerNeuronCount = 1;
        int outputsCount = 1;
		NeuralNetwork neuralNetwork = new NeuralNetwork(inputsCount,
														hiddenLayerCount,
														hiddenLayerNeuronCount,
														outputsCount );
		assertEquals(0, neuralNetwork.getEpoch());
	}


	
	// *** Copy will make a complete replica of the original Neuron *** //
	
	@Test
	void testCopiedNeuralNetworkEqualsSelf() {
		NeuralNetwork neuralNetwork = new NeuralNetwork(1,1,1,1);
		NeuralNetwork copiedNetwork = new NeuralNetwork(1);
		copiedNetwork = neuralNetwork.copyNeuralNetwork();
		assertTrue( neuralNetwork.equals(copiedNetwork) );
		assertTrue( copiedNetwork.equals(neuralNetwork) );
		
		NeuralNetwork uninitializedNetwork = new NeuralNetwork(1);
		uninitializedNetwork = copiedNetwork.copyNeuralNetwork();
		assertTrue( copiedNetwork.equals(uninitializedNetwork) );
	}
	
	@Test
	void testCanCopyMinimallyInitializedNetwork() {
		NeuralNetwork neuralNetwork = new NeuralNetwork(1);
		NeuralNetwork copiedNetwork = neuralNetwork.copyNeuralNetwork();
		assertTrue(copiedNetwork.equals(neuralNetwork));
	}
	
	@Test
	void testConfiguredNetworkCanBeCopiedOverSimpleNetwork() {
		NeuralNetwork neuralNetwork = new NeuralNetwork(1,1,1,1);
		NeuralNetwork simpleNeuralNetwork = new NeuralNetwork(1);
		simpleNeuralNetwork = neuralNetwork.copyNeuralNetwork();
		assertTrue( neuralNetwork.equals(simpleNeuralNetwork) );
		
		neuralNetwork = neuralNetwork.adjustNeuralNetwork();
		assertFalse( neuralNetwork.equals(simpleNeuralNetwork) );
	}

	@Test
	void testConfiguredNetworkCanBeCopiedOverUninitializedNetwork() {
		NeuralNetwork neuralNetwork = new NeuralNetwork(1,1,1,1);
		NeuralNetwork simpleNeuralNetwork; // uninitialized
		simpleNeuralNetwork = neuralNetwork.copyNeuralNetwork();
		assertTrue( neuralNetwork.equals(simpleNeuralNetwork) );
	}
	
	// *** Equal properly matches layers error, epoch and flags *** //

	@Test
	void testNeuralNetworkEqualsSelf() {
		NeuralNetwork neuralNetwork = new NeuralNetwork(1,1,1,1);
		assertTrue( neuralNetwork.equals(neuralNetwork) );
	}
	
	@Test
	void testNeuralNetworkDiffernetHiddenLayerCountsNotEqual() {
		NeuralNetwork neuralNetwork = new NeuralNetwork(1,1,1,1);
		NeuralNetwork otherNetwork =  new NeuralNetwork(1,2,1,1);
		assertFalse( neuralNetwork.equals(otherNetwork) );
		
	}
	
	@Test
	void testTwoNetworksWithDifferentOutputCountsNotEqual() {
		NeuralNetwork neuralNetwork = new NeuralNetwork(1,1,1,1);
		NeuralNetwork copiedNetwork = new NeuralNetwork(1,1,1,3);
		assertFalse( neuralNetwork.equals(copiedNetwork) );
		assertFalse( copiedNetwork.equals(neuralNetwork) );
	}
	

	
	@Test
	void testAdjustedNeuralNetworkDoesAffectOriginal() {
		NeuralNetwork neuralNetwork = new NeuralNetwork(1,1,1,1);
		NeuralNetwork originalNetwork = neuralNetwork.copyNeuralNetwork();
		NeuralNetwork adjustedNetwork = neuralNetwork.adjustNeuralNetwork();
		assertFalse( neuralNetwork.equals(adjustedNetwork) );
		assertTrue(originalNetwork.equals(neuralNetwork));;
	}
	
	
	
	// *** SetNeuralNetworkTo will make a complete replica of the original Network Layers *** //
	
	@Test
	void testSetToNetworkEqualsSelf() {
		NeuralNetwork neuralNetwork = new NeuralNetwork(1,1,1,1);
		NeuralNetwork copiedNetwork = new NeuralNetwork(1);
		copiedNetwork.setNeuralNetworkTo(neuralNetwork);
		assertTrue( neuralNetwork.equals(copiedNetwork) );
		assertTrue( copiedNetwork.equals(neuralNetwork) );
		
		NeuralNetwork uninitializedNetwork = new NeuralNetwork(10);
		uninitializedNetwork.setNeuralNetworkTo(copiedNetwork);
		assertTrue( copiedNetwork.equals(uninitializedNetwork) );
		
		// Note that the dynamic error, epoch and verbose flag are not compared
	}
	
	
	@Test
	void testAdjustedNeuronWeightNotEqualToOriginal() {
		NeuralNetwork neuralNetwork = new NeuralNetwork(1,1,1,1);
		NeuralNetwork adjustedNetwork = neuralNetwork.adjustNetworkNeuronWeight(0, 0, 0, 0.1);
		assertFalse( neuralNetwork.equals(adjustedNetwork) );
	}

	
	//  *** Run and Error Testing  *** //
	
	@Test
	void testRunNetwork() {
		int inputsCount = 2; 
        int hiddenLayerCount = 1; 
        int hiddenLayerNeuronCount = 2;
        int outputsCount = 1;
		NeuralNetwork neuralNetwork = new NeuralNetwork(inputsCount,
														hiddenLayerCount,
														hiddenLayerNeuronCount,
														outputsCount );
		double[] inputs = new double[] {2, 3};
		neuralNetwork.runNetwork(inputs);
	}
	
	@Test
	void testRunningThreeNeuronNetworkGeneratedOneOutput() {
		NeuralNetwork neuralNetwork = getNewThreeNeuronNeuralNetwork();
		double[] inputs = new double[] {1.0};
		neuralNetwork.runNetwork(inputs);
		neuralNetwork.runNetwork(inputs);
		neuralNetwork.runNetwork(inputs);
		neuralNetwork.runNetwork(inputs);
		
		System.out.println(neuralNetwork.toString());
		//assertEquals(
		
		//double[] input = new double[] {1.0};
		//gradientNetwork.runNetwork(input);
	}
	
	
		@Test
	void testGetRMSerrorReturnsZeroLayersOnSimpleCreate() {
		int inputsCount = 1; 
        int hiddenLayerCount = 1; 
        int hiddenLayerNeuronCount = 1;
        int outputsCount = 1;
		NeuralNetwork neuralNetwork = new NeuralNetwork(inputsCount,
														hiddenLayerCount,
														hiddenLayerNeuronCount,
														outputsCount );
		assertEquals(0, neuralNetwork.getRMSerror());
	}

	@Test
	void testRMSValueCalculatedCorrectly() {
		int inputsCount = 2; 
        int hiddenLayerCount = 1; 
        int hiddenLayerNeuronCount = 1;
        int outputsCount = 1;
		NeuralNetwork neuralNetwork = new NeuralNetwork(inputsCount,
														hiddenLayerCount,
														hiddenLayerNeuronCount,
														outputsCount );
		int trainingSetLength = TEST_TRAINING_SET.length;
		double[] inputs = new double[TEST_TRAINING_SET[0][0].length];
		inputs = TEST_TRAINING_SET[1][0];
		double result = 0;
		double runSquaresTotal = 0;
		
		neuralNetwork.runNetwork(inputs);
		for(int i = 0; i < trainingSetLength; i++) {
			neuralNetwork.runNetwork(inputs);
			result = neuralNetwork.getNetworkOutputs()[0];
			runSquaresTotal += (result - TEST_TRAINING_SET[i][1][0]) * (result - TEST_TRAINING_SET[i][1][0]);
		}
		double goalRMSerror = Math.sqrt(runSquaresTotal/trainingSetLength);
		assertTrue(goalRMSerror-neuralNetwork.calculateRMSerror(TEST_TRAINING_SET) < 0.001);
	}
	
	@Disabled
	@Test
	void testNeuralNetworkToString() {
		NeuralNetwork neuralNetwork = getNewThreeNeuronNeuralNetwork();
		System.out.println(neuralNetwork.toString());
	}
	
	
	///////////////////////////////
	// *** Support Functions *** //
	///////////////////////////////
	
	NeuralNetwork getNewThreeNeuronNeuralNetwork() {
		int inputsCount = 1;
        int hiddenLayerCount = 1; 
        int hiddenLayerNeuronCount = 1;
        int outputsCount = 1;
        NeuralNetwork neuralNetwork = new GradientDescent(inputsCount, hiddenLayerCount, hiddenLayerNeuronCount, outputsCount);
		double[] weights = new double[] {1.0};
		Neuron2 neuron = new Neuron2(weights, 0, 0);
		neuralNetwork.getNetworkLayers()[0].setNeuronAtIndex(neuron.copyNeuron(), 0);
		neuralNetwork.getNetworkLayers()[1].setNeuronAtIndex(neuron.copyNeuron(), 0);
		neuralNetwork.getNetworkLayers()[2].setNeuronAtIndex(neuron.copyNeuron(), 0);
		return neuralNetwork;
	}

}
