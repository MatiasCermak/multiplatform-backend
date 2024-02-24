package com.ubp.streamingmultiplatform.repository;

import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.ubp.streamingmultiplatform.connector.beans.dto.AdvertisementClickDTO;
import com.ubp.streamingmultiplatform.connector.beans.dto.ContentsDTO;
import com.ubp.streamingmultiplatform.connector.beans.dto.PartnerClicksDTO;
import com.ubp.streamingmultiplatform.model.ClickDTO;
import com.ubp.streamingmultiplatform.model.ContentPartnerDTO;
import com.ubp.streamingmultiplatform.model.ContentServeDTO;
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

    public ContentServeDTO retrieve_content(Integer contentId, Integer userId) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("content_id", contentId)
                .addValue("user_id", userId);
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

    public ClickDTO createAdvertisementClick(Integer userId, Integer contentId) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("user_id", userId)
                .addValue("advertisement_id", null)
                .addValue("content_id", contentId)
                .addValue("type", "CONTENT");
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("create_click")
                .withSchemaName("dbo")
                .returningResultSet("clicks",
                        BeanPropertyRowMapper.newInstance(ClickDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<ClickDTO>) out.get("clicks")).get(0);
    }

    public void createVisualization(Integer userId, Integer contentId, Integer partnerId) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("user_id", userId)
                .addValue("content_id", contentId)
                .addValue("partner_id", partnerId);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("create_visualization")
                .withSchemaName("dbo");

        Map<String, Object> out = jdbcCall.execute(in);
    }

    public ContentServeDTO createContent(ContentsDTO contents) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("eidr_number", contents.getEidr_number())
                .addValue("name", contents.getName())
                .addValue("actors", contents.getActors())
                .addValue("genre", contents.getGenre())
                .addValue("director", contents.getDirector())
                .addValue("year", contents.getYear())
                .addValue("image_url", contents.getImage_url());
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("create_content")
                .withSchemaName("dbo")
                .returningResultSet("content",
                        BeanPropertyRowMapper.newInstance(ContentServeDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<ContentServeDTO>) out.get("content")).get(0);
    }

    public ContentServeDTO retrieveContentByEidr(String eidr_number) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("eidr_number", eidr_number);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("retrieve_content_by_eidr")
                .withSchemaName("dbo")
                .returningResultSet("content",
                        BeanPropertyRowMapper.newInstance(ContentServeDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);

        if(out.isEmpty()) {
            return  null;
        }

        return ((List<ContentServeDTO>) out.get("content")).get(0);
    }

    public ContentPartnerDTO linkContentToPartner(Integer content_id, Integer partner_id, Boolean recently_added, Boolean promoted) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("content_id", content_id)
                .addValue("partner_id", partner_id)
                .addValue("recently_added", recently_added)
                .addValue("promoted", promoted);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("link_content_to_partner")
                .withSchemaName("dbo")
                .returningResultSet("content",
                        BeanPropertyRowMapper.newInstance(ContentPartnerDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);

        if(out.isEmpty()) {
            return  null;
        }

        return ((List<ContentPartnerDTO>) out.get("content")).get(0);
    }

    public void dumpContent() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("dump_content_partners")
                .withSchemaName("dbo");

        jdbcCall.execute();
    }

    public List<PartnerClicksDTO> obtainWeeklyClicksByPartner(Integer partner_id, Date week_from, Date week_to) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("partner_id", partner_id)
                .addValue("week_from", week_from)
                .addValue("week_to", week_to);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("obtain_weekly_clicks_by_partner")
                .withSchemaName("dbo")
                .returningResultSet("clicks",
                        BeanPropertyRowMapper.newInstance(PartnerClicksDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<PartnerClicksDTO>) out.get("clicks"));
    }
}
