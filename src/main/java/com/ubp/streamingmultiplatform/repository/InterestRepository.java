package com.ubp.streamingmultiplatform.repository;

import com.ubp.streamingmultiplatform.model.AdvertisementInterestDTO;
import com.ubp.streamingmultiplatform.model.InterestDTO;
import com.ubp.streamingmultiplatform.model.InterestUserDTO;
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
public class InterestRepository {
    private final JdbcTemplate jdbcTpl;

    public List<InterestDTO> list() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("list_interests")
                .withSchemaName("dbo")
                .returningResultSet("interests",
                        BeanPropertyRowMapper.newInstance(InterestDTO.class));

        Map<String, Object> out = jdbcCall.execute();
        return (List<InterestDTO>) out.get("interests");
    }

    public InterestUserDTO createInterestUser(Integer interest_id, Integer user_id) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("interest_id", interest_id)
                .addValue("user_id", user_id);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("create_interest_user")
                .withSchemaName("dbo")
                .returningResultSet("interestsUsers",
                        BeanPropertyRowMapper.newInstance(InterestUserDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return  ((List<InterestUserDTO>) out.get("interestsUsers")).get(0);
    }

    public AdvertisementInterestDTO createAdvertisementInterest(Integer interest_id, Integer advertisement_id) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("interest_id", interest_id)
                .addValue("advertisement_id", advertisement_id);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("create_advertisement_interest")
                .withSchemaName("dbo")
                .returningResultSet("advertisementsInterests",
                        BeanPropertyRowMapper.newInstance(AdvertisementInterestDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return  ((List<AdvertisementInterestDTO>) out.get("advertisementsInterests")).get(0);
    }

    public List<InterestDTO> retrieveAdvertisementInterests(Integer advertisement_id) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("advertisement_id", advertisement_id);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("retrieve_advertisement_interests")
                .withSchemaName("dbo")
                .returningResultSet("advertisementsInterests",
                        BeanPropertyRowMapper.newInstance(InterestDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return  (List<InterestDTO>) out.get("advertisementsInterests");
    }

    public AdvertisementInterestDTO deleteAdvertisementInterests(Integer advertisement_id, Integer interest_id) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("advertisement_id", advertisement_id)
                .addValue("interest_id", interest_id);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("delete_advertisement_interest")
                .withSchemaName("dbo")
                .returningResultSet("advertisementsInterests",
                        BeanPropertyRowMapper.newInstance(AdvertisementInterestDTO.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return  ((List<AdvertisementInterestDTO>) out.get("advertisementsInterests")).get(0);
    }
}