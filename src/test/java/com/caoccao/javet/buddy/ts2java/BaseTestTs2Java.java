/*
 * Copyright (c) 2024-2025. caoccao.com Sam Cao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.caoccao.javet.buddy.ts2java;

import com.caoccao.javet.utils.JavetOSUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class BaseTestTs2Java {
    protected TsClass tsClass;

    public BaseTestTs2Java() {
        tsClass = null;
    }

    protected void disableLogging() {
        Ts2Java.disableLogging();
    }

    protected void enableLogging() {
        Ts2Java.enableLogging();
    }

    protected String getTsCode(String relativePath) throws IOException {
        Path path = new File(JavetOSUtils.WORKING_DIRECTORY).toPath()
                .resolve("scripts/ts/test")
                .resolve(relativePath);
        return Files.readAllLines(path, StandardCharsets.UTF_8).stream()
                .filter(line -> !line.startsWith("console."))
                .collect(Collectors.joining("\n"));
    }

}
