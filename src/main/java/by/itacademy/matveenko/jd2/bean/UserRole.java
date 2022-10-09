package by.itacademy.matveenko.jd2.bean;

public enum UserRole {
	ADMIN(1, "admin"), USER(2, "user"), GUEST(3, "guest");
	private int role;
	private String name;
	
	UserRole (int role, String name) {
		this.role = role;
		this.name = name;
	};
	
	public int getRole() {
		return role;
	}	
	
	public String getName() {
		return name;
	}	
}