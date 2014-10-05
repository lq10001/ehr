package com.ly.employee.vo;

import java.util.Date;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.View;

@Table("employee")
public class Employee{

	@Id
	@Column
	private Long id;

	@Column
	private String no;

	@Column
	private String cnname;

	@Column
	private String name;

	@Column
	private Long genderid;

	@Column
	private String nation;

	@Column
	private String nativeplace;

	@Column
	private Long partyid;

	@Column
	private String hobby;

	@Column
	private String health;

	@Column
	private Long height;

	@Column
	private Long weight;

	@Column
	private Long bloodid;

	@Column
	private Long marital;

	@Column
	private Long procreateid;

	@Column
	private String native1;

	@Column
	private Long nativetypeid;

	@Column
	private Long ictypeid;

	@Column
	private String icno;

	@Column
	private String issue;

	@Column
	private Date usefullife;

	@Column
	private String idaddress;

	@Column
	private String address;

	@Column
	private String phone;

	@Column
	private String phone2;

	@Column
	private String linkman;

	@Column
	private String email;

	@Column
	private String emailwork;

	@Column
	private Long educationid;

	@Column
	private String major;

	@Column
	private String school;

	@Column
	private Date graduatetime;

	@Column
	private Date startworkingdate;

	@Column
	private Date birthday;

	@Column
	private Long age;

	@Column
	private String nationality;

	@Column
	private String cmbno;

	@Column
	private String housingno;

	@Column
	private String socialno;

	@Column
	private String socialaddress;

	@Column
	private String housingadress;

	@Column
	private String title;

	@Column
	private Long worktypeid;

	@Column
	private Date boarddate;

	@Column
	private Long deptid;

	@Column
	private String leader;

	@Column
	private Long position;

	@Column
	private String seatno;

	@Column
	private String personname1;

	@Column
	private String personreleation1;

	@Column
	private String personaddress1;

	@Column
	private String personname2;

	@Column
	private String personreleation2;

	@Column
	private String personaddress2;

	@Column
	private Date workstartdate1;

	@Column
	private Date workenddate1;

	@Column
	private String corpname1;

	@Column
	private String deptname1;

	@Column
	private Date workstartdate2;

	@Column
	private Date workenddate2;

	@Column
	private String corpname2;

	@Column
	private String deptname2;

	@Column
	private String memo;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getCnname() {
		return cnname;
	}

