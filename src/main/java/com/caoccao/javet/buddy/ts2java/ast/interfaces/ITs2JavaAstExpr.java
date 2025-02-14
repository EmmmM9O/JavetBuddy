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

package com.caoccao.javet.buddy.ts2java.ast.interfaces;

import com.caoccao.javet.buddy.ts2java.ast.expr.Ts2JavaAstBinExpr;
import com.caoccao.javet.buddy.ts2java.ast.expr.Ts2JavaAstIdent;
import com.caoccao.javet.buddy.ts2java.ast.expr.Ts2JavaAstUnaryExpr;
import com.caoccao.javet.buddy.ts2java.ast.expr.lit.Ts2JavaAstBool;
import com.caoccao.javet.buddy.ts2java.ast.expr.lit.Ts2JavaAstNumber;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemo;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaAstException;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstBinExpr;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstIdent;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstParenExpr;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstUnaryExpr;
import com.caoccao.javet.swc4j.ast.expr.lit.Swc4jAstBool;
import com.caoccao.javet.swc4j.ast.expr.lit.Swc4jAstNumber;
import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAstExpr;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;

public interface ITs2JavaAstExpr<AST extends ISwc4jAstExpr, Memo extends Ts2JavaMemo>
        extends ITs2JavaAstVarDeclOrExpr<AST, Memo>, ITs2JavaAstPat<AST, Memo>, ITs2JavaAstJsxExpr<AST, Memo>,
        ITs2JavaAstCallee<AST, Memo>, ITs2JavaAstBlockStmtOrExpr<AST, Memo>, ITs2JavaAstAssignTarget<AST, Memo> {
    static ITs2JavaAstExpr<?, ?> create(
            ITs2JavaAst<?, ?> parent,
            ISwc4jAstExpr ast,
            Ts2JavaMemoFunction memo) {
        switch (ast.getType()) {
            case BinExpr:
                return Ts2JavaAstBinExpr.create(parent, ast.as(Swc4jAstBinExpr.class), memo);
            case Bool:
                return Ts2JavaAstBool.create(parent, ast.as(Swc4jAstBool.class), memo);
            case Ident:
                return Ts2JavaAstIdent.create(parent, ast.as(Swc4jAstIdent.class), memo);
            case Number:
                return Ts2JavaAstNumber.create(parent, ast.as(Swc4jAstNumber.class), memo);
            case ParenExpr:
                return create(parent, ast.as(Swc4jAstParenExpr.class).unParenExpr(), memo);
            case UnaryExpr:
                return Ts2JavaAstUnaryExpr.create(parent, ast.as(Swc4jAstUnaryExpr.class), memo);
            default:
                throw new Ts2JavaAstException(
                        ast,
                        SimpleFreeMarkerFormat.format("Expr type ${type} is not supported.",
                                SimpleMap.of("type", ast.getType().name())));
        }
    }
}
