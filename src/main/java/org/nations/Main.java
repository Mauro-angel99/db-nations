package org.nations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {
	
	private static final String url = "jdbc:mysql://localhost:3306/db-nations";
	private static final String user = "root";
	private static final String password = "root";
	
public static void main(String[] args) {
	
            Scanner sc = new Scanner(System.in);	
            System.out.print("Inserire nome nazione");
            String nameNation = '%' + sc.next() + '%';
            sc.close();
	
			try (Connection con = DriverManager.getConnection(url, user, password)) {
				
				final String sql = "SELECT countries.name, countries.country_id, regions.name, continents.name\r\n"
						+ "FROM countries\r\n"
						+ "JOIN regions\r\n"
						+ "ON countries.region_id = regions.region_id\r\n"
						+ "JOIN continents\r\n"
						+ "ON regions.continent_id = continents.continent_id\r\n"
						+ "WHERE countries.name LIKE ?"
						+ "ORDER BY countries.name";
				
				try (PreparedStatement ps = con.prepareStatement(sql)) {
					ps.setString(1, nameNation);
					try (ResultSet rs = ps.executeQuery()) {
						while(rs.next()) {	
							final String name = rs.getString(1);
							final int id = rs.getInt(2);
							final String nameRegion = rs.getString(3);
							final String nameContinent = rs.getString(4);	
							System.out.println(
								id + " - " 
								+ name + " - " 
								+ id + " - " 
								+ nameRegion + " - " 
								+ nameContinent
							);
						}
					}
				} 
				
			} catch (Exception e) {
				
				System.err.println("ERROR: " + e.getMessage());
			}
		}

	}


