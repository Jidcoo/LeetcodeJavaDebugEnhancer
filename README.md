<p align="center">
  <img width="160" height="160" src="https://cdn-icons-png.freepik.com/512/10435/10435114.png">
  <h1 align="center">LeetcodeJavaDebugEnhancer </h1>
  <p align="center">🚀 A local debugging enhancer for Leetcode algorithm problems in Java 🚀</p>
  <p align="center">
     <a href="LICENSE">
      <img alt="License" src="https://img.shields.io/github/license/Jidcoo/LeetcodeJavaDebugEnhancer?label=License">
     </a>
     <a href="https://github.com/Jidcoo/LeetcodeJavaDebugEnhancer/actions/workflows/maven-build.yml">
      <img alt="Maven Build Status" src="https://github.com/Jidcoo/LeetcodeJavaDebugEnhancer/actions/workflows/maven-build.yml/badge.svg">
     </a> 
     <a href="https://github.com/Jidcoo/LeetcodeJavaDebugEnhancer/actions/workflows/maven-release.yml">
      <img alt="Maven Release Status" src="https://github.com/Jidcoo/LeetcodeJavaDebugEnhancer/actions/workflows/maven-release.yml/badge.svg">
     </a>
     <a href="https://github.com/Jidcoo/LeetcodeJavaDebugEnhancer/releases">
      <img alt="GitHub Release" src="https://img.shields.io/github/v/release/Jidcoo/LeetcodeJavaDebugEnhancer?label=Release">
     </a>
     <a href="https://central.sonatype.com/artifact/io.github.jidcoo/leetcode-java-debug-enhancer">
      <img alt="Maven Central Version" src="https://img.shields.io/maven-central/v/io.github.jidcoo/leetcode-java-debug-enhancer?label=Maven%20Central"> 
     </a>
     <a href="https://github.com/Jidcoo/LeetcodeJavaDebugEnhancer/issues?query=is%3Aissue&label=issue">
      <img alt="GitHub Issues" src="https://img.shields.io/github/issues/Jidcoo/LeetcodeJavaDebugEnhancer?label=Issue">
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
    <version>1.0.2</version>
</dependency>
```

#### **Gradle**

```gradle
implementation 'io.github.jidcoo:leetcode-java-debug-enhancer:1.0.2'
```

#### **Jar**

| Resource         | Index                                                                                                                                                                                        |
| ------------ |----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Repository Hosting    | [Click here to browse the repository for this project](https://central.sonatype.com/artifact/io.github.jidcoo/leetcode-java-debug-enhancer/)                                                 |
| Standard-Jar | [Click here to download directly(Standard-Jar)](https://repo1.maven.org/maven2/io/github/jidcoo/leetcode-java-debug-enhancer/1.0.2/leetcode-java-debug-enhancer-1.0.2.jar)                   |
| Full-Jar     | [Click here to download directly(Full-Jar)](https://repo1.maven.org/maven2/io/github/jidcoo/leetcode-java-debug-enhancer/1.0.2/leetcode-java-debug-enhancer-1.0.2-jar-with-dependencies.jar) |

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
LeetcodeJavaDebugEnhancer[1.0.2] started.
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

**LeetcodeJavaDebugEnhancer** provides support for a variety of input sources such as
Console([ConsoleInputProvider](src/main/java/io/github/jidcoo/opto/lcdb/enhancer/core/io/builtin/ConsoleInputProvider.java)),
File/Stream([FileInputProvider](src/main/java/io/github/jidcoo/opto/lcdb/enhancer/core/io/builtin/FileInputProvider.java)),
String([StringInputProvider](src/main/java/io/github/jidcoo/opto/lcdb/enhancer/core/io/builtin/StringInputProvider.java)) and
SerialMultipleInput([MultipleInputProvider](src/main/java/io/github/jidcoo/opto/lcdb/enhancer/core/io/builtin/MultipleInputProvider.java))
.

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

**LeetcodeJavaDebugEnhancer** provides support for a variety of output sources such as
Console([ConsoleOutputConsumer](src/main/java/io/github/jidcoo/opto/lcdb/enhancer/core/io/builtin/ConsoleOutputConsumer.java)),
File/Stream([FileOutputConsumer](src/main/java/io/github/jidcoo/opto/lcdb/enhancer/core/io/builtin/FileOutputConsumer.java)) and
ParallelMultipleOutput([MultipleOutputConsumer](src/main/java/io/github/jidcoo/opto/lcdb/enhancer/core/io/builtin/MultipleOutputConsumer.java))
.

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

### 3. Require annotation

#### API

```java
@Target({TYPE, FIELD, METHOD, PARAMETER})
@Retention(RUNTIME)
@Repeatable(Requires.class)
public @interface Require {

