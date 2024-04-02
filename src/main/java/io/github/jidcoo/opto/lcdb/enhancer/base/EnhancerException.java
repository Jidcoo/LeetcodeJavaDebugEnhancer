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

package io.github.jidcoo.opto.lcdb.enhancer.base;

/**
 * EnhancerException is an exception class that
 * can be thrown during the normal operation of the
 * {@code LeetcodeJavaDebugEnhancer}.
 *
 * @author Jidcoo
 * @since 1.0
 */
public class EnhancerException extends RuntimeException {

    /**
     * Constructs a new enhancer exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param msg the detail message. The detail message is saved for
     *            later retrieval by the {@link #getMessage()} method.
     */
    public EnhancerException(String msg) {
        super(msg);
    }

    /**
     * Constructs a new enhancer exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param msg   the detail message (which is saved for later retrieval
     *              by the {@link #getMessage()} method).
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link #getCause()} method).  (A <tt>null</tt> value is
     *              permitted, and indicates that the cause is nonexistent or
     *              unknown.)
     */
    public EnhancerException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * Constructs a new enhancer exception with the specified cause and a
     * detail message of <tt>(cause==null ? null : cause.toString())</tt>
     * (which typically contains the class and detail message of
     * <tt>cause</tt>).  This constructor is useful for runtime exceptions
     * that are little more than wrappers for other throwables.
     *
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link #getCause()} method).  (A <tt>null</tt> value is
     *              permitted, and indicates that the cause is nonexistent or
     *              unknown.)
     */
    public EnhancerException(Throwable cause) {
        super(cause);
    }
}
