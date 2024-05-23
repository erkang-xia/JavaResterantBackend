package com.sky.service;

import com.sky.vo.TurnoverReportVO;

import java.time.LocalDate;

public interface ReportService {

    /**营业额统计
     *
     * @param startDate
     * @param endDate
     * @return
     */
    TurnoverReportVO getTurnoverStatics(LocalDate startDate, LocalDate endDate);
}
