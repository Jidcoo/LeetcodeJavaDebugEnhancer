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

package io.github.jidcoo.opto.lcdb.enhancer;

import io.github.jidcoo.opto.lcdb.enhancer.base.EnhancerException;
import io.github.jidcoo.opto.lcdb.enhancer.utils.StringUtil;

/**
 * LeetcodeJavaDebugEnhancer starter.
 *
 * @author Jidcoo
 * @since 1.0.3
 */
public abstract class LJDEStarter implements LeetcodeJavaDebugEnhancer {

    public static void main(String[] args) {
        String AT = obtainATFromStartupArguments(args);
        if (StringUtil.isBlank(AT)) {
            // Depend on java runtime feature.
            AT = System.getProperty("sun.java.command");
        }
        if (LJDEStarter.class.getName().equals(AT)) {
            throw new EnhancerException("Cannot enhance from the abstract LJDEStarter.");
        }
        LeetcodeJavaDebugEnhancer.run(AT);
    }

    private static String obtainATFromStartupArguments(String[] args) {
        for (String arg : args) {
            if (!StringUtil.isBlank(arg)) {
                return arg;
            }
        }
        return null;
    }
}
