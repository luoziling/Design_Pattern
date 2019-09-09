package priv.wzb.design_pattern.behavioralpattern.visitorpattern.initial;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Satsuki
 * @time 2019/6/9 19:21
 * @description:
 */
public class EmployeeList {
    private ArrayList<Employee> list = new ArrayList<>();
    //增加员工
    public void addEmployee(Employee employee)
    {
        list.add(employee);
    }

    //处理员工数据
    public void handle(String departmentName)
    {
        if(departmentName.equalsIgnoreCase("财务部")) //财务部处理员工数据
        {
            for(Object obj : list)
            {
                if(obj.getClass().getName().equalsIgnoreCase("FulltimeEmployee"))
                {
                    System.out.println("财务部处理全职员工数据！");
                }
                else
                {
                    System.out.println("财务部处理兼职员工数据！");
                }
            }
        }
        else if(departmentName.equalsIgnoreCase("人力资源部")) //人力资源部处理员工数据
        {
            for(Object obj : list)
            {
                if(obj.getClass().getName().equalsIgnoreCase("FulltimeEmployee"))
                {
                    System.out.println("人力资源部处理全职员工数据！");
                }
                else
                {
                    System.out.println("人力资源部处理兼职员工数据！");
                }
            }
        }
    }
}
