package com.ihomefnt.o2o.intf.manager.util.common.bean;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class StringListTypeHandler implements TypeHandler<List<String>> {

    @Override
    public List<String> getResult(ResultSet arg0, String arg1) throws SQLException {
        String columnValue = arg0.getString(arg1);
        return this.getStringArray(columnValue);
    }

    @Override
    public List<String> getResult(ResultSet arg0, int arg1) throws SQLException {
        String columnValue = arg0.getString(arg1);
        return this.getStringArray(columnValue);
    }

    @Override
    public List<String> getResult(CallableStatement arg0, int arg1) throws SQLException {
        String columnValue = arg0.getString(arg1);
        return this.getStringArray(columnValue);
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType)
            throws SQLException {
        if (parameter == null)
            ps.setNull(i, Types.VARCHAR);
        else {
            StringBuffer result = new StringBuffer();
            for (String value : parameter)
                result.append(value).append(",");
            result.deleteCharAt(result.length() - 1);
            ps.setString(i, result.toString());
        }

    }

    private List<String> getStringArray(String columnValue) {
        if (columnValue == null)
            return null;
        List<String> list = new ArrayList<String>();
        String[] s = columnValue.split(",");
        for (int i = 0; i < s.length; i++) {
            list.add(s[i]);
        }
        return list;
    }

}
