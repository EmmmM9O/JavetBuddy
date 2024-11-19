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

package com.caoccao.javet.buddy.ts2java.ast;

import com.caoccao.javet.buddy.ts2java.compiler.JavaByteCodeHint;
import com.caoccao.javet.buddy.ts2java.compiler.JavaFunctionContext;
import com.caoccao.javet.buddy.ts2java.compiler.JavaLogicalLabels;
import com.caoccao.javet.buddy.ts2java.compiler.JavaOpcodeUtils;
import com.caoccao.javet.buddy.ts2java.compiler.instructions.JavaInstructionLogicalCompare;
import com.caoccao.javet.buddy.ts2java.compiler.visitors.JavaByteCodeMethodVisitor;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaAstException;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaException;
import com.caoccao.javet.swc4j.ast.enums.Swc4jAstBinaryOp;
import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAst;
import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAstExpr;
import com.caoccao.javet.utils.ListUtils;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.*;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;

import java.util.List;

public final class Ts2JavaAstBinaryOp {
    public static final String JAVA_LANG_MATH = "java/lang/Math";

    private Ts2JavaAstBinaryOp() {
    }

    private static Addition getAddition(TypeDescription type) {
        if (type.represents(int.class)) {
            return Addition.INTEGER;
        } else if (type.represents(long.class)) {
            return Addition.LONG;
        } else if (type.represents(float.class)) {
            return Addition.FLOAT;
        } else if (type.represents(double.class)) {
            return Addition.DOUBLE;
        }
        throw new Ts2JavaException(
                SimpleFreeMarkerFormat.format("Unsupported type ${type} in addition.",
                        SimpleMap.of("type", type.getName())));
    }

    public static StackManipulation getBitAndStackManipulation(JavaFunctionContext functionContext) {
        return new StackManipulation.Simple((
                MethodVisitor methodVisitor,
                Implementation.Context implementationContext) -> {
            // TODO
            return new StackManipulation.Size(-1, 0);
        });
    }

    public static StackManipulation getBitOrStackManipulation(JavaFunctionContext functionContext) {
        return new StackManipulation.Simple((
                MethodVisitor methodVisitor,
                Implementation.Context implementationContext) -> {
            // TODO
            return new StackManipulation.Size(-1, 0);
        });
    }

    private static Division getDivision(TypeDescription type) {
        if (type.represents(int.class)) {
            return Division.INTEGER;
        } else if (type.represents(long.class)) {
            return Division.LONG;
        } else if (type.represents(float.class)) {
            return Division.FLOAT;
        } else if (type.represents(double.class)) {
            return Division.DOUBLE;
        }
        throw new Ts2JavaException(
                SimpleFreeMarkerFormat.format("Unsupported type ${type} in division.",
                        SimpleMap.of("type", type.getName())));
    }

    private static StackManipulation getExp() {
        return new StackManipulation.Simple((
                MethodVisitor methodVisitor,
                Implementation.Context implementationContext) -> {
            methodVisitor.visitMethodInsn(
                    Opcodes.INVOKESTATIC,
                    JAVA_LANG_MATH,
                    "pow",
                    "(DD)D",
                    false);
            return new StackManipulation.Size(-2, 0);
        });
    }

    @Deprecated // TODO To be replaced by a built-in function in swc4j
    public static Swc4jAstBinaryOp getFlippedBinaryOpLogical(Swc4jAstBinaryOp binaryOp) {
        switch (binaryOp) {
            case EqEq:
                return Swc4jAstBinaryOp.NotEq;
            case EqEqEq:
                return Swc4jAstBinaryOp.NotEqEq;
            case Gt:
                return Swc4jAstBinaryOp.LtEq;
            case GtEq:
                return Swc4jAstBinaryOp.Lt;
            case Lt:
                return Swc4jAstBinaryOp.GtEq;
            case LtEq:
                return Swc4jAstBinaryOp.Gt;
            case LogicalAnd:
                return Swc4jAstBinaryOp.LogicalOr;
            case LogicalOr:
                return Swc4jAstBinaryOp.LogicalAnd;
            case NotEq:
                return Swc4jAstBinaryOp.EqEq;
            case NotEqEq:
                return Swc4jAstBinaryOp.EqEqEq;
            default:
                throw new Ts2JavaException(
                        SimpleFreeMarkerFormat.format("Unsupported binary op ${op} in logical operation.",
                                SimpleMap.of("op", binaryOp.name())));
        }
    }

