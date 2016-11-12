package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.Address;

public class AddressRepository {

	private String url = "jdbc:hsqldb:hsql://localhost/workdb";
	private Connection connection;
	private String createTableSql = 
			"CREATE TABLE address("
			+ "id bigint GENERATED BY DEFAULT AS IDENTITY,"
			+ "streetName VARCHAR(50),"
			+ "streetNumber bigint,"
			+ "houseNumber VARCHAR(10),"
			+ "city VARCHAR(50),"
			+ "postcode VARCHAR(5)"			
			+ ")";
	
	private String insertSql = "INSERT INTO address(streetName, streetNumber, houseNumber, city, postcode) VALUES (?,?,?,?,?)";
	private String deleteSql = "DELETE FROM address WHERE id=?";	
	private String updateSql = "UPDATE address SET streetName = ?, streetNumber = ?, houseNumber = ?, city = ?, postcode = ? WHERE id = ?";
	private String getSql = "SELECT * FROM address WHERE id = ?";
	private String listSql = "SELECT * FROM address";
	
	Statement createTable;
	PreparedStatement insert;
	PreparedStatement delete;
	PreparedStatement update;
	PreparedStatement get;
	PreparedStatement list;
	
	
	public AddressRepository(String tableName){
		
		try {
			
			connection = DriverManager.getConnection(url);
			createTable = connection.createStatement();
			ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
			boolean tableExists = false;
			while(rs.next()){
				if(tableName.equalsIgnoreCase(rs.getString("TABLE_NAME"))){
					tableExists=true;
					break;
				}
			}
			if(!tableExists)
				createTable.executeUpdate(createTableSql);
			
			insert = connection.prepareStatement(insertSql);
			delete = connection.prepareStatement(deleteSql);
			update = connection.prepareStatement(updateSql);
			get = connection.prepareStatement(getSql);
			list = connection.prepareStatement(listSql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(Address a){
		try{
			delete.setInt(1, a.getId());
			delete.executeUpdate();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}

	public void update(Address a){
		try{
			update.setString(1, a.getStreetName());
			update.setInt(2, a.getStreetNumber());
			update.setString(3, a.getHouseNumber());
			update.setString(4, a.getCity());
			update.setString(5, a.getPostcode());
			update.setInt(6, a.getId());
			update.executeUpdate();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}

	public Address get(int id){
		Address a = new Address();

		try {
			get.setInt(1, id);
			ResultSet rs = get.executeQuery();
			rs.next();
			a.setId(rs.getInt("id"));
			a.setStreetName(rs.getString("streetname"));
			a.setStreetNumber(rs.getInt("streetnumber"));
			a.setHouseNumber(rs.getString("housenumber"));
			a.setCity(rs.getString("city"));
			a.setPostcode(rs.getString("postcode"));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return a;
	}

	public List<Address> getAll(){
		List<Address> addresses = new ArrayList<Address>();

		try {
			ResultSet rs = list.executeQuery();

			while (rs.next()) {
				Address a = new Address();
				a.setId(rs.getInt("id"));
				a.setStreetName(rs.getString("streetname"));
				a.setStreetNumber(rs.getInt("streetnumber"));
				a.setHouseNumber(rs.getString("housenumber"));
				a.setCity(rs.getString("city"));
				a.setPostcode(rs.getString("postcode"));
				addresses.add(a);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return addresses;
	}
	
	
	public void add(Address a){
		try{
			insert.setString(1, a.getStreetName());
			insert.setInt(2, a.getStreetNumber());
			insert.setString(3, a.getHouseNumber());
			insert.setString(4, a.getCity());
			insert.setString(5, a.getPostcode());
			insert.executeUpdate();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	
}
