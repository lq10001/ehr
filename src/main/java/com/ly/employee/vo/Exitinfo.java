package com.ly.employee.vo;

import java.util.Date;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.View;

@Table("exitinfo")
public class Exitinfo{

	@Id
	@Column
	private Long id;

	@Column
	private Long employeeid;

	@Column
	private String exitform;

	@Column
	private Date lastday;

	@Column
	private String reason;

	@Column
	private String reason2;

	@Column
	private String memo;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(Long employeeid) {
		this.employeeid = employeeid;
	}

	public String getExitform() {
		return exitform;
	}

	public void setExitform(String exitform) {
		this.exitform = exitform;
	}

	public Date getLastday() {
		return lastday;
	}

	public void setLastday(Date lastday) {
		this.lastday = lastday;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getReason2() {
		return reason2;
	}

	public void setReason2(String reason2) {
		this.reason2 = reason2;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