    private static Multiplication getMultiplication(TypeDescription type) {
        if (type.represents(int.class)) {
            return Multiplication.INTEGER;
        } else if (type.represents(long.class)) {
            return Multiplication.LONG;
        } else if (type.represents(float.class)) {
            return Multiplication.FLOAT;
        } else if (type.represents(double.class)) {
            return Multiplication.DOUBLE;
        }
        throw new Ts2JavaException(
                SimpleFreeMarkerFormat.format("Unsupported type ${type} in multiplication.",
                        SimpleMap.of("type", type.getName())));
    }

    private static Remainder getRemainder(TypeDescription type) {
        if (type.represents(int.class)) {
            return Remainder.INTEGER;
        } else if (type.represents(long.class)) {
            return Remainder.LONG;
        } else if (type.represents(float.class)) {
            return Remainder.FLOAT;
        } else if (type.represents(double.class)) {
            return Remainder.DOUBLE;
        }
        throw new Ts2JavaException(
                SimpleFreeMarkerFormat.format("Unsupported type ${type} in mod.",
                        SimpleMap.of("type", type.getName())));
    }

    private static ShiftLeft getShiftLeft(TypeDescription type) {
        if (type.represents(int.class)) {
            return ShiftLeft.INTEGER;
        } else if (type.represents(long.class)) {
            return ShiftLeft.LONG;
        }
        throw new Ts2JavaException(
                SimpleFreeMarkerFormat.format("Unsupported type ${type} in left shift.",
                        SimpleMap.of("type", type.getName())));
    }

    private static ShiftRight getShiftRight(TypeDescription type) {
        if (type.represents(int.class)) {
            return ShiftRight.INTEGER;
        } else if (type.represents(long.class)) {
            return ShiftRight.LONG;
        }
        throw new Ts2JavaException(
                SimpleFreeMarkerFormat.format("Unsupported type ${type} in right shift.",
                        SimpleMap.of("type", type.getName())));
    }

    private static Subtraction getSubtraction(TypeDescription type) {
        if (type.represents(int.class)) {
            return Subtraction.INTEGER;
        } else if (type.represents(long.class)) {
            return Subtraction.LONG;
        } else if (type.represents(float.class)) {
            return Subtraction.FLOAT;
        } else if (type.represents(double.class)) {
            return Subtraction.DOUBLE;
        }
        throw new Ts2JavaException(
                SimpleFreeMarkerFormat.format("Unsupported type ${type} in subtraction.",
                        SimpleMap.of("type", type.getName())));
    }

    private static StackManipulation getZeroFillShiftRight(TypeDescription type) {
        if (type.represents(int.class)) {
            return new StackManipulation.Simple(
                    (MethodVisitor methodVisitor, Implementation.Context implementationContext) -> {
                        methodVisitor.visitInsn(Opcodes.IUSHR);
                        return new StackManipulation.Size(-1, 0);
                    });
        } else if (type.represents(long.class)) {
            return new StackManipulation.Simple(
                    (MethodVisitor methodVisitor, Implementation.Context implementationContext) -> {
                        methodVisitor.visitInsn(Opcodes.L2I);
                        methodVisitor.visitInsn(Opcodes.LUSHR);
                        return new StackManipulation.Size(-2, 0);
                    });
        }
        throw new Ts2JavaException(
                SimpleFreeMarkerFormat.format("Unsupported type ${type} in zero fill right shift.",
                        SimpleMap.of("type", type.getName())));
    }

