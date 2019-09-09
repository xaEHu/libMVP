
# libMVP
简单的MVP框架结构

[![](https://jitpack.io/v/xaEHu/libMVP.svg)](https://jitpack.io/#xaEHu/libMVP)
#### 如何使用：

## 依赖：
在根目录的build.gradle添加这一句代码：
```java
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
在app目录下的build.gradle添加依赖使用：
```java
dependencies {
        implementation 'com.github.xaEHu:libMVP:Tag'
}
```

## 使用
### 1、静态Activity或Fragment（即不做网络请求和逻辑处理，只做界面展示）
```java
public class MainActivity extends BaseV{

  @Override
  public int getLayoutId(){
    return R.layout.activity_main;
  }
  @Override
  public void initView(){

  }
  @Override
  public void initData(){

  }
  @Override
  public void initListener(){

  }
}
```


