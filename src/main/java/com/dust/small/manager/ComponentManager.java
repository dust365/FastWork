package com.dust.small.manager;

import com.dust.small.entity.ComponentInfo;
import com.dust.small.utils.CollectionUtils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理不同表格数据
 */
public class ComponentManager {

    private ComponentManager() {
    }

    private static ComponentManager INSTANCE = new ComponentManager();

    public static ComponentManager getInstance() {
        return INSTANCE;
    }

    private ArrayList<ComponentInfo> totalData = new ArrayList<>();
    private Map<String, ArrayList<ComponentInfo>> tableData = new HashMap<>();
    private String configFilePath;
    private int selectDelRow = -1;

    public void addTableWithName(String name, ArrayList<ComponentInfo> data) {
        tableData.put(name, data);
    }

    public ArrayList<ComponentInfo> getTableByName(String name) {
        return tableData.get(name);
    }

    public void setTotalData(List<ComponentInfo> componentInfoList) {
        totalData.clear();
        if (!CollectionUtils.isEmpty(componentInfoList)) {
            totalData.addAll(componentInfoList);
        }
    }

    public ArrayList<ComponentInfo> getTotalData() {
        return totalData;
    }

    public void setConfigFilePath(String path) {
        this.configFilePath = path;
    }

    public String getConfigFilePath() {
        return configFilePath;
    }

    public int getSelectDelRow() {
        return selectDelRow;
    }

    public void setSelectDelRow(int selectDelRow) {
        this.selectDelRow = selectDelRow;
    }
}
