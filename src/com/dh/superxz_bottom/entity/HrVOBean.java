package com.dh.superxz_bottom.entity;

/**
 * @author 超级小志
 *
 */
public class HrVOBean extends BaseBean {
	
	private String _id; // 自增主键
	private String sub_id;
	private String reward_welfare;
	private String creater;
	private String reward_claim_details;
	private String sub_name;
	private String reward_salary;
	private String reward_wpost_idelfare;
	private String sub_address;
	private String id;
	private String reward_title;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getSub_id() {
		return sub_id;
	}

	public void setSub_id(String sub_id) {
		this.sub_id = sub_id;
	}

	public String getReward_welfare() {
		return reward_welfare;
	}

	public void setReward_welfare(String reward_welfare) {
		this.reward_welfare = reward_welfare;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getReward_claim_details() {
		return reward_claim_details;
	}

	public void setReward_claim_details(String reward_claim_details) {
		this.reward_claim_details = reward_claim_details;
	}

	public String getSub_name() {
		return sub_name;
	}

	public void setSub_name(String sub_name) {
		this.sub_name = sub_name;
	}

	public String getReward_salary() {
		return reward_salary;
	}

	public void setReward_salary(String reward_salary) {
		this.reward_salary = reward_salary;
	}

	public String getReward_wpost_idelfare() {
		return reward_wpost_idelfare;
	}

	public void setReward_wpost_idelfare(String reward_wpost_idelfare) {
		this.reward_wpost_idelfare = reward_wpost_idelfare;
	}

	public String getSub_address() {
		return sub_address;
	}

	public void setSub_address(String sub_address) {
		this.sub_address = sub_address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReward_title() {
		return reward_title;
	}

	public void setReward_title(String reward_title) {
		this.reward_title = reward_title;
	}

	@Override
	public String toString() {
		return "HrVOBean [sub_id=" + sub_id + ", reward_welfare="
				+ reward_welfare + ", creater=" + creater
				+ ", reward_claim_details=" + reward_claim_details
				+ ", sub_name=" + sub_name + ", reward_salary=" + reward_salary
				+ ", reward_wpost_idelfare=" + reward_wpost_idelfare
				+ ", sub_address=" + sub_address + ", id=" + id
				+ ", reward_title=" + reward_title + "]";
	}

}
