package com.sky.service;

import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

import javax.servlet.http.HttpServletResponse;
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

    /**订单统计
     *
     * @param begin
     * @param end
     * @return
     */
    OrderReportVO getOrderStatics(LocalDate begin, LocalDate end);

    /**top10
     *
     * @param begin
     * @param end
     * @return
     */
    SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end);

    /**数据导出
     *
     * @param response
     */
    void exportBusinessData(HttpServletResponse response);
}
