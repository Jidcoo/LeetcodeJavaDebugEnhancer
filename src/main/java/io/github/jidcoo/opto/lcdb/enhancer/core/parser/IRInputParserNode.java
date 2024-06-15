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

import io.github.jidcoo.opto.lcdb.enhancer.base.Require;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>IRInputParserNode is an input parser node.</p>
 *
 * <p>IRInputParserNode is the highest priority node, which
 * is first scheduled to parse String type input
 * to IR input.</p>
 *
 * @author Jidcoo
 * @see InputParserNode
 * @since 1.0
 */
@Require
final class IRInputParserNode extends InputParserNode {

    /**
     * String end token.
     */
    private static final char STRING_END_TOKEN = '\0';

    /**
     * Null type token.
     */
    private static final String NULL_TYPE_TOKEN = "null";

    /**
     * Boolean type true token.
     */
    private static final String BOOLEAN_TYPE_TRUE_TOKEN = "true";

    /**
     * Boolean type false token.
     */
    private static final String BOOLEAN_TYPE_FALSE_TOKEN = "false";

    /**
     * Number double type dot token.
     */
    private static final char NUMBER_DOUBLE_TYPE_DOT_TOKEN = '.';

    /**
     * Number positive symbol token.
     */
    private static final char NUMBER_POSITIVE_SYMBOL_TOKEN = '+';

    /**
     * Number negative symbol token.
     */
    private static final char NUMBER_NEGATIVE_SYMBOL_TOKEN = '-';

    /**
     * String begin token.
     */
    private static final char STRING_BEGIN_TOKEN = '"';

    /**
     * String finish token.
     */
    private static final char STRING_FINISH_TOKEN = '"';

    /**
     * Array begin token.
     */
    private static final char ARRAY_BEGIN_TOKEN = '[';

    /**
     * Array finish token.
     */
    private static final char ARRAY_FINISH_TOKEN = ']';

    /**
     * Array elements separator token.
     */
    private static final char ARRAY_ELEMENTS_SEPARATOR_TOKEN = ',';

    /**
     * Get the order of the object.
     *
     * @return the int order of the object.
     */
    @Override
    public int getOrder() {
        // IRInputParserNode is the highest priority node, which
        // is first scheduled to parse String type input to IR input.
        return Integer.MAX_VALUE;
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
        // Get input object from context.
        Object inputObject = context.peekInput();
        // IRInputParserNode only accept String type input.
        if (!(inputObject instanceof String)) {
            return context.popInput();
        }
        // Get string type input from context.
        String input = (String) inputObject;
        // Initialize parsing index.
        int[] parseIndex = new int[1];
        // Initialize parsing result list.
        List<Object> objects = new ArrayList<>();

        // Parse each element.
        while (parseIndex[0] < input.length()) {
            Object result = parseInput(parseIndex, input);
            // Save result to result list.
            objects.add(result);
            skipWhiteSpace(parseIndex, input);
            if (peekChar(parseIndex[0], input) == ARRAY_ELEMENTS_SEPARATOR_TOKEN) {
                consumeChar(parseIndex, input, ARRAY_ELEMENTS_SEPARATOR_TOKEN);
                skipWhiteSpace(parseIndex, input);
            }
        }

        return objects;
    }

    /**
     * Look at the characters at the specified position
     * in the string.
     *
     * @param index the specified position.
     * @param input the string input.
     * @return the char at the index position.
     */
    private static char peekChar(int index, String input) {
        return index < input.length() ? input.charAt(index) : STRING_END_TOKEN;
    }

    /**
     * Conform the next char is the expected char
     * and consume it.
     *
     * @param index        the specified position.
     * @param input        the string input.
     * @param expectedChar the expected char.
     */
    private static void consumeChar(int[] index, String input, char expectedChar) {
        char nextChar;
        if ((nextChar = peekChar(index[0], input)) == expectedChar) {
            index[0]++;
            return;
        }
        throw new RuntimeException("Unexpected character. Expected '" + expectedChar + "', but found '" + nextChar +
                "'.");
    }

    /**
     * Skip the white space from the specified position
     * in the input.
     *
     * @param index the specified position.
     * @param input the string input.
     */
    private static void skipWhiteSpace(int[] index, String input) {
        while (index[0] < input.length() && Character.isWhitespace(peekChar(index[0], input))) {
            index[0]++;
        }
    }

