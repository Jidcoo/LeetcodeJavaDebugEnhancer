package io.github.jidcoo.opto.lcdb.enhancer.core.pipeline;

import io.github.jidcoo.opto.lcdb.enhancer.LeetcodeJavaDebugEnhancer;
import io.github.jidcoo.opto.lcdb.enhancer.base.InputProvider;
import io.github.jidcoo.opto.lcdb.enhancer.base.LeetcodeInvoker;
import io.github.jidcoo.opto.lcdb.enhancer.base.OutputConsumer;
import io.github.jidcoo.opto.lcdb.enhancer.core.executor.LeetcodeExecutorFactory;
import io.github.jidcoo.opto.lcdb.enhancer.core.executor.LeetcodeExecutorProcessor;
import io.github.jidcoo.opto.lcdb.enhancer.core.executor.LeetcodeInvokerFactory;
import io.github.jidcoo.opto.lcdb.enhancer.core.parser.InputParserProcessor;
import io.github.jidcoo.opto.lcdb.enhancer.core.printer.OutputPrinterProcessor;
import io.github.jidcoo.opto.lcdb.enhancer.utils.AssertUtil;
import io.github.jidcoo.opto.lcdb.enhancer.utils.BeanUtil;
import io.github.jidcoo.opto.lcdb.enhancer.utils.ReflectUtil;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

final class LeetcodeJavaDebugEnhancerPipeline extends PipelineRunner {

    /**
     * The enhancer instance of this pipeline.
     */
    private final LeetcodeJavaDebugEnhancer leetcodeJavaDebugEnhancer;

    /**
     * The input provider instance of this pipeline.
     */
    private final InputProvider inputProvider;

    /**
     * The output consumer instance of this pipeline.
     */
    private final OutputConsumer outputConsumer;

    /**
     * The output printer instance of this pipeline.
     */
    private final Object outputPrinter;

    /**
     * The leetcode executor instance of this pipeline.
     */
    private final Object leetcodeExecutor;

    /**
     * The input parser instance of this pipeline.
     */
    private final Object inputParser;

    /**
     * The builtin pipeline runner map.
     */
    private final Map<Integer, PipelineRunner> pipelineRunnerMap;

    /**
     * The pipeline runner invokers from the builtin pipeline runner.
     */
    private final List<LeetcodeInvoker> pipelineRunnerInvokers;

    /**
     * Built-in pipeline runner set package location.
     */
    private static final String BUILT_IN_PIPELINE_RUNNER_PACKAGE = "io.github.jidcoo.opto.lcdb.enhancer.core.pipeline";

    /**
     * Create a LeetcodeJavaDebugEnhancerPipeline instance.
     *
     * @param leetcodeJavaDebugEnhancer the enhancer instance.
     * @param inputProvider             the input provider.
     * @param outputConsumer            the output consumer.
     * @param outputPrinter             the output printer instance.
     * @param leetcodeExecutor          the bootstrap leetcode executor instance.
     * @param inputParser               the input parser instance.
     */
    LeetcodeJavaDebugEnhancerPipeline(LeetcodeJavaDebugEnhancer leetcodeJavaDebugEnhancer,
                                      InputProvider inputProvider, OutputConsumer outputConsumer,
                                      Object outputPrinter, Object leetcodeExecutor, Object inputParser) {
        AssertUtil.nonNull(leetcodeJavaDebugEnhancer, "The enhancer cannot be null.");
        this.leetcodeJavaDebugEnhancer = leetcodeJavaDebugEnhancer;
        AssertUtil.nonNull(inputProvider, "The input provider cannot be null.");
        this.inputProvider = inputProvider;
        AssertUtil.nonNull(outputConsumer, "The output consumer cannot be null.");
        this.outputConsumer = outputConsumer;
        AssertUtil.nonNull(outputPrinter, "The output printer cannot be null.");
        this.outputPrinter = outputPrinter;
        AssertUtil.nonNull(leetcodeExecutor, "The leetcode executor cannot be null.");
        this.leetcodeExecutor = leetcodeExecutor;
        AssertUtil.nonNull(inputParser, "The input parser cannot be null.");
        this.inputParser = inputParser;

        // Build up runner map and invokers list.
        pipelineRunnerMap = new HashMap<>();
        pipelineRunnerInvokers = new ArrayList<>();
        // Collect all builtin pipeline runner instance.
        List<PipelineRunner> builtinPipelineRunners = BeanUtil.collectBeans(PipelineRunner.class,
                BUILT_IN_PIPELINE_RUNNER_PACKAGE,
                klass -> klass.isAnnotationPresent(Resource.class) && ReflectUtil.isExtendsClass(klass,
                        PipelineRunner.class) && !Modifier.isAbstract(klass.getModifiers()),
                ReflectUtil::createInstance).stream().filter(Objects::nonNull).collect(Collectors.toList());
        for (PipelineRunner builtinPipelineRunner : builtinPipelineRunners) {
            for (Method candidateMethod : builtinPipelineRunner.getClass().getDeclaredMethods()) {
                if (!candidateMethod.isAnnotationPresent(Resource.class)) {
                    continue;
                }
                LeetcodeInvoker leetcodeInvoker = LeetcodeInvokerFactory.getLeetcodeInvoker(candidateMethod);
                pipelineRunnerInvokers.add(leetcodeInvoker);
                pipelineRunnerMap.put(leetcodeInvoker.getId(), builtinPipelineRunner);
            }
        }
    }

