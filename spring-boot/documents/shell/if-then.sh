#!/bin/sh

# 可直接执行
if [ $(ps -ef | grep -c "ssh") -gt 1 ]; then echo "true"; else echo "false";fi


a=10
b=20
if [ $a == $b ]
then
   echo "a == b"
elif [ $a -gt $b ]
then
   echo "a > b"
elif [ $a -lt $b ]
then
   echo "a < b"
else
   echo "error"
fi

# for loop in 1 2 3 4 5;do echo "The value is: $loop"; done;
# 一行代码需要在换行处打分号
for loop in 1 2 3 4 5
do
    echo "The value is: $loop"
done

for((i=1;i<=5;i++));do
    echo "这是第 $i 次调用";
done;

number=3

# 循环遍历
while(( $number>0 ))
do
  echo "$number";
  let "number--"
done

# case 选择
#aNum=3
#while :
#do
#    echo -n "输入 1 到 5 之间的数字:"
##    read aNum
#    case $aNum in
#        1|2|3|4|5) echo "你输入的数字为 $aNum!"
#        ;;
#        *) echo "你输入的数字不是 1 到 5 之间的! 游戏结束"
#            break
#        ;;
#    esac
#done

echo '按下 <CTRL-D> 退出'
echo -n '输入你最喜欢的网站名: '
while read FILM
do
    echo "是的！$FILM 是一个好网站"
done
