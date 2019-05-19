# 准备工作
    # 安装webpack
    npm install webpack -g
    
    安装vue脚手架
    npm install vue-cli -g
    
    # 根据模板创建工程
    vue init webpack-simple vue
    
    # 安装 vue 路由模块vue-router和网络请求模块vue-resource
    npm install vue-router vue-resource --save
    
    # 更新 npm
    npm update -g
    
    # 如果你用的是老版本的 vue-cli 还可能报其他错误，需要更新一下 vue-cli
    npm update vue-cli
    
    # 然后可以查看一下当前全局 vue-cli 的版本
    npm view vue-cli
    
    # 安装一下这个依赖到工程开发环境
    npm install opn --save-dev
    npm install webpack-dev-middleware --save-dev
    npm install express --save-dev
    npm install compression --save-dev
    npm install sockjs --save-dev
    npm install spdy --save-dev
    npm install http-proxy-middleware --save-dev
    npm install serve-index --save-dev
    npm install connect-history-api-fallback --save-dev
    
    # 安装 bootstrap
    npm install bootstrap@3
# vue

> This is vue demo !

## Build Setup

``` bash
# install dependencies
npm install

# serve with hot reload at localhost:8080
npm run dev

# build for production with minification
npm run build
```
