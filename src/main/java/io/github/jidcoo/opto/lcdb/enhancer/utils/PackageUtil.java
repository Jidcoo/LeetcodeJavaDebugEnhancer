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

package io.github.jidcoo.opto.lcdb.enhancer.utils;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * A common java package util.
 *
 * @author Jidcoo
 * @since 1.0
 */
public class PackageUtil {

    /**
     * Get all classes from the package.
     *
     * @param packageName the java package name.
     * @return the class list.
     */
    public static List<Class<?>> getClassesByPackage(String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        boolean recursive = true;
        String packageDirName = packageName.replace('.', '/');
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    findClassesByPackagePath(packageName, filePath, recursive, classes);
                } else if ("jar".equals(protocol)) {
                    JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
                    Enumeration<JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        String name = entry.getName();
                        if (name.charAt(0) == '/') {
                            name = name.substring(1);
                        }
                        if (name.startsWith(packageDirName)) {
                            int idx = name.lastIndexOf('/');
                            if (idx != -1) {
                                packageName = name.substring(0, idx).replace('/', '.');
                            }
                            if ((idx != -1) || recursive) {
                                if (name.endsWith(".class") && !entry.isDirectory()) {
                                    String className = name.substring(packageName.length() + 1, name.length() - 6);
                                    classes.add(Class.forName(packageName + '.' + className));
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception | Error e) {
            throw new RuntimeException(e);
        }
        return classes;
    }

    /**
     * Find classes by package path.
     *
     * @param packageName the package name.
     * @param packagePath the package path.
     * @param recursive   is recursive finding.
     * @param classes     the list for store class.
     */
    public static void findClassesByPackagePath(String packageName, String packagePath, final boolean recursive,
                                                List<Class<?>> classes) {
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        File[] dirFiles = dir.listFiles(file -> (recursive && file.isDirectory()) || (file.getName().endsWith(".class"
        )));
        for (File file : dirFiles) {
            if (file.isDirectory()) {
                findClassesByPackagePath(packageName + "." + file.getName(), file.getAbsolutePath(), recursive,
                        classes);
            } else {
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    classes.add(Class.forName(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
