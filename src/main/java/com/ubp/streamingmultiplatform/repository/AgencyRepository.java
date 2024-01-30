package com.ubp.streamingmultiplatform.repository;

import com.ubp.streamingmultiplatform.model.AgencyDTO;
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
public class AgencyRepository {
    private final JdbcTemplate jdbcTpl;

    public List<AgencyDTO> list() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("list_agencies")
                .withSchemaName("dbo")
                .returningResultSet("agencies",
                        BeanPropertyRowMapper.newInstance(AgencyDTO.class));

        Map<String, Object> out = jdbcCall.execute();
        return (List<AgencyDTO>) out.get("agencies");
    }

    public AgencyDTO create(AgencyDTO agency) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("name", agency.getName())
                .addValue("protocol", agency.getProtocol())
                .addValue("url_service", agency.getUrl_service())
                .addValue("secret_token", agency.getSecret_token())
                .addValue("active", agency.getActive())
                .addValue("advertisement_plan_id", agency.getAdvertisement_plan_id());
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("create_agency")
                .withSchemaName("dbo")
                .returningResultSet("agencies",
                        BeanPropertyRowMapper.newInstance(AgencyDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<AgencyDTO>) out.get("agencies")).get(0);
    }

    public AgencyDTO update(Integer agency_id, AgencyDTO agency) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("agency_id", agency_id)
                .addValue("name", agency.getName())
                .addValue("protocol", agency.getProtocol())
                .addValue("url_service", agency.getUrl_service())
                .addValue("secret_token", agency.getSecret_token())
                .addValue("active", agency.getActive())
                .addValue("advertisement_plan_id", agency.getAdvertisement_plan_id());
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("update_agency")
                .withSchemaName("dbo")
                .returningResultSet("agencies",
                        BeanPropertyRowMapper.newInstance(AgencyDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<AgencyDTO>) out.get("agencies")).get(0);
    }

    public AgencyDTO retrieve(Integer agency_id) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("agency_id", agency_id);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("retrieve_agency")
                .withSchemaName("dbo")
                .returningResultSet("agencies",
                        BeanPropertyRowMapper.newInstance(AgencyDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<AgencyDTO>) out.get("agencies")).get(0);
    }

    public AgencyDTO delete(Integer agency_id) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("agency_id", agency_id);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("delete_agency")
                .withSchemaName("dbo")
                .returningResultSet("agencies",
                        BeanPropertyRowMapper.newInstance(AgencyDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<AgencyDTO>) out.get("agencies")).get(0);
    }
}
