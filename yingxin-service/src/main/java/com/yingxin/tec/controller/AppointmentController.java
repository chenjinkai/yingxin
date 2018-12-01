package com.yingxin.tec.controller;

import com.yingxin.tec.exception.BusinessException;
import com.yingxin.tec.model.Appointment;
import com.yingxin.tec.model.DatatableQuery;
import com.yingxin.tec.model.DatatableRequest;
import com.yingxin.tec.service.AppointmentService;
import com.yingxin.tec.util.HttpUtil;
import com.yingxin.tec.validator.ValidateResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigInteger;

@RestController
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @RequestMapping(value = "/appointments", method = {RequestMethod.GET})
    @ResponseBody
    public Object index(@Validated(DatatableQuery.class) @ModelAttribute DatatableRequest datatableRequest,
                        BindingResult binding,
                        HttpServletRequest request,
                        HttpServletResponse response) throws BusinessException {
        if (binding.hasErrors()) {
            return ValidateResponseFactory.createValidateFailureResponse(binding);
        }
        return HttpUtil.getJSONPResponse(appointmentService.getTableData(datatableRequest), true, request, response);
    }

    @RequestMapping(value = "/appointments2", method = {RequestMethod.GET})
    @ResponseBody
    public Object getLatestAppointment() {
        return appointmentService.getLatestData();
    }

    @RequestMapping(value = "/appointments", method = {RequestMethod.POST})
    @ResponseBody
    public Object add(@Valid @RequestParam Appointment appointment,
                      BindingResult binding,
                      HttpServletRequest request,
                      HttpServletResponse response) throws BusinessException {
        if (binding.hasErrors()) {
            return ValidateResponseFactory.createValidateFailureResponse(binding);
        }
        return HttpUtil.getJSONPResponse(appointmentService.add(appointment), true, request, response);
    }

    @RequestMapping(value = "/appointments", method = {RequestMethod.PUT})
    @ResponseBody
    public Object update(@Valid @RequestParam Appointment appointment,
                         BindingResult binding,
                         HttpServletRequest request,
                         HttpServletResponse response) throws BusinessException {
        if (binding.hasErrors()) {
            return ValidateResponseFactory.createValidateFailureResponse(binding);
        }
        return HttpUtil.getJSONPResponse(appointmentService.update(appointment), true, request, response);
    }

    @RequestMapping(value = "/appointments/{id}", method = {RequestMethod.DELETE})
    @ResponseBody
    public Object delete(@PathVariable(value = "id") BigInteger id,
                         HttpServletRequest request,
                         HttpServletResponse response) throws BusinessException {
        return HttpUtil.getJSONPResponse(appointmentService.delete(id), true, request, response);
    }

}
