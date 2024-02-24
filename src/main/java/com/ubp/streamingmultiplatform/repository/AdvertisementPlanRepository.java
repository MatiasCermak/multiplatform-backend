package com.ubp.streamingmultiplatform.repository;

import com.ubp.streamingmultiplatform.model.AdvertisementPlanDTO;
import com.ubp.streamingmultiplatform.model.PartnerDTO;
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
public class AdvertisementPlanRepository {
    private final JdbcTemplate jdbcTpl;

    public List<AdvertisementPlanDTO> list() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("list_advertisement_plans")
                .withSchemaName("dbo")
                .returningResultSet("advertisement_plans",
                        BeanPropertyRowMapper.newInstance(AdvertisementPlanDTO.class));

        Map<String, Object> out = jdbcCall.execute();
        return (List<AdvertisementPlanDTO>) out.get("advertisement_plans");
    }

    public AdvertisementPlanDTO create(AdvertisementPlanDTO advertisementPlan) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("name", advertisementPlan.getName())
                .addValue("description", advertisementPlan.getDescription())
                .addValue("features", advertisementPlan.getFeatures())
                .addValue("price", advertisementPlan.getPrice());
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("create_advertisement_plan")
                .withSchemaName("dbo")
                .returningResultSet("advertisement_plans",
                        BeanPropertyRowMapper.newInstance(AdvertisementPlanDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<AdvertisementPlanDTO>) out.get("advertisement_plans")).get(0);
    }

    public AdvertisementPlanDTO update(Integer advertisement_plan_id, AdvertisementPlanDTO advertisementPlan) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("advertisement_plan_id", advertisement_plan_id)
                .addValue("name", advertisementPlan.getName())
                .addValue("description", advertisementPlan.getDescription())
                .addValue("features", advertisementPlan.getFeatures())
                .addValue("price", advertisementPlan.getPrice());
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("update_advertisement_plan")
                .withSchemaName("dbo")
                .returningResultSet("advertisement_plans",
                        BeanPropertyRowMapper.newInstance(AdvertisementPlanDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<AdvertisementPlanDTO>) out.get("advertisement_plans")).get(0);
    }

    public AdvertisementPlanDTO retrieve(Integer advertisement_plan_id) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("advertisement_plan_id", advertisement_plan_id);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("retrieve_advertisement_plan")
                .withSchemaName("dbo")
                .returningResultSet("advertisement_plans",
                        BeanPropertyRowMapper.newInstance(AdvertisementPlanDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<AdvertisementPlanDTO>) out.get("advertisement_plans")).get(0);
    }

    public AdvertisementPlanDTO delete(Integer advertisement_plan_id) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("advertisement_plan_id", advertisement_plan_id);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("delete_advertisement_plan")
                .withSchemaName("dbo")
                .returningResultSet("advertisement_plans",
                        BeanPropertyRowMapper.newInstance(AdvertisementPlanDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<AdvertisementPlanDTO>) out.get("advertisement_plans")).get(0);
    }
}
