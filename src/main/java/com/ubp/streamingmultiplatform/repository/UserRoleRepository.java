package com.ubp.streamingmultiplatform.repository;

import com.ubp.streamingmultiplatform.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class UserRoleRepository {
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

    public UserDTO save(UserDTO user) {
        return new UserDTO();
    }
}