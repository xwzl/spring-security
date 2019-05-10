#!/bin/sh
# 原生bash不支持简单的数学运算，但是可以通过其他命令来实现，例如 awk 和 expr，expr 最常用。
# 表达式和运算符之间要有空格，例如 2+2 是不对的，必须写成 2 + 2，这与我们熟悉的大多数编程语言不一样。
# 完整的表达式要被 ` ` 包含，注意这个字符不是常用的单引号，在 Esc 键下边。

parma1=`expr 1 + 1`
echo "两数之和为：${parma1}"

a=10
b=20

var1=`expr $a - $b`
echo "a - b : ${var1}"

var2=`expr $a + $b`
echo "a + b : ${var2}"

var3=`expr $a / $b`
echo "a / b : ${var3}"

# 特别注意 *
# 乘号(*)前边必须加反斜杠(\)才能实现乘法运算；
# if...then...fi 是条件语句，后续将会讲解。
# 在 MAC 中 shell 的 expr 语法是：$((表达式))，此处表达式中的 "*" 不需要转义符号 "\" 。
var4=`expr $a \* $b`
echo "a * b : ${var4}"

# 前后都要有空格
if [ $a == $b ]
then
    echo "a == b"
fi

if [ $a != $b ]
then
    echo "a != b"
fi
