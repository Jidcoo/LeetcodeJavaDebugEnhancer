<p align="center">
  <img width="160" height="160" src="https://cdn-icons-png.freepik.com/512/10435/10435114.png">
  <h1 align="center">LeetcodeJavaDebugEnhancer </h1>
  <p align="center">🚀 一个用于Java的Leetcode算法题的本地调试增强器 🚀</p>
  <p align="center">
     <a href="LICENSE">
      <img src="https://img.shields.io/hexpm/l/plug.svg" alt="License">
     </a>
     <a href="https://central.sonatype.com/artifact/io.github.jidcoo/leetcode-java-debug-enhancer">
      <img src="https://img.shields.io/maven-central/v/io.github.jidcoo/leetcode-java-debug-enhancer" alt="Maven Central Version">
     </a>
     <a href="https://github.com/Jidcoo/LeetcodeJavaDebugEnhancer/releases/tag/">
      <img alt="GitHub Release" src="https://img.shields.io/github/v/release/Jidcoo/LeetcodeJavaDebugEnhancer">
     </a>
     <a href="https://github.com/Jidcoo/LeetcodeJavaDebugEnhancer/issues?query=is%3Aissue&label=issue">
      <img alt="GitHub Issue" src="https://img.shields.io/github/issues-search/Jidcoo/LeetcodeJavaDebugEnhancer?query=is%3Aissue&label=issue">
     </a>
  </p>
  <p align="center"> [<a href="README.ZH-CN.md">中文</a>] [<a href="README.md">English</a>] </p>
</p>


<br>

## 🎯 目标

- 提供方便快速的调试功能。
- 支持多样的输入源和输出源。
- 自动适配各种输入参数类型。
- 提供易维护、易拓展的API接口用于适配更多Leetcode算法调试场景。




## 🔧 下载与安装

### 下载

#### **Maven**

```xml
<dependency>
    <groupId>io.github.jidcoo</groupId>
    <artifactId>leetcode-java-debug-enhancer</artifactId>
    <version>1.0.0</version>
</dependency>
```

#### **Gradle**

```gradle
implementation 'io.github.jidcoo:leetcode-java-debug-enhancer:1.0.0'
```

#### **Jar**

