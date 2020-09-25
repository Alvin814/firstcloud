package com.fit2cloud.ticket.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fit2cloud.commons.server.base.domain.*;
import com.fit2cloud.commons.server.base.mapper.FlowModelMapper;
import com.fit2cloud.commons.server.base.mapper.UserMapper;
import com.fit2cloud.commons.server.base.mapper.WorkspaceMapper;
import com.fit2cloud.commons.server.exception.F2CException;
import com.fit2cloud.commons.server.process.ProcessService;
import com.fit2cloud.commons.server.process.TaskService;
import com.fit2cloud.commons.server.service.RoleCommonService;
import com.fit2cloud.commons.server.utils.IDGenerator;
import com.fit2cloud.commons.server.utils.MetricUtils;
import com.fit2cloud.commons.server.utils.SessionUtils;
import com.fit2cloud.commons.server.utils.WorkspaceUtils;
import com.fit2cloud.commons.utils.BeanUtils;
import com.fit2cloud.commons.utils.LogUtil;
import com.fit2cloud.commons.utils.UUIDUtil;
import com.fit2cloud.ticket.constants.TicketConstants;
import com.fit2cloud.ticket.controller.request.*;
import com.fit2cloud.ticket.dao.mapper.GHCostMapper;
import com.fit2cloud.ticket.dao.mapper.TicketConfigMapper;
import com.fit2cloud.ticket.dao.mapper.TicketMapper;
import com.fit2cloud.ticket.dao.mapper.TicketWatchListMapper;
import com.fit2cloud.ticket.dao.mapper.ext.ExtTicketMapper;
import com.fit2cloud.ticket.dao.model.*;
import com.fit2cloud.ticket.dao.model.ext.ExtTicket;
import com.fit2cloud.ticket.dao.model.ext.TicketCount;
import com.fit2cloud.ticket.exception.BusinessException;
import com.fit2cloud.ticket.exception.ReturnCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Service
@Transactional(rollbackFor = Exception.class)
public class TicketService {

    @Value("${spring.application.name:null}")
    private String moduleId;

    @Resource
    private RoleCommonService roleCommonService;

    @Resource
    private ProcessService processService;

    @Resource
    private TaskService taskService;

    @Resource
    private TicketConfigMapper ticketConfigMapper;

    @Resource
    private TicketMapper ticketMapper;

    @Resource
    private GHCostMapper ghCostMapper;

    @Resource
    private ExtTicketMapper extTicketMapper;

    @Resource
    private WorkspaceMapper workspaceMapper;

    @Resource
    private TicketWatchListMapper ticketWatchListMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private FlowModelMapper flowModelMapper;

    public void addRoleParameters(ListTicketRequest request) {
        switch (roleCommonService.getCurrentRoleId()) {
            case USER:
                request.setWorkspaceId(SessionUtils.getUser().getWorkspaceId());
                request.setOwner(SessionUtils.getUser().getId());
                break;
            case ORGADMIN:
                request.setWorkspaceIds(WorkspaceUtils.getWorkspaceIdsByOrgIds(SessionUtils.getUser().getOrganizationId()));
                // 组织下没有工作空间时无法查到数据，所以随便设一个查询值（时间戳）
                if (request.getWorkspaceIds().isEmpty()) {
                    request.getWorkspaceIds().add(System.currentTimeMillis() + "");
                }
                break;
            default:
                break;
        }
    }

    private String getHTime(int time){
        int day = time/(24 * 60 * 60);
        int h = time % (24 * 60 * 60) / (60 * 60);
        int m = time % (24 * 60 * 60) % (60 * 60) / 60;
        String hTime = day + "天" + h + "小时" + m +"分钟";
        return  hTime;
    }

    public List<ExtTicket> list(ListTicketRequest request) {
        List<Ticket> tickets = extTicketMapper.listTicket(request);
        List<ExtTicket> extTickets = new ArrayList <>();
        for(Ticket ticket:tickets){
            ExtTicket extTicket = new ExtTicket();
            BeanUtils.copyBean(extTicket,ticket);
            if(ticket.getStatus().equals("RUNNING")){
                int handleTime = (int) ((System.currentTimeMillis() - ticket.getTime())/1000);
                ticket.setHandleTime(handleTime);
            }
            if(ticket.getHandleTime() != null) {
                extTicket.setViewHandleTime(getHTime(ticket.getHandleTime()));
            }
            extTickets.add(extTicket);
        }
        return  extTickets;
    }

