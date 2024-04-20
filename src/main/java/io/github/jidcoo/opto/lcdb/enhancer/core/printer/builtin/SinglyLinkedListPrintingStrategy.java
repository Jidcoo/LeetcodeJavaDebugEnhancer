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

package io.github.jidcoo.opto.lcdb.enhancer.core.printer.builtin;

import io.github.jidcoo.opto.lcdb.enhancer.base.BasePrintingStrategy;
import io.github.jidcoo.opto.lcdb.enhancer.base.Require;
import io.github.jidcoo.opto.lcdb.enhancer.base.Strategizable;
import io.github.jidcoo.opto.lcdb.enhancer.base.struct.ListNode;

import java.util.Map;
import java.util.Set;

/**
 * <p>SinglyLinkedListPrintingStrategy is an output
 * printing strategy used to print {@link ListNode}
 * type output.</p>
 *
 * @author Jidcoo
 * @see BasePrintingStrategy
 * @see ListNode
 * @since 1.0
 */
@Require
public final class SinglyLinkedListPrintingStrategy extends BasePrintingStrategy<ListNode> {

    /**
     * Print the output.
     *
     * @param node          the output.
     * @param strategiesMap the strategies map that can be used during this printing process.
     *                      <p>The key is the output object class to which this BasePrintingStrategy
     *                      applies. The value is a set of strategy with the same accepted type.
     *                      And the set is sorted the priority of {@link Strategizable} based on
     *                      {@code getOrder()}.
     *                      </p>
     * @return the printed string content.
     */
    @Override
    protected String printOutput(ListNode node, Map<Class<?>, Set<BasePrintingStrategy<?>>> strategiesMap) {
        StringBuilder stringBuilder = new StringBuilder("[");
        ListNode pointer = node;
        while (pointer != null) {
            stringBuilder.append(pointer.val);
            stringBuilder.append(",");
            pointer = pointer.next;
        }
        if (stringBuilder.charAt(stringBuilder.length() - 1) == ',')
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append("]");
        return stringBuilder.toString();
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
     * Get the acceptable type.
     *
     * @return the acceptable type.
     */
    @Override
    public Class<? extends ListNode> getAcceptableType() {
        return ListNode.class;
    }
}
