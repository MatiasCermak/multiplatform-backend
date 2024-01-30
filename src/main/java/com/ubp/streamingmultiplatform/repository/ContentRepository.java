package com.ubp.streamingmultiplatform.repository;

import com.ubp.streamingmultiplatform.model.ContentServeDTO;
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
public class ContentRepository {
    private final JdbcTemplate jdbcTpl;

    public List<ContentServeDTO> serve_content_new_entries(Integer userId) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("user_id", userId);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("serve_content_new_entries")
                .withSchemaName("dbo")
                .returningResultSet("contents",
                        BeanPropertyRowMapper.newInstance(ContentServeDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<ContentServeDTO>) out.get("contents"));
    }

    public List<ContentServeDTO> serve_content_promoted(Integer userId) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("user_id", userId);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("serve_content_promoted")
                .withSchemaName("dbo")
                .returningResultSet("contents",
                        BeanPropertyRowMapper.newInstance(ContentServeDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<ContentServeDTO>) out.get("contents"));
    }

    public List<ContentServeDTO> serve_content_most_watched(Integer userId) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("user_id", userId);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("serve_content_most_watched")
                .withSchemaName("dbo")
                .returningResultSet("contents",
                        BeanPropertyRowMapper.newInstance(ContentServeDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<ContentServeDTO>) out.get("contents"));
    }

    public ContentServeDTO retrieve_content(Integer contentId) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("content_id", contentId);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("retrieve_content")
                .withSchemaName("dbo")
                .returningResultSet("contents",
                        BeanPropertyRowMapper.newInstance(ContentServeDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<ContentServeDTO>) out.get("contents")).get(0);
    }

    public List<ContentServeDTO> filter_content(Integer userId, String query) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("query", query)
                .addValue("user_id", userId);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("filter_content")
                .withSchemaName("dbo")
                .returningResultSet("contents",
                        BeanPropertyRowMapper.newInstance(ContentServeDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<ContentServeDTO>) out.get("contents"));
    }
}
