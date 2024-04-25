/*
 * Copyright (C) 2024-2026 Jidcoo(https://github.com/jidcoo).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.jidcoo.opto.lcdb.enhancer.core.parser;

import io.github.jidcoo.opto.lcdb.enhancer.base.LeetcodeInvoker;
import io.github.jidcoo.opto.lcdb.enhancer.base.Require;
import io.github.jidcoo.opto.lcdb.enhancer.core.executor.LeetcodeInvokerFactory;
import io.github.jidcoo.opto.lcdb.enhancer.utils.ContainerCheckUtil;
import io.github.jidcoo.opto.lcdb.enhancer.utils.EnhancerLogUtil;
import io.github.jidcoo.opto.lcdb.enhancer.utils.GsonUtil;

import java.io.*;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>IRMatchInputParserNode is an input parser node.</p>
 *
 * <p>IRMatchInputParserNode is used to parse IR input
 * to parameters that LeetcodeInvoker can accept.
 * </p>
 *
 * @author Jidcoo
 * @see ParameterAcceptor
 * @see InputParserNode
 * @since 1.0
 */
@Require
final class IRMatchInputParserNode extends InputParserNode {

    /**
     * The ParameterAcceptor instance.
     */
    private final ParameterAcceptor parameterAcceptor;

    /**
     * Create an IRMatchInputParserNode instance.
     */
    IRMatchInputParserNode() {
        parameterAcceptor = new ParameterAcceptor();
    }

    /**
     * Get the order of the object.
     *
     * @return the int order of the object.
     */
    @Override
    public int getOrder() {
        return Integer.MAX_VALUE - 1;
    }

    /**
     * Parse input with context.
     * Parse String type input to IR input.
     *
     * @param context the instance parser context.
     * @return the parsed result object.
     * @apiNote You can call the {@link InputParserContext#peekInput()} method
     *          of the context to view the output of the previous node.
     *          But the prerequisite is that the
     *          {@link InputParserContext#getInputStackSize()} method of
     *          the context returns an int greater than 0.
     */
    @Override
    Object parse(InputParserContext context) {
        // Define the boss invoker.
        LeetcodeInvoker bossInvoker = null;
        // Define the boss input.
        List<Object> bossInput = new ArrayList<>();
        // Peek last input.
        List<Object> input = (List<Object>) context.peekInput();
        // Fetch all possible leetcode invokers.
        List<LeetcodeInvoker> leetcodeInvokers = fetchLeetcodeInvokers(context, input.size());
        // Create an invoker-matching tracer map to record the invoker matching detail.
        Map<LeetcodeInvoker, Map<Integer, Stack<ParameterAcceptStrategyTracer>>> matchTracer = new HashMap<>();
        if (!ContainerCheckUtil.isListEmpty(leetcodeInvokers)) {
            // Try to match all possible leetcode invokers.
            for (LeetcodeInvoker leetcodeInvoker : leetcodeInvokers) {
                // Filter out leetcodeInvoker with different numbers of parameters than the input parameters.
                if (leetcodeInvoker.getParameterCount() != input.size()) {
                    continue;
                }
                Map<Integer, Stack<ParameterAcceptStrategyTracer>> invokerMatchTracerMap = new HashMap<>();
                // A list used to record all accepted input object in order.
                List<Object> acceptedInput = new ArrayList<>();
                Parameter[] parameterTypes = leetcodeInvoker.getParameters();
                // Try to match all parameters.
                for (int i = 0; i < parameterTypes.length; i++) {
                    Parameter parameterType = parameterTypes[i];
                    // Copy the input parameter deeply.
                    Object copiedObject = deepCopy(input.get(i));
                    // Try to accept the input parameter.
                    ParameterAcceptResult result = parameterAcceptor.accept(parameterType, copiedObject);
                    // Add cur rejected result tracer to enable cur matching tracer if result is not accepted.
                    if (!result.isAccepted()) {
                        invokerMatchTracerMap.put(i, result.getTracer());
                        break;
                    }
                    // Add accepted object to acceptedInput.
                    acceptedInput.add(result.getObject());
                }
                // Add cur matching tracer to matchTracer if invokerMatchTracerMap is not empty.
                if (!invokerMatchTracerMap.isEmpty()) {
                    matchTracer.put(leetcodeInvoker, invokerMatchTracerMap);
                }
                // When the number of accepted input matches the number of
                // leetcodeInvoker in parameters, we consider this leetcodeInvoker
                // to be the bossInvoker and this accepted input to be the
                // boss Input.
                if (leetcodeInvoker.getParameterCount() == acceptedInput.size()) {
                    // Record the bossInvoker and bossInput.
                    bossInvoker = leetcodeInvoker;
                    bossInput = acceptedInput;
                    break;
                }
            }
        }

        // Log parameter accepting tracer detail after IR-Matching if the bossInvoker is null.
        logDetailAfterIRMatching(bossInvoker, leetcodeInvokers, input, matchTracer);
        context.setTargetInvoker(bossInvoker);
        return (bossInput.stream().toArray(Object[]::new));
    }

