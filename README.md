<p align="center">
  <img width="160" height="160" src="https://cdn-icons-png.freepik.com/512/10435/10435114.png">
  <h1 align="center">LeetcodeJavaDebugEnhancer </h1>
  <p align="center">🚀 A local debugging enhancer for Leetcode algorithm problems in Java 🚀</p>
  <p align="center">
     <a href="LICENSE">
      <img src="https://img.shields.io/hexpm/l/plug.svg" alt="License">
     </a>
     <a href="https://central.sonatype.com/artifact/io.github.jidcoo/leetcode-java-debug-enhancer">
      <img src="https://img.shields.io/maven-central/v/io.github.jidcoo/leetcode-java-debug-enhancer" alt="Maven Central Version">
     </a>
     <a href="https://github.com/Jidcoo/LeetcodeJavaDebugEnhancer/actions/workflows/maven.yml">
      <img alt="Build Status" src="https://img.shields.io/github/actions/workflow/status/Jidcoo/LeetcodeJavaDebugEnhancer/maven.yml">
     </a> 
     <a href="https://github.com/Jidcoo/LeetcodeJavaDebugEnhancer/releases/tag/">
      <img alt="GitHub Release" src="https://img.shields.io/github/v/release/Jidcoo/LeetcodeJavaDebugEnhancer">
     </a>
     <a href="https://github.com/Jidcoo/LeetcodeJavaDebugEnhancer/issues?query=is%3Aissue&label=issue">
      <img alt="GitHub Issue" src="https://img.shields.io/github/issues-search/Jidcoo/LeetcodeJavaDebugEnhancer?query=is%3Aissue&label=issue">
     </a>
  </p>
  <p align="center"> [<a href="README.md">English</a>] [<a href="README.ZH-CN.md">中文</a>] </p>
</p>


<br>

## 🎯 Goals

- Provide convenient and fast debugging functions.
- Support diverse input and output sources.
- Adapt to various input parameter types automatically.
- Provide easy to maintain and expand the APIs for adapting to more Leetcode algorithm debugging scenes.




## 🔧 Download & Install

### Download

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

