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

package io.github.jidcoo.opto.lcdb.enhancer.core.printer;

import io.github.jidcoo.opto.lcdb.enhancer.utils.AssertUtil;
import io.github.jidcoo.opto.lcdb.enhancer.utils.ReflectUtil;

/**
 * <p>OutputPrinterProcessor is a publicly available
 * OutputPrinter processor. It has used a proxy to
 * access {@link OutputPrinter}.</p>
 *
 * <p>The main {@link #process(Object, Object, Object)} method
 * of OutputPrinterProcessor is to proxy external callers
 * to execute {@link OutputPrinter}.
 * </p>
 *
 * @author Jidcoo
 * @since 1.0
 */
public final class OutputPrinterProcessor {

    /**
     * Do real print with OutputPrinter instance and Output instance.
     *
     * @param printer      the OutputPrinter instance.
     * @param executor     the LeetcodeExecutor instance.
     * @param outputObject the output object.
     * @return the printed output string.
     */
    public static String process(Object printer, Object executor, Object outputObject) {
        AssertUtil.nonNull(printer, "The printer cannot be null.");
        AssertUtil.isTrue((printer instanceof OutputPrinter), "The printer is not a OutputPrinter.");
        AssertUtil.nonNull(printer, "The executor cannot be null.");
        // Get output type from LeetcodeExecutor.
        Class invokerResponseType = ReflectUtil.getFieldValue("invokerResponseType", Class.class, executor);
        // Do real print and return the printed output string.
        String printedContent = ((OutputPrinter) (printer)).print(outputObject, invokerResponseType);
        return printedContent;
    }
}