    /**
     * Log tracer detail after IR-Matching if the bossInvoker is null.
     *
     * @param bossInvoker the bossInvoker.
     * @param invokers    the invokers list.
     * @param input       the input object.
     * @param matchTracer the match tracer.
     * @since 1.0.1
     */
    private void logDetailAfterIRMatching(LeetcodeInvoker bossInvoker, List<LeetcodeInvoker> invokers,
                                          List<Object> input, Map<LeetcodeInvoker, Map<Integer,
            Stack<ParameterAcceptStrategyTracer>>> matchTracer) {
        if (Objects.isNull(bossInvoker)) {
            if (ContainerCheckUtil.isListEmpty(invokers)) {
                throw new RuntimeException("Cannot find any possible leetcode invoker. Because the candidate leetcode invoker list is empty.");
            }
            StringBuilder logBuffer = new StringBuilder();
            logBuffer.append("[IR-Matching Tracer Error Report Detail Start]\n");
            logBuffer.append("LeetcodeInvokers: " + invokers.size() + ",  IRInputs: " + input.size());
            logBuffer.append("\n<TracersDetail>\n");
            int invokerIdx = 0;
            for (Map.Entry<LeetcodeInvoker, Map<Integer, Stack<ParameterAcceptStrategyTracer>>> methodListEntry :
                    matchTracer.entrySet()) {
                logBuffer.append("LeetcodeInvoker-" + (invokerIdx++));
                logBuffer.append(": ");
                logBuffer.append(methodListEntry.getKey().toGenericString());
                logBuffer.append("\n<Tracers(" + methodListEntry.getValue().size() + ")>\n");
                Map<Integer, Stack<ParameterAcceptStrategyTracer>> stackMap = methodListEntry.getValue();
                for (Map.Entry<Integer, Stack<ParameterAcceptStrategyTracer>> stackEntry : stackMap.entrySet()) {
                    logBuffer.append(" - Index: " + stackEntry.getKey());
                    logBuffer.append(",   Parameter: " + methodListEntry.getKey().getParameters()[stackEntry.getKey()].getName());
                    logBuffer.append(",   Type: " + methodListEntry.getKey().getParameters()[stackEntry.getKey()].getParameterizedType().getTypeName());
                    logBuffer.append(",   Input(" + (input.get(stackEntry.getKey()) == null ? "Null" :
                            input.get(stackEntry.getKey()).getClass().getSimpleName()) + "): " + GsonUtil.toJson(input.get(stackEntry.getKey())));
                    logBuffer.append("\n");
                    Stack<ParameterAcceptStrategyTracer> tracerStack = stackEntry.getValue();
                    while (!tracerStack.empty()) {
                        ParameterAcceptStrategyTracer tracer = tracerStack.pop();
                        logBuffer.append(tracer.toString());
                    }
                }
                if (invokerIdx < invokers.size()) {
                    logBuffer.append("\n");
                }
            }
            logBuffer.append("[IR-Matching Tracer Error Report Detail END]\n");
            EnhancerLogUtil.logE("Cannot match any leetcode invoker for IR-Input: %s\n\n%s", input,
                    logBuffer.toString());
            throw new RuntimeException("Cannot match any leetcode invoker for IR-Input.");
        }
    }

    /**
     * Copy object deeply.
     *
     * @param object the object.
     * @return the copied object.
     */
    private Object deepCopy(Object object) {
        if (Objects.isNull(object)) {
            return null;
        }
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            bis = new ByteArrayInputStream(bos.toByteArray());
            ois = new ObjectInputStream(bis);
            return ois.readObject();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException ignored) {
                }
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    /**
     * Fetch invokers from InputParserContext.
     *
     * @param context              the input parser context.
     * @param invokerParameterSize the limit invoker parameter size
     * @return the invokers list.
     * @since 1.0.1
     */
    private List<LeetcodeInvoker> fetchLeetcodeInvokers(InputParserContext context, int invokerParameterSize) {
        // Here are the new features for version 1.0.0 and later.
        List<LeetcodeInvoker> leetcodeInvokers = new ArrayList<>();
        // At first, we fetch the candidate invokers list from the context.
        if (!ContainerCheckUtil.isListEmpty(context.getCandidateInvokers())) {
            leetcodeInvokers.addAll(context.getCandidateInvokers());
        }
        // Then, aware target class from context's target instance.
        Class<?> targetClass = Objects.nonNull(context.getTargetInstance()) ?
                ((context.getTargetInstance() instanceof Class) ? (Class<?>) context.getTargetInstance() :
                        context.getTargetInstance().getClass()) : null;
        if (Objects.nonNull(targetClass)) {
            // Get all public methods from the targetClass and convert each method to leetcode invoker.
            List<LeetcodeInvoker> publicLeetcodeInvokers = Arrays.stream(targetClass.getDeclaredMethods())
                            .filter(m -> Modifier.isPublic(m.getModifiers()))
                            .filter(m -> !m.isDefault())
                            .filter(m -> m.getParameterCount() == invokerParameterSize)
                            .map(LeetcodeInvokerFactory::getLeetcodeInvoker)
                            .collect(Collectors.toList());
            // Add public leetcode invokers list to leetcodeInvokers.
            leetcodeInvokers.addAll(publicLeetcodeInvokers);
        }
        return leetcodeInvokers;
    }
}
