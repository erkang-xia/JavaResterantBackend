package com.sky.service.impl;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
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
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    UserMapper userMapper;

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

    /**用户数据统计
     *
     * @param begin
     * @param end
     * @return
     */
    public UserReportVO getUserStatics(LocalDate begin, LocalDate end) {
        List<LocalDate> localDates = new ArrayList<>();
        localDates.add(begin);
        while(!begin.equals(end)) {
            begin = begin.plusDays(1);
            localDates.add(begin);
        }

        List<Integer> userCounts = new ArrayList<>();
        List<Integer> totalCounts = new ArrayList<>();
        for(LocalDate localDate : localDates) {
            LocalDateTime start = LocalDateTime.of(localDate, LocalTime.MIN);
            LocalDateTime finish = LocalDateTime.of(localDate, LocalTime.MAX);

            Map map = new HashMap();
            Integer totalUserCount = userMapper.countByDate(map);
            totalUserCount = totalUserCount == null ? 0 : totalUserCount;
            map.put("end", finish);
            totalCounts.add(totalUserCount);
            map.put("start", start);
            Integer userCount = userMapper.countByDate(map);
            userCount = userCount == null ? 0 : userCount;
            userCounts.add(userCount);
        }


        return UserReportVO.builder().newUserList(StringUtils.join(userCounts,","))
                .dateList(StringUtils.join(localDates,","))
                .totalUserList(StringUtils.join(totalCounts,",")).build();
    }

    /**订单统计
     *
     * @param begin
     * @param end
     * @return
     */
    public OrderReportVO getOrderStatics(LocalDate begin, LocalDate end) {
        List<LocalDate> localDates = new ArrayList<>();
        localDates.add(begin);
        while(!begin.equals(end)) {
            begin = begin.plusDays(1);
            localDates.add(begin);
        }

        List<Integer> trueOrderCounts = new ArrayList<>();
        List<Integer> totalCounts = new ArrayList<>();
        for(LocalDate localDate : localDates) {
            LocalDateTime start = LocalDateTime.of(localDate, LocalTime.MIN);
            LocalDateTime finish = LocalDateTime.of(localDate, LocalTime.MAX);

            trueOrderCounts.add(getOrderCount(start,finish,Orders.COMPLETED));
            totalCounts.add(getOrderCount(start,finish,null));
        }

        Integer totalCount = totalCounts.stream().reduce(Integer::sum).get();
        Integer validCount = trueOrderCounts.stream().reduce(Integer::sum).get();

        Double orderCompleteRate = 0.0;
        if(totalCount!=0){
            orderCompleteRate = validCount.doubleValue()/totalCount;
        }

        return OrderReportVO.builder()
                .dateList(StringUtils.join(localDates,","))
                .orderCountList(StringUtils.join(totalCounts,","))
                .validOrderCountList(StringUtils.join(trueOrderCounts,","))
                .totalOrderCount(totalCount)
                .validOrderCount(validCount)
                .orderCompletionRate(orderCompleteRate)
                .build();
    }

    /**top10
     *
     * @param begin
     * @param end
     * @return
     */
    public SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end) {
        LocalDateTime start = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime finish = LocalDateTime.of(end, LocalTime.MAX);
        List<GoodsSalesDTO> top10 = orderMapper.getTop10(start,finish);
        List<String> names = top10.stream().map(GoodsSalesDTO::getName).collect(Collectors.toList());
        List<Integer> number = top10.stream().map(GoodsSalesDTO::getNumber).collect(Collectors.toList());


        return SalesTop10ReportVO.builder()
                .nameList(StringUtils.join(names,","))
                .numberList(StringUtils.join(number,","))
                .build();
    }

    private Integer getOrderCount(LocalDateTime begin, LocalDateTime end, Integer status) {
        Map map = new HashMap();
        map.put("end", end);
        map.put("start", begin);
        map.put("status", status);
        return orderMapper.countByDate(map);
    }
}