| 资源         | 索引                                          |
| ------------ | --------------------------------------------- |
| 托管仓库     | [点击这里浏览本项目的托管仓库](https://central.sonatype.com/artifact/io.github.jidcoo/leetcode-java-debug-enhancer/)                 |
| 标准-Jar | [点击这里直接下载(标准-Jar)](https://repo1.maven.org/maven2/io/github/jidcoo/leetcode-java-debug-enhancer/1.0.0/leetcode-java-debug-enhancer-1.0.0.jar) |
| 全量-Jar     | [点击这里直接下载(全量-Jar)](https://repo1.maven.org/maven2/io/github/jidcoo/leetcode-java-debug-enhancer/1.0.0/leetcode-java-debug-enhancer-1.0.0-jar-with-dependencies.jar)     |

### 安装

只需把**LeetcodeJavaDebugEnhancer**作为项目的库引入即可。

### 运行要求

- 支持的最低Java版本为**Java 8** 。
- **LeetcodeJavaDebugEnhancer**依赖于[Gson](https://github.com/google/gson)，版本为[2.10.1](https://central.sonatype.com/artifact/com.google.code.gson/gson/2.10.1)。非Maven、非Gradle项目且项目本身不包含Gson库的，请使用**全量-Jar**或者手动添加Gson库到项目中。




## 🛠 基本使用

### 步骤1:

创建一个名为SimpleTest的Java类，并确保SimpleTest是public的：

```java
//SimpleTest.java

public class SimpleTest {

}
```

### 步骤2:

在SimpleTest中导入`io.github.jidcoo.opto.lcdb.enhancer.LeetcodeJavaDebugEnhancer`，并声明SimpleTest继承自类`LeetcodeJavaDebugEnhancer`：

```java
//SimpleTest.java

import io.github.jidcoo.opto.lcdb.enhancer.LeetcodeJavaDebugEnhancer;

public class SimpleTest extends LeetcodeJavaDebugEnhancer {

}
```

### 步骤3:

把Leetcode网站上的题目的Java语言代码粘贴到SimpleTest类中并编写相应的算法代码完成题目要求，这里以题目[两数之和](https://leetcode.cn/problems/two-sum/)为例：

```java
//SimpleTest.java

import io.github.jidcoo.opto.lcdb.enhancer.LeetcodeJavaDebugEnhancer;

public class SimpleTest extends LeetcodeJavaDebugEnhancer {
    
    class Solution {
        public int[] twoSum(int[] nums, int target) {
            int n = nums.length;
            for (int i = 0; i < n; ++i) {
                for (int j = i + 1; j < n; ++j) {
                    if (nums[i] + nums[j] == target) {
                        return new int[]{i, j};
                    }
                }
            }
            return new int[0];
        }
    }

}
```

### 步骤4:

点击SimpleTest的运行或调试按钮从而运行SimpleTest，启动调试增强器。

增强器启动后你将会看到如下输出：

```
LeetcodeJavaDebugEnhancer[1.0.0] started.
```

> Case输入规则：一个Case占据一行，下一个Case需要在下一行输入，一个Case输入完成的标志是遇到换行符或者EOF。


然后在控制台中输入调试参数Case：

```
[2,7,11,15] 9
[3,2,4] 6
[3,3] 6

```

然后增强器会根据输入Case，运行Leetcode中的算法代码，并输出算法结果到控制台：

```
[0,1]
[1,2]
[0,1]
```




## 📚 LeetcodeJavaDebugEnhancer功能详述 


### 1、支持多样的输入源

#### API

```java
public InputProvider getInputProvider();
```

#### 描述

**LeetcodeJavaDebugEnhancer**提供了对控制台([ConsoleInputProvider](src/main/java/io/github/jidcoo/opto/lcdb/enhancer/core/io/builtin/ConsoleInputProvider.java))、文件/流([FileInputProvider](src/main/java/io/github/jidcoo/opto/lcdb/enhancer/core/io/builtin/FileInputProvider.java))等多样输入源的支持。

**LeetcodeJavaDebugEnhancer**使用控制台作为默认的输入源。

如果你想要自定义输入源，请通过重写该方法返回一个有效的[**InputProvider**](src/main/java/io/github/jidcoo/opto/lcdb/enhancer/base/InputProvider.java)。

#### 示例<a name="ref1"></a>

假设现在有一个名为input.txt的文件，文件内容如下：

```
[2,7,11,15] 9
[3,2,4] 6
[3,3] 6
```

下面是一个把input.txt文件作为输入源的示例代码：

```java
//SimpleTest.java

import io.github.jidcoo.opto.lcdb.enhancer.LeetcodeJavaDebugEnhancer;
import io.github.jidcoo.opto.lcdb.enhancer.base.InputProvider;
import io.github.jidcoo.opto.lcdb.enhancer.core.io.builtin.FileInputProvider;

import java.io.FileNotFoundException;

public class SimpleTest extends LeetcodeJavaDebugEnhancer {

    class Solution {
        public int[] twoSum(int[] nums, int target) {
            int n = nums.length;
            for (int i = 0; i < n; ++i) {
                for (int j = i + 1; j < n; ++j) {
                    if (nums[i] + nums[j] == target) {
                        return new int[]{i, j};
                    }
                }
            }
            return new int[0];
        }
    }

    @Override
    public InputProvider getInputProvider() {
        try {
            // FileInputProvider can accept file-name, file-object, and input-stream as construction parameters.
            return new FileInputProvider("input.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
```



### 2、支持多样的输出源

#### API

```java
public OutputConsumer getOutputConsumer();
```

#### 描述

**LeetcodeJavaDebugEnhancer**提供了对控制台([ConsoleOutputConsumer](src/main/java/io/github/jidcoo/opto/lcdb/enhancer/core/io/builtin/ConsoleOutputConsumer.java))、文件/流([FileOutputConsumer](src/main/java/io/github/jidcoo/opto/lcdb/enhancer/core/io/builtin/FileOutputConsumer.java))等多样输入源的支持。

**LeetcodeJavaDebugEnhancer**使用控制台作为默认的输出源。

如果你想要自定义输出源，请通过重写该方法返回一个有效的[**OutputConsumer**](src/main/java/io/github/jidcoo/opto/lcdb/enhancer/base/OutputConsumer.java)。

#### 示例

接着上面的[示例](#ref1)，下面是一个把input.txt文件作为输入源，output.txt文件作为输出源的示例代码：

```java
//SimpleTest.java

import io.github.jidcoo.opto.lcdb.enhancer.LeetcodeJavaDebugEnhancer;
import io.github.jidcoo.opto.lcdb.enhancer.base.InputProvider;
import io.github.jidcoo.opto.lcdb.enhancer.base.OutputConsumer;
import io.github.jidcoo.opto.lcdb.enhancer.core.io.builtin.FileInputProvider;
import io.github.jidcoo.opto.lcdb.enhancer.core.io.builtin.FileOutputConsumer;

import java.io.FileNotFoundException;

public class SimpleTest extends LeetcodeJavaDebugEnhancer {

    class Solution {
        public int[] twoSum(int[] nums, int target) {
            int n = nums.length;
            for (int i = 0; i < n; ++i) {
                for (int j = i + 1; j < n; ++j) {
                    if (nums[i] + nums[j] == target) {
                        return new int[]{i, j};
                    }
                }
            }
            return new int[0];
        }
    }

    @Override
    public InputProvider getInputProvider() {
        try {
            // FileInputProvider can accept file-name, file-object, and input-stream as construction parameters.
            return new FileInputProvider("input.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OutputConsumer getOutputConsumer() {
        try {
            // FileOutputConsumer can accept file-name, file-object, and ouput-stream as construction parameters.
            return new FileOutputConsumer("output.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
```



### 3、支持自定义调试增强入口点

#### API

```java
public Method getEnhancementPoint();
```

#### 描述

众所周知，在Leetcode上有一些算法题目为[数据结构设计题](https://leetcode.cn/tag/design/)，例如[最小栈](https://leetcode.cn/problems/min-stack/)、[用队列实现栈](https://leetcode.cn/problems/implement-stack-using-queues/)等。

上述这类题目对目前的调试增强器并不友好，因为这类题目并没有一个特别明确的算法入口，调试增强器很难找到一个有效的增强切入点。

因此针对这类题目的调试，请 **_尽最大可能地_** 通过重写该方法为调试增强器提供一个来自**当前public类的**(例如上面[示例](#ref1)中的SimpleTest)、有效的、明确的增强切入点Method，调试增强器将会从该增强点切入执行调试增强逻辑，从而实现代码调试的目的。

#### 示例

假设现在有一个名为input.txt的文件，文件内容如下：

```
["MyStack","push","push","top","pop","empty"] [[],[1],[2],[],[],[]]
```

下面是一个把input.txt文件作为输入源，并自定义实现增强点 `runHere(String[] operations, int[][] numbers)` 方法进行调试的示例代码：

```java
//SimpleTest.java

import io.github.jidcoo.opto.lcdb.enhancer.LeetcodeJavaDebugEnhancer;
import io.github.jidcoo.opto.lcdb.enhancer.base.InputProvider;
import io.github.jidcoo.opto.lcdb.enhancer.core.io.builtin.FileInputProvider;
import io.github.jidcoo.opto.lcdb.enhancer.utils.ReflectUtil;

import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SimpleTest extends LeetcodeJavaDebugEnhancer {

    class MyStack {
        Queue<Integer> queue1;
        Queue<Integer> queue2;

        /**
         * Initialize your data structure here.
         */
        public MyStack() {
            queue1 = new LinkedList<Integer>();
            queue2 = new LinkedList<Integer>();
        }

        /**
         * Push element x onto stack.
         */
        public void push(int x) {
            queue2.offer(x);
            while (!queue1.isEmpty()) {
                queue2.offer(queue1.poll());
            }
            Queue<Integer> temp = queue1;
            queue1 = queue2;
            queue2 = temp;
        }

        /**
         * Removes the element on top of the stack and returns that element.
         */
        public int pop() {
            return queue1.poll();
        }

        /**
         * Get the top element.
         */
        public int top() {
            return queue1.peek();
        }

        /**
         * Returns whether the stack is empty.
         */
        public boolean empty() {
            return queue1.isEmpty();
        }
    }

    /**
     * Enhancement point function.
     *
     * @param operations
     * @param numbers
     * @return
     */
    public List<Object> runHere(String[] operations, int[][] numbers) {
        List<Object> ans = new ArrayList<>();
        MyStack stack = null;
        for (int i = 0; i < operations.length; i++) {
            String operation = operations[i];
            Object curReturn = null;
            switch (operation) {
                case "MyStack":
                    stack = new MyStack();
                    break;
                case "push":
                    stack.push(numbers[i][0]);
                    break;
                case "top":
                    curReturn = stack.top();
                    break;
                case "pop":
                    curReturn = stack.pop();
                    break;
                case "empty":
                    curReturn = stack.empty();
                    break;
            }
            ans.add(curReturn);
        }
        return ans;
    }

    @Override
    public Method getEnhancementPoint() {
        // Return the runHere() method object.
        // By the way, you can use ReflectUtil.getMethod() to easily obtain the specified enhancement point method of a class.
        return ReflectUtil.getMethod(SimpleTest.class, "runHere", String[].class, int[][].class);
    }

    @Override
    public InputProvider getInputProvider() {
        try {
            return new FileInputProvider("input.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}

```




> 对于这种数据结构设计题目，`public Method getEnhancementPoint()`的设计思路是提供一种**迂回但有用**的方式让用户能够完成对此类题目的调试。
>
> 但不难发现的是，这样的设计可能会给用户增加额外的、意义不大的编码任务。
>
> 因此，在后续的版本迭代中，我们将尝试对这类数据结构设计题目场景进行抽象，把上述的“可能会给用户增加额外的、意义不大的编码任务”的过程交给调试增强器来自动完成！！！
>
> 请持续关注该项目，敬请期待 ！！！ 🎉  🎉  🎉





## 🐛 问题与反馈

### 关于问题

**LeetcodeJavaDebugEnhancer**使用GitHub的集成问题跟踪系统来记录缺陷和新特性功能请求。如果你想提出一个Issue，请遵循如下的推荐建议：

- 在你提出Issue前，请先通过搜索[Issue Tracker](https://github.com/Jidcoo/LeetcodeJavaDebugEnhancer/issues)，看看是否已经有人报告了相关的Issue。
- 如果没有相关的Issue记录，请[创建一个新的issue](https://github.com/Jidcoo/LeetcodeJavaDebugEnhancer/issues/new)。
- 在Issue报告中提供尽可能多的信息，让我能够快速了解你当前使用的如调试增强器版本、Java版本、Leetcode题目、输入、输出、异常堆栈输出等信息。
- 如果你需要粘贴代码或者包含堆栈信息的文本，请在相应文本前和文本后使用Markdown的代码块\```进行转义。
- 如果可以的话，请给出一个能够复现问题的测试Case，并将其附加到Issue上。

### 关于反馈

有关**LeetcodeJavaDebugEnhancer**的任何反馈、建议或者新特性功能请求，可以通过提出Issue的方式向我反馈。

当然，你也可以直接通过邮件的方式联系我：

**昵称：** Jidcoo

**邮箱：** jidcoo@163.com




## 🎉 贡献

欢迎任何人对此项目的任何形式的贡献！！！无论是新特性功能请求、Issue报告、代码提交、文档或其他类型的贡献或支持！！！

开源的快乐不就来自于此吗？！！！

所有提交的内容都需要经过代码审查，所以我们使用Github的[pull requests](https://github.com/Jidcoo/LeetcodeJavaDebugEnhancer/pulls)。有关PullRequest的使用，你可以查阅[Github的帮助文档](https://help.github.com/articles/about-pull-requests/)。




## 📜 许可证  

**LeetcodeJavaDebugEnhancer**是基于[Apache 2.0 license](LICENSE)发布的开源项目。

```
Copyright 2024-2026 Jidcoo(https://github.com/jidcoo).

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