    /**
     * The requirement string values;
     *
     * @return requirement string values.
     */
    String[] values() default "";

    /**
     * The requirement types.
     *
     * @return requirement types.
     */
    Class<?>[] types() default {};
}
```

#### Description

The Require annotation is used to mark and declare debugging resources required for the runtime of the debugging enhancer, such as input sources, output sources, etc. This annotation officially opened its feature to the public starting from version 1.0.2, supporting its use on class, field, method, and parameter.

Currently(v1.0.2), you can:
- Use the Require annotation on the debugging enhancer startup class to declare custom input and output sources. Furthermore, if you want to customize an IO source for a debugging enhancer now, you can not only provide a custom IO source instance for the debugging enhancer by
  overwriting the `getInputProvider()` or `getOutputConsumer()` methods, but also customize the IO source for the debugging enhancer through convenient annotations.


#### Example

##### Customize IO source using Require annotation

```java
//SimpleTest.java

//Customize input provider.
@Require(values = "case/input1.txt", types = FileInputProvider.class)
@Require(values = "[0,4,3,0] 0", types = StringInputProvider.class)
//Customize output consumer.
@Require(values = "case/output.txt", types = FileOutputConsumer.class)
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

The following is an example that lists all available uses of using Require annotation to customize IO sources.

```java
//SimpleTest.java

//Customize input provider.
@Require(types = ConsoleInputProvider.class)
@Require(values = "case/input1.txt", types = FileInputProvider.class)
@Require(values = {"case/input1.txt", "case/input2.txt"}, types = FileInputProvider.class)
@Require(values = "[0,4,3,0] 0", types = StringInputProvider.class)
@Require(values = {"[0,4,3,0] 0", "[3,3] 6"}, types = StringInputProvider.class)
@Require(values = {
                "[0,4,3,0] 0",
                "[3,3] 6",
                "case/input1.txt"
        },
        types = {
                StringInputProvider.class,
                StringInputProvider.class,
                FileInputProvider.class
})
@Require(values = {
                "case/input1.txt",
                "",
                "[0,4,3,0] 0"
        },
        types = {
                FileInputProvider.class,
                ConsoleInputProvider.class,
                StringInputProvider.class
})
@Require(values = {
                "case/input1.txt",
                "",
                "[0,4,3,0] 0",
                "case/input2.txt",
                "[3,3] 6",
        },
        types = {
                FileInputProvider.class,
                ConsoleInputProvider.class,
                StringInputProvider.class,
                FileInputProvider.class,
                StringInputProvider.class
})
//Customize output consumer.
@Require(types = ConsoleOutputConsumer.class)
@Require(values = "case/output.txt", types = FileOutputConsumer.class)
@Require(values = {"case/output.txt", "case/output_1.txt"}, types = FileOutputConsumer.class)
@Require(values = {"case/output.txt", ""}, types = {FileOutputConsumer.class, ConsoleOutputConsumer.class})
public class SimpleTest extends LeetcodeJavaDebugEnhancer {
    
    class Solution {/**ignored**/}
}
```

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
