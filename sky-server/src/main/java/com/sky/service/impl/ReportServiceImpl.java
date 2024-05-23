package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

    @Autowired
    OrderMapper orderMapper;

    /**营业额统计
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public TurnoverReportVO getTurnoverStatics(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> localDates = new ArrayList<>();
        localDates.add(startDate);
        while(!startDate.equals(endDate)) {
            startDate = startDate.plusDays(1);
            localDates.add(startDate);
        }

        List<Double> turnovers = new ArrayList<>();

        for(LocalDate localDate : localDates) {
            LocalDateTime start = LocalDateTime.of(localDate, LocalTime.MIN);
            LocalDateTime end = LocalDateTime.of(localDate, LocalTime.MAX);

            Map map = new HashMap();
            map.put("start", start);
            map.put("end", end);
            map.put("status", Orders.COMPLETED);
            Double turnover = orderMapper.sumByDate(map);
            turnover = turnover == null ? 0 : turnover;
            turnovers.add(turnover);
        }



        String dateString = StringUtils.join(localDates, ",");
        String turnoverString = StringUtils.join(turnovers, ",");


        return TurnoverReportVO.builder().dateList(dateString).turnoverList(turnoverString).build();
    }
}
