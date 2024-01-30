package com.ubp.streamingmultiplatform.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ubp.streamingmultiplatform.model.UserDTO;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final JdbcTemplate jdbcTpl;

    public List<UserDTO> getUserByEmail(String email) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("email", email);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("get_user_by_email")
                .withSchemaName("dbo")
                .returningResultSet("users",
                        BeanPropertyRowMapper.newInstance(UserDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return (List<UserDTO>) out.get("users");
    }

    public UserDTO create(UserDTO user) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("email", user.getEmail())
                .addValue("name", user.getName())
                .addValue("last_name", user.getLast_name())
                .addValue("password", user.getPassword())
                .addValue("role_id", user.getRole_id())
                .addValue("agency_id", user.getAgency_id())
                .addValue("verified_at", user.getVerified_at());

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("create_user")
                .withSchemaName("dbo")
                .returningResultSet("users",
                        BeanPropertyRowMapper.newInstance(UserDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<UserDTO>) out.get("users")).get(0);
    }
}