package se.ecutb.cheng;

import se.ecutb.cheng.data.CityDaoJDBC;
import se.ecutb.cheng.entity.City;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        CityDaoJDBC dao = new CityDaoJDBC();

//        System.out.println(dao.findById(2));

//        System.out.println(dao.findAll());

        City city = new City (4086,"Test", "SWE", "Test", 123456789);
//        dao.add(city);
        //dao.delete(new City ("Test", "SWE", "Test", 123456789));
        System.out.println(dao.delete(new City (4086,"Test", "SWE", "Test", 123456789)));
//        dao.update("Nest", "")


    }
}
