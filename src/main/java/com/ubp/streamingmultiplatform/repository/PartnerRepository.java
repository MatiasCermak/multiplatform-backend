package com.ubp.streamingmultiplatform.repository;

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
public class PartnerRepository {
    private final JdbcTemplate jdbcTpl;

    public List<PartnerDTO> list() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("list_partners")
                .withSchemaName("dbo")
                .returningResultSet("partners",
                        BeanPropertyRowMapper.newInstance(PartnerDTO.class));

        Map<String, Object> out = jdbcCall.execute();
        return (List<PartnerDTO>) out.get("partners");
    }

    public PartnerDTO create(PartnerDTO partner) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("name", partner.getName())
                .addValue("protocol", partner.getProtocol())
                .addValue("url_service", partner.getUrl_service())
                .addValue("login_fee", partner.getLogin_fee())
                .addValue("register_fee", partner.getRegister_fee())
                .addValue("secret_token", partner.getSecret_token())
                .addValue("active", partner.getActive());
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("create_partner")
                .withSchemaName("dbo")
                .returningResultSet("partners",
                        BeanPropertyRowMapper.newInstance(PartnerDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<PartnerDTO>) out.get("partners")).get(0);
    }

    public PartnerDTO update(Integer partner_id, PartnerDTO partner) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("partner_id", partner_id)
                .addValue("name", partner.getName())
                .addValue("protocol", partner.getProtocol())
                .addValue("url_service", partner.getUrl_service())
                .addValue("login_fee", partner.getLogin_fee())
                .addValue("register_fee", partner.getRegister_fee())
                .addValue("secret_token", partner.getSecret_token())
                .addValue("active", partner.getActive());
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("update_partner")
                .withSchemaName("dbo")
                .returningResultSet("partners",
                        BeanPropertyRowMapper.newInstance(PartnerDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<PartnerDTO>) out.get("partners")).get(0);
    }

    public PartnerDTO retrieve(Integer partnerId) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("partner_id", partnerId);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("select_partner")
                .withSchemaName("dbo")
                .returningResultSet("partners",
                        BeanPropertyRowMapper.newInstance(PartnerDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<PartnerDTO>) out.get("partners")).get(0);
    }

    public PartnerDTO delete(Integer partnerId) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("partner_id", partnerId);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("delete_partners")
                .withSchemaName("dbo")
                .returningResultSet("partners",
                        BeanPropertyRowMapper.newInstance(PartnerDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<PartnerDTO>) out.get("partners")).get(0);
    }
}
