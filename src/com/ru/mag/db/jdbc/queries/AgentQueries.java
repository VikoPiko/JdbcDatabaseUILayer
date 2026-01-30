package com.ru.mag.db.jdbc.queries;

import com.ru.mag.db.jdbc.models.Agent;
import com.ru.mag.db.jdbc.util.DBUtil;
import com.ru.mag.db.jdbc.util.DatabaseConnection;
import javafx.fxml.FXML;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.ru.mag.db.jdbc.util.DBUtil.SELECT_AGENT_BY_ID_QUERY;

public class AgentQueries {

    public DBUtil connection = (DBUtil) DBUtil.getInstance();

    // -----------------------------
    // CRUD METHODS: AGENTS
    // -----------------------------

    public ResultSet getAllAgentsCommand(){
        try{
            PreparedStatement statement = connection.getConnection().prepareStatement("Select * from Agent");
            return statement.executeQuery();
        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getAgentByIdCommand(int id){
        try{
            PreparedStatement statement = DBUtil.getInstance().getConnection().prepareStatement(SELECT_AGENT_BY_ID_QUERY);
            statement.setInt(1, id);

            return statement.executeQuery();
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static final String CREATE_AGENT_COMMAND =
            "Insert into Agent (first_name,last_name,email,phone_number) VALUES (?,?,?,?)";

    public int createAgentCommand(Agent agent) throws SQLException {
        try{
            PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(CREATE_AGENT_COMMAND);

            statement.setString(1,  agent.getFirstName());
            statement.setString(2, agent.getLastName());
            statement.setString(3, agent.getEmail());
            statement.setString(4, agent.getPhoneNumber());

//            statement.setString(5, "");

            return statement.executeUpdate();
        } catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public static final String DELETE_AGENT_COMMAND =
            "Delete Agent where person_id = ?";

    public int deleteAgentCommand(Agent agent) throws SQLException {
        try{
            PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(DELETE_AGENT_COMMAND);
            statement.setInt(1, agent.getId());
            return statement.executeUpdate();
        } catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public static final String UPDATE_AGENT_COMMAND =
            "Update Agent set salary = ? where person_id = ?";

    public int updateAgentCommand(Agent agent) throws SQLException {
        try{
            PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(UPDATE_AGENT_COMMAND);
            statement.setDouble(1, agent.getSalary());
            statement.setInt(2, agent.getId());
            return statement.executeUpdate();
        } catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

}
