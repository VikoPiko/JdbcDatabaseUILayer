package com.ru.mag.db.jdbc.queries;

import com.ru.mag.db.jdbc.util.DBUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AgentQueries {

    public ResultSet getAllAgentsCommand(){
        try{
            PreparedStatement statement = DBUtil.getInstance().getConnection().prepareStatement("Select * from Agent");
            return statement.executeQuery();
        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

}
