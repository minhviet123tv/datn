package model;

public class Provinces {
	private int administrative_unit_id, administrative_region_id;
	private String code, name, name_en, full_name, full_name_en, code_name;
	
	public Provinces () {
		
	}

	public Provinces(int administrative_unit_id, int administrative_region_id, String code, String name, String name_en,
			String full_name, String full_name_en, String code_name) {
		super();
		this.administrative_unit_id = administrative_unit_id;
		this.administrative_region_id = administrative_region_id;
		this.code = code;
		this.name = name;
		this.name_en = name_en;
		this.full_name = full_name;
		this.full_name_en = full_name_en;
		this.code_name = code_name;
	}

	public int getAdministrative_unit_id() {
		return administrative_unit_id;
	}

	public void setAdministrative_unit_id(int administrative_unit_id) {
		this.administrative_unit_id = administrative_unit_id;
	}

	public int getAdministrative_region_id() {
		return administrative_region_id;
	}

	public void setAdministrative_region_id(int administrative_region_id) {
		this.administrative_region_id = administrative_region_id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName_en() {
		return name_en;
	}

	public void setName_en(String name_en) {
		this.name_en = name_en;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getFull_name_en() {
		return full_name_en;
	}

	public void setFull_name_en(String full_name_en) {
		this.full_name_en = full_name_en;
	}

	public String getCode_name() {
		return code_name;
	}

	public void setCode_name(String code_name) {
		this.code_name = code_name;
	}
	
}
