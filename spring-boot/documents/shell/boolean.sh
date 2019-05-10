#!/bin/bash

a=10
b=10

if [ $a != $b ]
then
 echo "a != b"
fi

# 字符串运算符

a="abc"
b="abd"

if [ $a = $b ]
then
    echo "a == b"
else
    echo "a != b"
fi

if [ $a != $b ]
then
    echo 'a != b'
else
    echo 'a == b'
fi

# -a  长度是否为 0 ，为0 true

if [ -a $a ]
then
    echo "a length == 0"
else
    echo "a length > 0 "
fi

if [ -n $a ]
then
    echo "a length > 0"
else
    echo "a length == 0"
fi


if [ $a ]
then
    echo "string is not empty!"
else
    echo "string is empty !"
fi

file="./array.sh"

# 文件属性判断
echo "file property "
if [ -r $file ]
then
   echo "file is read"
else
   echo "file is not read"
fi

if [ -w $file ]
then
    echo "file is write"
else
    echo "file is not write"
fi

if [ -x $file ]
then
    echo "file is exec"
else
    echo "file is not exec"
fi

if [ -f $file ]
then
   echo "file is file"
else
   echo "file is not file"
fi
if [ -d $file ]
then
   echo "file is dir"
else
   echo "file is not dir"
fi

if [ -s $file ]
then
   echo "file is not empty"
else
   echo "file is empty"
fi
if [ -e $file ]
then
   echo "file is exit"
else
   echo "file is not exit"
fi