    /**
     * Parse boolean type data.
     *
     * @param index the specified position.
     * @param input the string input.
     * @return boolean type data.
     */
    private static boolean parseBooleanType(int[] index, String input) {
        if (input.startsWith(BOOLEAN_TYPE_TRUE_TOKEN, index[0])) {
            index[0] += BOOLEAN_TYPE_TRUE_TOKEN.length();
            return true;
        } else {
            // Only the "false" case will we reach here!
            index[0] += BOOLEAN_TYPE_FALSE_TOKEN.length();
            return false;
        }
    }

    /**
     * Parse Number type data. The supported number types are
     * {@link Integer}, {@link Long} and {@link Double}.
     *
     * @param index the specified position.
     * @param input the string input.
     * @return number type data.
     */
    private static Object parseNumberType(int[] index, String input) {
        StringBuilder stringBuilder = new StringBuilder();
        char nextChar;
        boolean isDoubleType = false;
        while (index[0] < input.length() && (Character.isDigit((nextChar = peekChar(index[0], input))) || nextChar == NUMBER_DOUBLE_TYPE_DOT_TOKEN || nextChar == NUMBER_POSITIVE_SYMBOL_TOKEN || nextChar == NUMBER_NEGATIVE_SYMBOL_TOKEN)) {
            stringBuilder.append(nextChar);
            index[0]++;
            if (nextChar == NUMBER_DOUBLE_TYPE_DOT_TOKEN) {
                isDoubleType = true;
            }
        }
        String numberString = stringBuilder.toString();
        if (isDoubleType) {
            return Double.parseDouble(numberString);
        } else {
            try {
                return Integer.parseInt(numberString);
            } catch (NumberFormatException numberFormatException) {
                // If the number exceeds the access limit of int, attempt to resolve to long.
                return Long.parseLong(numberString);
            }
        }
    }

    /**
     * Parse String type data.
     *
     * @param index the specified position.
     * @param input the string input.
     * @return string type data.
     */
    private static String parseStringType(int[] index, String input) {
        consumeChar(index, input, STRING_BEGIN_TOKEN);
        StringBuilder stringBuilder = new StringBuilder();
        char nextChar;
        while ((nextChar = peekChar(index[0], input)) != STRING_FINISH_TOKEN) {
            stringBuilder.append(nextChar);
            index[0]++;
        }
        consumeChar(index, input, STRING_FINISH_TOKEN);
        return stringBuilder.toString();
    }

    /**
     * Parse Array type data.
     *
     * @param index the specified position.
     * @param input the string input.
     * @return array type data.
     */
    private static List<Object> parseArrayType(int[] index, String input) {
        consumeChar(index, input, ARRAY_BEGIN_TOKEN);
        List<Object> array = new ArrayList<>();
        skipWhiteSpace(index, input);
        while (peekChar(index[0], input) != ARRAY_FINISH_TOKEN) {
            array.add(parseInput(index, input));
            skipWhiteSpace(index, input);
            if (peekChar(index[0], input) == ARRAY_ELEMENTS_SEPARATOR_TOKEN) {
                consumeChar(index, input, ARRAY_ELEMENTS_SEPARATOR_TOKEN);
                skipWhiteSpace(index, input);
            }
        }
        consumeChar(index, input, ARRAY_FINISH_TOKEN);
        return array;
    }

    /**
     * Parse input to object.
     *
     * @param index the specified position.
     * @param input the string input.
     * @return the object.
     */
    private static Object parseInput(int[] index, String input) {
        skipWhiteSpace(index, input);
        char nextChar = peekChar(index[0], input);
        if (nextChar == ARRAY_BEGIN_TOKEN) {
            return parseArrayType(index, input);
        } else if (nextChar == STRING_BEGIN_TOKEN) {
            return parseStringType(index, input);
        } else if (Character.isDigit(nextChar) || nextChar == NUMBER_POSITIVE_SYMBOL_TOKEN || nextChar == NUMBER_NEGATIVE_SYMBOL_TOKEN) {
            return parseNumberType(index, input);
        } else if (input.startsWith(BOOLEAN_TYPE_TRUE_TOKEN, index[0]) || input.startsWith(BOOLEAN_TYPE_FALSE_TOKEN,
                index[0])) {
            return parseBooleanType(index, input);
        } else if (input.startsWith(NULL_TYPE_TOKEN, index[0])) {
            // Forward index to skip "null".
            index[0] += NULL_TYPE_TOKEN.length();
            return null;
        } else {
            throw new RuntimeException("Unexpected character: " + nextChar);
        }
    }
}
