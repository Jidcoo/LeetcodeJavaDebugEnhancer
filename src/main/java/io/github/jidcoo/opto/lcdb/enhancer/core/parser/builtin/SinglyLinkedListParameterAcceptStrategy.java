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

package io.github.jidcoo.opto.lcdb.enhancer.core.parser.builtin;

import io.github.jidcoo.opto.lcdb.enhancer.base.BaseParameterAcceptStrategy;
import io.github.jidcoo.opto.lcdb.enhancer.base.Require;
import io.github.jidcoo.opto.lcdb.enhancer.base.Strategizable;
import io.github.jidcoo.opto.lcdb.enhancer.base.struct.ListNode;
import io.github.jidcoo.opto.lcdb.enhancer.utils.AssertUtil;

import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>SinglyLinkedListParameterAcceptStrategy is an parameter
 * acceptance strategy used to accept {@link ListNode} type
 * and accept it to a single linked list as {@link ListNode}
 * instance.</p>
 *
 * @author Jidcoo
 * @see BaseParameterAcceptStrategy
 * @see ListNode
 * @since 1.0
 */
@Require
public final class SinglyLinkedListParameterAcceptStrategy extends BaseParameterAcceptStrategy<ListNode> {

    /**
     * Accept the object.
     *
     * @param object        the object.
     * @param type          the parameter type.
     * @param strategiesMap the strategies map that can be used during this accepting process.
     *                      <p>The key is the output object class to which this BaseParameterAcceptStrategy
     *                      applies. The value is a set of strategy with the same accepted type.
     *                      And the set is sorted the priority of {@link Strategizable} based on
     *                      {@code getOrder()}.
     *                      </p>
     * @return the accepted parameter.
     */
    @Override
    protected ListNode acceptParameter(Object object, Parameter type,
                                       Map<Class<?>, Set<BaseParameterAcceptStrategy<?>>> strategiesMap) throws Throwable {
        AssertUtil.nonNull(object, "The object cannot be null.");
        AssertUtil.isTrue((object instanceof List), "The object is not a List.");
        List<Integer> originIntegerList = ((List<Integer>) object);
        ListNode header = null;
        ListNode last = null;
        for (int i = 0; i < originIntegerList.size(); i++) {
            Integer val = originIntegerList.get(i);
            ListNode node = new ListNode(val);
            if (header == null) header = node;
            if (last != null) last.next = node;
            last = node;
        }
        return header;
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
