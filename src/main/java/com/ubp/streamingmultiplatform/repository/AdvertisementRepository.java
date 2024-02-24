package com.ubp.streamingmultiplatform.repository;

import com.ubp.streamingmultiplatform.connector.beans.dto.AdvertisementClickDTO;
import com.ubp.streamingmultiplatform.model.AdvertisementDTO;
import com.ubp.streamingmultiplatform.model.AdvertisementPlanDTO;
import com.ubp.streamingmultiplatform.model.ClickDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AdvertisementRepository {
    private final JdbcTemplate jdbcTpl;

    public List<AdvertisementDTO> list() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("list_advertisements")
                .withSchemaName("dbo")
                .returningResultSet("advertisements",
                        BeanPropertyRowMapper.newInstance(AdvertisementDTO.class));

        Map<String, Object> out = jdbcCall.execute();
        return (List<AdvertisementDTO>) out.get("advertisements");
    }

    public AdvertisementDTO create(AdvertisementDTO advertisement) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("agency_id", advertisement.getAgency_id())
                .addValue("file_name", advertisement.getFile_name())
                .addValue("url_image", advertisement.getUrl_image())
                .addValue("status", advertisement.getStatus())
                .addValue("provided_url", advertisement.getProvided_url())
                .addValue("start_date", advertisement.getStart_date())
                .addValue("end_date", advertisement.getEnd_date());
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("create_advertisement")
                .withSchemaName("dbo")
                .returningResultSet("advertisements",
                        BeanPropertyRowMapper.newInstance(AdvertisementDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<AdvertisementDTO>) out.get("advertisements")).get(0);
    }

    public AdvertisementDTO update(Integer advertisement_id, AdvertisementDTO advertisement) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("advertisement_id", advertisement_id)
                .addValue("agency_id", advertisement.getAgency_id())
                .addValue("file_name", advertisement.getFile_name())
                .addValue("url_image", advertisement.getUrl_image())
                .addValue("status", advertisement.getStatus())
                .addValue("provided_url", advertisement.getProvided_url())
                .addValue("start_date", advertisement.getStart_date())
                .addValue("end_date", advertisement.getEnd_date());
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("update_advertisement")
                .withSchemaName("dbo")
                .returningResultSet("advertisements",
                        BeanPropertyRowMapper.newInstance(AdvertisementDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<AdvertisementDTO>) out.get("advertisements")).get(0);
    }

    public AdvertisementDTO retrieve(Integer advertisement_id) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("advertisement_id", advertisement_id);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("retrieve_advertisement")
                .withSchemaName("dbo")
                .returningResultSet("advertisements",
                        BeanPropertyRowMapper.newInstance(AdvertisementDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<AdvertisementDTO>) out.get("advertisements")).get(0);
    }

    public AdvertisementDTO delete(Integer advertisement_id) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("advertisement_id", advertisement_id);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("delete_advertisement")
                .withSchemaName("dbo")
                .returningResultSet("advertisements",
                        BeanPropertyRowMapper.newInstance(AdvertisementDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<AdvertisementDTO>) out.get("advertisements")).get(0);
    }

    public List<AdvertisementDTO> serve(Integer userId, String pageType) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("user_id", userId)
                .addValue("page_type", pageType);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("serve_advertisements")
                .withSchemaName("dbo")
                .returningResultSet("advertisements",
                        BeanPropertyRowMapper.newInstance(AdvertisementDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<AdvertisementDTO>) out.get("advertisements"));
    }

    public List<AdvertisementDTO> obtainInitializedAdvertisementsById(Integer agencyId) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("agency_id", agencyId);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("obtain_initialized_advertisements_by_agency_id")
                .withSchemaName("dbo")
                .returningResultSet("advertisements",
                        BeanPropertyRowMapper.newInstance(AdvertisementDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<AdvertisementDTO>) out.get("advertisements"));
    }

    public AdvertisementDTO activateAdvertisement(Integer advertisement_id, String provided_url, String url_image) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("advertisement_id", advertisement_id)
                .addValue("provided_url", provided_url)
                .addValue("url_image", url_image);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("activate_advertisement")
                .withSchemaName("dbo")
                .returningResultSet("advertisements",
                        BeanPropertyRowMapper.newInstance(AdvertisementDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<AdvertisementDTO>) out.get("advertisements")).get(0);
    }

    public ClickDTO createAdvertisementClick(Integer userId, Integer advertisementId) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("user_id", userId)
                .addValue("advertisement_id", advertisementId)
                .addValue("content_id", null)
                .addValue("type", "ADVERTISEMENT");
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("create_click")
                .withSchemaName("dbo")
                .returningResultSet("clicks",
                        BeanPropertyRowMapper.newInstance(ClickDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<ClickDTO>) out.get("clicks")).get(0);
    }

    public List<AdvertisementClickDTO> obtainWeeklyClicksByAgency(Integer agency_id, Date week_from, Date week_to) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("agency_id", agency_id)
                .addValue("week_from", week_from)
                .addValue("week_to", week_to);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("obtain_weekly_clicks_by_agency")
                .withSchemaName("dbo")
                .returningResultSet("clicks",
                        BeanPropertyRowMapper.newInstance(AdvertisementClickDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<AdvertisementClickDTO>) out.get("clicks"));
    }
}
