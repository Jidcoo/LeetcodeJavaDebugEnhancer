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

import io.github.jidcoo.opto.lcdb.enhancer.base.BasePrintingStrategy;
import io.github.jidcoo.opto.lcdb.enhancer.base.Strategizable;
import io.github.jidcoo.opto.lcdb.enhancer.utils.AssertUtil;
import io.github.jidcoo.opto.lcdb.enhancer.utils.GsonUtil;
import io.github.jidcoo.opto.lcdb.enhancer.base.Order;

import javax.lang.model.type.NullType;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * <p>OutputPrinter is an output printer.</p>
 *
 * <p>OutputPrinter performs appropriate printing of
 * output objects based on built-in printing strategies
 * and external printing strategies by
 * {@link #print(Object, Class)}.
 * </p>
 *
 * <p>And also OutputPrinter is a great {@link BasePrintingStrategy}
 * that it can print the {@link Object}, the {@link Void}
 * and the {@link NullType} value.
 * </p>
 *
 * @author Jidcoo
 * @see BasePrintingStrategy
 * @since 1.0
 */
final class OutputPrinter extends BasePrintingStrategy<Object> {

    /**
     * Store all available PrintingStrategy objects.
     *
     * <p>The key is the output object class to which
     * this PrintingStrategy applies.
     * </p>
     *
     * <p>The value is a set of strategy with the same accepted type.
     * And the set is sorted the priority of strategy based on
     * {@code getOrder()}.
     * </p>
     */
    private final Map<Class<?>, Set<BasePrintingStrategy<?>>> printingStrategyMap;

    /**
     * Create a OutputPrinter instance.
     *
     * @param printingStrategyList the available PrintingStrategy list.
     */
    OutputPrinter(List<BasePrintingStrategy> printingStrategyList) {
        this.printingStrategyMap = new HashMap<>();
        // OutputPrinter is also a PrintingStrategy class, so add it to the printingStrategyList.
        printingStrategyList.add(this);
        // Filter out all null object.
        printingStrategyList = printingStrategyList.stream().filter(Objects::nonNull).collect(Collectors.toList());
        // Define the PrintingStrategy Comparator.
        Comparator<Order> printingStrategyComparator = Comparator.comparingInt(Order::getOrder).reversed();
        // Wrap the printingStrategyMap build process as a BiConsumer.
        BiConsumer<Class<?>, BasePrintingStrategy<?>> mapBuilder = (Class<?> clazz,
                                                                    BasePrintingStrategy<?> printingStrategy) -> {
            AssertUtil.nonNull(clazz, "The accepted type of the " + printingStrategy + " cannot be null.");
            // Get the strategySet by clazz.
            Set<BasePrintingStrategy<?>> strategySet = printingStrategyMap.computeIfAbsent(clazz,
                    key -> new TreeSet<>(printingStrategyComparator));
            // Add the printingStrategy to the set.
            strategySet.add(printingStrategy);
        };
        // Using printingStrategyList to build printingStrategyMap.
        for (BasePrintingStrategy<?> printingStrategy : printingStrategyList) {
            mapBuilder.accept(printingStrategy.getAcceptableType(), printingStrategy);
        }
        // Make OutputPrinter can do the most great things. HaHaHa...
        Arrays.asList(Object.class, NullType.class, Void.class).forEach(type -> mapBuilder.accept(type, this));
    }

    /**
     * Print the output by appropriate strategies.
     *
     * @param output     the output instance.
     * @param outputType the output type.
     * @return the printed content.
     */
    String print(Object output, Class outputType) {
        // Find the strategy set for the output.
        Set<BasePrintingStrategy<?>> strategySet = findStrategySet(Objects.nonNull(outputType) ? outputType : output,
                printingStrategyMap);
        Throwable lastThrowable = null;
        for (BasePrintingStrategy<?> printingStrategy : strategySet) {
            try {
                return printingStrategy.accept(null, output, printingStrategyMap);
            } catch (Throwable e) {
                lastThrowable = e;
            }
        }
        throw new RuntimeException("Cannot print output :" + output, lastThrowable);
    }

    /**
     * Get the order of the object.
     *
     * @return the int order of the object.
     */
    @Override
    public int getOrder() {
        // The priority of this strategy is the lowest.
        return Integer.MIN_VALUE;
    }

    /**
     * Get the object type supported by this strategy for printing.
     *
     * @return the object type supported by this strategy for printing.
     */
    @Override
    public Class<?> getAcceptableType() {
        return Object.class;
    }

    /**
     * Print the output.
     *
     * @param outputObject  the output object.
     * @param strategiesMap the strategies map that can be used during this printing process.
     *                      <p>The key is the output object class to which this BasePrintingStrategy
     *                      applies. The value is a set of strategy with the same accepted type.
     *                      And the set is sorted the priority of {@link Strategizable} based on
     *                      {@code getOrder()}.
     *                      </p>
     * @return the printed string content.
     */
    @Override
    protected String printOutput(Object outputObject, Map<Class<?>, Set<BasePrintingStrategy<?>>> strategiesMap) {
        // Roughly print this object based on json style.
        // There is no other way here.
        // But this is the most stable method. Right?!!
        return GsonUtil.toJson(outputObject);
    }
}
