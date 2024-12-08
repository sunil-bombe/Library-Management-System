package com.webwork;

public class SingletonPattternDemo {

  private String id;

  private String name;

  private static SingletonPattternDemo singletonPattternDemo;

  private SingletonPattternDemo(){

  }

  public static SingletonPattternDemo getInstance(){
    if(singletonPattternDemo == null){
      singletonPattternDemo = new SingletonPattternDemo();
      return singletonPattternDemo;
    }else {
      return singletonPattternDemo;
    }
  }
}