    public Ticket getTicketById(String id) {
        return ticketMapper.selectByPrimaryKey(id);
    }

    public Ticket getTicketByTaskId(String taskId) {
        FlowTask flowTask = taskService.getTask(taskId);
        if (flowTask != null && StringUtils.equals(flowTask.getTaskAssignee(), SessionUtils.getUser().getId())) {
            return getTicketById(flowTask.getBusinessKey());
        }
        return null;
    }

    public Ticket create(CreateTicketRequest request) {
        if (StringUtils.isBlank(request.getConfigId())) {
            F2CException.throwException("工单模板ID不能为空");
        }

        TicketConfig ticketConfig = ticketConfigMapper.selectByPrimaryKey(request.getConfigId());
        if (ticketConfig == null) {
            F2CException.throwException("工单模板不存在，工单模板ID: " + request.getConfigId());
        }

        if (StringUtils.isBlank(request.getId())) {
            String ticketId = IDGenerator.newBusinessId(TicketConstants.TICKET_ID_PREFIX, SessionUtils.getUser().getWorkspaceId());
            request.setId(ticketId);
        }

        String processId = UUID.randomUUID().toString();

        Ticket ticket = new Ticket();
        ticket.setId(request.getId());
        ticket.setProcessId(processId);
        ticket.setTime(System.currentTimeMillis());
        ticket.setCreator(SessionUtils.getUser().getId());
        ticket.setWorkspaceId(SessionUtils.getUser().getWorkspaceId());
        ticket.setStatus(TicketConstants.Status.RUNNING.name());
        ticket.setConfigId(request.getConfigId());
        ticket.setFormId(ticketConfig.getFormId());
        ticket.setModelId(ticketConfig.getModelId());
        ticket.setName(Optional.of(request.getName()).orElse(ticketConfig.getName()));
        ticket.setContent(request.getContent());
        ticket.setOrganizationId(SessionUtils.getUser().getOrganizationId());

        processService.createProcess(ticket.getModelId(), processId, ticket.getCreator(), ticket.getId(), moduleId);
        ticketMapper.insert(ticket);
        return ticket;
    }

    public int delete(String id) {
        return ticketMapper.deleteByPrimaryKey(id);
    }

    public void complete(ApproveTicketRequest approveTicketRequest) {
        Ticket ticket = approveTicketRequest.getTicket();
        FlowTask flowTask = taskService.getTask(approveTicketRequest.getTaskId());
        flowTask.setTaskRemarks(approveTicketRequest.getRemarks());

        int handleTime = (int) (System.currentTimeMillis() - ticket.getTime())/1000;
        ticket.setHandleTime(handleTime);
        //判断是否处理了工时工单
        Map <Object, Object> exTicketMap = new HashMap <>();
        JSONObject jsonObject = JSONObject.parseObject(ticket.getContent());
        Object json = jsonObject.get("rows");
        JSONArray jsonArray = JSONArray.parseArray(json.toString());
        for (int i = 0;i  < jsonArray.size(); i++){
            JSONObject obj =  JSONObject.parseObject(jsonArray.get(i).toString());
            Object ticketObj = obj.get("cells");
            JSONArray ticketJsonArray = JSONArray.parseArray(ticketObj.toString());
            for(int j = 0; j < ticketJsonArray.size(); j++){
                JSONObject cellObject = JSONObject.parseObject(ticketJsonArray.get(j).toString());
                if(cellObject.get("element") != null){
                    Object cellObj = cellObject.get("element");
                    Map <String,Object> map = (Map <String, Object>) cellObj;
                    Map <String,Object> cellMap = (Map <String, Object>) map.get("property");


                    if (null == cellMap.get("id")) {
                        continue;
                    }
                    if (!cellMap.get("id").equals("16e0d7b7-7d17-494a-ae3f-83346737fe0e")) {
                        continue;
                    }
                    exTicketMap.put(cellMap.get("label"), cellMap.get("value"));
                }
            }
        }




        if (!exTicketMap.isEmpty()) {
            GHCostExample example = new GHCostExample();
            example.createCriteria().andTicketIdEqualTo(ticket.getId());
            List<GHCost> ghCosts = ghCostMapper.selectByExample(example);
            if (CollectionUtils.isEmpty(ghCosts)) {
                GHCost ghCost = new GHCost();
                ghCost.setId(UUIDUtil.newUUID());
                ghCost.setTicketId(ticket.getId());
                ghCost.setUserId(SessionUtils.getUser().getId());
                ghCost.setWorkspaceId(ticket.getWorkspaceId());
                ghCost.setOrganizationId(ticket.getOrganizationId());
                ghCost.setCost(BigDecimal.valueOf(Long.parseLong(exTicketMap.get("工单处理耗时（单位：小时）").toString())));
                // 设置同步时间
                Long time = System.currentTimeMillis();
                MetricUtils.fillSyncDate(time, ghCost);
                ghCost.setTime(time);
                ghCostMapper.insertSelective(ghCost);
            }
        }


        ticketMapper.updateByPrimaryKeySelective(ticket);
        taskService.complete(flowTask, null);
    }

