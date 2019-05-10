#!/bin/sh
# 添加数据元素以空格隔开，不要以 ，隔开，（）
array=(java python c++)

# for a in array; do
#   echo ${a}+"数据"
# done

:<<EOF
 多行注释
 多行注释
EOF

# 遍历所有数组元素
echo ${array[@]}

# 打印 1 个元素
echo ${array[0]}

# 获取一个元素的长度
echo ${#array[0]}

# 获取数组元素的个数
echo ${#array[@]}
