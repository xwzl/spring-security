#!/bin/sh
# 在命令行中通过 wc -l 命令计算 Here Document 的行数：
wc -l << EOF
    欢迎来到
    菜鸟教程
    www.runoob.com
EOF

echo "菜鸟教程：www.runoob.com" > users
cat users

cat users > role

cat role

echo "111" > list 2>&1

cat << EOF
欢迎来到
菜鸟教程
www.runoob.com
EOF
