#!/bin/sh

demoFun(){
    echo "Function is running !"
}

echo "-----Function is starting !-----"
demoFun
echo "-----Function is stopping !-----"

funParam(){
    echo "First parma1:${1}"
    echo "Second parma2:${2}"
    echo "All params:$*"
    echo "params's number:$#"
    echo "All params:$@"
}

funParam 12 13

funWithReturn(){
     echo "Function is start !"
     echo "Write one "
     read num1
     echo "Write one "
     read num2
     return $(($num1+$num2))

}

funWithReturn

echo "输入两数之和是 $? !"

