/*
 * Copyright (c) 2024. caoccao.com Sam Cao
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

package com.caoccao.javet.buddy.ts2java.ast.expr;

import com.caoccao.javet.buddy.ts2java.BaseTestTs2Java;
import com.caoccao.javet.buddy.ts2java.TsClassX;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestTs2JavaAstUnaryExpr extends BaseTestTs2Java {
    @Test
    public void testBang() throws Exception {
        tsClass = new TsClassX("return !true;", boolean.class);
        assertFalse((boolean) tsClass.invoke());
        tsClass = new TsClassX("return !false;", boolean.class);
        assertTrue((boolean) tsClass.invoke());
        tsClass = new TsClassX("return !(!true);", boolean.class);
        assertTrue((boolean) tsClass.invoke());
        tsClass = new TsClassX("return !(!false);", boolean.class);
        assertFalse((boolean) tsClass.invoke());
    }

    @Test
    public void testMinus() throws Exception {
        tsClass = new TsClassX("return -1;", int.class);
        assertEquals(-1, tsClass.invoke());
        tsClass = new TsClassX("return -(1);", int.class);
        assertEquals(-1, tsClass.invoke());
        tsClass = new TsClassX("return -(-1);", int.class);
        assertEquals(1, tsClass.invoke());
    }

    @Test
    public void testPlus() throws Exception {
        tsClass = new TsClassX("return +1;", int.class);
        assertEquals(1, tsClass.invoke());
        tsClass = new TsClassX("return +(+1);", int.class);
        assertEquals(1, tsClass.invoke());
        tsClass = new TsClassX("return +(-1);", int.class);
        assertEquals(-1, tsClass.invoke());
    }
}
