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

import java.util.Stack;

/**
 * <p>ParameterAcceptResults is a result class
 * that represents whether a parameter object
 * is accepted.</p>
 *
 * <p>When the result get from {@link ParameterAcceptResult#isAccepted()}
 * is true, it indicates that the parameter object
 * has been accepted, and you can obtain the
 * accepted object by {@link ParameterAcceptResult#getObject()}.
 * </p>
 *
 * <p>When the result get from {@link ParameterAcceptResult#isAccepted()}
 * is false, it indicates that the parameter object
 * cannot be accepted. You can obtain the details of
 * the exception that occurred during the parameter
 * acceptance process by {@link ParameterAcceptResult#getTracer()}}.
 * </p>
 *
 * @author Jidcoo
 * @see ParameterAcceptStrategyTracer
 * @see ParameterAcceptor
 * @since 1.0
 */
final class ParameterAcceptResult {

    /**
     * Parameter accept result code.
     */
    private enum Code {
        /**
         * PASS indicates that the parameter is accepted.
         */
        PASS,

        /**
         * REJECT indicates that the parameter is rejected.
         */
        REJECT
    }

    /**
     * The accepted object.
     * <p>When the current result {@link Code} is {@link Code#PASS},
     * this object must be assigned a value of accepted data.</p>
     */
    private final Object object;

    /**
     * Parameter object acceptance exception tracker.
     */
    private final Stack<ParameterAcceptStrategyTracer> tracer;

    /**
     * Parameter accept result code.
     */
    private final Code resultCode;

    /**
     * Create a ParameterAcceptResult instance.
     *
     * @param object the accepted object.
     * @param code   the result code.
     * @param tracer the parameter object acceptance exception tracker.
     */
    private ParameterAcceptResult(Object object, Code code, Stack<ParameterAcceptStrategyTracer> tracer) {
        this.object = object;
        this.resultCode = code;
        this.tracer = tracer;
    }

    /**
     * Get the accepted object.
     *
     * @return the accepted object.
     */
    public Object getObject() {
        return object;
    }

    /**
     * Get the result state.
     *
     * @return true if the result code is {@link Code#PASS}.
     */
    public boolean isAccepted() {
        return Code.PASS == this.resultCode;
    }

    /**
     * Get the parameter object acceptance exception tracker.
     *
     * @return the tracker stack.
     */
    public Stack<ParameterAcceptStrategyTracer> getTracer() {
        return tracer;
    }

    /**
     * Create a accepted result.
     *
     * @param object the accepted object.
     * @return the accepted result.
     */
    static ParameterAcceptResult accept(Object object) {
        return new ParameterAcceptResult(object, Code.PASS, null);
    }

    /**
     * Create a rejected result.
     *
     * @param object      the object.
     * @param tracerStack the parameter object acceptance exception tracker stack.
     * @return the rejected result.
     */
    static ParameterAcceptResult reject(Object object, Stack<ParameterAcceptStrategyTracer> tracerStack) {
        return new ParameterAcceptResult(object, Code.REJECT, tracerStack);
    }
}