    /**
     * Run this pipeline.
     */
    void run() {
        try {

            // Now we can happily run the io loop to perform leetcode debugging enhancements.
            while (true) {
                // Provide the next string input from the InputProvider.
                String input = inputProvider.provideNextInput();
                // We need to break this loop when the input indicates end.
                if (inputProvider.isEnd(input)) {
                    break;
                }
                Object bossLeetcodeExecutor = leetcodeExecutor;
                // Parse the string input to input object.
                Object inputObject = InputParserProcessor.process(inputParser, bossLeetcodeExecutor, input);
                // Do some enhancement after input parsing.
                bossLeetcodeExecutor = doEnhanceAfterInputParseProcess(bossLeetcodeExecutor);
                // Execute leetcode target and get the output object.
                Object outputObject = LeetcodeExecutorProcessor.process(bossLeetcodeExecutor, inputObject);
                // Print the output object.
                String output = OutputPrinterProcessor.process(outputPrinter, bossLeetcodeExecutor, outputObject);
                // Consume the next output string to the OutputConsumer.
                outputConsumer.consumeNextOutput(output);
            }

        } finally {
            // Close the input resource finally.
            try {
                inputProvider.close();
            } catch (Exception ignored) {
            }
            // Close the output resource finally.
            try {
                outputConsumer.close();
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * Do enhance after input parse process.
     * In this function, we will make a decision on
     * a suitable leetcode executor as the final
     * leetcode executor.
     *
     * @param bossLeetcodeExecutor the leetcode executor instance.
     * @return the final leetcode executor.
     */
    private Object doEnhanceAfterInputParseProcess(Object bossLeetcodeExecutor) {
        // Aware leetcode invoker from the bossLeetcodeExecutor.
        LeetcodeInvoker leetcodeInvoker = ReflectUtil.getFieldValue("executor", LeetcodeInvoker.class,
                bossLeetcodeExecutor);

        if (!pipelineRunnerMap.containsKey(leetcodeInvoker.getId())) {
            // If this current leetcodeInvoker is not a pipeline runner invoker,
            // then we consider the bossLeetcodeExecutor to be the final leetcode executor.
            // So just return it.
            return bossLeetcodeExecutor;
        }

        // Get pipeline runner instance by invoker id.
        PipelineRunner pipelineRunner = pipelineRunnerMap.get(leetcodeInvoker.getId());
        // Set up the pipelineRunner.
        setupPipelineRunner(pipelineRunner);
        // Create a new leetcode executor as the final leetcode executor.
        Object finalLeetcodeExecutor = LeetcodeExecutorFactory.getLeetcodeExecutor(pipelineRunner, leetcodeInvoker);
        // Set the bossLeetcodeExecutor's invoker to null.
        ReflectUtil.setFieldValue("executor", LeetcodeInvoker.class, null, bossLeetcodeExecutor);
        // Return the final leetcode executor.
        return finalLeetcodeExecutor;
    }

    /**
     * Set up pipeline runner with this current pipeline instance.
     *
     * @param pipelineRunner the pipeline runner.
     */
    private void setupPipelineRunner(PipelineRunner pipelineRunner) {
        // Set cur pipeline instance to the pipelineRunner.
        ReflectUtil.setFieldValue("baseRunner", PipelineRunner.class, this, pipelineRunner);
    }

    /**
     * Get the order of the object.
     *
     * @return the int order of the object.
     */
    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * Get enhancer of this pipeline.
     *
     * @return enhancer.
     */
    protected LeetcodeJavaDebugEnhancer getEnhancer() {
        return leetcodeJavaDebugEnhancer;
    }

    /**
     * Get input provider of this pipeline.
     *
     * @return input provider.
     */
    protected InputProvider getInputProvider() {
        return inputProvider;
    }

    /**
     * Get output consumer of this pipeline.
     *
     * @return output consumer.
     */
    protected OutputConsumer getOutputConsumer() {
        return outputConsumer;
    }

    /**
     * Get output printer of this pipeline.
     *
     * @return output printer instance.
     */
    protected Object getOutputPrinter() {
        return outputPrinter;
    }

    /**
     * Get leetcode executor of this pipeline.
     *
     * @return leetcode executor instance.
     */
    protected Object getLeetcodeExecutor() {
        return leetcodeExecutor;
    }

    /**
     * Get input parser of this pipeline runner.
     *
     * @return input parser instance.
     */
    protected Object getInputParser() {
        return inputParser;
    }
}
