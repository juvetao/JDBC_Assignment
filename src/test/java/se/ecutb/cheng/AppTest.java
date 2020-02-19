package se.ecutb.cheng;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import se.ecutb.cheng.data.CityDaoJDBC;
import se.ecutb.cheng.entity.City;

import java.util.ArrayList;
import java.util.List;


public class AppTest 
{
    CityDaoJDBC dao = new CityDaoJDBC();

    @Test
    public void find_by_id_successfully()
    {
        City expected = new City(2, "Qandahar", "AFG", "Qandahar",237500);
        City actual = dao.findById(2);
        assertEquals(expected, actual);
    }

    @Test
    public void find_by_code_successfully()
    {
        int expected = 4;
        int actual = dao.findByCode("AFG").size();
        assertEquals(expected, actual);
    }

    @Test
    public void find_by_name_successfully()
    {
        List<City> expected = new ArrayList<City>();
        expected.add(0, new City(3, "Herat", "AFG", "Herat", 186800));
        List<City> actual = dao.findByName("Herat");
        assertEquals(expected, actual);
    }

    @Test
    public void find_All_successfully()
    {
        int expected = 4079;
        int actual = dao.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    public void insert_city_successfully()
    {
        int expected = 4080;
        City city = new City (4080,"Test", "SWE", "Test", 123456789);
        dao.add(city);
        int actual = dao.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    public void delete_city_successfully()
    {
        int expected = 4079;
        City city = dao.findById(4080);
        dao.delete(city);
        int actual = dao.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    public void update_city_successfully()
    {
        City city_added = new City (4081,"Test", "SWE", "Test", 123456789);
        dao.add(city_added);
        City city_updated = dao.findById(4081);
        city_updated.setName("Nest");
        String expected = "Nest";
        String actual = dao.update(city_updated).getName();
        assertEquals(expected, actual);
    }



}
