package se.ecutb.cheng.data;

import se.ecutb.cheng.dao.CityDao;
import se.ecutb.cheng.entity.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static se.ecutb.cheng.data.Database.getConnection;



public class CityDaoJDBC implements CityDao {
    private static final String FIND_BY_ID =
            "SELECT * FROM City WHERE ID = ?";

    private static final String FIND_BY_CODE =
            "SELECT * FROM City WHERE countryCode = ?";

    private static final String FIND_BY_NAME =
            "SELECT * FROM City WHERE Name = ?";

    private static final String FIND_ALL_CITIES =
            "SELECT * FROM City";

    private static final String INSERT_CITY =
            "INSERT INTO City (Name, CountryCode, District, Population) VALUES (?, ?, ?, ?)";

    private static final String UPDATE_CITY =
            "UPDATE City SET Name =?, CountryCode =?, District =?, Population = ?";

    private static final String DELETE_CITY =
            "DELETE FROM City WHERE Name =? AND CountryCode =? AND District =? AND Population = ?";



    public City createCityFromResultSet (ResultSet resultSet) throws SQLException {
        return  new City(
                resultSet.getInt("ID"),
                resultSet.getString("Name"),
                resultSet.getString("CountryCode"),
                resultSet.getString("District"),
                resultSet.getInt("Population"));
    }

    //Find city/cities by ID
    private PreparedStatement create_findById(Connection connection, int id) throws SQLException{
        PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public City findById(int id) {
        City city = null;
        try(
            Connection connection = getConnection();
            PreparedStatement statement = create_findById(connection, id);
            ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()){
                city = createCityFromResultSet(resultSet);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return city;
    }

    //Find city/cities by Code
    private PreparedStatement create_findByCode(Connection connection, String code) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_BY_CODE);
        statement.setString(1, code);
        return statement;
    }

    @Override
    public List<City> findByCode(String code) {
        List<City> cities = new ArrayList<City>();
        try(
            Connection connection = getConnection();
            PreparedStatement statement = create_findByCode(connection, code);
            ResultSet resultSet = statement.executeQuery()
        ){
            while (resultSet.next()){
                cities.add(createCityFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cities;
    }

    //Find city/cities by Name
    private PreparedStatement create_findByName (Connection connection, String name) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME);
        statement.setString(1, name);
        return statement;
    }

    @Override
    public List<City> findByName(String name) {
        List<City> cities = new ArrayList<City>();
        try(
            Connection connection = getConnection();
            PreparedStatement statement = create_findByName(connection, name);
            ResultSet resultSet = statement.executeQuery();
            ) {
            while (resultSet.next()){
                cities.add(createCityFromResultSet(resultSet));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }

    //Find all cities
    private PreparedStatement create_findAll (Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_ALL_CITIES);
        return statement;
    }

    @Override
    public List<City> findAll() {
        List<City> cities = new ArrayList<City>();
        try(
                Connection connection = getConnection();
                PreparedStatement statement = create_findAll(connection);
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()){
                cities.add(createCityFromResultSet(resultSet));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }

    @Override
    public City add(City city) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet keySet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(INSERT_CITY, statement.RETURN_GENERATED_KEYS);
            statement.setString(1, city.getName());
            statement.setString(2, city.getCountryCode());
            statement.setString(3, city.getDistrict());
            statement.setInt(4, city.getPopulation());
            statement.execute();
            keySet = statement.getGeneratedKeys();

            while (keySet.next()) {
                city = new City(
//                        keySet.getInt("ID"),
                        keySet.getInt(1),
                        city.getName(),
                        city.getCountryCode(),
                        city.getDistrict(),
                        city.getPopulation());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (keySet != null) {
                    keySet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return city;
        }
    }

    @Override
    public City update(City city) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(UPDATE_CITY);
            statement.setString(1, city.getName());
            statement.setString(2, city.getCountryCode());
            statement.setString(3, city.getDistrict());
            statement.setInt(4, city.getPopulation());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return city;
    }

    @Override
    public int delete(City city) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(DELETE_CITY);
            statement.setString(1, city.getName());
            statement.setString(2, city.getCountryCode());
            statement.setString(3, city.getDistrict());
            statement.setInt(4, city.getPopulation());
            statement.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        if(findById(city.getCityId()) != null){
            return 1;
        }else{
            return 0;
        }
    }
}
