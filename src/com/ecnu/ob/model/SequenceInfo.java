package com.ecnu.ob.model;

public class SequenceInfo {

	private String sequence_name;
	private int data_type;
	private String current_value;
	private String increment_by;
	private String min_value;
	private String max_value;
	private int is_cycle;
	private int cache_num;
	private int is_order;
	private int is_valid;
	private String const_start_with;
	private int can_use_prevval;
	private int use_quick_path;
	public String getSequence_name() {
		return sequence_name;
	}
	public void setSequence_name(String sequence_name) {
		this.sequence_name = sequence_name;
	}
	public int getData_type() {
		return data_type;
	}
	public void setData_type(int data_type) {
		this.data_type = data_type;
	}
	public String getCurrent_value() {
		return current_value;
	}
	public void setCurrent_value(String current_value) {
		this.current_value = current_value;
	}
	public String getIncrement_by() {
		return increment_by;
	}
	public void setIncrement_by(String increment_by) {
		this.increment_by = increment_by;
	}
	public String getMin_value() {
		return min_value;
	}
	public void setMin_value(String min_value) {
		this.min_value = min_value;
	}
	public String getMax_value() {
		return max_value;
	}
	public void setMax_value(String max_value) {
		this.max_value = max_value;
	}
	public int getIs_cycle() {
		return is_cycle;
	}
	public void setIs_cycle(int is_cycle) {
		this.is_cycle = is_cycle;
	}
	public int getCache_num() {
		return cache_num;
	}
	public void setCache_num(int cache_num) {
		this.cache_num = cache_num;
	}
	public int getIs_order() {
		return is_order;
	}
	public void setIs_order(int is_order) {
		this.is_order = is_order;
	}
	public int getIs_valid() {
		return is_valid;
	}
	public void setIs_valid(int is_valid) {
		this.is_valid = is_valid;
	}
	public String getConst_start_with() {
		return const_start_with;
	}
	public void setConst_start_with(String const_start_with) {
		this.const_start_with = const_start_with;
	}
	public int getCan_use_prevval() {
		return can_use_prevval;
	}
	public void setCan_use_prevval(int can_use_prevval) {
		this.can_use_prevval = can_use_prevval;
	}
	public int getUse_quick_path() {
		return use_quick_path;
	}
	public void setUse_quick_path(int use_quick_path) {
		this.use_quick_path = use_quick_path;
	}
	
}
