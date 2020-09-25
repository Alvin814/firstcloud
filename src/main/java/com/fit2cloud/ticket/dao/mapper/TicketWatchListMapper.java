package com.fit2cloud.ticket.dao.mapper;

import com.fit2cloud.ticket.dao.model.TicketWatchList;
import com.fit2cloud.ticket.dao.model.TicketWatchListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TicketWatchListMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_watch_list
     *
     * @mbg.generated
     */
    long countByExample(TicketWatchListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_watch_list
     *
     * @mbg.generated
     */
    int deleteByExample(TicketWatchListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_watch_list
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_watch_list
     *
     * @mbg.generated
     */
    int insert(TicketWatchList record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_watch_list
     *
     * @mbg.generated
     */
    int insertSelective(TicketWatchList record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_watch_list
     *
     * @mbg.generated
     */
    List<TicketWatchList> selectByExample(TicketWatchListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_watch_list
     *
     * @mbg.generated
     */
    TicketWatchList selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_watch_list
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TicketWatchList record, @Param("example") TicketWatchListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_watch_list
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TicketWatchList record, @Param("example") TicketWatchListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_watch_list
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TicketWatchList record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_watch_list
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TicketWatchList record);
}