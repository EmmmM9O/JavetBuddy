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

package com.caoccao.javet.buddy.ts2java.ast.expr;

import com.caoccao.javet.buddy.ts2java.ast.enums.Ts2JavaLogicalMode;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.abilities.ITs2JavaBangFlippable;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.swc4j.ast.enums.Swc4jAstBinaryOp;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstBinExpr;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.jar.asm.Label;

import java.util.Objects;

public abstract class Ts2JavaAstBinExprLogical extends Ts2JavaAstBinExpr
        implements ITs2JavaBangFlippable {
    protected boolean bangFlipped;
    protected Label labelFalse;
    protected boolean labelOverridden;
    protected boolean labelSwitched;
    protected Label labelTrue;
    protected Ts2JavaLogicalMode logicalMode;

    protected Ts2JavaAstBinExprLogical(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstBinExpr ast,
            Ts2JavaMemoFunction memo) {
        super(parent, ast, memo);
        bangFlipped = false;
        labelFalse = new Label();
        labelTrue = new Label();
        labelOverridden = false;
        labelSwitched = false;
        logicalMode = Ts2JavaLogicalMode.Default;
        type = TypeDescription.ForLoadedType.of(boolean.class);
    }

    @Override
    public void flipBang() {
        bangFlipped = !bangFlipped;
        if (left instanceof ITs2JavaBangFlippable) {
            left.as(ITs2JavaBangFlippable.class).flipBang();
        }
        if (right instanceof ITs2JavaBangFlippable) {
            right.as(ITs2JavaBangFlippable.class).flipBang();
        }
    }

    public Label getLabelFalse() {
        return labelFalse;
    }

    public Label getLabelTrue() {
        return labelTrue;
    }

    public Ts2JavaLogicalMode getLogicalMode() {
        return logicalMode;
    }

    public abstract Swc4jAstBinaryOp getResolvedOp();

    @Override
    public boolean isBangFlippable() {
        return true;
    }

    public boolean isBangFlipped() {
        return bangFlipped;
    }

    public boolean isLabelOverridden() {
        return labelOverridden;
    }

    public boolean isLabelSwitched() {
        return labelSwitched;
    }

    public Ts2JavaAstBinExprLogical setLabelFalse(Label labelFalse) {
        this.labelFalse = labelFalse;
        labelOverridden = true;
        return this;
    }

    public Ts2JavaAstBinExprLogical setLabelSwitched(boolean labelSwitched) {
        this.labelSwitched = labelSwitched;
        return this;
    }

    public Ts2JavaAstBinExprLogical setLabelTrue(Label labelTrue) {
        this.labelTrue = labelTrue;
        labelOverridden = true;
        return this;
    }

    public void setLogicalMode(Ts2JavaLogicalMode logicalMode) {
        this.logicalMode = Objects.requireNonNull(logicalMode);
    }
}