    public static void manipulateArithmetic(
            List<StackManipulation> stackManipulations,
            Swc4jAstBinaryOp binaryOp,
            JavaByteCodeHint hint) {
        switch (binaryOp) {
            case Add:
                stackManipulations.add(getAddition(hint.getType()));
                break;
            case Div:
                stackManipulations.add(getDivision(hint.getType()));
                break;
            case LShift:
                stackManipulations.add(getShiftLeft(hint.getType()));
                break;
            case Mod:
                stackManipulations.add(getRemainder(hint.getType()));
                break;
            case Mul:
                stackManipulations.add(getMultiplication(hint.getType()));
                break;
            case RShift:
                stackManipulations.add(getShiftRight(hint.getType()));
                break;
            case Sub:
                stackManipulations.add(getSubtraction(hint.getType()));
                break;
            case ZeroFillRShift:
                stackManipulations.add(getZeroFillShiftRight(hint.getType()));
                break;
            case Exp:
                stackManipulations.add(getExp());
                break;
            default:
                throw new Ts2JavaException(
                        SimpleFreeMarkerFormat.format("Binary op ${op} is not supported.",
                                SimpleMap.of("op", binaryOp.name())));
        }
    }

    public static void manipulateLogicalAnd(
            JavaFunctionContext functionContext,
            int leftEndIndex,
            ISwc4jAstExpr leftExpression,
            JavaByteCodeHint leftHint,
            ISwc4jAstExpr rightExpression,
            JavaByteCodeHint rightHint) {
        if (!leftHint.getType().represents(boolean.class)) {
            throw new Ts2JavaAstException(
                    leftExpression,
                    SimpleFreeMarkerFormat.format("Unsupported left type ${type} in logical AND (&&).",
                            SimpleMap.of("type", leftHint.getType().getName())));
        }
        if (!rightHint.getType().represents(boolean.class)) {
            throw new Ts2JavaAstException(
                    rightExpression,
                    SimpleFreeMarkerFormat.format("Unsupported right type ${type} in logical AND (&&).",
                            SimpleMap.of("type", rightHint.getType().getName())));
        }
        final List<StackManipulation> stackManipulations = functionContext.getStackManipulations();
        final JavaLogicalLabels logicalLabels = functionContext.getLogicalLabels();
        Label labelFalse = logicalLabels.getLastLabel();
        if (!leftHint.isJump()) {
            stackManipulations.add(leftEndIndex, new StackManipulation.Simple(
                    (MethodVisitor methodVisitor, Implementation.Context implementationContext) -> {
                        methodVisitor.visitJumpInsn(Opcodes.IFEQ, labelFalse);
                        return new StackManipulation.Size(-1, 0);
                    }));
            ++leftEndIndex;
        }
        if (!rightHint.isJump()) {
            stackManipulations.add(new StackManipulation.Simple(
                    (MethodVisitor methodVisitor, Implementation.Context implementationContext) -> {
                        methodVisitor.visitJumpInsn(Opcodes.IFEQ, labelFalse);
                        return new StackManipulation.Size(-1, 0);
                    }));
        }
    }

    public static void manipulateLogicalCompare(
            JavaFunctionContext functionContext,
            ISwc4jAst ast,
            Swc4jAstBinaryOp binaryOp,
            JavaByteCodeHint hint) {
        JavaInstructionLogicalCompare instruction = new JavaInstructionLogicalCompare(
                ast,
                binaryOp,
                hint.getType(),
                functionContext.getLogicalLabels().getLastLabel());
        functionContext.getStackManipulations().add(instruction);
    }

