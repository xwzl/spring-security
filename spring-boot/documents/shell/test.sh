#!/bin/sh
# Shell中的 test 命令用于检查某个条件是否成立，它可以进行数值、字符和文件三个方面的测试。

num1=100
num2=200

# 指令同关系运算符
if test $num1 -eq $num2
then
    echo "num1 == num2"
else
    echo "num2 != num2"
fi

# 字符串测试

var1="aaa"
var2="bbb"

if test $var1 = $var2
then
    echo "equal"
else
    echo "false"
fi

# .e 文件名

if test -e ./var.sh
then
    echo 'file is e!'
else
    echo 'file is not e!'
fi