	public void setCnname(String cnname) {
		this.cnname = cnname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getGenderid() {
		return genderid;
	}

	public void setGenderid(Long genderid) {
		this.genderid = genderid;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getNativeplace() {
		return nativeplace;
	}

	public void setNativeplace(String nativeplace) {
		this.nativeplace = nativeplace;
	}

	public Long getPartyid() {
		return partyid;
	}

	public void setPartyid(Long partyid) {
		this.partyid = partyid;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getHealth() {
		return health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	public Long getHeight() {
		return height;
	}

	public void setHeight(Long height) {
		this.height = height;
	}

	public Long getWeight() {
		return weight;
	}

	public void setWeight(Long weight) {
		this.weight = weight;
	}

	public Long getBloodid() {
		return bloodid;
	}

	public void setBloodid(Long bloodid) {
		this.bloodid = bloodid;
	}

	public Long getMarital() {
		return marital;
	}

	public void setMarital(Long marital) {
		this.marital = marital;
	}

	public Long getProcreateid() {
		return procreateid;
	}

	public void setProcreateid(Long procreateid) {
		this.procreateid = procreateid;
	}

	public String getNative1() {
		return native1;
	}

	public void setNative1(String native1) {
		this.native1 = native1;
	}

	public Long getNativetypeid() {
		return nativetypeid;
	}

	public void setNativetypeid(Long nativetypeid) {
		this.nativetypeid = nativetypeid;
	}

	public Long getIctypeid() {
		return ictypeid;
	}

	public void setIctypeid(Long ictypeid) {
		this.ictypeid = ictypeid;
	}

	public String getIcno() {
		return icno;
	}

	public void setIcno(String icno) {
		this.icno = icno;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public Date getUsefullife() {
		return usefullife;
	}

	public void setUsefullife(Date usefullife) {
		this.usefullife = usefullife;
	}

	public String getIdaddress() {
		return idaddress;
	}

	public void setIdaddress(String idaddress) {
		this.idaddress = idaddress;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailwork() {
		return emailwork;
	}

	public void setEmailwork(String emailwork) {
		this.emailwork = emailwork;
	}

	public Long getEducationid() {
		return educationid;
	}

	public void setEducationid(Long educationid) {
		this.educationid = educationid;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public Date getGraduatetime() {
		return graduatetime;
	}

	public void setGraduatetime(Date graduatetime) {
		this.graduatetime = graduatetime;
	}

	public Date getStartworkingdate() {
		return startworkingdate;
	}

	public void setStartworkingdate(Date startworkingdate) {
		this.startworkingdate = startworkingdate;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getCmbno() {
		return cmbno;
	}

	public void setCmbno(String cmbno) {
		this.cmbno = cmbno;
	}

	public String getHousingno() {
		return housingno;
	}

	public void setHousingno(String housingno) {
		this.housingno = housingno;
	}

	public String getSocialno() {
		return socialno;
	}

	public void setSocialno(String socialno) {
		this.socialno = socialno;
	}

	public String getSocialaddress() {
		return socialaddress;
	}

	public void setSocialaddress(String socialaddress) {
		this.socialaddress = socialaddress;
	}

	public String getHousingadress() {
		return housingadress;
	}

	public void setHousingadress(String housingadress) {
		this.housingadress = housingadress;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getWorktypeid() {
		return worktypeid;
	}

	public void setWorktypeid(Long worktypeid) {
		this.worktypeid = worktypeid;
	}

	public Date getBoarddate() {
		return boarddate;
	}

	public void setBoarddate(Date boarddate) {
		this.boarddate = boarddate;
	}

	public Long getDeptid() {
		return deptid;
	}

	public void setDeptid(Long deptid) {
		this.deptid = deptid;
	}

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public Long getPosition() {
		return position;
	}

	public void setPosition(Long position) {
		this.position = position;
	}

	public String getSeatno() {
		return seatno;
	}

	public void setSeatno(String seatno) {
		this.seatno = seatno;
	}

	public String getPersonname1() {
		return personname1;
	}

	public void setPersonname1(String personname1) {
		this.personname1 = personname1;
	}

	public String getPersonreleation1() {
		return personreleation1;
	}

	public void setPersonreleation1(String personreleation1) {
		this.personreleation1 = personreleation1;
	}

	public String getPersonaddress1() {
		return personaddress1;
	}

	public void setPersonaddress1(String personaddress1) {
		this.personaddress1 = personaddress1;
	}

	public String getPersonname2() {
		return personname2;
	}

	public void setPersonname2(String personname2) {
		this.personname2 = personname2;
	}

	public String getPersonreleation2() {
		return personreleation2;
	}

	public void setPersonreleation2(String personreleation2) {
		this.personreleation2 = personreleation2;
	}

	public String getPersonaddress2() {
		return personaddress2;
	}

	public void setPersonaddress2(String personaddress2) {
		this.personaddress2 = personaddress2;
	}

	public Date getWorkstartdate1() {
		return workstartdate1;
	}

	public void setWorkstartdate1(Date workstartdate1) {
		this.workstartdate1 = workstartdate1;
	}

	public Date getWorkenddate1() {
		return workenddate1;
	}

	public void setWorkenddate1(Date workenddate1) {
		this.workenddate1 = workenddate1;
	}

	public String getCorpname1() {
		return corpname1;
	}

	public void setCorpname1(String corpname1) {
		this.corpname1 = corpname1;
	}

	public String getDeptname1() {
		return deptname1;
	}

	public void setDeptname1(String deptname1) {
		this.deptname1 = deptname1;
	}

	public Date getWorkstartdate2() {
		return workstartdate2;
	}

	public void setWorkstartdate2(Date workstartdate2) {
		this.workstartdate2 = workstartdate2;
	}

	public Date getWorkenddate2() {
		return workenddate2;
	}

	public void setWorkenddate2(Date workenddate2) {
		this.workenddate2 = workenddate2;
	}

	public String getCorpname2() {
		return corpname2;
	}

	public void setCorpname2(String corpname2) {
		this.corpname2 = corpname2;
	}

	public String getDeptname2() {
		return deptname2;
	}

	public void setDeptname2(String deptname2) {
		this.deptname2 = deptname2;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
