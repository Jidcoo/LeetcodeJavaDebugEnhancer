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
import io.github.jidcoo.opto.lcdb.enhancer.base.struct.TreeNode;
import io.github.jidcoo.opto.lcdb.enhancer.utils.AssertUtil;

import java.lang.reflect.Parameter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>BinaryTreeParameterAcceptStrategy is an parameter
 * acceptance strategy used to accept {@link TreeNode} type
 * and accept it to a binary tree as {@link TreeNode}
 * instance.</p>
 *
 * @author Jidcoo
 * @see BaseParameterAcceptStrategy
 * @see TreeNode
 * @since 1.0
 */
@Require
public final class BinaryTreeParameterAcceptStrategy extends BaseParameterAcceptStrategy<TreeNode> {

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
    protected TreeNode acceptParameter(Object object, Parameter type,
                                       Map<Class<?>, Set<BaseParameterAcceptStrategy<?>>> strategiesMap) throws Throwable {
        AssertUtil.nonNull(object, "The object cannot be null.");
        AssertUtil.isTrue((object instanceof List), "The object is not a List.");
        List<Integer> originIntegerList = ((List<Integer>) object);
        if (originIntegerList.isEmpty()) {
            return null;
        }
        Function<Integer, TreeNode> treeNodeCreator = (Integer val) -> {
            if (Objects.isNull(val)) {
                return null;
            }
            return new TreeNode(val);
        };
        List<TreeNode> treeNodeList = originIntegerList.stream()
                .map(val -> treeNodeCreator.apply(val))
                .collect(Collectors.toList());
        Queue<TreeNode> nodeQueue = new ArrayDeque<>();
        int treeNodeIdx = 0;
        int treeNodeSize = treeNodeList.size();
        Function<Integer, TreeNode> treeNodeGetter = (Integer idx) -> {
            if (idx < treeNodeSize) {
                return treeNodeList.get(idx);
            }
            return null;
        };
        nodeQueue.offer(treeNodeList.get(treeNodeIdx++));
        while (!nodeQueue.isEmpty() && treeNodeIdx < treeNodeSize) {
            int nodeQueueSize = nodeQueue.size();
            while (nodeQueueSize > 0 && treeNodeIdx < treeNodeSize) {
                TreeNode curRootNode = nodeQueue.poll();
                --nodeQueueSize;
                curRootNode.left = treeNodeGetter.apply(treeNodeIdx++);
                curRootNode.right = treeNodeGetter.apply(treeNodeIdx++);
                if (Objects.nonNull(curRootNode.left)) {
                    nodeQueue.offer(curRootNode.left);
                }
                if (Objects.nonNull(curRootNode.right)) {
                    nodeQueue.offer(curRootNode.right);
                }
            }
        }
        return treeNodeList.get(0);
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
    public Class<? extends TreeNode> getAcceptableType() {
        return TreeNode.class;
    }
}
