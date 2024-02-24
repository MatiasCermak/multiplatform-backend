package com.ubp.streamingmultiplatform.repository;

import com.ubp.streamingmultiplatform.connector.beans.dto.PartnerBalanceDTO;
import com.ubp.streamingmultiplatform.model.PartnerDTO;
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

    public PartnerUserDTO create_user_partner(Integer partnerId, Integer userId, String transactionId) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("partner_id", partnerId)
                .addValue("user_id", userId)
                .addValue("transaction_id", transactionId);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("create_user_partner")
                .withSchemaName("dbo")
                .returningResultSet("partners",
                        BeanPropertyRowMapper.newInstance(PartnerUserDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<PartnerUserDTO>) out.get("partners")).get(0);
    }

    public PartnerUserDTO activate_user_partner(Integer partnerId, Integer userId, String identity_key, String type, Float fee) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("partner_id", partnerId)
                .addValue("user_id", userId)
                .addValue("identity_key", identity_key)
                .addValue("type", type)
                .addValue("fee", fee);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("activate_user_partner")
                .withSchemaName("dbo")
                .returningResultSet("partners",
                        BeanPropertyRowMapper.newInstance(PartnerUserDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<PartnerUserDTO>) out.get("partners")).get(0);
    }

    public PartnerUserDTO deactivate_user_partner(Integer partnerId, Integer userId) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("partner_id", partnerId)
                .addValue("user_id", userId);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("deactivate_user_partner")
                .withSchemaName("dbo")
                .returningResultSet("partners",
                        BeanPropertyRowMapper.newInstance(PartnerUserDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<PartnerUserDTO>) out.get("partners")).get(0);
    }

    public PartnerUserDTO retrieve_user_partner(Integer partnerId, Integer userId) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("partner_id", partnerId)
                .addValue("user_id", userId);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("retrieve_user_partner")
                .withSchemaName("dbo")
                .returningResultSet("partners",
                        BeanPropertyRowMapper.newInstance(PartnerUserDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<PartnerUserDTO>) out.get("partners")).get(0);
    }

    public PartnerUserDTO retrieve_user_partner_by_transaction_id(String transactionId) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("transaction_id", transactionId);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("retrieve_user_partner_by_transaction_id")
                .withSchemaName("dbo")
                .returningResultSet("partners",
                        BeanPropertyRowMapper.newInstance(PartnerUserDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<PartnerUserDTO>) out.get("partners")).get(0);
    }

    public PartnerBalanceDTO obtainPartnerBalance(Integer partner_id, Date dayFrom, Date dayTo) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("partner_id", partner_id)
                .addValue("day_from", dayFrom)
                .addValue("day_to", dayTo);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("obtain_partner_balance")
                .withSchemaName("dbo")
                .returningResultSet("partnerbalance",
                        BeanPropertyRowMapper.newInstance(PartnerBalanceDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return ((List<PartnerBalanceDTO>) out.get("partnerbalance")).get(0);
    }
}
