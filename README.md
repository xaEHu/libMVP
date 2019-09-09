
# libMVP
### 简单的MVP框架结构

[![](https://jitpack.io/v/xaEHu/libMVP.svg)](https://jitpack.io/#xaEHu/libMVP)
#### 如何使用：

## 依赖：
在根目录的`build.gradle`添加这一句代码：
```java
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
在app目录下的`build.gradle`添加依赖使用：
```java
dependencies {
        implementation 'com.github.xaEHu:libMVP:1.0'
}
```

## 使用
### 1、静态`Activity`或`Fragment`（即不做网络请求和逻辑处理，只做界面展示）
```java
//Fragment的话继承BaseF
public class MainActivity extends BaseV {

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(View v) {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
```
### 2、动态 `Activity`或`Fragment`
```java
//Fragment的话继承BaseF
public class MainActivity extends BaseV<MainPersenter> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(View v) {

    }

    @Override
    public void initData() {
  
    }

    @Override
    public void initListener() {

    }
}
```
同时要为这个`Activity`或`Fragment`创建一个对应的P类（继承`BaseP`类）：
```java
public class MainPersenter extends BaseP<MainActivity> {
    
}
```
##### 这样一来，V层和P层就可以通过get的方式互相调用了：
    在MainActivity中通过getP()即可调用MainPersenter中的方法
    在MainPersenter中通过getV()即可调用MainActivity中的方法

## 自定义Base基类
### 比如BaseActivity
```java
public abstract class BaseActivity<P extends BaseP> extends BaseV<P> {

}
```
#### 如果有重写到IView接口的实现方法的话，可以加一个`@CallSuper`注解,这样的话子类重写该方法也必须调用super.该方法()，这样一来就不会覆盖掉基类对这个方法的实现了。可以做一些公共的初始化（比如此处的ActionBar的返回键、EventBus的初始化之类的。具体控制请看demo）:
```java
public abstract class BaseActivity<P extends BaseP> extends BaseV<P> {
    @Override
    @CallSuper
    public void initView(View v){
        //左侧添加一个默认的返回图标
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            //设置返回键可用
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }
}
```


