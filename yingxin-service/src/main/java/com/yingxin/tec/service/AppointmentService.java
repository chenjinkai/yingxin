package com.yingxin.tec.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yingxin.tec.exception.BusinessException;
import com.yingxin.tec.mapper.AppointmentMapper;
import com.yingxin.tec.model.Appointment;
import com.yingxin.tec.model.DatatableRequest;
import com.yingxin.tec.model.ResultResponse;
import com.yingxin.tec.util.MybatisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Transactional
    public ResultResponse add(Appointment appointment) throws BusinessException {
        try {
            appointmentMapper.insert(appointment);
        } catch (Exception e) {
            throw new BusinessException("增加预约失败", e);
        }
        return ResultResponse.OK.setMsg("增加预约成功");
    }

    @Transactional
    public ResultResponse delete(BigInteger id) throws BusinessException {
        try {
            Appointment appointment = new Appointment();
            appointment.setId(id);
            appointmentMapper.delete(appointment);
        } catch (Exception e) {
            throw new BusinessException("删除预约失败", e);
        }
        return ResultResponse.OK.setMsg("删除预约成功");
    }

    @Transactional
    public ResultResponse update(Appointment appointment) throws BusinessException {
        try {
            appointmentMapper.update(appointment);
        } catch (Exception e) {
            throw new BusinessException("更新预约失败", e);
        }
        return ResultResponse.OK.setMsg("更新预约成功");
    }

    public ResultResponse getLatestData() {
        return ResultResponse.OK.setData(appointmentMapper.latestAppointments());
    }

    @Transactional
    public JSONObject getTableData(DatatableRequest datatableRequest) throws BusinessException {
        JSONObject data = new JSONObject();
        JSONArray dataResult = new JSONArray();
        data.put("data", dataResult);
        try {
            Map<String, Object> params = MybatisUtil.datatableRequest2Map(datatableRequest);
            List<Appointment> appointments = appointmentMapper.query(params);
            if (appointments != null && appointments.size() > 0) {
                for (Appointment appointment : appointments) {
                    JSONObject result = new JSONObject();
                    result.put("id", appointment.getId());
                    result.put("username", appointment.getUsername());
                    result.put("phone", appointment.getPhone());
                    result.put("appointmentdate", appointment.getAppointmentdate());
                    result.put("city", appointment.getCity());
                    result.put("district", appointment.getDistrict());
                    result.put("address", appointment.getAddress());
                    result.put("description", appointment.getDescription());
                    result.put("ip", appointment.getIp());
                    result.put("status", appointment.getStatus());
                    result.put("updatedat", appointment.getUpdatedat());
                    result.put("createdat", appointment.getCreatedat());
                    dataResult.add(result);
                }
            }
        } catch (Exception e) {
            throw new BusinessException("获取预约数据失败", e);
        }
        return data;
    }

}
