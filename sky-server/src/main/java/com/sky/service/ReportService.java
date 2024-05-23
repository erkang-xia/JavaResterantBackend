package com.sky.service;

import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

import java.time.LocalDate;

public interface ReportService {

    /**营业额统计
     *
     * @param startDate
     * @param endDate
     * @return
     */
    TurnoverReportVO getTurnoverStatics(LocalDate startDate, LocalDate endDate);

    /**用户统计
     *
     * @param begin
     * @param end
     * @return
     */
    UserReportVO getUserStatics(LocalDate begin, LocalDate end);
}
