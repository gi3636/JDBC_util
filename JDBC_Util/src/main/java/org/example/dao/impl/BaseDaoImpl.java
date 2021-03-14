package org.example.dao.impl;

import org.example.BaseDao;
import org.example.annotation.Column;
import org.example.annotation.Table;
import org.example.dao.Basic;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class BaseDaoImpl extends BaseDao implements Basic {

    @Override
    public <T> Set<T> queryEntity(T t) {
        //存放查询结果
        Set<T> result=new HashSet<T>();

        try {
            //获取连接
            Connection connection=super.getConnection();

            //获得类的类型
            Class<?> clazz=t.getClass();

            //检查有没有类有没有使用table,没有就直接使用类名当成表名
            Table table=clazz.getAnnotation(Table.class);
            String tableName="";
            if (table==null){
                tableName=clazz.getName();
            }
            {
                tableName=table.value();
            }

            //组装sql，要注意空格
            StringBuffer sql=new StringBuffer();
            sql.append("select * from ");
            sql.append(tableName);
            sql.append(" where 1=1");//防止别人使用注入

            //获取属性的值
            Field[] fields=clazz.getDeclaredFields();
            for (Field f:fields) {
                //先获取get set方法
                Method getMethod = this.getFieldSetOrGetMethod("GET", clazz, f);
                //调用方法获得属性值
                Object fieldValue = getMethod.invoke(t);
                if (fieldValue != null) {
                    //获取字段名
                    Column column =f.getAnnotation(Column.class);
                    String columnName="";
                    if (column==null){
                        columnName=f.getName();
                    }
                    else {
                        columnName=column.value();
                    }

                    //组装sql
                    sql.append(" AND ");
                    sql.append(columnName+"= ? ");
                }
            }
            System.out.println("未填充前："+sql);
            //填入参数
            PreparedStatement preparedStatement=connection.prepareStatement(sql.toString());
            int index=0;
            for (Field f:fields){
                Method getMethod=getFieldSetOrGetMethod("GET",clazz,f);
                Object fieldValue=getMethod.invoke(t);
                if (fieldValue!=null){
                    preparedStatement.setObject(++index,fieldValue);
                }
            }

            //获取结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                //实例化
                T r=(T) clazz.newInstance();
                for (Field f:fields){
                    Method setMethod=getFieldSetOrGetMethod("SET",clazz,f);
                    //获取列名
                    String columnName="";
                    Column column=f.getAnnotation(Column.class);
                    if (column==null){
                        columnName=f.getName();
                    }
                    else {
                        columnName=column.value();
                    }

                    //获取属性值
                    if ("java.lang.Integer".equals(f.getType().getName())){
                        int intValue=resultSet.getInt(columnName);
                        setMethod.invoke(r,intValue);
                    }else if("java.lang.String".equals(f.getType().getName())){
                        String stringValue=resultSet.getString(columnName);
                        setMethod.invoke(r,stringValue);
                    }else if("java.lang.Double".equals(f.getType().getName())){
                        Double doubleValue=resultSet.getDouble(columnName);
                        setMethod.invoke(r,doubleValue);
                    }else if("java.util.Date".equals(f.getType().getName())){
                        Date dateValue=resultSet.getDate(columnName);
                        setMethod.invoke(r,dateValue);
                    }
                }
                //添加去结果集
                result.add(r);
            }


        } catch (SQLException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException throwables) {
            throwables.printStackTrace();
        }

        return result;
    }

    @Override
    public <T> int updateEntity(T t) {
        return 0;
    }

    @Override
    public <T> int deleteEntity(T t) {
        return 0;
    }

    @Override
    public <T> int insertEntity(T t) {
        return 0;
    }


    private  Method getFieldSetOrGetMethod(String methodType, Class<?>clazz,Field field) throws NoSuchMethodException {
        //获取方法名
        String methodName=methodType.toLowerCase();
        String fieldName=field.getName();
        //属性第一个字母大写+第一个字母后面的字
        methodName+= String.valueOf(fieldName.charAt(0)).toUpperCase()+fieldName.substring(1);
        Method method=null;

        //判断是get 还是 set 方法
        if ("get".equalsIgnoreCase(methodType)){
            method=clazz.getMethod(methodName);
        }else if ("set".equalsIgnoreCase(methodType)){
            method=clazz.getMethod(methodName,field.getType());
        }
        return method;
    }




}
