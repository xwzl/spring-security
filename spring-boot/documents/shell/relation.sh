#!/bin/bash

a=10
b=20

# 关系运算符只支持数字，不支持字符串，除非字符串的值是数字。

# a == b
if [ $a -eq $b ]
then
  echo " a == b"
else
  echo " a != b"
fi

# a != b
if [ $a -ne $b ]
then
    echo " a != b"
else
    echo " a == b"
fi

# a > b
if [ $a -gt $b ]
then
    echo "a > b"
else
    echo " a <= b"
fi

# a < b
if [ $a -lt $b ]
then
    echo " a < b "
else
    echo " a >= b"

fi

# a >= b
if [ $a -ge $b ]
then
    echo " a >= b"
else
    echo " a < b"
fi

# a <= b
if [ $a -le $b ]
then
    echo " a <= b "
else
    echo " a > b"
fi

