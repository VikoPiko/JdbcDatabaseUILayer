package com.ru.mag.db.jdbc.queries;

import com.ru.mag.db.jdbc.models.Agent;
import com.ru.mag.db.jdbc.util.DBUtil;
import com.ru.mag.db.jdbc.util.DatabaseConnection;
import javafx.fxml.FXML;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.ru.mag.db.jdbc.util.DBUtil.SELECT_AGENT_BY_ID_QUERY;

public class AgentQueries {

    // -----------------------------
    // CRUD METHODS: AGENTS
    // -----------------------------

    public ResultSet getAllAgentsCommand(){
        try{
            PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement("Select * from Agent");
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

    private static final String CREATE_PERSON_COMMAND =
            "INSERT INTO Person(first_name,last_name,email,phone_number) VALUES (?,?,?,?)";

    private static final String CREATE_AGENT_COMMAND =
            "INSERT INTO Agent(person_id,salary,hire_date) VALUES (?,?,?)";

    //TODO FINISH
    public int createAgent(Agent a) throws SQLException {
        try {
            Connection conn = DatabaseConnection.getConnection();

            PreparedStatement ps = conn.prepareStatement(CREATE_PERSON_COMMAND, new String[]{"person_id"});
            ps.setString(1, a.getFirstName());
            ps.setString(2, a.getLastName());
            ps.setString(3, a.getEmail());
            ps.setString(4, a.getPhoneNumber());
            ps.executeUpdate();

            int temp;

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                a.setId(id);

                PreparedStatement ps2 = conn.prepareStatement(CREATE_AGENT_COMMAND);
                ps2.setInt(1, id);
                ps2.setDouble(2, a.getSalary());
                ps2.setDate(3, new java.sql.Date(a.getHireDate().getTime()));
                return ps2.executeUpdate();
            }
            return 0;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public ResultSet getTopAgents(double minPrice) throws SQLException {
        String sql =
                "SELECT DISTINCT p.first_name, p.last_name, s.final_price " +
                        "FROM Successful_Deals s " +
                        "JOIN Agent a ON s.agent_id = a.person_id " +
                        "JOIN Person p ON a.person_id = p.person_id " +
                        "WHERE s.final_price > ?";

        PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
        ps.setDouble(1, minPrice);
        return ps.executeQuery();
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
