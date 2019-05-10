#!/bin/sh
# 变量
# 变量类型
# 运行shell时，会同时存在三种变量：
#   局部变量 局部变量在脚本或命令中定义，仅在当前shell实例中有效，其他shell启动的程序不能访问局部变量。
#   环境变量 所有的程序，包括shell启动的程序，都能访问环境变量，有些程序需要环境变量来保证其正常运行。必要的时候shell脚本也可以定义环境变量。
#   shell变量 shell变量是由shell程序设置的特殊变量。shell变量中有一部分是环境变量，有一部分是局部变量，这些变量保证了shell的正常运行

# 循环 /root/tool/sh 目录的文件名
# for file in `/root/tool/sh` do
#    echo ${file}
# done

# 赋值操作
user_name="张三"
echo $user_name
# 二次赋值不需要加$
user_name="李四"
echo ${user_name}

# 只读变量
my_var="readOnly"
readonly my_var
# ./var.sh: line 18: my_var: readonly variable
# my_var="haha"

# 删除变量,变量被删除后不能再次使用，不能删除只读变量
unset user_name
echo #{user_name}

# shell 字符串

# 单引号：
#    单引号里的任何字符都会原样输出，单引号字符串中的变量是无效的；
#    单引号字串中不能出现单独一个的单引号（对单引号使用转义符后也不行），但可成对出现，作为字符串拼接使用。

# 拼接字符串
your_name="runoob"

# 使用双引号拼接
greeting="hello, "$your_name" !"
greeting_1="hello, ${your_name} !"
echo $greeting  $greeting_1

# 使用单引号拼接
greeting_2='hello, '$your_name' !'
greeting_3='hello, ${your_name} !'
echo $greeting_2  $greeting_3

# 加或括号确定变量边界，开始do，结束done
for array in java pyhton c; do
   echo "I am good at ${array} script"
done


