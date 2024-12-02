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

import com.caoccao.javet.buddy.ts2java.ast.BaseTs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstExpr;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaAstException;
import com.caoccao.javet.swc4j.ast.enums.Swc4jAstBinaryOp;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstBinExpr;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.jar.asm.MethodVisitor;

public abstract class Ts2JavaAstBinExpr
        extends BaseTs2JavaAst<Swc4jAstBinExpr, Ts2JavaMemoFunction>
        implements ITs2JavaAstExpr<Swc4jAstBinExpr, Ts2JavaMemoFunction> {
    protected final ITs2JavaAstExpr<?, ?> left;
    protected final ITs2JavaAstExpr<?, ?> right;
    protected Swc4jAstBinaryOp op;

    public Ts2JavaAstBinExpr(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstBinExpr ast,
            Ts2JavaMemoFunction memo) {
        super(parent, ast, memo);
        op = ast.getOp();
        // TODO
        if (op.isLogicalOperator()) {
            type = TypeDescription.ForLoadedType.of(boolean.class);
        }
        left = ITs2JavaAstExpr.create(parent, ast.getLeft(), memo);
        right = ITs2JavaAstExpr.create(parent, ast.getRight(), memo);
    }

    public static Ts2JavaAstBinExpr create(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstBinExpr ast,
            Ts2JavaMemoFunction memo) {
        if (ast.getOp().isArithmeticOperator()) {
            return new Ts2JavaAstBinExprArithmetic(parent, ast, memo);
        }
        throw new Ts2JavaAstException(
                ast,
                SimpleFreeMarkerFormat.format("Bin expr op ${op} is not supported.",
                        SimpleMap.of("op", ast.getOp().name())));
    }

    @Override
    public Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        visitLineNumber(methodVisitor);
        return Size.ZERO;
    }

    @Override
    public void compile() {
        left.compile();
        right.compile();
    }

    public ITs2JavaAstExpr<?, ?> getLeft() {
        return left;
    }

    public Swc4jAstBinaryOp getOp() {
        return op;
    }

    public ITs2JavaAstExpr<?, ?> getRight() {
        return right;
    }
}
