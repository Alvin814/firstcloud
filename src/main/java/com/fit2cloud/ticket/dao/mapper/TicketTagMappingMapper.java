package com.fit2cloud.ticket.dao.mapper;

import com.fit2cloud.ticket.dao.model.TicketTagMapping;
import com.fit2cloud.ticket.dao.model.TicketTagMappingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TicketTagMappingMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_tag_mapping
     *
     * @mbg.generated
     */
    long countByExample(TicketTagMappingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_tag_mapping
     *
     * @mbg.generated
     */
    int deleteByExample(TicketTagMappingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_tag_mapping
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_tag_mapping
     *
     * @mbg.generated
     */
    int insert(TicketTagMapping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_tag_mapping
     *
     * @mbg.generated
     */
    int insertSelective(TicketTagMapping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_tag_mapping
     *
     * @mbg.generated
     */
    List<TicketTagMapping> selectByExample(TicketTagMappingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_tag_mapping
     *
     * @mbg.generated
     */
    TicketTagMapping selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_tag_mapping
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TicketTagMapping record, @Param("example") TicketTagMappingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_tag_mapping
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TicketTagMapping record, @Param("example") TicketTagMappingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_tag_mapping
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TicketTagMapping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_tag_mapping
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TicketTagMapping record);
}