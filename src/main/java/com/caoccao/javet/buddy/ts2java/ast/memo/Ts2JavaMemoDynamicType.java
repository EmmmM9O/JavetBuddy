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

package com.caoccao.javet.buddy.ts2java.ast.memo;

import net.bytebuddy.dynamic.DynamicType;

public class Ts2JavaMemoDynamicType extends Ts2JavaMemo {
    protected DynamicType.Builder<?> builder;

    public Ts2JavaMemoDynamicType(DynamicType.Builder<?> builder) {
        setBuilder(builder);
    }

    public DynamicType.Builder<?> getBuilder() {
        return builder;
    }

    public void setBuilder(DynamicType.Builder<?> builder) {
        this.builder = builder;
    }
}
