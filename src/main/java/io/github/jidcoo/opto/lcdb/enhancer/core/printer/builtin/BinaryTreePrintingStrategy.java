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
import io.github.jidcoo.opto.lcdb.enhancer.base.struct.TreeNode;

import java.util.*;

/**
 * <p>BinaryTreePrintingStrategy is an output
 * printing strategy used to print {@link TreeNode}
 * type output.</p>
 *
 * @author Jidcoo
 * @see BasePrintingStrategy
 * @see TreeNode
 * @since 1.0
 */
@Require
public final class BinaryTreePrintingStrategy extends BasePrintingStrategy<TreeNode> {

    /**
     * Print the output.
     *
     * @param treeNode      the output.
     * @param strategiesMap the strategies map that can be used during this printing process.
     *                      <p>The key is the output object class to which this BasePrintingStrategy
     *                      applies. The value is a set of strategy with the same accepted type.
     *                      And the set is sorted the priority of {@link Strategizable} based on
     *                      {@code getOrder()}.
     *                      </p>
     * @return the printed string content.
     */
    @Override
    protected String printOutput(TreeNode treeNode, Map<Class<?>, Set<BasePrintingStrategy<?>>> strategiesMap) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        if (Objects.nonNull(treeNode)) {
            List<Integer> valList = new ArrayList<>();
            Queue<TreeNode> treeNodeQueue = new LinkedList<>();
            treeNodeQueue.add(treeNode);

            while (!treeNodeQueue.isEmpty()) {
                int nodeSize = treeNodeQueue.size();
                while (nodeSize > 0) {
                    TreeNode curNode = treeNodeQueue.poll();
                    if (Objects.nonNull(curNode)) {
                        valList.add(curNode.val);
                        treeNodeQueue.offer(curNode.left);
                        treeNodeQueue.offer(curNode.right);
                    } else {
                        valList.add(null);
                    }
                    --nodeSize;
                }
            }

            int treeNodeSize = valList.size() - 1;
            for (; treeNodeSize >= 0; --treeNodeSize) {
                if (Objects.nonNull(valList.get(treeNodeSize))) {
                    break;
                }
            }
            treeNodeSize++;

            for (int i = 0; i < treeNodeSize; ++i) {
                if (i > 0) {
                    stringBuilder.append(",");
                }
                stringBuilder.append(valList.get(i));
            }
        }
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
    public Class<? extends TreeNode> getAcceptableType() {
        return TreeNode.class;
    }
}
