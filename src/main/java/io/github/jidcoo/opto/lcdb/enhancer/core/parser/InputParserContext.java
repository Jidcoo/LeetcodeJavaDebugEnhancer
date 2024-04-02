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

import java.lang.reflect.Method;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * <p>InputParserContext is an input parse context
 * used to hold the necessary intermediate products
 * during the execution of the input parser.
 * </p>
 *
 * @author Jidcoo
 * @since 1.0
 */
final class InputParserContext {

    /**
     * The target instance used for debug.
     */
    private final Object targetInstance;

    /**
     * The input stack used for debug.
     */
    private final Stack<Object> inputStack;

    /**
     * The target method used for debug.
     */
    private Method targetMethod;

    /**
     * Create a InputParserContext instance.
     *
     * @param targetInstance the target instance used for debug.
     * @param input          the string input used for debug.
     * @param targetMethod   the target method used for debug.
     */
    InputParserContext(Object targetInstance, String input, Method targetMethod) {
        this.targetInstance = targetInstance;
        this.inputStack = new Stack<>();
        // Add the first input to the inputStack
        this.inputStack.push(input);
        this.targetMethod = targetMethod;
    }


    /**
     * Get the target instance used for debug.
     *
     * @return the target instance used for debug.
     */
    Object getTargetInstance() {
        return targetInstance;
    }

    /**
     * Push an input into the top of the input stack from this context.
     *
     * @param input the input.
     * @return the input.
     */
    <T> T pushInput(T input) {
        return (T) this.inputStack.push(input);
    }

    /**
     * Look at the input at the top of the input stack
     * from this context without removing it from
     * the stack.
     *
     * @return the input at the top of the input stack from this context.
     * @throws EmptyStackException if the input stack from this context is empty.
     */

    Object peekInput() {
        return this.inputStack.peek();
    }


    /**
     * Remove the input at the top of the input stack
     * from this context and return it.
     *
     * @return the input at the top of the input stack from this context.
     * @throws EmptyStackException if the input stack from this context is empty.
     */
    Object popInput() {
        return this.inputStack.pop();
    }

    /**
     * Return the size of the stack from this context.
     *
     * @return the size of the stack from this context.
     */
    int getInputStackSize() {
        return this.inputStack.size();
    }

    /**
     * Get the target method used for debug.
     *
     * @return the target method used for debug.
     */
    Method getTargetMethod() {
        return targetMethod;
    }

    /**
     * Set the target method used for debug.
     *
     * @param targetMethod the target method used for debug.
     */
    void setTargetMethod(Method targetMethod) {
        this.targetMethod = targetMethod;
    }
}
