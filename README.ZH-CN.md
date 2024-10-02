<p align="center">
  <img width="160" height="160" src="https://cdn-icons-png.freepik.com/512/10435/10435114.png">
  <h1 align="center">LeetcodeJavaDebugEnhancer </h1>
  <p align="center">🚀 一个用于Java的Leetcode算法题的本地调试增强器 🚀</p>
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
    <version>1.0.2</version>
</dependency>
```

#### **Gradle**

```gradle
implementation 'io.github.jidcoo:leetcode-java-debug-enhancer:1.0.2'
```

#### **Jar**

| 资源         | 索引                                                                                                                                                                  |
| ------------ |---------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 托管仓库     | [点击这里浏览本项目的托管仓库](https://central.sonatype.com/artifact/io.github.jidcoo/leetcode-java-debug-enhancer/)                                                              |
| 标准-Jar | [点击这里直接下载(标准-Jar)](https://repo1.maven.org/maven2/io/github/jidcoo/leetcode-java-debug-enhancer/1.0.2/leetcode-java-debug-enhancer-1.0.2.jar)                       |
| 全量-Jar     | [点击这里直接下载(全量-Jar)](https://repo1.maven.org/maven2/io/github/jidcoo/leetcode-java-debug-enhancer/1.0.2/leetcode-java-debug-enhancer-1.0.2-jar-with-dependencies.jar) |

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
LeetcodeJavaDebugEnhancer[1.0.2] started.
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

**LeetcodeJavaDebugEnhancer**提供了对
控制台([ConsoleInputProvider](src/main/java/io/github/jidcoo/opto/lcdb/enhancer/core/io/builtin/ConsoleInputProvider.java))、
文件/流([FileInputProvider](src/main/java/io/github/jidcoo/opto/lcdb/enhancer/core/io/builtin/FileInputProvider.java))、
字符串([StringInputProvider](src/main/java/io/github/jidcoo/opto/lcdb/enhancer/core/io/builtin/StringInputProvider.java))、
串行化多输入([MultipleInputProvider](src/main/java/io/github/jidcoo/opto/lcdb/enhancer/core/io/builtin/MultipleInputProvider.java))
等多样输入源的支持。

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

**LeetcodeJavaDebugEnhancer**提供了对
控制台([ConsoleOutputConsumer](src/main/java/io/github/jidcoo/opto/lcdb/enhancer/core/io/builtin/ConsoleOutputConsumer.java))、
文件/流([FileOutputConsumer](src/main/java/io/github/jidcoo/opto/lcdb/enhancer/core/io/builtin/FileOutputConsumer.java))、
并行化多输出([MultipleOutputConsumer](src/main/java/io/github/jidcoo/opto/lcdb/enhancer/core/io/builtin/MultipleOutputConsumer.java))
等多样输入源的支持。

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



### 3、Require注解

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

#### 描述

Require注解用于标记和声明调试增强器运行所需的如输入源、输出源等调试资源。该注解在1.0.2版本开始正式对外开放功能特性，支持在类、字段、方法和形参上使用。

目前(v1.0.2)，你可以：
- 在调试增强器启动类上使用Require注解声明自定义的输入源和输出源。更进一步地说，如果现在你想要为调试增强器自定义IO源，你不仅可以通过重写`getInputProvider()`
或`getOutputConsumer()`方法为调试增强器提供自定义的IO源实例，还可以通过便捷的注解的方式为调试增强器自定义IO源。


#### 示例

##### 使用Require注解自定义IO源

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

下面是一个列举了使用Require注解自定义IO源的所有可行用法的示例。

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
