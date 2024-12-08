package com.webwork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Demo {

  public static void main(String args[]){
    List<Integer> integerList = new ArrayList<>();
    integerList.add(10);
    integerList.add(23);
    integerList.add(44);
    integerList.add(45);
    integerList.add(50);


    for(int i = 0; i< integerList.size(); i++){
      if(integerList.get(i).equals(23) || integerList.get(i).equals(45)){
        integerList.remove(i);
      }
    }

    integerList.stream().forEach(System.out:: println);
    }

  //create list of interger

  // 10, 23 ,44 , 45, 50

  // 23, 45


}
