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

package io.github.jidcoo.opto.lcdb.enhancer.utils;

import java.io.PrintStream;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A common logger util for log output.
 *
 * @author Jidcoo
 * @since 1.0
 */
public class EnhancerLogUtil {

    /**
     * Log msg to console.
     *
     * @param level log level.
     * @param msg   message.
     */
    private static void log(Level level, String msg) {
        if (Logger.getGlobal().isLoggable(level)) {
            PrintStream printStream = System.out;
            if (Level.SEVERE.equals(level)) {
                printStream = System.err;
            }
            printStream.println("[" + level + "] " + msg);
            printStream.flush();
        }
    }

    /**
     * Log ERROR level msg to console.
     *
     * @param format message format.
     * @param args   message args.
     */
    public static void logE(String format, Object... args) {
        log(Level.SEVERE, String.format(format, args));
    }

    /**
     * Log INFO level msg to console.
     *
     * @param format message format.
     * @param args   message args.
     */
    public static void logI(String format, Object... args) {
        log(Level.INFO, String.format(format, args));
    }

    /**
     * Log WARN level msg to console.
     *
     * @param format message format.
     * @param args   message args.
     */
    public static void logW(String format, Object... args) {
        log(Level.WARNING, String.format(format, args));
    }

    /**
     * Set log level.
     *
     * @param level log level.
     */
    public static void setLogLevel(Level level) {
        if (Objects.nonNull(level) && level.intValue() >= Level.INFO.intValue()) {
            Logger.getGlobal().setLevel(level);
        }
    }
}
