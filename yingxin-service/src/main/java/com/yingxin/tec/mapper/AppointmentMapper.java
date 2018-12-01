package com.yingxin.tec.mapper;

import com.yingxin.tec.model.Appointment;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.math.BigInteger;
import java.util.Map;

@Repository
public interface AppointmentMapper{

	public List<Appointment> latestAppointments();

	public void insert(@Param(value = "appointment") Appointment appointment);

	public void update(@Param(value = "appointment") Appointment appointment);

	public void delete(@Param(value = "appointment") Appointment appointment);

	public BigInteger count(Map<String, Object> params);

	public List<Appointment> query(Map<String, Object> params);

}
