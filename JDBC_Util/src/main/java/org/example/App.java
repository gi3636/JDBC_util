package org.example;

import org.example.dao.Basic;
import org.example.dao.impl.BaseDaoImpl;
import org.example.entity.Emp;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Basic baseDao= new BaseDaoImpl();
        Emp emp=new Emp();
        emp.setJob("SALESMAN");
        Set<Emp> empSet=baseDao.queryEntity(emp);
        Iterator<Emp> empIterator=empSet.iterator();
        while(empIterator.hasNext()){
            Emp emp1=empIterator.next();
            System.out.println(emp1.toString());
        }


    }
}