    public void reject(ApproveTicketRequest approveTicketRequest) {
        FlowTask flowTask = taskService.getTask(approveTicketRequest.getTaskId());
        flowTask.setTaskRemarks(approveTicketRequest.getRemarks());
        taskService.reject(flowTask);
        Ticket ticket = approveTicketRequest.getTicket();
        int handleTime = (int) (System.currentTimeMillis() - ticket.getTime())/1000;
        ticket.setHandleTime(handleTime);
        ticket.setStatus(TicketConstants.Status.REJECT.name());
        ticketMapper.updateByPrimaryKeySelective(ticket);
    }

    public void cancel(CancelTicketRequest cancelTicketRequest) {
        Ticket ticket = ticketMapper.selectByPrimaryKey(cancelTicketRequest.getTicketId());
        processService.cancelProcess(ticket.getProcessId(), SessionUtils.getUser().getId());
        int handleTime = (int) (System.currentTimeMillis() - ticket.getTime())/1000;
        ticket.setHandleTime(handleTime);
        ticket.setStatus(TicketConstants.Status.CANCEL.name());
        ticketMapper.updateByPrimaryKeySelective(ticket);
    }

    public void updateTicketStatus(String id, String statues) {
        Ticket ticket = ticketMapper.selectByPrimaryKey(id);
        if (ticket != null) {
            ticket.setStatus(statues);
            ticketMapper.updateByPrimaryKeyWithBLOBs(ticket);
        } else {
            LogUtil.error("工单不存在，id：" + id);
        }
    }

    public TicketCount analysisAllTicket() {
        return extTicketMapper.analysisAllTicket();
    }

    public TicketCount analysisMyTicket() {
        return extTicketMapper.analysisMyTicket(SessionUtils.getUser().getId(), SessionUtils.getUser().getSourceId());
    }

    /**
     * 获取工单处理时长
     * @param ticket
     * @param flowTask
     * @return
     */
    public int getHandleTime(Ticket ticket,FlowTask flowTask){
        FlowModel flowModel = flowModelMapper.selectByPrimaryKey(ticket.getModelId());
        JSONObject content = JSONObject.parseObject(flowModel.getModelContent());
        Object json = content.get("activities");
        JSONArray jsonArray = JSONArray.parseArray(json.toString());
        JSONObject contentStep = JSONObject.parseObject(jsonArray.get(jsonArray.size()-1).toString());
        int step = Integer.parseInt(contentStep.get("step").toString());
        int handleTime = 0;
        if(step == flowTask.getTaskStep()) {
            handleTime = (int) ((flowTask.getTaskStartTime() - ticket.getTime()) / 1000 / 60 / 60);
        } else {
            handleTime = (int) ((new Date().getTime() - ticket.getTime()) / 1000 / 60 / 60);
        }

        return handleTime;
    }