| Resource         | Index                                          |
| ------------ | --------------------------------------------- |
| Repository Hosting    | [Click here to browse the repository for this project](https://central.sonatype.com/artifact/io.github.jidcoo/leetcode-java-debug-enhancer/)                 |
| Standard-Jar | [Click here to download directly(Standard-Jar)](https://repo1.maven.org/maven2/io/github/jidcoo/leetcode-java-debug-enhancer/1.0.0/leetcode-java-debug-enhancer-1.0.0.jar) |
| Full-Jar     | [Click here to download directly(Full-Jar)](https://repo1.maven.org/maven2/io/github/jidcoo/leetcode-java-debug-enhancer/1.0.0/leetcode-java-debug-enhancer-1.0.0-jar-with-dependencies.jar)     |

### Install

Just import **LeetcodeJavaDebugEnhancer** as the project library.

### Requirements

- The minimum supported Java version is **Java 8**.
- **LeetcodeJavaDebugEnhancer** depends on [Gson](https://github.com/google/gson), version [2.10.1](https://central.sonatype.com/artifact/com.google.code.gson/gson/2.10.1). For non Maven, non Gradle projects that do not include the Gson library, please use **Full-Jar** or manually import Gson library to your project.




## 🛠 Basic Usage

### Step 1:

Create a Java class called SimpleTest and ensure that SimpleTest is public:

```java
//SimpleTest.java

public class SimpleTest {

}
```

### Step 2:

Import `io.github.jidcoo.opto.lcdb.enhancer.LeetcodeJavaDebugEnhancer` into SimpleTest, and then declare SimpleTest to inherit from the class `LeetcodeJavaDebugEnhancer`:

```java
//SimpleTest.java

import io.github.jidcoo.opto.lcdb.enhancer.LeetcodeJavaDebugEnhancer;

public class SimpleTest extends LeetcodeJavaDebugEnhancer {

}
```

### Step 3:

Paste the Java code of the questions on the Leetcode into the SimpleTest and write the corresponding algorithm code to complete the requirements of the questions. Let's take the question [Two Sum](https://leetcode.com/problems/two-sum/) for example: 

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

### Step 4:

Click on the Run or Debug button of SimpleTest to run SimpleTest and start the debugging enhancer.

After the debugging enhancer starts, you will see the following output: 

```
LeetcodeJavaDebugEnhancer[1.0.0] started.
```

> Case input rule: One Case occupies one line, and the next Case needs to be input on the next line. The completion flag for a Case is encountering a new-line break or the EOF.


Then enter the following debugging parameter Case in the console:

```
[2,7,11,15] 9
[3,2,4] 6
[3,3] 6

```

Next, the enhancer will run the algorithm code based on the input Case and output the algorithm results to the console:

```
[0,1]
[1,2]
[0,1]
```




## 📚 LeetcodeJavaDebugEnhancer Function Details


### 1. Support diverse input sources

#### API

```java
public InputProvider getInputProvider();
```

#### Description

**LeetcodeJavaDebugEnhancer** provides support for a variety of input sources such as Console([ConsoleInputProvider](src/main/java/io/github/jidcoo/opto/lcdb/enhancer/core/io/builtin/ConsoleInputProvider.java)) and File/Stream([FileInputProvider](src/main/java/io/github/jidcoo/opto/lcdb/enhancer/core/io/builtin/FileInputProvider.java)).

**LeetcodeJavaDebugEnhancer** uses the console as the default input source.

If you want to customize the input source, please return a valid [**InputProvider**](src/main/java/io/github/jidcoo/opto/lcdb/enhancer/base/InputProvider.java) by overwriting this method.


#### Example<a name="ref1"></a>

Assuming there is now a file named "input.txt", the file content is as follows:

```
[2,7,11,15] 9
[3,2,4] 6
[3,3] 6
```

Here is an example code that uses the input.txt file as the input source:

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



### 2. Support diverse output sources

#### API

```java
public OutputConsumer getOutputConsumer();
```

#### Description

**LeetcodeJavaDebugEnhancer** provides support for a variety of output sources such as Console([ConsoleOutputConsumer](src/main/java/io/github/jidcoo/opto/lcdb/enhancer/core/io/builtin/ConsoleOutputConsumer.java)) and File/Stream([FileOutputConsumer](src/main/java/io/github/jidcoo/opto/lcdb/enhancer/core/io/builtin/FileOutputConsumer.java)).

**LeetcodeJavaDebugEnhancer** uses the console as the default output source.

If you want to customize the output source, please return a valid [**OutputConsumer**](src/main/java/io/github/jidcoo/opto/lcdb/enhancer/base/OutputConsumer.java) by overwriting this method.


#### Example

Continuing from [the above example](#ref1), the following is an example code that uses the input.txt file as the input source and the output.txt file as the output source:

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



### 3. Support custom debugging enhancement point

#### API

```java
public Method getEnhancementPoint();
```

#### Description

As we all know, there are some algorithm problems in Leetcode for [data structure design problems](https://leetcode.com/tag/design/), such as [Min Stack](https://leetcode.com/problems/min-stack/), [Implement Stack using Queues](https://leetcode.com/problems/implement-stack-using-queues/) and so on.

The above kinds of problems are not friendly to the current debugging enhancer, because they do not have a particularly clear algorithm entry point. It is difficult for the debugging enhancer to find an effective enhancement point.

So for debugging such problems, try your best to overwrite this method to provide the debugging enhancer with a valid, explicit enhancement point method from the current public class(like SimpleTest in [the above example](#ref1)), from which the debugging enhancer will execute the debugging enhancement point method to achieve the purpose of code debugging.

#### Example

Assuming there is now a file named "input.txt", the file content is as follows:

```
["MyStack","push","push","top","pop","empty"] [[],[1],[2],[],[],[]]
```

The following is an example code that uses the input.txt file as the input source and customizes the implementation of the enhanced point      method `runHere(String[] operations, int[][] numbers)` for debugging:

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



> For this kind of data structure design problem, the design idea of `public Method getEnhancementPoint()` is to provide a **circuitous but useful** way for users to complete debugging for such problems.
>
> But it is not difficult to see that such a design may add "additional, meaningless coding tasks" to users.
>
> Therefore, in future iterations, we will try to abstract this kind of data structure design problem scene and hand over the process of "additional, meaningless coding tasks" to the debugging enhancer for automatic processing!!!
>
> Please continue to follow this project! Stay tuned!!! 🎉  🎉  🎉





## 🐛 Issue & Feedback

### About Issue

**LeetcodeJavaDebugEnhancer** uses GitHub’s integrated issue tracking system to record bugs and feature requests. If you want to raise an issue, please follow the recommendations below:

- Before you log a bug, please search the [Issue Tracker](https://github.com/Jidcoo/LeetcodeJavaDebugEnhancer/issues) to see if someone has already reported the problem.
- If the issue doesn’t already exist, please [create a new issue](https://github.com/Jidcoo/LeetcodeJavaDebugEnhancer/issues/new).
- Please provide as much information as possible with the issue report. We like to know the debugging enhancer version, jvm version, exception stack output, leetcode problem, input and output you’re using or getting.
- If you need to paste code or include a stack trace, use Markdown. ``` escapes before and after your text.
- If possible, try to create a test case that replicates the problem and attach it to the issue.

### About Feedback

Any feedback, suggestions, or new feature requests regarding **LeetcodeJavaDebugEnhancer** can be provided to me by posting an Issue.

Of course, you can also contact me directly by email:

**Nickname:** Jidcoo

**Email:** jidcoo@163.com




## 🎉 Contributing

Welcome anyone to contribute in any form to this project!!! Whether it's new feature requests, Issue reports, code submissions, documentation, or other kinds of contributions or support!!!

Isn't the true joy of open source from here?!!! Right?!!!

All submissions require review. We use GitHub [pull requests](https://github.com/Jidcoo/LeetcodeJavaDebugEnhancer/pulls) for this purpose. Consult [GitHub Help](https://help.github.com/articles/about-pull-requests/) for more information on using pull requests.



## 📜 License  

**LeetcodeJavaDebugEnhancer** is an Open Source project released under the [Apache 2.0 license](LICENSE).


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
