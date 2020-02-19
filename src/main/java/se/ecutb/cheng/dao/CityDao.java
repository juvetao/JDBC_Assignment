package se.ecutb.cheng.dao;

import se.ecutb.cheng.entity.City;

import java.sql.SQLException;
import java.util.List;

public interface CityDao {
    City findById(int id) throws SQLException;
    List<City> findByCode(String code);
    List<City> findByName (String name);
    List<City> findAll();
    City add(City city);
    City update(City city);
    int delete(City city);
}