    /**
     * 对工单信息进行截取，获取紧急程度，联系人，电话等信息
     * @param ticket
     * @return
     */
    public ExtTicket getExtTicket(Ticket ticket){
           ExtTicket extTicket = new ExtTicket();
           BeanUtils.copyBean(extTicket,ticket);

           Map <Object, Object> exTicketMap = new HashMap <>();

           JSONObject jsonObject = JSONObject.parseObject(ticket.getContent());
           Object json = jsonObject.get("rows");
           JSONArray jsonArray = JSONArray.parseArray(json.toString());

           for (int i=0;i<jsonArray.size();i++){
               JSONObject obj =  JSONObject.parseObject(jsonArray.get(i).toString());
               Object ticketObj = obj.get("cells");
               JSONArray ticketJsonArray = JSONArray.parseArray(ticketObj.toString());
               for(int j =0;j<ticketJsonArray.size();j++){
                   JSONObject cellObject = JSONObject.parseObject(ticketJsonArray.get(j).toString());
                   if(cellObject.get("element") != null){
                       Object cellObj = cellObject.get("element");
                       Map <String,Object> map = (Map <String, Object>) cellObj;
                       Map <String,Object> cellMap = (Map <String, Object>) map.get("property");
                       exTicketMap.put(cellMap.get("label"),cellMap.get("value"));
                   }
               }
           }

//            if(null != exTicketMap.get("联系人").toString()){
//               extTicket.setLinkman(exTicketMap.get("联系人").toString());
//            }
//            if(null != exTicketMap.get("联系电话").toString()){
//                extTicket.setPhone(exTicketMap.get("联系电话").toString());
//            }
//            if(null != exTicketMap.get("处理期限").toString()){
//                extTicket.setEndTime(exTicketMap.get("处理期限").toString());
//            }
//            if(null != exTicketMap.get("紧急程度").toString()){
//                extTicket.setPriority(exTicketMap.get("紧急程度").toString());
//            }
//
//            if(null != exTicketMap.get("工单处理确认")){
//                extTicket.setHandleResult(exTicketMap.get("工单处理确认").toString());
//            }
//            if(null != exTicketMap.get("处理速度评价(分)")){
//                extTicket.setSpeed(exTicketMap.get("处理速度评价(分)").toString());
//            }
//            if(null != exTicketMap.get("处理态度评价(分)")){
//                extTicket.setHandleAttitude(exTicketMap.get("处理态度评价(分)").toString());
//            }
//            if(null != exTicketMap.get("处理满意度评价(分)")){
//                extTicket.setHandleSatisfaction(exTicketMap.get("处理满意度评价(分)").toString());
//            }

            return  extTicket;
    }