    public static void manipulateLogicalOr(
            JavaFunctionContext functionContext,
            int leftEndIndex,
            ISwc4jAstExpr leftExpression,
            JavaByteCodeHint leftHint,
            ISwc4jAstExpr rightExpression,
            JavaByteCodeHint rightHint) {
        if (!leftHint.getType().represents(boolean.class)) {
            throw new Ts2JavaAstException(
                    leftExpression,
                    SimpleFreeMarkerFormat.format("Unsupported left type ${type} in logical OR (||).",
                            SimpleMap.of("type", leftHint.getType().getName())));
        }
        if (!rightHint.getType().represents(boolean.class)) {
            throw new Ts2JavaAstException(
                    rightExpression,
                    SimpleFreeMarkerFormat.format("Unsupported right type ${type} in logical OR (||).",
                            SimpleMap.of("type", rightHint.getType().getName())));
        }
        final List<StackManipulation> stackManipulations = functionContext.getStackManipulations();
        final JavaLogicalLabels logicalLabels = functionContext.getLogicalLabels();
        Label labelFalse = logicalLabels.getLastLabel();
        Label labelTrue = logicalLabels.append();
        if (leftHint.isJump()) {
            JavaByteCodeMethodVisitor byteCodeMethodVisitor = new JavaByteCodeMethodVisitor(Opcodes.ASM9);
            StackManipulation stackManipulationCompare = stackManipulations.get(leftEndIndex - 1);
            stackManipulationCompare.apply(byteCodeMethodVisitor, null);
            List<JavaByteCodeMethodVisitor.OpcodeAndLabel> opcodeAndLabels = byteCodeMethodVisitor.getJumpInstList();
            if (opcodeAndLabels.size() != 1) {
                throw new Ts2JavaAstException(
                        rightExpression,
                        SimpleFreeMarkerFormat.format("Unsupported left type ${type} in logical OR (||).",
                                SimpleMap.of("type", rightHint.getType().getName())));
            }
            JavaByteCodeMethodVisitor.OpcodeAndLabel opcodeAndLabel = opcodeAndLabels.get(0);
            int originalOpcodeCompare = Opcodes.NOP;
            if (ListUtils.isNotEmpty(byteCodeMethodVisitor.getInstList())) {
                int opcode = byteCodeMethodVisitor.getInstList().get(byteCodeMethodVisitor.getInstList().size() - 1);
                switch (opcode) {
                    case Opcodes.DCMPG:
                    case Opcodes.DCMPL:
                    case Opcodes.FCMPG:
                    case Opcodes.FCMPL:
                    case Opcodes.LCMP:
                        originalOpcodeCompare = opcode;
                        break;
                }
            }
            final int opcodeCompare, opcodeIf;
            switch (opcodeAndLabel.getOpcode()) {
                case Opcodes.IF_ICMPEQ:
                case Opcodes.IF_ICMPGE:
                case Opcodes.IF_ICMPGT:
                case Opcodes.IF_ICMPLE:
                case Opcodes.IF_ICMPLT:
                case Opcodes.IF_ICMPNE:
                    opcodeCompare = Opcodes.NOP;
                    opcodeIf = JavaOpcodeUtils.getOpposite(opcodeAndLabel.getOpcode());
                    break;
                case Opcodes.IFEQ:
                case Opcodes.IFGE:
                case Opcodes.IFGT:
                case Opcodes.IFLE:
                case Opcodes.IFLT:
                case Opcodes.IFNE:
                    opcodeCompare = originalOpcodeCompare > Opcodes.NOP ? originalOpcodeCompare : Opcodes.LCMP;
                    opcodeIf = JavaOpcodeUtils.getOpposite(opcodeAndLabel.getOpcode());
                    break;
                default:
                    throw new Ts2JavaAstException(
                            rightExpression,
                            SimpleFreeMarkerFormat.format("Unsupported left type ${type} in logical OR (||).",
                                    SimpleMap.of("type", rightHint.getType().getName())));
            }
            stackManipulations.set(leftEndIndex - 1, new StackManipulation.Simple(
                    (MethodVisitor methodVisitor, Implementation.Context implementationContext) -> {
                        if (opcodeCompare > Opcodes.NOP) {
                            methodVisitor.visitInsn(opcodeCompare);
                        }
                        methodVisitor.visitJumpInsn(opcodeIf, labelTrue);
                        return new StackManipulation.Size(-2, 0);
                    }));
        } else {
            stackManipulations.add(leftEndIndex, new StackManipulation.Simple(
                    (MethodVisitor methodVisitor, Implementation.Context implementationContext) -> {
                        methodVisitor.visitJumpInsn(Opcodes.IFGT, labelTrue);
                        return new StackManipulation.Size(-1, 0);
                    }));
            ++leftEndIndex;
        }
        if (rightHint.isJump()) {
            JavaByteCodeMethodVisitor byteCodeMethodVisitor = new JavaByteCodeMethodVisitor(Opcodes.ASM9);
            StackManipulation stackManipulationCompare = stackManipulations.get(stackManipulations.size() - 1);
            stackManipulationCompare.apply(byteCodeMethodVisitor, null);
            List<JavaByteCodeMethodVisitor.OpcodeAndLabel> opcodeAndLabels = byteCodeMethodVisitor.getJumpInstList();
            if (opcodeAndLabels.size() != 1) {
                throw new Ts2JavaAstException(
                        rightExpression,
                        SimpleFreeMarkerFormat.format("Unsupported right type ${type} in logical OR (||).",
                                SimpleMap.of("type", rightHint.getType().getName())));
            }
            int originalOpcodeCompare = Opcodes.NOP;
            if (ListUtils.isNotEmpty(byteCodeMethodVisitor.getInstList())) {
                int opcode = byteCodeMethodVisitor.getInstList().get(byteCodeMethodVisitor.getInstList().size() - 1);
                switch (opcode) {
                    case Opcodes.DCMPG:
                    case Opcodes.DCMPL:
                    case Opcodes.FCMPG:
                    case Opcodes.FCMPL:
                    case Opcodes.LCMP:
                        originalOpcodeCompare = opcode;
                        break;
                }
            }
            JavaByteCodeMethodVisitor.OpcodeAndLabel opcodeAndLabel = opcodeAndLabels.get(0);
            final int opcodeCompare, opcodeIf;
            switch (opcodeAndLabel.getOpcode()) {
                case Opcodes.IF_ICMPEQ:
                case Opcodes.IF_ICMPGE:
                case Opcodes.IF_ICMPGT:
                case Opcodes.IF_ICMPLE:
                case Opcodes.IF_ICMPLT:
                case Opcodes.IF_ICMPNE:
                    opcodeCompare = Opcodes.NOP;
                    opcodeIf = opcodeAndLabel.getOpcode();
                    break;
                case Opcodes.IFEQ:
                case Opcodes.IFGE:
                case Opcodes.IFGT:
                case Opcodes.IFLE:
                case Opcodes.IFLT:
                case Opcodes.IFNE:
                    opcodeCompare = originalOpcodeCompare > Opcodes.NOP ? originalOpcodeCompare : Opcodes.LCMP;
                    opcodeIf = opcodeAndLabel.getOpcode();
                    break;
                default:
                    throw new Ts2JavaAstException(
                            rightExpression,
                            SimpleFreeMarkerFormat.format("Unsupported right type ${type} in logical OR (||).",
                                    SimpleMap.of("type", rightHint.getType().getName())));
            }
            stackManipulations.set(stackManipulations.size() - 1, new StackManipulation.Simple(
                    (MethodVisitor methodVisitor, Implementation.Context implementationContext) -> {
                        if (opcodeCompare > Opcodes.NOP) {
                            methodVisitor.visitInsn(opcodeCompare);
                        }
                        methodVisitor.visitJumpInsn(opcodeIf, labelFalse);
                        return new StackManipulation.Size(-2, 0);
                    }));
        } else {
            stackManipulations.add(new StackManipulation.Simple(
                    (MethodVisitor methodVisitor, Implementation.Context implementationContext) -> {
                        methodVisitor.visitJumpInsn(Opcodes.IFLE, labelFalse);
                        return new StackManipulation.Size(-1, 0);
                    }));
        }
    }
}