    /**
     * 导出工单
     * @param response
     * @param request
     */
    public void exportTicket(HttpServletResponse response, ListTicketRequest request){
        List<Ticket> tickets = extTicketMapper.listTicket(request);
        OutputStream out;
        try {
            out = response.getOutputStream();
            write(out, tickets);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(OutputStream out,List<Ticket> tickets){
        //初始一个workbook
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建单个sheet
        String sheetName = "Sheet0";
        HSSFSheet sheet = workbook.createSheet(sheetName);
        //创建多行
        //创建第一行，设置列名
        HSSFRow row0 = sheet.createRow(0);
        int baseIndex = 7;
        int index = tickets.size();
        for (int cellIndex = 0; cellIndex < baseIndex; cellIndex++) {
            HSSFCell cell = row0.createCell(cellIndex);
            switch (cellIndex) {
                case 0:
                    cell.setCellValue("工单ID");
                    break;
                case 1:
                    cell.setCellValue("工单名称");
                    break;
                case 2:
                    cell.setCellValue("提交人");
                    break;
                case 3:
                    cell.setCellValue("工作空间");
                    break;
                case 4:
                    cell.setCellValue("工单状态");
                    break;
                case 5:
                    cell.setCellValue("提交日期");
                    break;
                case 6:
                    cell.setCellValue("处理时长(h)");
                    break;
            }
        }
        //创建剩余行
        for (int rowIndex = 1; rowIndex <= index; rowIndex++) {
            HSSFRow row = sheet.createRow(rowIndex);
            Ticket ticket = tickets.get(rowIndex-1);
            ExtTicket extTicket = getExtTicket(ticket);

            //创建多列
            for(int cellIndex = 0; cellIndex < baseIndex; cellIndex++){
                HSSFCell cell = row.createCell(cellIndex);
                switch (cellIndex){
                    case 0:
                        cell.setCellValue(extTicket.getId());
                        break;
                    case 1:
                        cell.setCellValue(extTicket.getName());
                        break;
                    case 2:
                        cell.setCellValue(extTicket.getCreator());
                        break;
                    case 3:
                        Workspace workspace = workspaceMapper.selectByPrimaryKey(extTicket.getWorkspaceId());
                        if (null != workspace) {
                            cell.setCellValue(workspace.getName());
                        }
                        break;
                    case 4:
                        if(ticket.getStatus().equals("RUNNING")){
                            cell.setCellValue("待处理");
                        }else if(ticket.getStatus().equals("COMPLETE")){
                            cell.setCellValue("已完成");
                        }else if(ticket.getStatus().equals("TERMINATED")){
                            cell.setCellValue("已终止");
                        }else if(ticket.getStatus().equals("CANCEL")){
                            cell.setCellValue("已撤销");
                        }else if(ticket.getStatus().equals("REJECT")){
                            cell.setCellValue("已驳回");
                        }
                        break;
                    case 5:
                        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String sd = sdf.format(new Date(extTicket.getTime()));// 时间戳转换成时间
                        cell.setCellValue(sd);
                        break;
                    case 6:
                        if(ticket.getHandleTime() == null){
                            cell.setCellValue("");
                        }else {
                            cell.setCellValue(extTicket.getHandleTime());
                        }
                        break;
                }

            }

        }

        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 值班人列表
     * @param request
     * @return
     */
    public List<TicketWatchList> getWatchLists(TicketWatchListRequest request){
        TicketWatchListExample example = new TicketWatchListExample();
        TicketWatchListExample.Criteria criteria = example.createCriteria();

        if( null != request.getUserName()){
            criteria.andDayUserNameGreaterThan(request.getUserName());
        }
        if( null != request.getWatchDate()){
            criteria.andWatchDateEqualTo(request.getWatchDate());
        }

        List<TicketWatchList> ticketWatchLists = ticketWatchListMapper.selectByExample(example);

        return ticketWatchLists;
    }

    /**
     * 获取值班人的name以及ID
     * @param userIds
     * @return
     */
    private List<String> getWatchUsersInfo(String userIds){
        String usersId = "";
        String userName = "";
        String[] usersArray = userIds.substring(1,userIds.length()-1).split(",");
        for (int i=0;i<usersArray.length-1;i++){
            usersId =usersId + usersArray[i].substring(1,usersArray[i].length()-1)+",";
            userName =userName + userMapper.selectByPrimaryKey(usersArray[i].substring(1,usersArray[i].length()-1)).getName()+",";
        }
        usersId = "["+ usersId + usersArray[usersArray.length-1].substring(1,usersArray[usersArray.length-1].length()-1)+"]" ;
        userName = userName + userMapper.selectByPrimaryKey(usersArray[usersArray.length-1].substring(1,usersArray[usersArray.length-1].length()-1)).getName();
        
        List<String> list = new ArrayList <>();
        list.add(usersId);
        list.add(userName);

        return list;
    }

    /**
     * 创建值班人
     * @param record
     */
    public void createWatchList(TicketWatchList record) {
        if (null == record) {
            throw new BusinessException(ReturnCode.E400000, "请求目标不存在");
        }

        TicketWatchList ticketWatchList = new TicketWatchList();
        List<String> dayUsers = getWatchUsersInfo(record.getDayUserId());
        List<String> nightUsers = getWatchUsersInfo(record.getNightUserId());
        ticketWatchList.setDayUserId(dayUsers.get(0));
        ticketWatchList.setDayUserName(dayUsers.get(1));
        ticketWatchList.setNightUserId(nightUsers.get(0));
        ticketWatchList.setNightUserName(nightUsers.get(1));
        ticketWatchList.setWatchDate(record.getWatchDate());

        ticketWatchListMapper.insert(ticketWatchList);
    }

    /**
     * 更新值班人
     * @param record
     */
    public void updateWatchList(TicketWatchList record){

        TicketWatchList dbRecord = ticketWatchListMapper.selectByPrimaryKey(record.getId());
        if (null == dbRecord) {
            throw new BusinessException(ReturnCode.E402000, "请求目标不存在");
        }
        List<String> dayUsers = getWatchUsersInfo(record.getDayUserId());
        List<String> nightUsers = getWatchUsersInfo(record.getNightUserId());
        dbRecord.setDayUserId(dayUsers.get(0));
        dbRecord.setDayUserName(dayUsers.get(1));
        dbRecord.setNightUserId(nightUsers.get(0));
        dbRecord.setNightUserName(nightUsers.get(1));

        ticketWatchListMapper.updateByPrimaryKey(dbRecord);
    }

    /**
     * 删除值班人
     * @param id
     */
    public void deleteWatchList(Integer id){
        if (null == id) {
            throw new BusinessException(ReturnCode.E400000, "请求目标不存在");
        }
        ticketWatchListMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取所有值班人信息
     * @return
     */
    public List<User> getDayusers(){
        UserExample example = new UserExample();
        List<User> users = userMapper.selectByExample(example);
        return users;
    }


}
